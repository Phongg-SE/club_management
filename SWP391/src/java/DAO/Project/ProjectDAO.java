package DAO.Project;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.dto.DBContext;
import model.entity.Event;
import model.entity.ProjectMember;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.entity.User;

public class ProjectDAO {

    public List<Event> getProjectsByClubId(int clubID) throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.event_id, e.name, e.leader_id, u.full_name AS leader_name, "
                + "e.guest, e.location, e.start_time, e.end_time, e.created_date, "
                + "e.is_public, e.status, e.description, "
                + "COUNT(DISTINCT pm.user_id) AS total_members "
                + "FROM event e "
                + "JOIN user u ON e.leader_id = u.user_id "
                + "LEFT JOIN project_event_member pm ON pm.project_id = e.event_id "
                + "WHERE e.club_id = ? AND e.type = 'Project' "
                + "GROUP BY e.event_id, e.name, e.leader_id, u.full_name, "
                + "e.guest, e.location, e.start_time, e.end_time, "
                + "e.created_date, e.is_public, e.status, e.description";

        Connection connection = DBContext.getJDBCConnection();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm"); // Không hiển thị giây

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clubID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int eventID = resultSet.getInt("event_id");
                String projectName = resultSet.getString("name");
                int leaderID = resultSet.getInt("leader_id");
                String leaderName = resultSet.getString("leader_name");
                String guest = resultSet.getString("guest");
                String location = resultSet.getString("location");

                Timestamp startTimeRaw = resultSet.getTimestamp("start_time");
                Timestamp endTimeRaw = resultSet.getTimestamp("end_time");
                Timestamp createdDateRaw = resultSet.getTimestamp("created_date");

                String startTime = (startTimeRaw != null) ? dateTimeFormat.format(startTimeRaw) : null;
                String endTime = (endTimeRaw != null) ? dateTimeFormat.format(endTimeRaw) : null;
                String createdDate = (createdDateRaw != null) ? dateTimeFormat.format(createdDateRaw) : null;

                String isPublic = resultSet.getString("is_public");
                String status = resultSet.getString("status");
                String description = resultSet.getString("description");
                int totalMembers = resultSet.getInt("total_members");

                Event event = new Event(eventID, clubID, leaderID, leaderName, projectName, guest, location, startTime, endTime, createdDate, isPublic, status, description, totalMembers);
                events.add(event);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } else {
            System.out.println("Kết nối cơ sở dữ liệu không thành công.");
        }

        return events;
    }

//    //test  list theo club
//    public static void main(String[] args) {
//        try {
//            ProjectDAO projectDAO = new ProjectDAO(); // Giả sử class DAO chứa phương thức
//            int clubID = 1; // Thay bằng ID của CLB muốn test
//
//            List<Event> projects = projectDAO.getProjectsByClubId(clubID);
//
//            if (projects.isEmpty()) {
//                System.out.println("Không có dự án nào trong CLB với ID: " + clubID);
//            } else {
//                System.out.println("Danh sách dự án của CLB ID " + clubID + ":");
//                for (Event p : projects) {
//                    System.out.println("Project ID: " + p.getClub_id());
//                    System.out.println("Tên: " + p.getName());
//                    System.out.println("Leader: " + p.getLeader_name());
//                    System.out.println("Số thành viên: " + p.getTotal_member());
//                    System.out.println("Trạng thái: " + p.getStatus());
//                    System.out.println("Ngày tạo: " + p.getCreated_date());
//                    System.out.println("--------------------------");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    // Thêm Project (Sự kiện kiểu dự án)
    public boolean addProject(Event event) {
        String eventSql = "INSERT INTO event (club_id, type, leader_id, name, avatar_url, guest, location, start_time, end_time, is_public, status, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String memberSql = "INSERT INTO project_event_member (user_id, project_id, status) VALUES (?, ?, 'Active')";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement eventStmt = conn.prepareStatement(eventSql, Statement.RETURN_GENERATED_KEYS); PreparedStatement memberStmt = conn.prepareStatement(memberSql)) {

            // Thêm sự kiện (Project) vào bảng event
            eventStmt.setInt(1, event.getClub_id());
            eventStmt.setString(2, event.getType()); // Loại sự kiện (Project, Event,...)

            eventStmt.setInt(3, event.getLeader_id());
            eventStmt.setString(4, event.getName());

            // Kiểm tra nếu avatar_url là null
            if (event.getAvatar_url() != null) {
                eventStmt.setString(5, event.getAvatar_url());
            } else {
                eventStmt.setNull(5, Types.VARCHAR);
            }

            // Kiểm tra nếu guest là null
            if (event.getGuest() != null) {
                eventStmt.setString(6, event.getGuest());
            } else {
                eventStmt.setNull(6, Types.VARCHAR);
            }

            eventStmt.setString(7, event.getLocation());
            eventStmt.setString(8, event.getStart_time());
            eventStmt.setString(9, event.getEnd_time());
            eventStmt.setString(10, event.getIs_public());
            eventStmt.setString(11, event.getStatus());

            // Kiểm tra giá trị null cho description
            if (event.getDescription() != null) {
                eventStmt.setString(12, event.getDescription());
            } else {
                eventStmt.setNull(12, Types.VARCHAR);
            }

            int rowsInserted = eventStmt.executeUpdate();

            // Kiểm tra xem có thêm thành công không
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = eventStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int eventId = generatedKeys.getInt(1); // Lấy event_id vừa tạo

                        // Thêm leader vào bảng project_event_member
                        memberStmt.setInt(1, event.getLeader_id());
                        memberStmt.setInt(2, eventId);
                        memberStmt.executeUpdate();

                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    //test add
//    public static void main(String[] args) {
//        ProjectDAO projectDAO = new ProjectDAO();
//
//        // Tạo đối tượng Event mới
//        Event newProject = new Event();
//        newProject.setClub_id(1); // Club ID hợp lệ trong database
//        newProject.setType("Project");
//        newProject.setLeader_id(2); // Leader ID hợp lệ (người này phải là thành viên của club)
//        newProject.setName("duyngu");
//        newProject.setAvatar_url(null);
//        newProject.setGuest(null);
//        newProject.setLocation("Room 101, Tech Building");
//        newProject.setStart_time("2025-04-01 09:00:00");
//        newProject.setEnd_time("2025-06-30 17:00:00");
//        newProject.setIs_public("No");
//        newProject.setStatus("Ongoing");
//        newProject.setDescription("A research project on AI and Machine Learning applications.");
//
//        // Gọi phương thức addProject để thêm vào database
//        boolean success = projectDAO.addProject(newProject);
//
//        // Kiểm tra kết quả
//        if (success) {
//            System.out.println("Project added successfully!");
//        } else {
//            System.out.println("Failed to add project.");
//        }
//    }
    // Kiểm tra sự kiện có tồn tại hay không trong bảng event theo tên và club_id
    public boolean isEventExist(String eventName, int clubId) {
        String sql = "SELECT COUNT(*) FROM event WHERE name = ? AND club_id = ?";
        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, eventName);
            statement.setInt(2, clubId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Trả về true nếu đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Integer> getClubIdsByUserId(int userId) throws SQLException {
        List<Integer> clubIds = new ArrayList<>();
        String sql = "SELECT distinct club_id FROM club_member WHERE user_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clubIds.add(rs.getInt("club_id"));
                }
            }
        }
        return clubIds; // Trả về danh sách các club_id (có thể rỗng nếu user không thuộc club nào)
    }

//     public static void main(String[] args) {
//        ProjectDAO projectDAO = new ProjectDAO();
//        
//        // Test với user có trong club_member
//        int userId = 51; // Thay đổi userId để kiểm thử
//        try {
//            List<Integer> clubIds = projectDAO.getClubIdsByUserId(userId);
//            if (clubIds.isEmpty()) {
//                System.out.println("User " + userId + " không thuộc câu lạc bộ nào.");
//            } else {
//                System.out.println("User " + userId + " thuộc các câu lạc bộ: " + clubIds);
//            }
//        } catch (SQLException e) {
//            System.err.println("Lỗi khi lấy club ID: " + e.getMessage());
//        }
//    }
    public boolean isClubLeader(int userId) {
        String sql = "SELECT COUNT(*) FROM club_member WHERE user_id = ? AND role_id = 4";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu có  ít nhất 1 dòng, user là Club Leader
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu có lỗi, giả định không phải Leader
    }

//    public static void main(String[] args) {
//        ProjectDAO projectDAO = new ProjectDAO();
//
//        int testUserId = 51; // Thay bằng user_id thực tế trong DB
//
//        if (projectDAO.isClubLeader(testUserId)) {
//            System.out.println("User ID " + testUserId + " là Club Leader.");
//        } else {
//            System.out.println("User ID " + testUserId + " KHÔNG phải Club Leader.");
//        }
//    }
    //filter da tich hop them phan trang 
    public List<Event> listProjectByFilter(int clubId, String status, String searchName, int page) throws SQLException {
        List<Event> events = new ArrayList<>();
        int recordsPerPage = 5; // Mỗi trang có 5 bản ghi
        int offset = (page - 1) * recordsPerPage; // Tính OFFSET dựa vào trang

        StringBuilder sql = new StringBuilder(
                "SELECT e.event_id, e.club_id, e.type, e.name, e.avatar_url, e.guest, "
                + "e.location, e.start_time, e.end_time, e.created_date, e.is_public, "
                + "e.status, e.description, e.leader_id, u.full_name AS leader_name, "
                + "COUNT(DISTINCT pm.user_id) AS total_members "
                + "FROM event e "
                + "JOIN user u ON e.leader_id = u.user_id "
                + "LEFT JOIN project_event_member pm ON pm.project_id = e.event_id "
                + "WHERE e.club_id = ? AND e.type = 'Project' "
        );

        if (status != null && !status.isEmpty()) {
            sql.append(" AND e.status = ?");
        }
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND e.name LIKE ?");
        }

        sql.append(" GROUP BY e.event_id, e.club_id, e.type, e.name, e.avatar_url, e.guest, "
                + "e.location, e.start_time, e.end_time, e.created_date, e.is_public, "
                + "e.status, e.description, e.leader_id, u.full_name");

        // Thêm phân trang
        sql.append(" LIMIT ? OFFSET ?");

        // Định dạng ngày giờ: "dd-MM-yyyy HH:mm"
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy");

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int index = 1;
            statement.setInt(index++, clubId);

            if (status != null && !status.isEmpty()) {
                statement.setString(index++, status);
            }
            if (searchName != null && !searchName.trim().isEmpty()) {
                statement.setString(index++, "%" + searchName + "%");
            }

            // Thêm limit và offset vào query
            statement.setInt(index++, recordsPerPage);
            statement.setInt(index++, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String startTime = resultSet.getTimestamp("start_time") != null
                            ? dateTimeFormat.format(resultSet.getTimestamp("start_time"))
                            : null;

                    String endTime = resultSet.getTimestamp("end_time") != null
                            ? dateTimeFormat.format(resultSet.getTimestamp("end_time"))
                            : null;

                    String createdDate = resultSet.getTimestamp("created_date") != null
                            ? dateTimeFormat.format(resultSet.getTimestamp("created_date"))
                            : null;

                    events.add(new Event(
                            resultSet.getInt("event_id"),
                            resultSet.getInt("club_id"),
                            resultSet.getInt("leader_id"),
                            resultSet.getString("leader_name"),
                            resultSet.getString("name"),
                            resultSet.getString("guest"),
                            resultSet.getString("location"),
                            startTime,
                            endTime,
                            createdDate,
                            resultSet.getString("is_public"),
                            resultSet.getString("status"),
                            resultSet.getString("description"),
                            resultSet.getInt("total_members"))
                    );
                }
            }
        }

        return events;
    }

    public int getTotalPages(int clubId, String status, String searchName) throws SQLException {
        int recordsPerPage = 5; // Số bản ghi mỗi trang
        int totalRecords = 0; // Tổng số bản ghi

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM event WHERE club_id = ? AND type = 'Project'"
        );

        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
        }
        if (searchName != null && !searchName.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int index = 1;
            statement.setInt(index++, clubId);

            if (status != null && !status.isEmpty()) {
                statement.setString(index++, status);
            }
            if (searchName != null && !searchName.isEmpty()) {
                statement.setString(index++, "%" + searchName + "%");
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalRecords = resultSet.getInt(1);
                }
            }
        }

        return (int) Math.ceil((double) totalRecords / recordsPerPage);
    }

//    public static void main(String[] args) {
//        ProjectDAO projectDAO = new ProjectDAO();
//        // Các tham số test
//        int clubId = 1; // ID của câu lạc bộ cần test
//        String status = ""; // Có thể set "Active", "Pending", hoặc ""
//        String searchName = ""; // Tìm kiếm theo tên dự án hoặc ""
//        int page = 1; // Trang cần test
//
//        try {
//            // Lấy danh sách project theo phân trang
//            List<Event> events = projectDAO.listProjectByFilter(clubId, status, searchName, page);
//            int totalPages = projectDAO.getTotalPages(clubId, status, searchName);
//
//            // In kết quả test
//            System.out.println("📌 Tổng số trang: " + totalPages);
//            System.out.println("📌 Dữ liệu của trang " + page + ":");
//
//            for (Event event : events) {
//                System.out.println("🔹 " + event.getEvent_id() + " - " + event.getName()
//                        + " | Leader: " + event.getLeader_name()
//                        + " | Status: " + event.getStatus());
//            }
//
//        } catch (SQLException e) {
//            System.err.println("❌ Lỗi khi test phân trang: " + e.getMessage());
//        }
//    }
    public void updateEventStatusAutomatically() throws SQLException {
        String sql = """
                 UPDATE event
                 SET status = 
                     CASE 
                         WHEN status != 'Completed' AND NOW() < start_time THEN 'Planned'
                         WHEN status != 'Completed' AND NOW() BETWEEN start_time AND end_time THEN 'Ongoing'
                         WHEN status != 'Completed' AND NOW() > end_time THEN 'Completed'
                         ELSE status
                     END;
                 """;

        try (Connection connection = DBContext.getJDBCConnection(); Statement statement = connection.createStatement()) {
            statement.execute("SET SQL_SAFE_UPDATES = 0"); // Câu lệnh SET phải được thực thi riêng biệt
            int rowsUpdated = statement.executeUpdate(sql);
            System.out.println("✅ Updated " + rowsUpdated + " event(s) status.");
        }
    }

//    public void updateMemberRolesAutomatically() throws SQLException {
//        // Câu lệnh SQL cho từng phần
//        String deleteDuplicateRolesSql = """
//        DELETE cm FROM club_member cm
//        JOIN (
//            SELECT club_id, user_id, MIN(role_id) AS role_id
//            FROM club_member
//            WHERE role_id != 4
//            GROUP BY club_id, user_id
//            HAVING COUNT(*) > 1
//        ) dup ON cm.club_id = dup.club_id AND cm.user_id = dup.user_id
//        WHERE cm.role_id != dup.role_id;
//    """;
//
//        String updateRolesSql = """
//        UPDATE club_member 
//        SET role_id = 3
//        WHERE role_id != 4;
//    """;
//
//        String insertEventMembersSql = """
//        INSERT IGNORE INTO club_member (club_id, user_id, role_id, status, note)
//        SELECT e.club_id, e.leader_id, 2, 'Active', Null
//        FROM event e
//        LEFT JOIN club_member cm 
//            ON e.club_id = cm.club_id 
//            AND e.leader_id = cm.user_id
//        WHERE e.status = 'Ongoing' 
//          AND e.type = 'Event'
//          AND (cm.role_id IS NULL OR cm.role_id != 2);
//    """;
//
//        String insertProjectMembersSql = """
//        INSERT IGNORE INTO club_member (club_id, user_id, role_id, status, note)
//        SELECT e.club_id, e.leader_id, 5, 'Active', Null
//        FROM event e
//        LEFT JOIN club_member cm 
//            ON e.club_id = cm.club_id 
//            AND e.leader_id = cm.user_id
//        WHERE e.status = 'Ongoing' 
//          AND e.type = 'Project'
//          AND (cm.role_id IS NULL OR cm.role_id != 5);
//    """;
//
//        String deleteRole3MembersSql = """
//        DELETE cm
//        FROM club_member cm
//        JOIN (
//            SELECT user_id, club_id
//            FROM club_member
//            WHERE role_id = 4
//        ) AS temp ON cm.user_id = temp.user_id
//           AND cm.club_id = temp.club_id
//        WHERE cm.role_id = 3;
//    """;
//
//        try (Connection connection = DBContext.getJDBCConnection(); Statement statement = connection.createStatement()) {
//            // Tắt chế độ bảo vệ SQL_SAFE_UPDATES
//            statement.execute("SET SQL_SAFE_UPDATES = 0");
//
//            // Thực thi các câu lệnh theo đúng thứ tự
//            statement.executeUpdate(deleteDuplicateRolesSql);
//            System.out.println("✅ Deleted duplicate roles.");
//
//            statement.executeUpdate(updateRolesSql);
//            System.out.println("✅ Updated roles to 3.");
//
//            statement.executeUpdate(insertEventMembersSql);
//            System.out.println("✅ Inserted event members.");
//
//            statement.executeUpdate(insertProjectMembersSql);
//            System.out.println("✅ Inserted project members.");
//
//            statement.executeUpdate(deleteRole3MembersSql);
//            System.out.println("✅ Deleted role 3 members who have role 4.");
//        }
//    }
//    public static void main(String[] args) {
//        ProjectDAO projectDAO = new ProjectDAO();
//
//        // Tạo ScheduledExecutorService để chạy task mỗi 5 phút
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
//
//        Runnable task = () -> {
//            try {
//                System.out.println("🔄 Auto Update Started...");
//                projectDAO.updateEventStatusAutomatically();   // Cập nhật status của Event
//                //projectDAO.updateMemberRolesAutomatically(); // Cập nhật role_id của club_member
//                System.out.println("✅ Auto Update Completed.");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        };
//
//        // Chạy task ngay lập tức và lặp lại mỗi 1 phút
//        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.MINUTES);
//    }
    //check co phai ProjectLeader khong
    public boolean isProjectLeader(int userId, int clubId) {
        String sql = "SELECT COUNT(*) \n"
                + "FROM club_member \n"
                + "WHERE user_id = ? AND club_id = ? AND role_id = 5;";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, clubId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//
//        ProjectDAO projectDAO = new ProjectDAO();
//
//        // Nhập userId cần kiểm tra
//        int testUserId = 5;
//        int testClubId = 1;// Thay đổi giá trị này để test với user khác
//
//        // Kiểm tra quyền Project Leader
//        boolean isLeader = projectDAO.isProjectLeader(testUserId, testClubId);
//
//        // In kết quả ra màn hình
//        if (isLeader) {
//            System.out.println("User ID " + testUserId + " là Project Leader.");
//        } else {
//            System.out.println("User ID " + testUserId + " KHÔNG phải là Project Leader.");
//        }
//    }
    public List<User> getAllEventLeaderByClubID(int clubID) throws SQLException {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT DISTINCT u.user_id, u.full_name, u.email, u.username, u.password, u.gender, "
                + "u.phone_number, u.avatar_url "
                + "FROM user u "
                + "JOIN club_member cm ON u.user_id = cm.user_id "
                + "JOIN setting s on s.setting_id = cm.role_id "
                + "WHERE cm.club_id = ? AND s.value = 'Event Leader';";

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

    public List<User> getAllProjectLeaderByClubID(int clubID) throws SQLException {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT DISTINCT u.user_id, u.full_name, u.email, u.username, u.password, u.gender, "
                + "u.phone_number, u.avatar_url "
                + "FROM user u "
                + "JOIN club_member cm ON u.user_id = cm.user_id "
                + "JOIN setting s on s.setting_id = cm.role_id "
                + "WHERE cm.club_id = ? AND s.value = 'Project Leader';";

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

//    public static void main(String[] args) {
//        ProjectDAO projectDAO = new ProjectDAO();
//
//        int testClubID = 1; // Thay thế bằng club_id bạn muốn kiểm tra
//
//        try {
//            List<User> leaders = projectDAO.getAllProjectLeaderByClubID(testClubID);
//
//            if (leaders.isEmpty()) {
//                System.out.println("Không có leader nào trong club ID: " + testClubID);
//            } else {
//                System.out.println("Danh sách leaders của club ID " + testClubID + ":");
//                for (User leader : leaders) {
//                    System.out.println("ID: " + leader.getUser_id() + ", Name: " + leader.getFull_name()
//                            + ", Email: " + leader.getEmail() + ", Username: " + leader.getUsername());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    //nammm
    public Event getProjectDetail(int eventID) throws SQLException {
        String sql = "SELECT e.event_id, e.club_id, e.name, e.leader_id, u.full_name AS leader_name, "
                + "e.start_time, e.end_time, e.status, e.description, "
                + "e.created_date, e.location "
                + "FROM event e "
                + "JOIN user u ON e.leader_id = u.user_id "
                + "WHERE e.event_id = ?";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, eventID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Event(
                            resultSet.getInt("event_id"),
                            resultSet.getInt("club_id"), // club_id đứng thứ 2
                            resultSet.getString("name"),
                            resultSet.getInt("leader_id"),
                            resultSet.getString("leader_name"),
                            formatDate(resultSet.getTimestamp("start_time"), dateFormat),
                            formatDate(resultSet.getTimestamp("end_time"), dateFormat),
                            resultSet.getString("status"),
                            resultSet.getString("description"),
                            formatDate(resultSet.getTimestamp("created_date"), dateFormat),
                            resultSet.getString("location")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProject(Event event) throws SQLException {
        String sql = "UPDATE event SET name = ?, location = ?, start_time = ?, end_time = ?, status = ?, description = ?, leader_id = ? WHERE event_id = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, event.getName());
            statement.setString(2, event.getLocation());

            // Chuyển đổi String -> Timestamp (đặt giờ phút giây về 00:00:00)
            statement.setTimestamp(3, convertStringToTimestamp(event.getStart_time(), dateFormat));
            statement.setTimestamp(4, convertStringToTimestamp(event.getEnd_time(), dateFormat));

            statement.setString(5, event.getStatus());
            statement.setString(6, event.getDescription());
            statement.setInt(7, event.getLeader_id()); // Cập nhật leader_id
            statement.setInt(8, event.getEvent_id());  // Điều kiện WHERE event_id

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addMemberToProject(int userId, int projectId) throws SQLException {
        String sql = "INSERT INTO project_event_member (user_id, event_id, project_id, status) VALUES (?, NULL, ?, 'Active')";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, projectId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<User> getAvailableClubMembers(int clubId, int id) throws SQLException {
        List<User> members = new ArrayList<>();
        String sql = "SELECT DISTINCT u.user_id, u.full_name, u.email "
                + "FROM user u "
                + "JOIN club_member cm ON u.user_id = cm.user_id "
                + "WHERE cm.club_id = ? "
                + // Thêm điều kiện club_id
                "AND cm.club_id = ( "
                + "    SELECT e.club_id FROM event e WHERE e.event_id = ? "
                + ") "
                + "AND u.user_id NOT IN ( "
                + "    SELECT pem.user_id FROM project_event_member pem WHERE pem.project_id = ? "
                + ")";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clubId); // Truyền club_id
            stmt.setInt(2, id);     // Truyền event_id
            stmt.setInt(3, id);     // Truyền project_id

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    members.add(user);
                }
            }
        }
        return members;
    }

    public static void main(String[] args) {

    }

//    public static void main(String[] args) {
//         ProjectDAO eventDAO = new ProjectDAO(); // Giả sử class DAO của bạn có tên là EventDAO
//        int testEventID = 1; // Thay bằng ID có trong database của bạn
//
//        try {
//            Event event = eventDAO.getProjectDetail(testEventID);
//            if (event != null) {
//                System.out.println("Event ID: " + event.getEvent_id());
//                System.out.println("Name: " + event.getName());
//                System.out.println("Leader ID: " + event.getLeader_id());
//                System.out.println("Leader Name: " + event.getLeader_name());
//                System.out.println("Start Date: " + event.getStart_time());
//                System.out.println("End Date: " + event.getEnd_time());
//                System.out.println("Status: " + event.getStatus());
//                System.out.println("Description: " + event.getDescription());
//                System.out.println("Created Date: " + event.getCreated_date());
//                System.out.println("Location: " + event.getLocation());
//            } else {
//                System.out.println("Event not found!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
// Hàm định dạng Timestamp thành chuỗi ngày (bỏ giờ phút)
    private String formatDate(Timestamp timestamp, SimpleDateFormat dateFormat) {
        return (timestamp != null) ? dateFormat.format(timestamp) : null;
    }

// Hàm chuyển đổi chuỗi ngày thành Timestamp (đặt giờ phút giây về 00:00:00)
    private Timestamp convertStringToTimestamp(String dateStr, SimpleDateFormat dateFormat) throws ParseException {
        Date date = dateFormat.parse(dateStr); // Đúng cú pháp
        return new Timestamp(date.getTime());
    }

    // Hàm tiện ích để format Timestamp
//    public static void main(String[] args) {
//        ProjectDAO projectService = new ProjectDAO();
//
//        // Nhập event_id cần test
//        int testEventId = 1;
//
//        // Gọi phương thức getProjectMembers
//        List<ProjectMember> members = projectService.getProjectMembers(testEventId);
//
//        // Kiểm tra kết quả
//        if (members.isEmpty()) {
//            System.out.println("Không tìm thấy thành viên nào cho event_id = " + testEventId);
//        } else {
//            System.out.println("Danh sách thành viên của event_id = " + testEventId);
//            for (ProjectMember member : members) {
//                System.out.println("ID: " + member.getMember_id()
//                        + ", User ID: " + member.getUser_id()
//                        + ", Role: " + member.getRole()
//                        + ", Status: " + member.getStatus()
//                        + ", Username: " + member.getFull_name()
//                        + ", Email: " + member.getEmail());
//            }
//        }
//    }
    public boolean deleteProjectMember(int memberId) {
        String query = "DELETE FROM project_event_member WHERE member_id = ?";

        try (Connection con = DBContext.getJDBCConnection(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, memberId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    public List<ProjectMember> getProjectMembers(int eventId) {
        List<ProjectMember> members = new ArrayList<>();
        String query = "SELECT pm.member_id, pm.user_id, pm.project_id, pm.status, "
                + "u.full_name, u.email "
                + "FROM project_event_member pm "
                + "JOIN user u ON pm.user_id = u.user_id "
                + "WHERE pm.project_id = ? ";

        try (Connection con = DBContext.getJDBCConnection(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProjectMember member = new ProjectMember();
                member.setMember_id(rs.getInt("member_id"));
                member.setUser_id(rs.getInt("user_id"));
                member.setEvent_id(rs.getInt("project_id"));

                member.setStatus(rs.getString("status"));
                member.setFull_name(rs.getString("full_name"));
                member.setEmail(rs.getString("email"));

                members.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }
    private Connection conn;

    public ProjectDAO() {
        this.conn = DBContext.getJDBCConnection();
    }

    public List<User> listClubMembers(int clubId) {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "WHERE cm.club_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clubId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("full_name");

                User user = new User();
                user.setUser_id(id);
                user.setFull_name(name);

                members.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return members;
    }

}

//    //test  list theo club
//    public static void main(String[] args) {
//        try {
//            ProjectDAO projectDAO = new ProjectDAO(); // Giả sử class DAO chứa phương thức
//            int clubID = 1; // Thay bằng ID của CLB muốn test
//
//            List<Event> projects = projectDAO.getProjectsByClubId(clubID);
//
//            if (projects.isEmpty()) {
//                System.out.println("Không có dự án nào trong CLB với ID: " + clubID);
//            } else {
//                System.out.println("Danh sách dự án của CLB ID " + clubID + ":");
//                for (Event p : projects) {
//                    System.out.println("Project ID: " + p.getClub_id());
//                    System.out.println("Tên: " + p.getName());
//                    System.out.println("Leader: " + p.getLeader_name());
//                    System.out.println("Số thành viên: " + p.getTotal_member());
//                    System.out.println("Trạng thái: " + p.getStatus());
//                    System.out.println("Ngày tạo: " + p.getCreated_date());
//                    System.out.println("--------------------------");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

