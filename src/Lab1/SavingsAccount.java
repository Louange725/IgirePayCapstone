package Lab1;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountNumber, Customer accountholder, double balance) {
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
