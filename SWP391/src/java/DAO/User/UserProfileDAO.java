/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.sql.Types;
import model.dto.DBContext;
import static model.dto.DBContext.getJDBCConnection;
import model.entity.ClubActitivies;
import model.entity.Post;
import model.entity.User;
import model.entity.UserActivities;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author pc
 */
public class UserProfileDAO extends DBContext {

    public List<Post> getPostsByUserId(int userId) {
        List<Post> posts = new ArrayList<>();
        String sql = """
        SELECT p.post_id, p.user_id, p.club_id, 
               c.name AS club_name, u.full_name AS author,
               p.image_url, p.tittle, p.status, p.description, p.post_at
        FROM post p
        INNER JOIN club c ON p.club_id = c.club_id
        INNER JOIN user u ON p.user_id = u.user_id
        WHERE p.user_id = ?
        ORDER BY p.post_at DESC
    """;

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getInt("post_id"),
                            rs.getInt("user_id"),
                            rs.getInt("club_id"),
                            rs.getString("image_url"),
                            rs.getString("tittle"),
                            rs.getString("status"),
                            rs.getString("description"),
                            rs.getTimestamp("post_at"),
                            rs.getString("club_name"),
                            rs.getString("author")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

//    public static void main(String[] args) {
//        UserProfileDAO dao = new UserProfileDAO();
//        int userId = 51;
//
//        List<Post> posts = dao.getPostsByUserId(userId);
//
//        if (posts.isEmpty()) {
//            System.out.println("Không có bài viết nào cho user_id = " + userId);
//        } else {
//            for (Post post : posts) {
//                System.out.println("Post ID: " + post.getPostId());
//                System.out.println("User ID: " + post.getUserId());
//                System.out.println("Club ID: " + post.getClubId());
//                System.out.println("Club Name: " + post.getClubName());
//                System.out.println("Author: " + post.getAuthor());
//                System.out.println("Title: " + post.getTitle());
//                System.out.println("Image URL: " + post.getImageUrl());
//                System.out.println("Status: " + post.getStatus());
//                System.out.println("Description: " + post.getDescription());
//                System.out.println("Posted At: " + post.getPostedAt());
//                System.out.println("====================================");
//            }
//        }
//    }
    public ClubActitivies getClubStatistics(int userId, int clubId) {
        ClubActitivies clubactivites = new ClubActitivies();
        String sql = """
            SELECT 
                    (SELECT COUNT(DISTINCT user_id) FROM club_member WHERE club_id = ?) AS numberMember,
                    (SELECT COUNT(*) FROM event WHERE club_id = ?) AS numberEvent,
                    (SELECT COUNT(*) FROM event WHERE club_id = ? AND avatar_url IS NULL) AS numberProject,
                    (SELECT IFNULL(SUM(amount), 0) FROM finance WHERE club_id = ? AND is_expense = 'Yes') AS totalExpense,
                    (SELECT IFNULL(SUM(amount), 0) FROM finance WHERE club_id = ? AND is_expense = 'No') AS totalIncome,
                    (SELECT COUNT(*) FROM club_member WHERE club_id = ? AND status = 'Registed') AS numberRegistration,
                    (SELECT COUNT(*) FROM club_member WHERE club_id = ? AND status = 'Rejected') AS numberRejection
            """;

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 7; i++) {
                stmt.setInt(i, clubId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clubactivites.setNumberMember(rs.getInt("numberMember"));
                    clubactivites.setNumberEvent(rs.getInt("numberEvent"));
                    clubactivites.setNumberProject(rs.getInt("numberProject"));
                    clubactivites.setTotalExpense(rs.getDouble("totalExpense"));
                    clubactivites.setTotalIncome(rs.getDouble("totalIncome"));
                    clubactivites.setNumberRegistration(rs.getInt("numberRegistration"));
                    clubactivites.setNumberRejection(rs.getInt("numberRejection"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubactivites;
    }

//    public static void main(String[] args) {
//        UserProfileDAO dao = new UserProfileDAO();
//        ClubActitivies stats = dao.getClubStatistics(51, 5); // Giả sử user_id = 51, club_id = 1
//
//        System.out.println("Number Member: " + stats.getNumberMember());
//        System.out.println("Number Event: " + stats.getNumberEvent());
//        System.out.println("Number Project: " + stats.getNumberProject());
//        System.out.println("Total Club Expense: "+ stats.getTotalExpense());
//        System.out.println("Total Club Income: " +  stats.getTotalIncome());
//        System.out.println("Number Registration: " + stats.getNumberRegistration());
//        System.out.println("Number Rejection: " + stats.getNumberRejection());
//    }
    public UserActivities getUserClubStatistics(int userId, int clubId) {
        UserActivities stats = new UserActivities();
        String sql = """
        SELECT 
            (SELECT IFNULL(SUM(amount), 0) FROM finance WHERE club_id = ? AND member_id = ? AND is_expense = 'No') AS myDonation,
            (SELECT COUNT(*) FROM task WHERE club_id = ? AND assignee_id = ?) AS numberTask,
            (SELECT COUNT(*) FROM task WHERE club_id = ? AND assignee_id = ? AND status = 'Completed') AS doneTask,
            (SELECT COUNT(*) FROM task WHERE club_id = ? AND assignee_id = ? AND status = 'In Progress') AS doingTask,
            (SELECT COUNT(*) FROM task WHERE club_id = ? AND assigner_id = ? AND status = 'Pending') AS assignedTask,
            (SELECT COUNT(*) FROM registration WHERE register_id = ? AND event_id IN 
                (SELECT event_id FROM event WHERE club_id = ? AND avatar_url IS NOT NULL)) AS eventParticipation,
            (SELECT COUNT(*) FROM registration WHERE register_id = ? AND event_id IN 
                (SELECT event_id FROM event WHERE club_id = ? AND avatar_url IS NULL)) AS projectParticipation
    """;

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 1;
            stmt.setInt(index++, clubId);
            stmt.setInt(index++, userId); // My donation

            stmt.setInt(index++, clubId);
            stmt.setInt(index++, userId); // Number task

            stmt.setInt(index++, clubId);
            stmt.setInt(index++, userId); // Done task

            stmt.setInt(index++, clubId);
            stmt.setInt(index++, userId); // Doing task

            stmt.setInt(index++, clubId);
            stmt.setInt(index++, userId); // Assigned task

            stmt.setInt(index++, userId);
            stmt.setInt(index++, clubId); // Event participation (loại project)

            stmt.setInt(index++, userId);
            stmt.setInt(index++, clubId); // Project participation

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.setMyDonation(rs.getDouble("myDonation"));
                    stats.setNumberTask(rs.getInt("numberTask"));
                    stats.setDoneTask(rs.getInt("doneTask"));
                    stats.setDoingTask(rs.getInt("doingTask"));
                    stats.setAssignedTask(rs.getInt("assignedTask"));
                    stats.setEventParticipation(rs.getInt("eventParticipation"));
                    stats.setProjectParticipation(rs.getInt("projectParticipation"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }

//    public static void main(String[] args) {
//        UserProfileDAO userDAO = new UserProfileDAO(); // Hoặc class DAO chứa phương thức này
//
//        int userId = 51; // ID của user cần test
//        int clubId = 5;  // ID của club cần test
//
//        // Gọi phương thức
//        UserActivities stats = userDAO.getUserClubStatistics(userId, clubId);
//
//        // Hiển thị kết quả
//        System.out.println("===== User Club Statistics =====");
//        System.out.println("My Donation: " + stats.getMyDonation() + " VND");
//        System.out.println("Number of Tasks Assigned: " + stats.getNumberTask());
//        System.out.println("Tasks Completed: " + stats.getDoneTask());
//        System.out.println("Tasks In Progress: " + stats.getDoingTask());
//        System.out.println("Tasks Assigned to Others: " + stats.getAssignedTask());
//        System.out.println("Events Participated (excluding projects): " + stats.getEventParticipation());
//        System.out.println("Projects Participated: " + stats.getProjectParticipation());
//    }
    public User getUserById(int userId) {
        String sql = """
        SELECT user_id, full_name, email, username, password, gender, phone_number, avatar_url,
               date_of_birth, address, city, district, facebook, instargram 
        FROM user 
        WHERE user_id = ?
    """;

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Date dob = rs.getDate("date_of_birth");
                    String formattedDOB = null;

                    // Kiểm tra nếu dob không null thì format thành dd/MM/yyyy
                    if (dob != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        formattedDOB = sdf.format(dob);
                    }
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("gender"),
                            rs.getString("avatar_url"), // Sửa lại thứ tự
                            rs.getString("phone_number"),
                            rs.getString("date_of_birth"),
                            rs.getString("city"),
                            rs.getString("district"),
                            rs.getString("address"),
                            rs.getString("facebook"),
                            rs.getString("instargram")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public static void main(String[] args) {
//        UserProfileDAO userDAO = new UserProfileDAO();  // Khởi tạo đối tượng DAO
//        int testUserId = 51;  // ID cần test
//
//        // Gọi phương thức non-static từ đối tượng
//        User user = userDAO.getUserById(testUserId);
//
//        if (user != null) {
//            System.out.println("User ID: " + user.getUser_id());
//            System.out.println("Full Name: " + user.getFull_name());
//            System.out.println("Email: " + user.getEmail());
//            System.out.println("Username: " + user.getUsername());
//            System.out.println("Password: " + user.getPassword());
//            System.out.println("Gender: " + user.getGender());
//            System.out.println("Avatar URL: " + user.getAvatar_url());
//            System.out.println("Phone Number: " + user.getPhone_number());
//            System.out.println("Date of Birth: " + user.getDate_of_birth());
//            System.out.println("City: " + user.getCity());
//            System.out.println("District: " + user.getDistrict());
//            System.out.println("Address: " + user.getAddress());
//            System.out.println("Facebook: " + user.getFacebook());
//            System.out.println("Instagram: " + user.getInstargram());
//        } else {
//            System.out.println("User not found!");
//        }
//    }
    public boolean updateAvatar(int userId, String avatarUrl) {
        String sql = "UPDATE user SET avatar_url = ? WHERE user_id = ?";
        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, avatarUrl);
            ps.setInt(2, userId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE user SET full_name=?, email=?, phone_number=?, date_of_birth=?, address=?, city=?, district=?, facebook=?, instargram=? WHERE user_id=?";
        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Tắt auto-commit để kiểm soát transaction

            stmt.setString(1, user.getFull_name());
            stmt.setString(2, user.getEmail()); // Thêm email
            stmt.setString(3, user.getPhone_number());
            stmt.setString(4, user.getDate_of_birth());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getCity());
            stmt.setString(7, user.getDistrict());
            stmt.setString(8, user.getFacebook());
            stmt.setString(9, user.getInstargram());
            stmt.setInt(10, user.getUser_id()); // WHERE user_id=?

            int rowsAffected = stmt.executeUpdate();
            conn.commit(); // Xác nhận thay đổi

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) {
//        UserProfileDAO userProfileService = new UserProfileDAO();
//
//        // Tạo một đối tượng User giả lập với dữ liệu test
//        User testUser = new User();
//        testUser.setUser_id(1); // Giả sử user_id = 1 đang tồn tại trong DB
//        testUser.setFull_name("Nguyễn Văn A");
//        testUser.setEmail("nguyenvanan@example.com");
//        testUser.setPhone_number("0987654321");
//        testUser.setDate_of_birth("1995-08-15");
//        testUser.setAddress("123 Đường ABC, Quận 1");
//        testUser.setCity("Hồ Chí Minh");
//        testUser.setDistrict("Quận 1");
//        testUser.setFacebook("facebook.com/nguyenvana");
//        testUser.setInstargram("instagram.com/nguyenvana");
//
//        // Gọi phương thức updateUser() để test
//        boolean isUpdated = userProfileService.updateUser(testUser);
//
//        // Kiểm tra kết quả
//        if (isUpdated) {
//            System.out.println("✅ Cập nhật thông tin thành công!");
//        } else {
//            System.out.println("❌ Cập nhật thất bại! Kiểm tra lại dữ liệu.");
//        }
//    }
    public boolean isEmailDuplicateWhenUpdate(String email, int userId) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ? AND user_id != ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setInt(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu số lượng > 0, nghĩa là email đã tồn tại cho người dùng khác
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Thêm thông báo lỗi để debug
        }
        return false; // Trả về false nếu không tìm thấy email trùng
    }

//    public static void main(String[] args) {
//        UserProfileDAO userDAO = new UserProfileDAO();
//
//        // Test 1: User có ID 1 muốn thay đổi sang email đã tồn tại của user khác
//        int userIdToUpdate = 1;
//        String newEmail = "levanc@example.com"; // Giả sử email này đã tồn tại trong DB
//
//        boolean isDuplicate = userDAO.isEmailDuplicateWhenUpdate(newEmail, userIdToUpdate);
//        if (isDuplicate) {
//            System.out.println("Test 1: Email đã tồn tại cho user khác. Không thể cập nhật.");
//        } else {
//            System.out.println("Test 1: Email hợp lệ, có thể cập nhật.");
//        }
//
//        // Test 2: User có ID 1 muốn thay đổi sang email chưa ai sử dụng
//        String uniqueEmail = "newemail@example.com"; // Giả sử email này chưa tồn tại trong DB
//
//        isDuplicate = userDAO.isEmailDuplicateWhenUpdate(uniqueEmail, userIdToUpdate);
//        if (isDuplicate) {
//            System.out.println("Test 2: Email đã tồn tại. Lỗi!");
//        } else {
//            System.out.println("Test 2: Email hợp lệ, có thể cập nhật.");
//        }
//
//        // Test 3: Tiến hành cập nhật user nếu email không bị trùng
//        User user = new User();
//        user.setUser_id(userIdToUpdate);
//        user.setFull_name("New Name");
//        user.setPhone_number("0123456789");
//        user.setDate_of_birth("2000-01-01");
//        user.setAddress("New Address");
//        user.setCity("New City");
//        user.setDistrict("New District");
//        user.setFacebook("fb.com/newprofile");
//        user.setInstargram("insta.com/newprofile");
//        user.setEmail(uniqueEmail);
//
//        if (!userDAO.isEmailDuplicateWhenUpdate(user.getEmail(), user.getUser_id())) {
//            boolean updated = userDAO.updateUser(user);
//            if (updated) {
//                System.out.println("Test 3: Cập nhật thành công!");
//            } else {
//                System.out.println("Test 3: Cập nhật thất bại!");
//            }
//        } else {
//            System.out.println("Test 3: Không thể cập nhật vì email bị trùng.");
//        }
//    }

    public boolean isPhoneDuplicateWhenUpdate(String phoneNumber, int userId) {
        String sql = "SELECT COUNT(*) FROM user WHERE phone_number = ? AND user_id != ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phoneNumber);
            stmt.setInt(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu số lượng > 0, nghĩa là số điện thoại đã tồn tại cho người dùng khác
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi để debug
        }
        return false; // Trả về false nếu không tìm thấy số điện thoại trùng
    }

//    public static void main(String[] args) {
//        UserProfileDAO userProfileDAO = new UserProfileDAO();
//
//        // Test với user ID giả định (thay đổi user_id để kiểm tra)
//        int testUserId = 5; // ID của user đang muốn kiểm tra
//        String testPhoneNumber = "0326950068"; // Số điện thoại cần kiểm tra
//
//        boolean isDuplicate = userProfileDAO.isPhoneDuplicateWhenUpdate(testPhoneNumber, testUserId);
//
//        if (isDuplicate) {
//            System.out.println("Số điện thoại " + testPhoneNumber + " đã tồn tại cho người dùng khác!");
//        } else {
//            System.out.println("Số điện thoại " + testPhoneNumber + " có thể sử dụng.");
//        }
//    }
    
    ///=================TEST====================//
    public boolean validateLogin(String email, String password) {
        Connection con = DBContext.getJDBCConnection();
        String query = "SELECT email, password FROM user WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String email, String hashedPassword) {
        String sql = "UPDATE user SET password = ? WHERE email = ?";

        try (Connection con = DBContext.getJDBCConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hashedPassword);
            ps.setString(2, email);

            return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        String plainPassword = "261204";  // Thay bằng mật khẩu bạn muốn mã hóa
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));

        System.out.println("Mật khẩu gốc: " + plainPassword);
        System.out.println("Mật khẩu đã mã hóa: " + hashedPassword);
    }


}
