package FurniturePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class handles the database connection and operations for the Furniture Hub application.
 * It uses SQLite as the database engine.
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:furniture_hub.db";
    private Connection connection;
    
    /**
     * Constructor that initializes the database connection and creates the necessary tables if they don't exist.
     */
    public DatabaseConnection() {
        try {
            // Create a connection to the database
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connection established successfully.");
            
            // Create the users table if it doesn't exist
            createTables();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    
    /**
     * Creates the necessary tables in the database if they don't exist.
     */
    private void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        
        try (Statement statement = connection.createStatement()) {
            statement.execute(createUsersTable);
            System.out.println("Users table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
        }
    }
    
    /**
     * Registers a new user in the database.
     * 
     * @param username The username of the user
     * @param email The email of the user
     * @param password The password of the user
     * @return true if the registration was successful, false otherwise
     */
    public boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?);";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // In a real application, you should hash the password
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Authenticates a user by checking if the username and password match a record in the database.
     * 
     * @param username The username of the user
     * @param password The password of the user
     * @return true if the authentication was successful, false otherwise
     */
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?;";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password); // In a real application, you should verify the hashed password
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If there's a result, the authentication is successful
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a username already exists in the database.
     * 
     * @param username The username to check
     * @return true if the username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        String query = "SELECT * FROM users WHERE username = ?;";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If there's a result, the username exists
        } catch (SQLException e) {
            System.err.println("Error checking if username exists: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if an email already exists in the database.
     * 
     * @param email The email to check
     * @return true if the email exists, false otherwise
     */
    public boolean emailExists(String email) {
        String query = "SELECT * FROM users WHERE email = ?;";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If there's a result, the email exists
        } catch (SQLException e) {
            System.err.println("Error checking if email exists: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the username of a user from the database.
     *
     * @param username The username to retrieve
     * @return The username if found, null otherwise
     */
    public String getUsernameByUsername(String username) {
        String query = "SELECT username FROM users WHERE username = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error retrieving username: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}