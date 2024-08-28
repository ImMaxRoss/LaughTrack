package com.cognixia.daoClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.dao.UserDao;
import com.cognixia.exception.UserAlreadyExistsException;
import com.cognixia.models.User;
import com.cognixia.utils.DatabaseConnection;

public class UserDaoClass implements UserDao {

    @Override
    public boolean registerUser(User user) throws UserAlreadyExistsException  {
        String checkQuery = "SELECT username FROM Users WHERE username = ?";
        String insertQuery = "INSERT INTO Users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Check if the user already exists
            checkStmt.setString(1, user.getUsername());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                throw new UserAlreadyExistsException("User already exists!");
            }

            // Register new user
            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, user.getPassword());
            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User loginUser(String username, String password) {
        String query = "SELECT user_id, username, password FROM Users WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            } else {
                return null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}