
package Service;
import DAO.ProcessedRequestDAO;
import DAO.AccountDAO;
import DAO.TransactionDAO;
import Exceptions.DuplicateTransactionException;
import Exceptions.InvalidAccountException;
import Lab1.Account;
import Lab1.Transaction;
import Lab1.TransactionType;
import Util.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionService {

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final ProcessedRequestDAO processedDAO = new ProcessedRequestDAO();

// deposit
    public void deposit(String accountId, double amount, String referenceId) throws SQLException {
        try (Connection conn = DBconnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                registerReference(conn, referenceId);

                Account account = loadAccount(conn, accountId);
                account.deposit(amount);                       // Lab 1 rules
                accountDAO.updateBalance(conn, accountId, account.getBalance());

                transactionDAO.save(conn,
                        new Transaction(referenceId, accountId, amount, TransactionType.Deposit));

                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }
// withdraw
    public void withdraw(String accountId, double amount, String referenceId) throws SQLException {
        try (Connection conn = DBconnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                registerReference(conn, referenceId);

                Account account = loadAccount(conn, accountId);
                account.withdraw(amount);   // Wallet: 50,000 minimum. Savings: fee.
                accountDAO.updateBalance(conn, accountId, account.getBalance());

                transactionDAO.save(conn,
                        new Transaction(referenceId, accountId, amount, TransactionType.withdrawal));

                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }
// transfer
    public void transfer(String fromId, String toId, double amount, String referenceId) throws SQLException {
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        try (Connection conn = DBconnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                registerReference(conn, referenceId);

                Account from = loadAccount(conn, fromId);
                Account to = loadAccount(conn, toId);

                from.withdraw(amount);
                to.deposit(amount);

                accountDAO.updateBalance(conn, fromId, from.getBalance());
                accountDAO.updateBalance(conn, toId, to.getBalance());

                // two records, same reference id (sender and receiver)
                transactionDAO.save(conn,
                        new Transaction(referenceId, fromId, amount, TransactionType.Transfer));
                transactionDAO.save(conn,
                        new Transaction(referenceId, toId, amount, TransactionType.Transfer));

                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Saves the reference id. If it already exists, the request is a duplicate.
    private void registerReference(Connection conn, String referenceId) throws SQLException {
        if (!processedDAO.save(conn, referenceId)) {
            throw new DuplicateTransactionException("Duplicate transaction: " + referenceId);
        }
    }

    private Account loadAccount(Connection conn, String accountId) throws SQLException {
        Account account = accountDAO.findById(conn, accountId);
        if (account == null) {
            throw new InvalidAccountException("Account not found: " + accountId);
        }
        return account;
    }
}

