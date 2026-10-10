/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.User;

import constant.EmailUtility;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.dto.DBContext;
import model.entity.User;
import model.entity.UserRole;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author datkh
 */
public class UserDAO {

    public boolean validateEmailLogin(String email, String password) {
        String sql = "SELECT password FROM user WHERE email = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (storedHash == null || storedHash.isEmpty()) {
                        return false;
                    }
                    return BCrypt.checkpw(password, storedHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateLogin(String username, String password) {
        String sql = "SELECT password FROM user WHERE username = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByField(String fieldName, String value) {
        if (fieldName == null || !fieldName.matches("^[a-zA-Z0-9_]+$")) {
            return null;
        }
        User user = null;
        String sql = "SELECT "
                + "    u.user_id, u.full_name, u.email, u.username, u.password, "
                + "    u.gender, u.phone_number, u.avatar_url, u.date_of_birth, "
                + "    u.address, u.city, u.district, u.facebook, u.instargram, s.value "
                + "FROM user u "
                + "JOIN user_role ur ON ur.user_id = u.user_id "
                + "JOIN setting s ON ur.role_id = s.setting_id "
                + "WHERE " + fieldName + " = ?";

        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setPhone_number(rs.getString("phone_number"));
                    user.setAvatar_url(rs.getString("avatar_url"));
                    user.setDate_of_birth(rs.getString("date_of_birth"));
                    user.setAddress(rs.getString("address"));
                    user.setCity(rs.getString("city"));
                    user.setDistrict(rs.getString("district"));
                    user.setFacebook(rs.getString("facebook"));
                    user.setInstargram(rs.getString("instargram"));
                    user.setRole_name(rs.getString("value"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean registerUser(User user, UserRole userRole) {
        String insertUserSQL = "INSERT INTO user (full_name, email, username, password, gender, phone_number, avatar_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertUserRoleSQL = "INSERT INTO user_role (user_id, role_id, status) VALUES (?, ?, ?)";

        Connection con = null;
        PreparedStatement userStmt = null;
        PreparedStatement roleStmt = null;
        ResultSet rs = null;

        try {
            con = DBContext.getJDBCConnection();
            if (con == null) {
                return false;
            }
            con.setAutoCommit(false);

            userStmt = con.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, user.getFull_name());
            userStmt.setString(2, user.getEmail());
            userStmt.setString(3, user.getUsername());
            userStmt.setString(4, user.getPassword());
            userStmt.setString(5, user.getGender());
            userStmt.setString(6, user.getPhone_number());
            userStmt.setString(7, user.getAvatar_url());

            int userInserted = userStmt.executeUpdate();
            if (userInserted == 0) {
                con.rollback();
                return false;
            }

            rs = userStmt.getGeneratedKeys();
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                con.rollback();
                return false;
            }

            roleStmt = con.prepareStatement(insertUserRoleSQL);
            roleStmt.setInt(1, userId);
            roleStmt.setInt(2, userRole.getRole_id());
            roleStmt.setString(3, userRole.getStatus());

            int roleInserted = roleStmt.executeUpdate();
            if (roleInserted == 0) {
                con.rollback();
                return false;
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (userStmt != null) userStmt.close();
                if (roleStmt != null) roleStmt.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updatePassword(String email, String hashedPassword) {
        String sql = "UPDATE user SET password = ?, token = ? WHERE email = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String newToken = EmailUtility.generateToken();
            ps.setString(1, hashedPassword);
            ps.setString(2, newToken);
            ps.setString(3, email);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateResetToken(String email, String token) {
        String sql = "UPDATE user SET token = ?, token_expiry = DATE_ADD(NOW(), INTERVAL 15 MINUTE) WHERE email = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LocalDateTime getTokenExpiry(String email) {
        String sql = "SELECT token_expiry FROM user WHERE email = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("token_expiry");
                    if (timestamp != null) {
                        return timestamp.toLocalDateTime();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValidResetToken(String email, String token) {
        String sql = "SELECT token FROM user WHERE email = ? AND token = ? AND token_expiry > NOW()";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, token);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getRoleIdByUserId(int userId) {
        String sql = "SELECT role_id FROM swp391.user_role WHERE user_id = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("role_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Integer> getClubRoleByUserId(int userId) {
        List<Integer> roleIds = new ArrayList<>();
        String sql = "SELECT role_id FROM club_member WHERE user_id = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roleIds.add(rs.getInt("role_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleIds;
    }

    public String getEmailByToken(String token) {
        String sql = "SELECT email FROM user WHERE token = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getClubIdByUserId(int userId) {
        String sql = "SELECT club_id FROM club_member WHERE user_id = ?";
        try (Connection con = DBContext.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("club_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<User> listAllUsers() throws SQLException {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT user_id, full_name, email, username, password, gender, phone_number, avatar_url, status FROM user";

        try (Connection connection = DBContext.getJDBCConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sql);
                     ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int userId = resultSet.getInt("user_id");
                        String fullName = resultSet.getString("full_name");
                        String email = resultSet.getString("email");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String gender = resultSet.getString("gender");
                        String phoneNumber = resultSet.getString("phone_number");
                        String avatarUrl = resultSet.getString("avatar_url");

                        User user = new User(userId, fullName, email, username, password, gender, avatarUrl, phoneNumber);
                        listUsers.add(user);
                    }
                }
            }
        }
        return listUsers;
    }

    public List<User> getAllUsersByClubID(int clubID) throws SQLException {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT DISTINCT u.user_id, u.full_name, u.email, u.username, u.password, u.gender, "
                + "u.phone_number, u.avatar_url "
                + "FROM user u "
                + "JOIN club_member cm ON u.user_id = cm.user_id "
                + "WHERE cm.club_id = ?";

        try (Connection connection = DBContext.getJDBCConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, clubID);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int userId = resultSet.getInt("user_id");
                            String fullName = resultSet.getString("full_name");
                            String email = resultSet.getString("email");
                            String username = resultSet.getString("username");
                            String password = resultSet.getString("password");
                            String gender = resultSet.getString("gender");
                            String phoneNumber = resultSet.getString("phone_number");
                            String avatarUrl = resultSet.getString("avatar_url");

                            User user = new User(userId, fullName, email, username, password, gender, avatarUrl, phoneNumber);
                            listUsers.add(user);
                        }
                    }
                }
            }
        }
        return listUsers;
    }
}
