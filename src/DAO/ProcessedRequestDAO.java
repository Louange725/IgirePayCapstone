package DAO;
import Util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ProcessedRequestDAO {
    // READ: was this reference id already processed?
    public boolean exists(String reference_id) throws SQLException {
        String sql = "SELECT 1 FROM processed_requests WHERE reference_id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reference_id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    // CREATE: save the reference id
    // Returns true if saved, false if it was already there (duplicate).
    public boolean save(String reference_id) throws SQLException {
        String sql = "INSERT INTO processed_requests (reference_id) VALUES (?) "
                + "ON CONFLICT (reference_id) DO NOTHING";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reference_id);
            return ps.executeUpdate() > 0;
        }
    }
}