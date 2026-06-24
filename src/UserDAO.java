/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    /**
     * Registers a new user into the database users table.
     */
    public boolean registerUser(String fullName, String mobileNo, String email, String password) {
        String sql = "INSERT INTO users (full_name, mobile_no, email, password, green_score) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, fullName);
            pstmt.setString(2, mobileNo);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies user credentials during login.
     */
    public boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Looks up the emission factor from the database reference matrix.
     */
    public double getEmissionFactor(String vehicleType, String fuelType) {
        String sql = "SELECT factor_value FROM emission_factors WHERE vehicle_type = ? AND fuel_type = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, vehicleType);
            pstmt.setString(2, fuelType);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("factor_value");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public double getTransportationFactor(String vehicleType, String fuelType) {
        if (vehicleType == null) return 0.00;
    
        switch (vehicleType) {
            case "Car":
                if ("Diesel".equals(fuelType)) {
                return 0.17;
                } else if ("Electric".equals(fuelType)) {
                return 0.05;
                } else {
                return 0.19; // Default Petrol / CNG
                }
            
            case "Bus":
                return 0.08;
            
            case "Bike":
                if ("Electric".equals(fuelType)) {
                    return 0.02;
                }
                return 0.11;
            
            case "Cycle":
            case "Walk":
                return 0.00;
            
            default:
                return 0.00;
        }
    }
    public boolean insertCarbonRecord(int userId, String activityType, double calculatedCo2) {
        String sql = "INSERT INTO emission_logs (user_id, activity_type, calculated_co2e, log_date) VALUES (?, ?, ?, NOW())";
    
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setInt(1, userId);
            pstmt.setString(2, activityType);
            pstmt.setDouble(3, calculatedCo2);
        
            return pstmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    }
}
    /**
     * Saves a calculated footprint log into the database.
     */
    public boolean saveEmissionLog(int userId, String category, String details, double usage, double calculatedCo2) {
        String sql = "INSERT INTO emission_logs (user_id, category, details, activity_usage, calculated_co2e, log_date) VALUES (?, ?, ?, ?, ?, CURDATE())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            pstmt.setString(3, details);
            pstmt.setDouble(4, usage);
            pstmt.setDouble(5, calculatedCo2);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
    * Fetches specific utility multiplier factor coefficients from MySQL.
    */
    public double getUtilityFactor(String utilityType) {
        String sql = "SELECT factor_value FROM emission_factors WHERE details = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setString(1, utilityType);
        
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                return rs.getDouble("factor_value");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    /**
    * Adds Eco-Reward points directly to a user's running balance inside the database.
    */
    public boolean addGreenPoints(int userId, int points) {
        String sql = "UPDATE users SET green_score = green_score + ? WHERE user_id = ?";
        try (java.sql.Connection conn = DBConnection.getConnection();
         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setInt(1, points);
            pstmt.setInt(2, userId);
        
            return pstmt.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
        /**
     * Automatically populates default challenges for a user if their tracking table is empty.
     */
    public void initializeDefaultChallenges(int userId) {
        String checkSql = "SELECT COUNT(*) FROM user_challenges WHERE user_id = ?";
        String insertSql = "INSERT IGNORE INTO user_challenges (user_id, challenge_name, status) VALUES (?, ?, 'active')";

        String[] defaultChallenges = {
            "Transit/Carpool", "No Single-Use Plastic", "Eco-Friendly Milestone",
            "Plant Sapling", "Weekly Eco Task 2", "Unplug Idle Appliances",
            "LED Bulbs Switch", "Continuous Tracking Streak", "E-Waste Recycling"
        };

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, userId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) { // Table is empty for this user!
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        for (String challenge : defaultChallenges) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setString(2, challenge);
                            insertStmt.addBatch(); // Groups them for efficiency
                        }
                        insertStmt.executeBatch();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        /**
     * Verifies if user tracking parameters are missing, and if so,
     * injects default challenges to guarantee dashboard counters function correctly.
     */
    public void seedUserChallengesIfEmpty(int userId) {
        String checkSql = "SELECT COUNT(*) FROM user_challenges WHERE user_id = ?";
        String insertSql = "INSERT INTO user_challenges (user_id, challenge_name, status) VALUES (?, ?, 'active')";

        String[] baselineChallenges = {
            "Transit/Carpool", 
            "No Single-Use Plastic", 
            "Eco-Friendly Milestone",
            "Plant Sapling", 
            "Weekly Eco Task 2", 
            "Unplug Idle Appliances",
            "LED Bulbs Switch", 
            "Continuous Tracking Streak", 
            "E-Waste Recycling"
        };

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, userId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) { // Table is empty for this user!
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        for (String challengeName : baselineChallenges) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setString(2, challengeName);
                            insertStmt.addBatch(); // Batch execution prevents visual frame stuttering
                        }
                        insertStmt.executeBatch();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error seeding baseline challenge rows: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
