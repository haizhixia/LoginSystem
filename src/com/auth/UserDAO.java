package com.auth;

import java.sql.*;
import java.security.*;
import java.util.Base64;


public class UserDAO {
	// ���ݿ�������Ϣ
    private static final String URL = "jdbc:mysql://localhost:3306/user_message";
    private static final String USER = "root";
    private static final String PASSWORD = "Password"; // ���ݿ�����
    
    // ע���û�
    public static boolean register(User user) throws SQLException, NoSuchAlgorithmException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashPassword(user.getPassword()));
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    // ��֤��¼
    public static boolean login(String username, String password) 
            throws SQLException, NoSuchAlgorithmException {
        String sql = "SELECT password FROM users WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return checkPassword(password, storedPassword);
                }
            }
        }
        return false;
    }
    
    // ����û����Ƿ����
    public static boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    // �����ϣ����
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
    
    // ��֤����
    private static boolean checkPassword(String inputPassword, String storedPassword) 
            throws NoSuchAlgorithmException {
        return hashPassword(inputPassword).equals(storedPassword);
    }
}

