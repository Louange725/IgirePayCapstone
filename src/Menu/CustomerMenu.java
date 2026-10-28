package Menu;

import DAO.AccountDAO;
import DAO.CustomerDAO;
import Lab1.Account;
import Lab1.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    public void show(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. Register customer");
            System.out.println("2. Update customer information");
            System.out.println("3. View customer accounts");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> register(scanner);
                    case "2" -> update(scanner);
                    case "3" -> viewAccounts(scanner);
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

    private void register(Scanner scanner) throws Exception {
        System.out.print("Customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Full name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine();

        customerDAO.addCustomer(new Customer(id, name, email, phone));
        System.out.println("Customer registered.");
    }

    private void update(Scanner scanner) throws Exception {
        System.out.print("Customer ID to update: ");
        String id = scanner.nextLine();

        Customer customer = customerDAO.findById(id);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("New full name: ");
        customer.setFullName(scanner.nextLine());
        System.out.print("New email: ");
        customer.setEmail(scanner.nextLine());
        System.out.print("New phone number: ");
        customer.setPhoneNumber(scanner.nextLine());

        customerDAO.updateCustomer(customer);
        System.out.println("Customer updated.");
    }

    private void viewAccounts(Scanner scanner) throws Exception {
        System.out.print("Customer ID: ");
        String id = scanner.nextLine();

        List<Account> accounts = accountDAO.findByCustomerId(id);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
            return;
        }
        for (Account a : accounts) {
            System.out.println(a);
        }
    }
}
