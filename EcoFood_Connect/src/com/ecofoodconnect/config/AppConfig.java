/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author tanmay
 */

/**
 * AppConfig: Provides the configuration and connection setup for the database.
 * Ensure to update the database credentials and URL to match your environment.
 */
public class AppConfig {

//    // Database credentials and URL
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecofoodconnect"; // Update with your database name
//    private static final String DB_USERNAME = "root"; // Update with your database username
//    private static final String DB_PASSWORD = "Rutuja@28"; // Update with your database password
    
    // Database credentials and URL
    private static final String DB_URL = "jdbc:mysql://avnadmin:AVNS_SOe8jd35Vdb9Qfl2gui@ecofoodconnect-ecofoodconnect.e.aivencloud.com:15380/ecofoodconnect?ssl-mode=REQUIRED"; // Update with your database name
    private static final String DB_USERNAME = "avnadmin"; // Update with your database username
    private static final String DB_PASSWORD = "AVNS_SOe8jd35Vdb9Qfl2gui";

    // Initialize the database connection
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
            throw new SQLException("Unable to load JDBC driver.", e);
        }
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
