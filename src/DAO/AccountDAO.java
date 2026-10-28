package DAO;
import Lab1.Account;
import Lab1.Customer;
import Lab1.SavingsAccount;
import Lab1.WalletAccount;
import Util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final CustomerDAO customerDAO = new CustomerDAO();

    // CREATE
    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_id, customer_id, account_type, balance) VALUES (?, ?, ?, ?)";

        String type = (account instanceof SavingsAccount) ? "SAVINGS" : "WALLET";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getAccountHolder().getCustomerId());
            ps.setString(3, type);
            ps.setDouble(4, account.getBalance());
            ps.executeUpdate();
        }
    }

    // READ: one account (returns null if not found)
    public Account findById(String account_id) throws SQLException {
        String sql = "SELECT account_id, customer_id, account_type, balance FROM accounts WHERE account_id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buildAccount(rs);
                }
            }
        }
        return null;
    }

    // READ: all accounts of one customer
    public List<Account> findByCustomerId(String customer_id) throws SQLException {
        String sql = "SELECT account_id, customer_id, account_type, balance FROM accounts WHERE customer_id = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer_id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(buildAccount(rs));
                }
            }
        }
        return accounts;
    }

    // UPDATE: set a new balance
    public boolean updateBalance(String account_id, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, newBalance);
            ps.setString(2, account_id);
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE: only if the account is inactive (balance = 0)
    public boolean deleteInactive(String account_id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id = ? AND balance = 0";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account_id);
            return ps.executeUpdate() > 0;
        }
    }

    // Helper: turns one database row into WalletAccount or SavingsAccount
    private Account buildAccount(ResultSet rs) throws SQLException {
        String id = rs.getString("account_id");
        String type = rs.getString("account_type");
        double balance = rs.getDouble("balance");
        Customer owner = customerDAO.findById(rs.getString("customer_id"));

        if ("SAVINGS".equals(type)) {
            return new SavingsAccount(id, owner, balance);
        }
        return new WalletAccount(id, owner, balance);
    }

    // Used inside a JDBC transaction: the connection comes from the service
    public Account findById(Connection conn, String id) throws SQLException {
        String sql = "SELECT account_id, customer_id, account_type, balance FROM accounts WHERE account_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buildAccount(rs);
                }
            }
        }
        return null;
    }
    public boolean updateBalance(Connection conn, String accountId, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setString(2, accountId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean savePin(String accountId, String pin) throws SQLException {
        String sql = "UPDATE accounts SET pin = ? WHERE account_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pin);
            ps.setString(2, accountId);
            return ps.executeUpdate() > 0;
        }
    }

    public String findPin(String accountId) throws SQLException {
        String sql = "SELECT pin FROM accounts WHERE account_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("pin");
                }
            }
        }
        return null;
    }
}

