package Lab1;

public class SavingsAccount extends Account {
    private double withdrawFee = 50;

    public SavingsAccount(String accountNumber, Customer accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (getBalance() < (amount + withdrawFee)) {
            throw new IllegalArgumentException("Insufficient balance(fee must be included ).");
        }
        super.withdraw(amount + withdrawFee);
    }
}
