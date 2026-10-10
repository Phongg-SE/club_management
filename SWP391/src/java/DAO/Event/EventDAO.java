package DAO.Event;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.text.SimpleDateFormat;
import model.dto.DBContext;
import model.entity.Event;
import java.util.Date;

public class EventDAO {

//get list Event
    public List<Event> listAllEvents() throws SQLException {
        List<Event> listEvents = new ArrayList<>();
        String sql = "SELECT e.event_id, e.club_id, e.leader_id, u.full_name, e.name, e.avatar_url, e.guest, e.location, e.start_time, e.end_time, e.created_date, e.is_public, e.status, e.description\n"
                + "FROM event e\n"
                + "JOIN user u on e.leader_id = u.user_id";

        Connection connection = DBContext.getJDBCConnection();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Không hiển thị giây

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int eventID = resultSet.getInt("event_id");
                int clubID = resultSet.getInt("club_id");
                int leaderID = resultSet.getInt("leader_id");
                String leaderName = resultSet.getString("full_name");
                String eventName = resultSet.getString("name");
                String image = resultSet.getString("avatar_url");
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

                Event event = new Event(eventID, clubID, leaderID, leaderName, eventName, image, guest, location, startTime, endTime, createdDate, isPublic, status, description);

                listEvents.add(event);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } else {
            System.out.println("Kết nối cơ sở dữ liệu không thành công.");
        }

        return listEvents;
    }

//    // test get all lists
//    public static void main(String[] args) {
//        EventDAO eventDAO = new EventDAO();
//        try {
//            List<Event> events = eventDAO.listAllEvents();
//            for (Event event : events) {
//                System.out.println("Event ID: " + event.getEvent_id());
//                System.out.println("Club ID: " + event.getClub_id());
//                System.out.println("Leader ID: " + event.getLeader_id());
//                System.out.println("Leader Name: " + event.getLeader_name());
//                System.out.println("Name: " + event.getName());
//                System.out.println("Avatar URL: " + event.getAvatar_url());
//                System.out.println("Guest: " + event.getGuest());
//                System.out.println("Location: " + event.getLocation());
//                System.out.println("Start Time: " + event.getStart_time());
//                System.out.println("End Time: " + event.getEnd_time());
//                System.out.println("Created Date: " + event.getCreated_date());
//                System.out.println("Is Public: " + event.getIs_public());
//                System.out.println("Status: " + event.getStatus());
//                System.out.println("Description: " + event.getDescription());
//                System.out.println("-----------------------");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public List<Event> getEventByClubId(int clubID) throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.event_id, e.name, e.club_id, e.leader_id, u.full_name, e.avatar_url, "
                + "e.guest, e.location, e.start_time, e.end_time, e.created_date, "
                + "e.is_public, e.status, e.description "
                + "FROM event e "
                + "JOIN user u ON e.leader_id = u.user_id "
                + "WHERE e.club_id = ?";

        Connection connection = DBContext.getJDBCConnection();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clubID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int eventID = resultSet.getInt("event_id");
                String eventName = resultSet.getString("name");
                int leaderID = resultSet.getInt("leader_id");
                String leaderName = resultSet.getString("full_name");
                String image = resultSet.getString("avatar_url");
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

                Event event = new Event(eventID, clubID, leaderID, leaderName, eventName, image, guest, location, startTime, endTime, createdDate, isPublic, status, description);
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

//    //test get event by clubID
//    public static void main(String[] args) {
//        EventDAO eventDAO = new EventDAO(); // Giả sử class DAO của bạn là EventDAO
//        int testClubID = 1; // Nhập ID CLB bạn muốn test
//
//        try {
//            List<Event> events = eventDAO.getEventByClubId(testClubID);
//
//            if (events.isEmpty()) {
//                System.out.println("Không có sự kiện nào thuộc CLB ID: " + testClubID);
//            } else {
//                for (Event event : events) {
//                    System.out.println("Event ID: " + event.getEvent_id());
//                    System.out.println("Event Name: " + event.getName());
//                    System.out.println("Leader Name: " + event.getLeader_name());
//                    System.out.println("Location: " + event.getLocation());
//                    System.out.println("Start Time: " + event.getStart_time());
//                    System.out.println("End Time: " + event.getEnd_time());
//                    System.out.println("Status: " + event.getStatus());
//                    System.out.println("-------------------------------------");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Lỗi khi truy xuất dữ liệu: " + e.getMessage());
//        }
//    }
//    
    public Event getEventById(int eventID) throws SQLException {
        Event event = null;
        String sql = "SELECT e.event_id,  e.name, e.club_id, e.leader_id, u.full_name, e.avatar_url, e.guest, e.location, e.start_time, e.end_time, e.created_date, e.is_public, e.status, e.description\n"
                + "FROM event e\n"
                + "JOIN user u on e.leader_id = u.user_id WHERE event_id = ? ";

        Connection connection = DBContext.getJDBCConnection();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Không hiển thị giây

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, eventID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String eventName = resultSet.getString("name");
                int clubID = resultSet.getInt("club_id");
                int leaderID = resultSet.getInt("leader_id");
                String leaderName = resultSet.getString("full_name");
                String image = resultSet.getString("avatar_url");
                String guest = resultSet.getString("guest");
                String location = resultSet.getString("location");

                Timestamp startTimeRaw = resultSet.getTimestamp("start_time");
                Timestamp endTimeRaw = resultSet.getTimestamp("end_time");
                Date createdDateRaw = resultSet.getDate("created_date");

                String startTime = (startTimeRaw != null) ? dateTimeFormat.format(startTimeRaw) : null;
                String endTime = (endTimeRaw != null) ? dateTimeFormat.format(endTimeRaw) : null;
                String createdDate = (createdDateRaw != null) ? dateTimeFormat.format(createdDateRaw) : null;

                String isPublic = resultSet.getString("is_public");
                String status = resultSet.getString("status");
                String description = resultSet.getString("description");

                event = new Event(eventID, clubID, leaderID, leaderName, eventName, image, guest, location, startTime, endTime, createdDate, isPublic, status, description);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } else {
            System.out.println("Kết nối cơ sở dữ liệu không thành công.");
        }

        return event;
    }

//    //test getEventById
//    public static void main(String[] args) {
//        EventDAO eventDAO = new EventDAO(); // Gi? s? EventDAO l� l?p ch?a ph??ng th?c getEventById
//        int eventID = 1; // ID s? ki?n c?n ki?m th?
//
//        try {
//            Event event = eventDAO.getEventById(eventID);
//            if (event != null) {
//                System.out.println("Event ID: " + event.getEvent_id());
//                System.out.println("Name: " + event.getName());
//                System.out.println("Club ID: " + event.getClub_id());
//                System.out.println("Leader ID: " + event.getLeader_id());
//                System.out.println("Leader Name: " + event.getLeader_name());
//                System.out.println("Image URL: " + event.getAvatar_url());
//                System.out.println("Guest: " + event.getGuest());
//                System.out.println("Location: " + event.getLocation());
//                System.out.println("Start Time: " + event.getStart_time());
//                System.out.println("End Time: " + event.getEnd_time());
//                System.out.println("Created Date: " + event.getCreated_date());
//                System.out.println("Is Public: " + event.getIs_public());
//                System.out.println("Status: " + event.getStatus());
//                System.out.println("Description: " + event.getDescription());
//            } else {
//                System.out.println("No event found with ID: " + eventID);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching event: " + e.getMessage());
//        }
//    }
    public boolean updateEvent(Event event) throws SQLException {
        Connection connection = DBContext.getJDBCConnection();
        if (connection != null) {
            String updateSql = """
            UPDATE event 
            SET name = ?, club_id = ?, leader_id = ?, avatar_url = ?, guest = ?, location = ?, 
                start_time = ?, end_time = ?, is_public = ?, status = ?, description = ? 
            WHERE event_id = ?
        """;

            // Xóa leader cũ khỏi `project_event_member`
            String deleteOldLeaderSql = """
            DELETE pem FROM project_event_member pem
            JOIN event e ON pem.event_id = e.event_id
            WHERE e.event_id = ? AND pem.user_id = e.leader_id
        """;

            // Thêm leader mới nếu chưa có
            String insertNewLeaderSql = """
            INSERT INTO project_event_member (user_id, event_id, status)
            SELECT ?, ?, 'Active'
            WHERE NOT EXISTS (
                SELECT 1 FROM project_event_member 
                WHERE user_id = ? AND event_id = ?
            )
        """;

            try {
                connection.setAutoCommit(false); // Bắt đầu transaction

                // Cập nhật event
                try (PreparedStatement pstmt = connection.prepareStatement(updateSql)) {
                    pstmt.setString(1, event.getName());
                    pstmt.setInt(2, event.getClub_id());
                    pstmt.setInt(3, event.getLeader_id());
                    pstmt.setString(4, event.getAvatar_url());
                    pstmt.setString(5, event.getGuest());
                    pstmt.setString(6, event.getLocation());
                    pstmt.setString(7, event.getStart_time());
                    pstmt.setString(8, event.getEnd_time());
                    pstmt.setString(9, event.getIs_public());
                    pstmt.setString(10, event.getStatus());
                    pstmt.setString(11, event.getDescription());
                    pstmt.setInt(12, event.getEvent_id());
                    pstmt.executeUpdate();
                }

                // Xóa leader cũ nếu có
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteOldLeaderSql)) {
                    deleteStmt.setInt(1, event.getEvent_id());
                    deleteStmt.executeUpdate();
                }

                // Thêm leader mới nếu chưa có
                try (PreparedStatement insertStmt = connection.prepareStatement(insertNewLeaderSql)) {
                    insertStmt.setInt(1, event.getLeader_id());
                    insertStmt.setInt(2, event.getEvent_id());
                    insertStmt.setInt(3, event.getLeader_id());
                    insertStmt.setInt(4, event.getEvent_id());
                    insertStmt.executeUpdate();
                }

                connection.commit(); // Xác nhận thay đổi
                return true;
            } catch (SQLException e) {
                connection.rollback(); // Hoàn tác nếu có lỗi
                e.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws SQLException {
        EventDAO eventDAO = new EventDAO();

        // Tạo một sự kiện mẫu để cập nhật
        Event event = new Event();
        event.setEvent_id(2); // ID của event cần update
        event.setName("New Event Name");
        event.setClub_id(2);
        event.setLeader_id(14); // ID của leader mới
        event.setAvatar_url(null);
        event.setGuest("Guest Speaker");
        event.setLocation("Conference Room");
        event.setStart_time("2025-04-01 10:00:00");
        event.setEnd_time("2025-04-01 12:00:00");
        event.setIs_public("Yes");
        event.setStatus("Ongoing");
        event.setDescription("Updated event description");

        // Gọi hàm update
        boolean success = eventDAO.updateEvent(event);

        // Kiểm tra kết quả
        if (success) {
            System.out.println("Cập nhật sự kiện thành công!");
        } else {
            System.out.println("Cập nhật sự kiện thất bại!");
        }
    }

    public boolean addEvent(Event event) {
        String eventSql = "INSERT INTO event (name, club_id, type, leader_id, avatar_url, guest, location, start_time, end_time, is_public, status, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String memberSql = "INSERT INTO project_event_member (user_id, event_id, status) VALUES (?, ?, 'Active')";

        try (Connection conn = DBContext.getJDBCConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Thêm sự kiện vào bảng event
            try (PreparedStatement stmt = conn.prepareStatement(eventSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, event.getName());
                stmt.setInt(2, event.getClub_id());
                stmt.setString(3, event.getType());
                stmt.setInt(4, event.getLeader_id());

                // Kiểm tra giá trị null cho avatar_url
                if (event.getAvatar_url() != null) {
                    stmt.setString(5, event.getAvatar_url());
                } else {
                    stmt.setNull(5, Types.VARCHAR);
                }

                // Kiểm tra giá trị null cho guest
                if (event.getGuest() != null) {
                    stmt.setString(6, event.getGuest());
                } else {
                    stmt.setNull(6, Types.VARCHAR);
                }

                stmt.setString(7, event.getLocation());
                stmt.setString(8, event.getStart_time());
                stmt.setString(9, event.getEnd_time());
                stmt.setString(10, event.getIs_public());
                stmt.setString(11, event.getStatus());

                // Kiểm tra giá trị null cho description
                if (event.getDescription() != null) {
                    stmt.setString(12, event.getDescription());
                } else {
                    stmt.setNull(12, Types.VARCHAR);
                }

                int rowsInserted = stmt.executeUpdate();

                // Lấy event_id mới được tạo
                int eventId = -1;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        eventId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Lỗi: Không thể lấy event_id sau khi insert.");
                    }
                }

                // 2. Thêm leader vào bảng project_event_member
                try (PreparedStatement memberStmt = conn.prepareStatement(memberSql)) {
                    memberStmt.setInt(1, event.getLeader_id());
                    memberStmt.setInt(2, eventId);
                    memberStmt.executeUpdate();
                }

                conn.commit(); // Xác nhận transaction
                return rowsInserted > 0;

            } catch (SQLException e) {
                conn.rollback(); // Rollback nếu có lỗi
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true); // Trả lại trạng thái ban đầu
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    //test add
//    public static void main(String[] args) {
//        EventDAO eventDAO = new EventDAO(); // Tạo DAO để gọi addEvent()
//
//        // Tạo đối tượng Event với dữ liệu test
//        Event event = new Event();
//        event.setName("Phát vé concert");
//        event.setClub_id(2);  // ID câu lạc bộ
//        event.setType("Event");
//        event.setLeader_id(13); // ID người tổ chức
//        event.setAvatar_url(null);
//        event.setGuest(null);
//        event.setLocation("Sảnh Delta");
//        event.setStart_time("2025-03-28 06:00:00");
//        event.setEnd_time("2025-03-28 12:00:00");
//        event.setIs_public("Yes"); // Công khai
//        event.setStatus("Planned");
//        event.setDescription(null);
//
//        // Gọi hàm addEvent() để thêm sự kiện vào database
//        boolean result = eventDAO.addEvent(event);
//
//        // Kiểm tra kết quả
//        if (result) {
//            System.out.println("✅ Thêm sự kiện thành công!");
//        } else {
//            System.out.println("❌ Thêm sự kiện thất bại!");
//        }
//    }
    //listByFilter
    public List<Event> listEventByFilter(int clubId, String status, String isPublic, String searchName, int page) throws SQLException {
        List<Event> events = new ArrayList<>();
        int recordsPerPage = 5; // Mỗi trang có 5 bản ghi
        int offset = (page - 1) * recordsPerPage; // Tính OFFSET dựa vào trang

        StringBuilder sql = new StringBuilder(
                "SELECT e.event_id, e.club_id, e.type, e.name, e.avatar_url, e.guest, "
                + "e.location, e.start_time, e.end_time, e.created_date, e.is_public, "
                + "e.status, e.description, e.leader_id, u.full_name AS leader_name "
                + "FROM event e "
                + "JOIN user u ON e.leader_id = u.user_id "
                + "WHERE e.club_id = ? AND e.type = 'Event' "
        );

        // Điều kiện lọc theo status, is_public và eventName
        if (status != null && !status.isEmpty()) {
            sql.append(" AND e.status = ?");
        }
        if (isPublic != null && !isPublic.isEmpty()) {
            sql.append(" AND e.is_public = ?");
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
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int index = 1;
            statement.setInt(index++, clubId);

            if (status != null && !status.isEmpty()) {
                statement.setString(index++, status);
            }
            if (isPublic != null && !isPublic.isEmpty()) {
                statement.setString(index++, isPublic);
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
                            resultSet.getString("avatar_url"),
                            resultSet.getString("guest"),
                            resultSet.getString("location"),
                            startTime,
                            endTime,
                            createdDate,
                            resultSet.getString("is_public"),
                            resultSet.getString("status"),
                            resultSet.getString("description")
                    ));
                }
            }
        }

        return events;
    }

    public int getTotalPages(int clubId, String status, String isPublic, String searchName) throws SQLException {
        int recordsPerPage = 5; // Số bản ghi mỗi trang
        int totalRecords = 0; // Tổng số bản ghi

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM event WHERE club_id = ? AND type = 'Event'"
        );

        // Điều kiện lọc theo status, is_public và eventName
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
        }
        if (isPublic != null && !isPublic.isEmpty()) {
            sql.append(" AND is_public = ?");
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
            if (isPublic != null && !isPublic.isEmpty()) {
                statement.setString(index++, isPublic);
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

//    //test listByFilter
//    public static void main(String[] args) {
//        // Test với các tham số giả lập
//        int clubId = 1; // Giả sử club ID là 1
//        String status = ""; // Lọc theo status
//        String isPublic = ""; // Lọc theo public
//        String searchName = ""; // Tìm kiếm theo tên sự kiện
//        int page = 1; // Trang hiện tại
//
//        EventDAO eventDAO = new EventDAO();
//
//        try {
//            // Gọi phương thức listEventByFilter để lấy danh sách sự kiện với phân trang
//            List<Event> events = eventDAO.listEventByFilter(clubId, status, isPublic, searchName, page);
//
//            // In ra danh sách sự kiện
//            if (events != null && !events.isEmpty()) {
//                System.out.println("Danh sách sự kiện: ");
//                for (Event event : events) {
//                    System.out.println(event);
//                }
//            } else {
//                System.out.println("Không có sự kiện nào được tìm thấy.");
//            }
//
//            // Gọi phương thức getTotalPages để tính số trang
//            int totalPages = eventDAO.getTotalPages(clubId, status, isPublic, searchName);
//            System.out.println("Tổng số trang: " + totalPages);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    //check co phai eventLeader khong
    public boolean isEventLeader(int userId, int clubId) {
        String sql = "SELECT COUNT(*) \n"
                + "FROM club_member \n"
                + "WHERE user_id = ? AND club_id = ? AND role_id = 2;";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, clubId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0 => Là Event Leader
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//
//        EventDAO eventDAO = new EventDAO();
//
//        // Nhập userId cần kiểm tra
//        int testUserId = 52;
//        int testClubId = 1;// Thay đổi giá trị này để test với user khác
//
//        // Kiểm tra quyền Event Leader
//        boolean isLeader = eventDAO.isEventLeader(testUserId, testClubId);
//
//        // In kết quả ra màn hình
//        if (isLeader) {
//            System.out.println("User ID " + testUserId + " là Event Leader.");
//        } else {
//            System.out.println("User ID " + testUserId + " KHÔNG phải là Event Leader.");
//        }
//    }
    private static void printEventList(List<Event> eventList) {
        if (eventList.isEmpty()) {
            System.out.println("Không có dữ liệu.");
        } else {
            for (Event e : eventList) {
                System.out.println(e);
            }
        }
    }

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

    //day la code cua duy
    public boolean isUserRegisteredForEvent(int userId, int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM registration WHERE register_id = ? AND event_id = ? AND status = 'Pending'";
        boolean isRegistered = false;

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, eventId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isRegistered = resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user is registered: " + e.getMessage());
            throw e;
        }

        return isRegistered;
    }

    public boolean isUserJoinedEvent(int userId, int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM registration WHERE register_id = ? AND event_id = ? AND status = 'Confirmed'";
        boolean isJoined = false;

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, eventId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isJoined = resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user has joined: " + e.getMessage());
            throw e;
        }

        return isJoined;
    }

    public boolean registerUserForEventWithMobile(int userId, int eventId, String note, String mobile) throws SQLException {
        String sql = "INSERT INTO registration (event_id, register_id, full_name, email, mobile, status, note) "
                + "SELECT ?, ?, u.full_name, u.email, ?, 'Pending', ? "
                + "FROM user u WHERE u.user_id = ?";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, eventId);
            statement.setInt(2, userId);
            // Use the provided mobile number if available, otherwise use null
            if (mobile != null && !mobile.trim().isEmpty()) {
                statement.setString(3, mobile);
            } else {
                // Try to get the user's phone number from the database
                statement.setNull(3, java.sql.Types.VARCHAR);
            }
            statement.setString(4, note);
            statement.setInt(5, userId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user for event with mobile: " + e.getMessage());
            throw e;
        }
    }

    public boolean registerUserForEvent(int userId, int eventId, String note) throws SQLException {
        String sql = "INSERT INTO registration (event_id, register_id, full_name, email, mobile, status, note) "
                + "SELECT ?, ?, u.full_name, u.email, u.phone_number, 'Pending', ? "
                + "FROM user u WHERE u.user_id = ?";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, eventId);
            statement.setInt(2, userId);
            statement.setString(3, note);
            statement.setInt(4, userId);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user for event: " + e.getMessage());
            throw e;
        }
    }
}
