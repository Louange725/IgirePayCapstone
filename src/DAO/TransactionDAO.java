package DAO;
import Lab1.Transaction;
import Lab1.TransactionType;
import Util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
public class TransactionDAO {

    // CREATE
    public void save(Transaction t) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_id, account_id, reference_id, transaction_type, amount) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getTransactionId());
            ps.setString(2, t.getAccountNumber());
            ps.setString(3, t.getRefTransID());
            ps.setString(4, t.getTransType().name());
            ps.setDouble(5, t.getAmount());
            ps.executeUpdate();
        }
    }
    // READ: history of one account, newest first
    public List<Transaction> findByAccountId(String account_id) throws SQLException {
        String sql = "SELECT transaction_id, account_id, reference_id, transaction_type, amount, created_at "
                + "FROM transactions WHERE account_id = ? ORDER BY created_at DESC";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account_id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Transaction(
                            rs.getString("transaction_id"),
                            rs.getString("reference_id"),
                            rs.getString("account_id"),
                            rs.getDouble("amount"),
                            TransactionType.valueOf(rs.getString("transaction_type")),
                            rs.getTimestamp("created_at").toLocalDateTime()));
                }
            }
        }
        return list;
    }

    // CUSTOM QUERY: find transactions by reference id
    public List<Transaction> findByReferenceId(String referenceId) throws SQLException {
        String sql = "SELECT transaction_id, account_id, reference_id, transaction_type, amount, created_at "
                + "FROM transactions WHERE reference_id = ?";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, referenceId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Transaction(
                            rs.getString("reference_id"),
                            rs.getString("reference_id"),
                            rs.getString("account_id"),
                            rs.getDouble("amount"),
                            TransactionType.valueOf(rs.getString("transaction_type")),
                            rs.getTimestamp("created_at").toLocalDateTime()));
                }
            }
        }
        return list;
    }
    public void save(Connection conn, Transaction t) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_id, account_id, reference_id, transaction_type, amount) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getTransactionId());
            ps.setString(2, t.getAccountNumber());
            ps.setString(3, t.getRefTransID());
            ps.setString(4, t.getTransType().name());
            ps.setDouble(5, t.getAmount());
            ps.executeUpdate();
        }
    }

    public List<String> dailySummary(java.time.LocalDate date) throws SQLException {
        String sql = "SELECT transaction_type, COUNT(*) AS total_count, SUM(amount) AS total_amount "
                + "FROM transactions WHERE created_at::date = ? GROUP BY transaction_type";
        List<String> lines = new ArrayList<>();

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lines.add(rs.getString("transaction_type")
                            + ": " + rs.getInt("total_count") + " transaction(s), total "
                            + rs.getDouble("total_amount"));
                }
            }
        }
        return lines;
    }
}