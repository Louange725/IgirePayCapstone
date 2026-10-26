package Lab1;
import static Lab1.TransactionType.Deposit;
import static Lab1.TransactionType.withdrawal;

public class Account {
    private String accountNumber;
    private Customer accountHolder;
    private double balance;
// Constructor
    public Account(String accountNumber, Customer accountholder, double balance) {
//        if (accountHolder == null) {
//            throw new IllegalArgumentException("Account holder cannot be null");
//        }
        this.accountNumber = accountNumber;

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

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Customer accountholder) {
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
        if( amount <= 0 ) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
       if (amount < balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
            balance -= amount;

    }
// deposit method
    public void deposit(double amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        balance += amount;
    }
// ProcessTransaction
public void processTransaction(Transaction transaction) {
    switch (transaction.getTransType()) {
        case Deposit -> deposit(transaction.getAmount());
        case withdrawal -> withdraw(transaction.getAmount());
        default -> throw new IllegalArgumentException("Unsupported transaction type");
    }
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
