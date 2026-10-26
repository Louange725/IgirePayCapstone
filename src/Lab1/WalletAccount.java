package Lab1;

public class WalletAccount extends Account{
    public WalletAccount(String accountNumber, Customer accountholder, double balance) {
        super(accountNumber, accountholder, balance);
    }
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }
    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }
}
