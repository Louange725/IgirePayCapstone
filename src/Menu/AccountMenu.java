package Menu;
import DAO.AccountDAO;
import DAO.CustomerDAO;
import Lab1.Account;
import Lab1.Customer;
import Lab1.SavingsAccount;
import Lab1.WalletAccount;

import java.util.Scanner;

public class AccountMenu {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    public void show(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Account Management ---");
            System.out.println("1. Create wallet account");
            System.out.println("2. Create savings account");
            System.out.println("3. View account balance");
            System.out.println("4. Delete inactive account");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> createAccount(scanner, true);
                    case "2" -> createAccount(scanner, false);
                    case "3" -> viewBalance(scanner);
                    case "4" -> deleteInactive(scanner);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice, try again.");
                }
            }
            catch (java.sql.SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            }
            catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        }
    }

    // isWallet = true creates a WalletAccount, false creates a SavingsAccount
    private void createAccount(Scanner scanner, boolean isWallet) throws Exception {
        System.out.print("Customer ID: ");
        Customer customer = customerDAO.findById(scanner.nextLine());
        if (customer == null) {
            System.out.println("Customer not found. Register the customer first.");
            return;
        }

        System.out.print("New account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Opening balance: ");
        double balance = Double.parseDouble(scanner.nextLine());
        if (balance < 0) {
            System.out.println("Opening balance cannot be negative.");
            return;
        }

        Account account = isWallet
                ? new WalletAccount(accountNumber, customer, balance)
                : new SavingsAccount(accountNumber, customer, balance);

        accountDAO.createAccount(account);
        System.out.println((isWallet ? "Wallet" : "Savings") + " account created.");
    }

    private void viewBalance(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        Account account = accountDAO.findById(scanner.nextLine());

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.println("Balance: " + account.getBalance());
    }

    private void deleteInactive(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        boolean deleted = accountDAO.deleteInactive(scanner.nextLine());

        if (deleted) {
            System.out.println("Inactive account deleted.");
        } else {
            System.out.println("Not deleted. The account does not exist or its balance is not 0.");
        }
    }
}
