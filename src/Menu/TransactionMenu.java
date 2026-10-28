package Menu;
import DAO.TransactionDAO;
import Exceptions.DuplicateTransactionException;
import Exceptions.InsufficientBalanceException;
import Exceptions.InvalidAccountException;
import Exceptions.InvalidAmountException;
import Lab1.Transaction;
import Service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class TransactionMenu {

    private final TransactionService service = new TransactionService();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public void show(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Transaction Management ---");
            System.out.println("1. Deposit money");
            System.out.println("2. Withdraw money");
            System.out.println("3. Transfer money");
            System.out.println("4. View transaction history");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> deposit(scanner);
                    case "2" -> withdraw(scanner);
                    case "3" -> transfer(scanner);
                    case "4" -> history(scanner);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: the amount must be a number.");
            }
            catch (InvalidAmountException | InsufficientBalanceException
                   | DuplicateTransactionException | InvalidAccountException e) {
            System.out.println("Transaction refused: " + e.getMessage());
            }
            catch (java.sql.SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void deposit(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        String account = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Reference ID: ");
        String reference = scanner.nextLine();

        service.deposit(account, amount, reference);
        System.out.println("Deposit successful.");
    }

    private void withdraw(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        String account = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Reference ID: ");
        String reference = scanner.nextLine();

        service.withdraw(account, amount, reference);
        System.out.println("Withdrawal successful.");
    }

    private void transfer(Scanner scanner) throws Exception {
        System.out.print("From account number: ");
        String from = scanner.nextLine();
        System.out.print("To account number: ");
        String to = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Reference ID: ");
        String reference = scanner.nextLine();

        service.transfer(from, to, amount, reference);
        System.out.println("Transfer successful.");
    }

    private void history(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        List<Transaction> list = transactionDAO.findByAccountId(scanner.nextLine());

        if (list.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }
        for (Transaction t : list) {
            System.out.println(t);
        }
    }
}