package Lab1;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    // transaction fields
    private final String transactionId;
    private final int RefTransID;
    private final  double amount;
    private final String transType;
    private final LocalDateTime timestamp;
    private final String accountNumber;

// Constructor
    public Transaction( int refTransID, double amount, String transType, String accountNumber) {
        this.transactionId = UUID.randomUUID().toString();
        this.RefTransID= refTransID;
        this.amount = amount;
        this.transType = transType;
        this.accountNumber = accountNumber;
        this.timestamp = LocalDateTime.now();
    }

// getters
    public String  getTransactionId() {
        return transactionId;
    }
    public int getRefTransID() {
        return RefTransID;
    }

    public double getAmount() {
        return amount;
    }
    public String getTransType() {
        return transType;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
// tostring
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionId +
                ", RefTransID=" + RefTransID +
                ", amount=" + amount +
                ", transType='" + transType + '\'' +
                ", timestamp=" + timestamp +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
