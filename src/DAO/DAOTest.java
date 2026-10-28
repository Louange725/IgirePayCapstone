package DAO;
import Lab1.*;

public class DAOTest {
    public static void main(String[] args) throws Exception {
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        ProcessedRequestDAO processedDAO = new ProcessedRequestDAO();

        // 1. Customer
        Customer c = new Customer("C211", "Louange", "098024080", "louae@mail.com");
        customerDAO.addCustomer(c);

        // 2. Accounts
        accountDAO.createAccount(new WalletAccount("ACC200", c, 100000));
        accountDAO.createAccount(new SavingsAccount("ACC201", c, 0));
        System.out.println("Accounts of C200: " + accountDAO.findByCustomerId("C200").size());

        // 3. Deposit: update balance + save transaction + save reference id
        Account wallet = accountDAO.findById("ACC200");
        wallet.deposit(5000);
        accountDAO.updateBalance("ACC200", wallet.getBalance());
        transactionDAO.save(new Transaction("REF200", "ACC200", 5000, TransactionType.Deposit));
        System.out.println("Saved REF200 (expect true): " + processedDAO.save("REF200"));
        System.out.println("New balance (expect 105000.0): " + accountDAO.findById("ACC200").getBalance());

        // 4. Duplicate reference id
        System.out.println("Exists REF200 (expect true): " + processedDAO.exists("REF200"));
        System.out.println("Saved REF200 again (expect false): " + processedDAO.save("REF200"));

        // 5. History
        transactionDAO.findByAccountId("ACC200").forEach(System.out::println);

        // 6. Delete inactive account (ACC201 has balance 0)
        System.out.println("Deleted ACC201 (expect true): " + accountDAO.deleteInactive("ACC201"));
        System.out.println("Deleted ACC200 (expect false, balance not 0): " + accountDAO.deleteInactive("ACC200"));
    }
}