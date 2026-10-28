package Lab1;
import java.util.ArrayList;
import java.util.List;
public class Customer {
    // customer fields
    private String customerId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private List<Account> accounts = new ArrayList<>();
// Constructor
    public Customer(String customerId, String fullName, String phoneNumber, String email) {
        this.customerId = customerId;
    // full name can not be null
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
// getters and  setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
// Accounts
    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public int getAccountCount() {
        return accounts.size();
    }
// toString
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts.size() +
                '}';
    }
}