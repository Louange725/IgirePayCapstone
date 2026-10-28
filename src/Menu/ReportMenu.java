package Menu;

import DAO.AccountDAO;
import DAO.TransactionDAO;
import Lab1.Account;
import Lab1.Transaction;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ReportMenu {

    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    public void show(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Export transaction history to CSV");
            System.out.println("2. Daily transaction summary");
            System.out.println("3. Customer transaction statement");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> exportCsv(scanner);
                    case "2" -> dailySummary(scanner);
                    case "3" -> customerStatement(scanner);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: use the date format yyyy-MM-dd, for example 2026-10-28.");
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // 1. Export one account's history to a CSV file
    private void exportCsv(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine();

        List<Transaction> list = transactionDAO.findByAccountId(accountNumber);
        if (list.isEmpty()) {
            System.out.println("No transactions to export.");
            return;
        }

        String fileName = "transactions_" + accountNumber + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("transaction_id,reference_id,account_number,type,amount,timestamp");
            for (Transaction t : list) {
                writer.println(t.getTransactionId() + "," + t.getRefTransID() + ","
                        + t.getAccountNumber() + "," + t.getTransType() + ","
                        + t.getAmount() + "," + t.getTimestamp());
            }
        }
        System.out.println("Exported " + list.size() + " transaction(s) to " + fileName);
    }

    // 2. Summary of one day
    private void dailySummary(Scanner scanner) throws Exception {
        System.out.print("Date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        List<String> lines = transactionDAO.dailySummary(date);
        if (lines.isEmpty()) {
            System.out.println("No transactions on " + date);
            return;
        }
        System.out.println("Summary for " + date + ":");
        for (String line : lines) {
            System.out.println("  " + line);
        }
    }

    // 3. All transactions of all accounts of one customer
    private void customerStatement(Scanner scanner) throws Exception {
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine();

        List<Account> accounts = accountDAO.findByCustomerId(customerId);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
            return;
        }

        System.out.println("Statement for customer " + customerId);
        for (Account account : accounts) {
            System.out.println("\nAccount " + account.getAccountNumber()
                    + " | balance: " + account.getBalance());
            List<Transaction> list = transactionDAO.findByAccountId(account.getAccountNumber());
            if (list.isEmpty()) {
                System.out.println("  (no transactions)");
            }
            for (Transaction t : list) {
                System.out.println("  " + t.getTimestamp() + " | " + t.getTransType()
                        + " | " + t.getAmount() + " | ref " + t.getRefTransID());
            }
        }
    }
}
