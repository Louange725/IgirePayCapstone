package Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        CustomerMenu customerMenu = new CustomerMenu();
        AccountMenu accountMenu = new AccountMenu();
        TransactionMenu transactionMenu = new TransactionMenu();
        ReportMenu reportMenu = new ReportMenu();



        while (running) {
            System.out.println("\n===== IGIREPAY =====");
            System.out.println("1. Customer Management");
            System.out.println("2. Account Management");
            System.out.println("3. Transaction Management");
            System.out.println("4. Reports");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> customerMenu.show(scanner);
                case "2" -> accountMenu.show(scanner);
                case "3" -> transactionMenu.show(scanner);
                case "4" -> reportMenu.show(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        System.out.println("Goodbye!");
    }
}