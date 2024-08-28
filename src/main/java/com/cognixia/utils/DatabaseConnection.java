package com.cognixia.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "./resources/db.properties"; // Update the path
    private static String url;
    private static String user;
    private static String password;
    private static boolean propertiesLoaded = false;

    static {
        try (InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();

            // Load the properties file
            if (input == null) {
                System.out.println("Sorry, unable to find " + PROPERTIES_FILE);
            } else {
                prop.load(input);

                // Get properties values
                url = prop.getProperty("url");
                user = prop.getProperty("user");
                password = prop.getProperty("password");
                propertiesLoaded = true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (!propertiesLoaded) {
            throw new IllegalStateException("Database properties not loaded. Cannot establish connection.");
        }

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect to the database.", e);
        }
    }
}