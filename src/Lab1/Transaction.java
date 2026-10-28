package Lab1;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    // transaction fields
    private final String transactionId;
    private final String RefTransID;
    private final  double amount;
    private final   TransactionType transType;
    private final LocalDateTime timestamp;
    private final String accountNumber;

//// Constructor
//    public Transaction(String refTransID, double  amount, TransactionType transType, String  accountNumber) {
//        this.transactionId = UUID.randomUUID().toString();
//        this.RefTransID= refTransID;
//        this.amount = amount;
//        this.transType = transType;
//        this.accountNumber = accountNumber;
//        this.timestamp = LocalDateTime.now();
//    }

    public Transaction(String RefTransID, String accountNumber, double amount, TransactionType transType) {
        this.transactionId = UUID.randomUUID().toString();
        this.RefTransID= RefTransID;
        this.amount = amount;
        this.transType = transType;
        this.accountNumber = accountNumber;
        this.timestamp = LocalDateTime.now();

    }
    public Transaction(String transactionId, String RefTransID, String accountNumber,
                       double amount, TransactionType transType, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.RefTransID = RefTransID;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transType = transType;
        this.timestamp = timestamp;
    }

    // getters
    public String  getTransactionId() {
        return transactionId;
    }
    public String getRefTransID() {
        return RefTransID;
    }

    public double getAmount() {
        return amount;
    }
    public TransactionType getTransType() {
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
