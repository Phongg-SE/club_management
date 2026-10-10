/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.User;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.dto.DBContext;
import model.entity.User;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nam
 */
public class AccountDAO {

    public List<User> getAllAccounts() {
        List<User> accounts = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, "
                + "u.phone_number, u.avatar_url, ur.status, s.value AS role_name "
                + "FROM user u "
                + "JOIN user_role ur ON u.user_id = ur.user_id "
                + "JOIN setting s ON ur.role_id = s.setting_id";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User account = new User();
                account.setUser_id(rs.getInt("user_id"));
                account.setFull_name(rs.getString("full_name"));
                account.setEmail(rs.getString("email"));
                account.setUsername(rs.getString("username"));
                account.setGender(rs.getString("gender"));
                account.setPhone_number(rs.getString("phone_number"));
                account.setAvatar_url(rs.getString("avatar_url"));
                account.setRole_name(rs.getString("role_name"));
                account.setStatus(rs.getString("status"));

                accounts.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public List<User> getAccountsByPageAndFilter(int start, int total, String status, String role, String username) {
        List<User> accounts = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT u.user_id, u.username, u.gender, u.email, u.phone_number, u.avatar_url, "
                + "s.value AS role_name, ur.status "
                + "FROM user u "
                + "JOIN user_role ur ON u.user_id = ur.user_id "
                + "JOIN setting s ON ur.role_id = s.setting_id "
                + "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        // Thêm điều kiện lọc nếu có giá trị
        if (status != null && !status.isEmpty()) {
            sql.append("AND ur.status = ? ");
            params.add(status);
        }
        if (role != null && !role.isEmpty()) {
            sql.append("AND s.value = ? ");
            params.add(role);
        }
        if (username != null && !username.isEmpty()) {
            sql.append("AND u.username LIKE ? ");
            params.add("%" + username + "%");
        }

        sql.append("LIMIT ?, ?");

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Set giá trị vào câu SQL
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ps.setInt(params.size() + 1, start);
            ps.setInt(params.size() + 2, total);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User account = new User();
                account.setUser_id(rs.getInt("user_id"));
                account.setUsername(rs.getString("username"));
                account.setGender(rs.getString("gender"));
                account.setEmail(rs.getString("email"));
                account.setPhone_number(rs.getString("phone_number"));
                account.setRole_name(rs.getString("role_name"));
                account.setStatus(rs.getString("status"));
                account.setAvatar_url(rs.getString("avatar_url"));
                accounts.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public int getTotalAccountsWithFilter(String status, String role, String username) {
        int count = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM user u "
                + "JOIN user_role ur ON u.user_id = ur.user_id "
                + "JOIN setting s ON ur.role_id = s.setting_id "
                + "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (status != null && !status.isEmpty()) {
            sql.append("AND ur.status = ? ");
            params.add(status);
        }
        if (role != null && !role.isEmpty()) {
            sql.append("AND s.value = ? ");
            params.add(role);
        }
        if (username != null && !username.isEmpty()) {
            sql.append("AND u.username LIKE ? ");
            params.add("%" + username + "%");
        }

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }



    public int getTotalAccounts() {
        String sql = "SELECT COUNT(*) FROM user";
        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
   public User getAccountDetail(int userId) {
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, u.avatar_url, " +
                     "u.phone_number, r.role_id, s.value AS role_name, r.status " +
                     "FROM user u " +
                     "JOIN user_role r ON u.user_id = r.user_id " +
                     "LEFT JOIN setting s ON r.role_id = s.setting_id " +  
                     "WHERE u.user_id = ?";

        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("gender"),
                    rs.getString("avatar_url"),
                    rs.getString("phone_number"),
                    rs.getInt("role_id"),
                    rs.getString("role_name"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
   
   public boolean updateAccount(int userId, String fullName, String email, String username, 
                             String gender, String avatarUrl, String phoneNumber, 
                             int roleId, String status) {
    String updateUserSQL = "UPDATE user SET full_name = ?, email = ?, username = ?, " +
                       "gender = ?, avatar_url = ?, phone_number = ? " +
                       "WHERE user_id = ?";
    String updateUserRoleSQL = "UPDATE user_role SET role_id = ?, status = ? WHERE user_id = ?";
    
    try (Connection conn = DBContext.getJDBCConnection();
         PreparedStatement psUser = conn.prepareStatement(updateUserSQL);
         PreparedStatement psUserRole = conn.prepareStatement(updateUserRoleSQL)) {
        
        conn.setAutoCommit(false); // Bắt đầu transaction
        
        // Cập nhật bảng user
        psUser.setString(1, fullName);
        psUser.setString(2, email);
        psUser.setString(3, username);
        psUser.setString(4, gender);
        psUser.setString(5, avatarUrl);
        psUser.setString(6, phoneNumber);
        psUser.setInt(7, userId);
        psUser.executeUpdate();
        
        // Cập nhật bảng user_role
        psUserRole.setInt(1, roleId);
        psUserRole.setString(2, status);
        psUserRole.setInt(3, userId);
        psUserRole.executeUpdate();
        
        conn.commit(); // Xác nhận transaction
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
   }
   public String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    public static boolean sendEmail(String recipientEmail, String password) {
        String senderEmail = "lehainam8114@gmail.com"; // Thay bằng email của bạn
        String senderPassword = "nywv sbuw zrik sqmy"; // Thay bằng mật khẩu ứng dụng email của bạn

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Tài khoản mới của bạn");
            message.setContent("<p>Chào bạn,</p>"
                    + "<p>Tài khoản của bạn đã được tạo thành công.</p>"
                    + "<p><b>Tên Email Đăng ký:</b> " + recipientEmail + "</p>"
                    + "<p><b>Mật khẩu:</b> " + password + "</p>"
                    + "<p>Vui lòng đăng nhập và thay đổi mật khẩu.</p>"
                    + "<p>Trân trọng,<br>Hệ thống quản lý tài khoản</p>",
                    "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public Connection getConnection() {
        return DBContext.getJDBCConnection();
    }
    public boolean addAccount(String fullName, String email, String username, String password,
                          String gender, String phoneNumber, String avatarUrl, int roleId, String status) {
    String userSql = "INSERT INTO user (full_name, email, username, password, gender, phone_number, avatar_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String userRoleSql = "INSERT INTO user_role (user_id, role_id, status) VALUES (?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement userStmt = conn.prepareStatement(userSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        userStmt.setString(1, fullName);
        userStmt.setString(2, email);
        userStmt.setString(3, username);
        userStmt.setString(4, password); // Dùng mật khẩu từ Service, không tạo lại
        userStmt.setString(5, gender);
        userStmt.setString(6, phoneNumber);
        userStmt.setString(7, avatarUrl);

        int affectedRows = userStmt.executeUpdate();
        if (affectedRows == 0) {
            return false; // Không có dòng nào được chèn
        }

        try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                try (PreparedStatement userRoleStmt = conn.prepareStatement(userRoleSql)) {
                    userRoleStmt.setInt(1, userId);
                    userRoleStmt.setInt(2, roleId);
                    userRoleStmt.setString(3, status);
                    return userRoleStmt.executeUpdate() > 0;
                }
            }
        }
    } catch (SQLException e) {
        Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
    }
    return false;
}
   public boolean isEmailExists(String email, int userId) {
    String sql = "SELECT COUNT(*) FROM user WHERE email = ? AND user_id != ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, email);
        stmt.setInt(2, userId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean isUsernameExists(String username, int userId) {
    String sql = "SELECT COUNT(*) FROM user WHERE username = ? AND user_id != ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setInt(2, userId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean isPhoneNumberExists(String phoneNumber, int userId) {
    String sql = "SELECT COUNT(*) FROM user WHERE phone_number = ? AND user_id != ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, phoneNumber);
        stmt.setInt(2, userId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
       
    
   
