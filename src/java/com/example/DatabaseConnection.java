package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/stock_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("Loading MySQL JDBC Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");

            System.out.println("Connecting to database...");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found! Ensure the MySQL Connector/J is added.");
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found!", e);
        } catch (SQLException e) {
            System.err.println("Database connection failed! Check your credentials and MySQL server status.");
            e.printStackTrace();
            throw new SQLException("Database connection failed!", e);
        }
    }
}

