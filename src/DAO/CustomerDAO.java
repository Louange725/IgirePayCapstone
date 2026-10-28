package DAO;
import Lab1.Customer;
import Util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // CREATE: add a new customer
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (customer_id, full_name, email, phone_number) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getCustomerId());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.executeUpdate();
        }
    }

    // READ: find one customer by id (returns null if not found)
    public Customer findById(String customer_id) throws SQLException {
        String sql = "SELECT customer_id, full_name, email, phone_number FROM customers WHERE customer_id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getString("customer_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"));
                }
            }
        }
        return null;
    }

    // READ: get all customers
    public List<Customer> findAll() throws SQLException {
        String sql = "SELECT customer_id, full_name, email, phone_number FROM customers";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("customer_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone_number")));
            }
        }
        return customers;
    }
    // UPDATE: change customer details
    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET full_name = ?, email = ?, phone_number = ? WHERE customer_id= ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getCustomerId());

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE: remove a customer
    public boolean deleteCustomer(String customer_id) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer_id);
            return ps.executeUpdate() > 0;
        }
    }

    // CUSTOM QUERY: find a customer by email
    public Customer findByEmail(String email) throws SQLException {
        String sql = "SELECT customer_id, full_name, email, phone_number FROM customers WHERE email = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getString("customer_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"));
                }
            }
        }
        return null;
    }
}
