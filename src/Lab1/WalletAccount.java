package Lab1;

public class WalletAccount extends Account{
    private double minBalance = 5000;
    public WalletAccount(String accountNumber, Customer accountHolder, double balance) {
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
        if (getBalance() - amount < minBalance) {
            throw new IllegalStateException(
                    "Withdrawal refused: at least " + minBalance + " must remain on the account");
        }
        super.withdraw(amount);
    }
}
