package Menu;

import Service.PinService;

import java.util.Scanner;

public class PinMenu {

    private final PinService pinService = new PinService();

    public void show(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- PIN Management ---");
            System.out.println("1. Create PIN");
            System.out.println("2. Validate PIN");
            System.out.println("3. Change PIN");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> create(scanner);
                    case "2" -> validate(scanner);
                    case "3" -> change(scanner);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void create(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        String account = scanner.nextLine();
        System.out.print("New PIN (4 digits): ");
        pinService.createPin(account, scanner.nextLine());
        System.out.println("PIN created.");
    }

    private void validate(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        String account = scanner.nextLine();
        System.out.print("PIN: ");
        boolean ok = pinService.validatePin(account, scanner.nextLine());
        System.out.println(ok ? "PIN is correct." : "PIN is wrong.");
    }

    private void change(Scanner scanner) throws Exception {
        System.out.print("Account number: ");
        String account = scanner.nextLine();
        System.out.print("Old PIN: ");
        String oldPin = scanner.nextLine();
        System.out.print("New PIN (4 digits): ");
        String newPin = scanner.nextLine();
        pinService.changePin(account, oldPin, newPin);
        System.out.println("PIN changed.");
    }
}