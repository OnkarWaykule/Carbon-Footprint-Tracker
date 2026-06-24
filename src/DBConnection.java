/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Standard connection endpoint pointing to your unified MySQL schema instance
    private static final String URL = "jdbc:mysql://localhost:3306/carbon_footprint_tracker";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; // <-- CHANGE THIS to your real MySQL root password!

    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load and register the modern MySQL driver class variant
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not located! Double-check your NetBeans Project Libraries.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}