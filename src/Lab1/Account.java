package Lab1;
public class Account {
    private String accountNumber;
    private Customer accountHolder;
    private double balance;
// Constructor
    public Account(String accountNumber, Customer accountholder, double balance) {
        this.accountNumber = accountNumber;
        if (accountHolder == null) {
            throw new IllegalArgumentException("Account holder cannot be null");
        }
        this.accountHolder = accountholder;
        this.balance = balance;
    }
// getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Customer getAccountholder() {
        return accountHolder;
    }

    public void setAccountholder(Customer accountholder) {
        this.accountHolder = accountholder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

// withdraw method
    public void withdraw(double amount) {
        if( balance >= amount ) {
            balance -= amount;
        }
        else {
            System.out.println("Insufficient balance");
        }

    }
// deposit method
    public void deposit(double amount) {
        balance += amount;
    }
// tostring
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountholder='" + accountHolder.getFullName() + '\'' +
                ", balance=" + balance +
                '}';
    }
}
