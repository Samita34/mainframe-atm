package bo.edu.ucb.sis213;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/atm";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
 }

}