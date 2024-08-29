package com.cognixia;

import java.sql.*;
import java.util.Scanner;

import com.cognixia.exception.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/LaughTrack";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static int loggedInUserId;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);

        mainPage(scanner);
    }

    private static void mainPage(Scanner scanner) {
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    log("Registered user and returning to main page.");
                    break;
                case 2:
                    if (loginUser(scanner)) {
                        viewOptions(scanner);
                    }
                    break;
                case 3:
                    log("Exiting application.");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    private static void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        String checkQuery = "SELECT username FROM Users WHERE username = ?";
        String insertQuery = "INSERT INTO Users (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                throw new UserAlreadyExistsException("User already exists!");
            }

            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static boolean loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT user_id FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loggedInUserId = rs.getInt("user_id");
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void viewOptions(Scanner scanner) {
        while (true) {
            System.out.println("1. View All Groups");
            System.out.println("2. View Tracked Groups");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewComedyGroups(scanner);
                    log("Viewed all comedy groups, returning to view options.");
                    break;
                case 2:
                    viewTrackedGroups(scanner);
                    log("Viewed tracked comedy groups, returning to view options.");
                    break;
                case 3:
                    log("Exiting view options.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void viewTrackedGroups(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT UserTrackedGroups.group_id, ComedyGroups.name, UserTrackedGroups.status FROM UserTrackedGroups "
                    + "JOIN ComedyGroups ON UserTrackedGroups.group_id = ComedyGroups.group_id WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
    
            System.out.println("Tracked Comedy Groups:");
            while (rs.next()) {
                int groupId = rs.getInt("group_id");
                String groupName = rs.getString("name");
                String groupStatus = rs.getString("status");
    
                try {
                    if ("Want to Watch".equalsIgnoreCase(groupStatus)) {
                        throw new GroupNotReadyForRatingException("Haven't watched yet");
                    }
    
                    String ratingQuery = "SELECT ROUND(AVG(rating),1) AS avg_rating "
                            + "FROM UserRatings AS UR "
                            + "JOIN Sketches AS S ON UR.sketch_id = S.sketch_id "
                            + "WHERE user_id = ? AND S.group_id = ?";
                    PreparedStatement ratingStmt = conn.prepareStatement(ratingQuery);
                    ratingStmt.setInt(1, loggedInUserId);
                    ratingStmt.setInt(2, groupId);
                    ResultSet ratingRs = ratingStmt.executeQuery();
    
                    double avgRating = 0.0;
                    if (ratingRs.next()) {
                        avgRating = ratingRs.getDouble("avg_rating");
                    }
    
                    System.out.println(groupId + ". " + groupName + " (Status: " + groupStatus + ") - Your Avg Rating: " + avgRating);
    
                } catch (GroupNotReadyForRatingException e) {
                    System.out.println(groupId + ". " + groupName + " (Status: " + groupStatus + ") - " + e.getMessage());
                }
            }
    
            System.out.print("Enter a group number to view details & sketches or '0' to go back: ");
            int groupId = scanner.nextInt();
            if (groupId > 0) {
                showComedyGroupDetails(groupId, conn, scanner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void viewComedyGroups(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT group_id, name FROM ComedyGroups";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Available Comedy Groups:");
            while (rs.next()) {
                System.out.println(rs.getInt("group_id") + ". " + rs.getString("name"));
            }

            System.out.print("Enter a group number to view details & sketches: ");
            int groupId = scanner.nextInt();
            showComedyGroupDetails(groupId, conn, scanner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showComedyGroupDetails(int groupId, Connection conn, Scanner scanner) {
        try {
            String query = "SELECT * FROM ComedyGroups WHERE group_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Group Name: " + rs.getString("name"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Image URL: " + rs.getString("image_url"));

                System.out.println("Available Sketches:");
                showGroupSketches(groupId, conn, scanner);

                if (!isGroupAlreadyTracked(groupId, conn)) {
                    System.out.print("Would you like to track this group? (yes/no): ");
                    String trackChoice = scanner.next();
                    if (trackChoice.equalsIgnoreCase("yes")) {
                        trackGroup(groupId, conn);
                    }
                } else {
                    System.out.println("You are already tracking this group.");
                }
            } else {
                System.out.println("Comedy Group not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isGroupAlreadyTracked(int groupId, Connection conn) {
        try {
            String query = "SELECT COUNT(*) AS count FROM UserTrackedGroups WHERE user_id = ? AND group_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            stmt.setInt(2, groupId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("count") > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void showGroupSketches(int groupId, Connection conn, Scanner scanner) {
        try {
            String query = "SELECT sketch_id, title, description FROM Sketches WHERE group_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Available Sketches:");
            while (rs.next()) {
                System.out.println(rs.getInt("sketch_id") + ". " + rs.getString("title") + " - " + rs.getString("description"));
            }

            rateSketchMenu(groupId, conn, scanner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void rateSketchMenu(int groupId, Connection conn, Scanner scanner) {
        while (true) {
            System.out.println("Enter the number of the sketch you'd like to rate, or '0' to go back to main options: ");
            int sketchId = scanner.nextInt();

            if (sketchId == 0) {
                return;
            }

            boolean rated = checkIfRated(sketchId, conn);

            if (rated) {
                System.out.print("You have already rated this sketch. Would you like to update your rating? (yes/no): ");
                String updateChoice = scanner.next();
                if (updateChoice.equalsIgnoreCase("yes")) {
                    rateSketch(sketchId, scanner, conn);
                }
            } else {
                rateSketch(sketchId, scanner, conn);
            }

            log("Returning to rate sketch menu.");
        }
    }

    private static boolean checkIfRated(int sketchId, Connection conn) {
        try {
            String query = "SELECT COUNT(*) AS count FROM UserRatings WHERE user_id = ? AND sketch_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            stmt.setInt(2, sketchId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("count") > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void rateSketch(int sketchId, Scanner scanner, Connection conn) {
        System.out.print("Enter your rating (1-10): ");
        int rating = scanner.nextInt();

        if (rating < 1 || rating > 10) {
            System.out.println("Invalid rating. Please enter a value between 1 and 10.");
            return;
        }

        try {
            String query = "REPLACE INTO UserRatings (user_id, sketch_id, rating) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            stmt.setInt(2, sketchId);
            stmt.setInt(3, rating);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Rating submitted successfully!");

                int groupId = getGroupIdBySketchId(sketchId, conn);
                updateGroupStatus(groupId, conn);
            } else {
                System.out.println("Failed to submit rating.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getGroupIdBySketchId(int sketchId, Connection conn) throws SQLException {
        String query = "SELECT group_id FROM Sketches WHERE sketch_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, sketchId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("group_id");
        } else {
            throw new SQLException("Group ID not found for sketch ID: " + sketchId);
        }
    }

    private static void trackGroup(int groupId, Connection conn) {
        try {
            String query = "INSERT INTO UserTrackedGroups (user_id, group_id, status) VALUES (?, ?, 'Want to Watch') "
                    + "ON DUPLICATE KEY UPDATE status = 'Want to Watch'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();

            System.out.println("Group tracked successfully with status: Want to Watch");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateGroupStatus(int groupId, Connection conn) {
        try {
            // Determine appropriate status after rating
            String query = "UPDATE UserTrackedGroups SET status = 'In Progress' WHERE user_id = ? AND group_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();

            System.out.println("Group status updated to: In Progress");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void log(String message) {
        System.out.println("LOG: " + message);
    }
}