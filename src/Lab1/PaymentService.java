package Lab1;
import java.util.*;

    public class PaymentService {
        private final Map<String, Account> accounts = new HashMap<>();
        private final Map<String, List<Transaction>> transactionHistory = new HashMap<>();
        private final Set<String> processedReferenceIds = new HashSet<>();
        private final List<String> failedTransactions = new ArrayList<>();

        public void addAccount(Account account) {
            if (account == null) {
                throw new IllegalArgumentException("Account cannot be null");
            }
            accounts.put(account.getAccountNumber(), account);
            account.getAccountHolder().addAccount(account);
        }

        public Account getAccount(String accountNumber) {
            Account account = accounts.get(accountNumber);
            if (account == null) {
                throw new IllegalArgumentException("Account not found: " + accountNumber);
            }
            return account;
        }

        public void deposit(String accountNumber, double amount, String RefTransID) {
            // 1. Duplicate check
            if (processedReferenceIds.contains(RefTransID)) {
                failedTransactions.add(RefTransID + " - Duplicate request");
                throw new IllegalStateException("Duplicate transaction: " + RefTransID);
            }
            try {
                // 2. Find account and do the deposit
                Account account = getAccount(accountNumber);
                account.deposit(amount);

                // 3. Record the transaction
                Transaction t = new Transaction(RefTransID, accountNumber, amount, TransactionType.Deposit);
                transactionHistory.computeIfAbsent(accountNumber, k -> new ArrayList<>()).add(t);
                // 4. Mark as processed ONLY after success
                processedReferenceIds.add(RefTransID);
            } catch (RuntimeException e) {
                failedTransactions.add(RefTransID + " - " + e.getMessage());
                throw e;
            }
        }

        public List<Transaction> getHistory(String accountNumber) {
            List<Transaction> list = transactionHistory.get(accountNumber);
            if (list == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(list);
        }

        public List<String> getFailedTransactions() {
            return new ArrayList<>(failedTransactions);
        }
    }

