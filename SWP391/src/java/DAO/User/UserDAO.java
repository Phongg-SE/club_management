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
import service.user.UserService;

/**
 *
 * @author datkh
 */
public class UserDAO {

    Connection con = DBContext.getJDBCConnection();

    public boolean validateEmailLogin(String email, String password) {
        String sql = "SELECT password FROM user WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (storedHash == null || storedHash.isEmpty()) {
                    return false;
                }
                return BCrypt.checkpw(password, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateLogin(String username, String password) {
        String sql = "SELECT password FROM user WHERE username = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByField(String fieldName, String value) {
        User user = null;
        String sql = "SELECT \n"
                + "    u.user_id, u.full_name, u.email, u.username, u.password, \n"
                + "    u.gender, u.phone_number, u.avatar_url, u.date_of_birth, \n"
                + "    u.address, u.city, u.district, u.facebook, u.instargram, s.value\n"
                + "FROM user u\n"
                + "JOIN user_role ur ON ur.user_id = u.user_id\n"
                + "JOIN setting s ON ur.role_id = s.setting_id\n"
                + "Where " + fieldName + " = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setPhone_number(("phone_number"));
                user.setAvatar_url(rs.getString("avatar_url"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setAddress(rs.getString("address"));
                user.setCity(rs.getString("city"));
                user.setDistrict(rs.getString("district"));
                user.setFacebook(rs.getString("facebook"));
                user.setInstargram(rs.getString("instargram"));
                user.setRole_name(rs.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean registerUser(User user, UserRole userRole) {
        String insertUserSQL = "INSERT INTO user (full_name, email, username, password, gender,phone_number, avatar_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertUserRoleSQL = "INSERT INTO user_role (user_id, role_id, status) VALUES (?, ?, ?)";

        PreparedStatement userStmt = null;
        PreparedStatement roleStmt = null;
        ResultSet rs = null;

        try {
            con = DBContext.getJDBCConnection();
            con.setAutoCommit(false); // Bắt đầu transaction

            userStmt = con.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, user.getFull_name());
            userStmt.setString(2, user.getEmail());
            userStmt.setString(3, user.getUsername());
            //    userStmt.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); 
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

            // Thêm vào bảng user_role
            roleStmt = con.prepareStatement(insertUserRoleSQL);
            roleStmt.setInt(1, userId);
            roleStmt.setInt(2, userRole.getRole_id());
            roleStmt.setString(3, userRole.getStatus());

            int roleInserted = roleStmt.executeUpdate();

            if (roleInserted == 0) {
                con.rollback();
                return false;
            }

            con.commit(); // Xác nhận transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (userStmt != null) {
                    userStmt.close();
                }
                if (roleStmt != null) {
                    roleStmt.close();
                }
                if (con != null) {
                    con.setAutoCommit(true); // Đặt lại chế độ auto-commit
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updatePassword(String email, String hashedPassword) {
        String sql = "UPDATE user SET password = ?, token = ? WHERE email = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, token);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu cập nhật thành công

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LocalDateTime getTokenExpiry(String email) {
        String sql = "SELECT token_expiry FROM user WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("token_expiry");
                if (timestamp != null) {
                    return timestamp.toLocalDateTime(); // Chuyển đổi sang LocalDateTime
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy hoặc lỗi xảy ra
    }

    public boolean isValidResetToken(String email, String token) {
        String sql = "SELECT token FROM user WHERE email = ? AND token = ? AND token_expiry > NOW()";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, token);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả => token hợp lệ

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getRoleIdByUserId(int userId) {
        String sql = "SELECT role_id FROM swp391.user_role WHERE user_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
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

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy email, trả về null
    }

    public int getClubIdByUserId(int userId) {
        String sql = "SELECT club_id FROM club_member WHERE user_id = ?"; // Truy vấn từ bảng club_member

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("club_id"); // Lấy club_id tương ứng với user_id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    //get all user
    public List<User> listAllUsers() throws SQLException {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT user_id, full_name, email, username, password, gender, phone_number, avatar_url, status FROM user";

        Connection connection = DBContext.getJDBCConnection();

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

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

            resultSet.close();
            statement.close();
            connection.close();
        } else {
            System.out.println("Kết nối cơ sở dữ liệu không thành công.");
        }

        return listUsers;
    }
////test
//    public static void main(String[] args) {
//        try {
//            UserDAO userDAO = new UserDAO(); // Đảm bảo bạn có class UserDAO chứa phương thức listAllUsers()
//            List<User> users = userDAO.listAllUsers();
//
//            if (users.isEmpty()) {
//                System.out.println("Không có người dùng nào trong hệ thống.");
//            } else {
//                for (User user : users) {
//                    System.out.println(user);
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public List<User> getAllUsersByClubID(int clubID) throws SQLException {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT DISTINCT u.user_id, u.full_name, u.email, u.username, u.password, u.gender, "
                + "u.phone_number, u.avatar_url "
                + "FROM user u "
                + "JOIN club_member cm ON u.user_id = cm.user_id "
                + "WHERE cm.club_id = ?";

        Connection connection = DBContext.getJDBCConnection();

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clubID);
            ResultSet resultSet = statement.executeQuery();

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

            resultSet.close();
            statement.close();
            connection.close();
        } else {
            System.out.println("Kết nối cơ sở dữ liệu không thành công.");
        }

        return listUsers;
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        String fieldName = "username"; // Kiểm tra theo email
        String value = "nguyenvana"; // Thay bằng email có trong database

        boolean user = userDAO.validateLogin(value, "12345678");

        if (user) {
            System.out.println("thanh cong");
        } else {
            System.out.println("that bai");
        }
    }

//    public static void main(String[] args) {
//        // Tạo dữ liệu user
//        User user = new User();
//        user.setFull_name("Nguyễn Văn test");
//        user.setEmail("nguyenvantest@example.com");
//        user.setUsername("nguyenvantest");
//        user.setPassword("123456");
//        user.setGender("Male");
//        user.setPhone_number("0987654321");
//        user.setAvatar_url("https://example.com/avatar.jpg");
//
//        UserRole userRole = new UserRole();
//        userRole.setRole_id(6);
//        userRole.setStatus("Active");
//
//        String email = "phongday";
//        String password = "261204";
//        // Gọi hàm registerUser
//        UserService userService = new UserService();
//        user  = userService.getUserByUsername(email);
//
////        if (isRegistered) {
////            System.out.println("Đăng ký thành công!");
////        } else {
////            System.out.println("Đăng ký thất bại!");
////        }
//        System.out.println(user);
//    }
}
