package Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/IgirePay_DB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "louange";

  public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}