package DAO.Event;

import java.security.Timestamp;
import model.dto.DBContext;
import model.entity.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.entity.Club;
import model.entity.User;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static model.dto.DBContext.getJDBCConnection;
import model.entity.RegistrationEvent;

public class PublicEventDAO extends DBContext {

    // Phương thức lấy sự kiện theo event_id
    public Event getEventById(int eventId) {
        String sql = "SELECT "
                + "e.event_id, "
                + "e.club_id, "
                + "e.leader_id, "
                + "e.name AS event_name, "
                + "e.avatar_url AS event_avatar_url, "
                + "e.guest, "
                + "e.location, "
                + "e.start_time, "
                + "e.end_time, "
                + "e.created_date, "
                + "e.is_public AS event_is_public, "
                + "e.status AS event_status, "
                + "e.description AS event_description, "
                + "c.club_id, "
                + "c.name AS club_name, "
                + "c.club_email, "
                + "c.club_fanpage_url, "
                + "c.club_logo_url, "
                + "c.is_public AS club_is_public, "
                + "c.status AS club_status, "
                + "u.user_id AS leader_id, "
                + "u.full_name AS leader_name, "
                + "u.email AS leader_email "
                + "FROM event e "
                + "JOIN club c ON e.club_id = c.club_id "
                + "JOIN user u ON e.leader_id = u.user_id "
                + "WHERE e.event_id = ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Lấy thông tin sự kiện
                    Event event = new Event();
                    event.setEvent_id(rs.getInt("event_id"));
                    event.setClub_id(rs.getInt("club_id"));
                    event.setLeader_id(rs.getInt("leader_id"));
                    event.setName(rs.getString("event_name"));
                    event.setAvatar_url(rs.getString("event_avatar_url"));
                    event.setGuest(rs.getString("guest"));
                    event.setLocation(rs.getString("location"));
                    // Lấy dữ liệu thời gian từ ResultSet
                    java.sql.Timestamp startTime = rs.getTimestamp("start_time");
                    java.sql.Timestamp endTime = rs.getTimestamp("end_time");
                    java.sql.Timestamp createdDate = rs.getTimestamp("created_date");

                    // Sử dụng SimpleDateFormat để định dạng lại thời gian
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    // Định dạng thời gian start_time
                    String formattedStartTime = sdf.format(startTime);
                    // Định dạng thời gian end_time
                    String formattedEndTime = sdf.format(endTime);
                    // Định dạng thời gian created_date
                    String formattedCreatedDate = sdf.format(createdDate);

                    // Gán giá trị định dạng lại cho event
                    event.setStart_time(formattedStartTime);
                    event.setEnd_time(formattedEndTime);
                    event.setCreated_date(formattedCreatedDate);
                    event.setIs_public(rs.getString("event_is_public"));
                    event.setStatus(rs.getString("event_status"));
                    event.setDescription(rs.getString("event_description"));

                    // Lấy thông tin câu lạc bộ
                    Club club = new Club();
                    club.setClub_id(rs.getInt("club_id"));
                    club.setName(rs.getString("club_name"));
                    club.setClub_email(rs.getString("club_email"));
                    club.setClub_fanpage_url(rs.getString("club_fanpage_url"));
                    club.setClub_logo_url(rs.getString("club_logo_url"));
                    club.setIs_public(rs.getString("club_is_public"));
                    club.setStatus(rs.getString("club_status"));
                    event.setClub(club);  // Gán câu lạc bộ cho sự kiện

                    // Lấy thông tin người lãnh đạo (leader)
                    User leader = new User();
                    leader.setUser_id(rs.getInt("leader_id"));
                    leader.setFull_name(rs.getString("leader_name"));
                    leader.setEmail(rs.getString("leader_email"));
                    event.setUser(leader);  // Gán người lãnh đạo cho sự kiện

                    return event;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
        return null;  // Nếu không tìm thấy sự kiện
    }

//    public static void main(String[] args) {
//        // Tạo đối tượng EventDAO
//        PublicEventDAO eventDAO = new PublicEventDAO();
//
//        // Chỉ số event_id muốn lấy (ví dụ eventId = 1)
//        int eventId = 1;
//
//        // Lấy sự kiện từ database
//        Event event = eventDAO.getEventById(eventId);
//        // Kiểm tra xem sự kiện có tồn tại không
//        if (event != null) {
//            // In thông tin sự kiện
//            System.out.println("Event ID: " + event.getEventId());
//            System.out.println("Event Name: " + event.getName());
//            System.out.println("Event Location: " + event.getLocation());
//            System.out.println("Event Status: " + event.getStatus());
//            
//            // In thông tin câu lạc bộ và người lãnh đạo
//            if (event.getClub() != null) {
//                System.out.println("Club Name: " + event.getClub().getName());
//            }
//            if (event.getUser() != null) {
//                System.out.println("Leader Name: " + event.getUser().getFull_name());
//            }
//        } else {
//            System.out.println("No event found with ID: " + eventId);
//        }
//    }
    // Phương thức lấy 3 sự kiện ngẫu nhiên theo club_id
    public List<Event> getRandomEventsByClubId(int clubId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.event_id, e.club_id, e.leader_id, e.name, e.avatar_url, e.guest, e.location, "
                + "e.start_time, e.end_time, e.created_date, e.is_public, e.status, e.description "
                + "FROM event e WHERE e.club_id = ? AND e.is_public != 'No' ORDER BY RAND() LIMIT 5";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, clubId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEvent_id(rs.getInt("event_id"));
                event.setClub_id(rs.getInt("club_id"));
                event.setLeader_id(rs.getInt("leader_id"));
                event.setName(rs.getString("name"));
                event.setAvatar_url(rs.getString("avatar_url"));
                event.setGuest(rs.getString("guest"));
                event.setLocation(rs.getString("location"));
                // Lấy dữ liệu thời gian từ ResultSet
                java.sql.Timestamp startTime = rs.getTimestamp("start_time");
                java.sql.Timestamp endTime = rs.getTimestamp("end_time");
                java.sql.Timestamp createdDate = rs.getTimestamp("created_date");

// Sử dụng SimpleDateFormat để định dạng lại thời gian
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

// Định dạng thời gian start_time
                String formattedStartTime = sdf.format(startTime);
// Định dạng thời gian end_time
                String formattedEndTime = sdf.format(endTime);
// Định dạng thời gian created_date
                String formattedCreatedDate = sdf.format(createdDate);

// Gán giá trị định dạng lại cho event
                event.setStart_time(formattedStartTime);
                event.setEnd_time(formattedEndTime);
                event.setCreated_date(formattedCreatedDate);

                event.setIs_public(rs.getString("is_public"));
                event.setStatus(rs.getString("status"));
                event.setDescription(rs.getString("description"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    public List<Event> getAllPublicEvent() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, start_time, \n"
                + "       end_time, created_date, is_public, status, description\n"
                + "FROM event\n"
                + "WHERE is_public = 'Yes'";
        try {
            Connection conn = getJDBCConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setEvent_id(rs.getInt("event_id"));
                event.setClub_id(rs.getInt("club_id"));
                event.setLeader_id(rs.getInt("leader_id"));
                event.setName(rs.getString("name"));
                event.setAvatar_url(rs.getString("avatar_url"));
                event.setGuest(rs.getString("guest"));
                event.setLocation(rs.getString("location"));
                // Lấy dữ liệu thời gian từ ResultSet
                java.sql.Timestamp startTime = rs.getTimestamp("start_time");
                java.sql.Timestamp endTime = rs.getTimestamp("end_time");
                java.sql.Timestamp createdDate = rs.getTimestamp("created_date");

                // Sử dụng SimpleDateFormat để định dạng lại thời gian
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                // Định dạng thời gian start_time
                String formattedStartTime = sdf.format(startTime);
                // Định dạng thời gian end_time
                String formattedEndTime = sdf.format(endTime);
                // Định dạng thời gian created_date
                String formattedCreatedDate = sdf.format(createdDate);

                // Gán giá trị định dạng lại cho event
                event.setStart_time(formattedStartTime);
                event.setEnd_time(formattedEndTime);
                event.setCreated_date(formattedCreatedDate);

                event.setIs_public(rs.getString("is_public"));
                event.setStatus(rs.getString("status"));
                event.setDescription(rs.getString("description"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<Event> getUpcomingEvents(int limit) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event WHERE status = 'Upcoming' LIMIT ?";

        try (Connection con = DBContext.getJDBCConnection(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEvent_id(rs.getInt("event_id"));
                event.setClub_id(rs.getInt("club_id"));
                event.setLeader_id(rs.getInt("leader_id"));
                event.setName(rs.getString("name"));
                event.setAvatar_url(rs.getString("avatar_url"));
                event.setGuest(rs.getString("guest"));
                event.setLocation(rs.getString("location"));
                // Lấy dữ liệu thời gian từ ResultSet
                java.sql.Timestamp startTime = rs.getTimestamp("start_time");
                java.sql.Timestamp endTime = rs.getTimestamp("end_time");
                java.sql.Timestamp createdDate = rs.getTimestamp("created_date");

                // Sử dụng SimpleDateFormat để định dạng lại thời gian
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                // Định dạng thời gian start_time
                String formattedStartTime = sdf.format(startTime);
                // Định dạng thời gian end_time
                String formattedEndTime = sdf.format(endTime);
                // Định dạng thời gian created_date
                String formattedCreatedDate = sdf.format(createdDate);

                // Gán giá trị định dạng lại cho event
                event.setStart_time(formattedStartTime);
                event.setEnd_time(formattedEndTime);
                event.setCreated_date(formattedCreatedDate);

                event.setIs_public(rs.getString("is_public"));
                event.setStatus(rs.getString("status"));
                event.setDescription(rs.getString("description"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static void main(String[] args) {
        // Tạo đối tượng EventDAO
        PublicEventDAO eventDAO = new PublicEventDAO();

        // Đặt club_id mà bạn muốn kiểm tra
        int clubId = 1;  // Ví dụ club_id là 1, thay bằng giá trị thực tế trong cơ sở dữ liệu của bạn

        // Lấy 3 sự kiện ngẫu nhiên theo club_id
        List<Event> randomEvents = eventDAO.getRandomEventsByClubId(clubId);

        // In kết quả ra console
        if (randomEvents.isEmpty()) {
            System.out.println("Không có sự kiện nào được tìm thấy cho club_id: " + clubId);
        } else {
            System.out.println("Danh sách 3 sự kiện ngẫu nhiên cho club_id " + clubId + ":");
            for (Event event : randomEvents) {
                System.out.println("Event ID: " + event.getEvent_id());
                System.out.println("Name: " + event.getName());
                System.out.println("Start Time: " + event.getStart_time());
                System.out.println("End Time: " + event.getEnd_time());
                System.out.println("Location: " + event.getLocation());
                System.out.println("Description: " + event.getDescription());
                System.out.println("-----------------------------------");
            }
        }
    }

    public boolean registerForEvent(RegistrationEvent registration) {
        String sql = "INSERT INTO registration (event_id, register_id, full_name, email, mobile, status, note) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, registration.getEvent_id());
            ps.setInt(2, registration.getRegister_id());
            ps.setString(3, registration.getFull_name());
            ps.setString(4, registration.getEmail());
            ps.setString(5, registration.getMobile());
            ps.setString(6, registration.getStatus());
            ps.setString(7, registration.getNote());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserRegisteredForEvent(int userId, int eventId) {
        String sql = "SELECT COUNT(*) FROM registration WHERE register_id = ? AND event_id = ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Club> getClubsFromPublicEvents() {
        List<Club> clubs = new ArrayList<>();
        String query = "SELECT DISTINCT c.club_id, c.name "
                + "FROM event e "
                + "JOIN club c ON e.club_id = c.club_id "
                + "WHERE e.is_public = 'Yes'";

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Club club = new Club();
                int clubId = rs.getInt("club_id");
                String clubName = rs.getString("name");
                club.setClub_id(clubId);
                club.setName(clubName);
                clubs.add(club);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubs;
    }

    public List<Event> getPublicEventsByClubId(Integer clubId, String searchQuery) {
        List<Event> events = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, start_time, "
                + "end_time, created_date, is_public, status, description "
                + "FROM event WHERE is_public = 'Yes'");

        if (clubId != null) {
            sql.append(" AND club_id = ?");
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND LOWER(name) LIKE ?");
        }

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (clubId != null) {
                ps.setInt(paramIndex++, clubId);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                ps.setString(paramIndex, "%" + searchQuery.toLowerCase() + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setEvent_id(rs.getInt("event_id"));
                event.setClub_id(rs.getInt("club_id"));
                event.setLeader_id(rs.getInt("leader_id"));
                event.setName(rs.getString("name"));
                event.setAvatar_url(rs.getString("avatar_url"));
                event.setGuest(rs.getString("guest"));
                event.setLocation(rs.getString("location"));

                // Lấy và định dạng thời gian
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                event.setStart_time(sdf.format(rs.getTimestamp("start_time")));
                event.setEnd_time(sdf.format(rs.getTimestamp("end_time")));
                event.setCreated_date(sdf.format(rs.getTimestamp("created_date")));

                event.setIs_public(rs.getString("is_public"));
                event.setStatus(rs.getString("status"));
                event.setDescription(rs.getString("description"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean isUserMemberOfEventClub(int userId, int eventId) {
        String sql = "SELECT COUNT(*) FROM club_member cm "
                + "JOIN event e ON cm.club_id = e.club_id "
                + "WHERE cm.user_id = ? AND e.event_id = ? AND cm.status != 'Rejected'";

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isEmailRegisteredForEvent(String email, int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM registration WHERE email = ? AND event_id = ?";
        boolean isRegistered = false;

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setInt(2, eventId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isRegistered = resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if email is registered: " + e.getMessage());
            throw e;
        }

        return isRegistered;
    }

    public boolean registerGuestForEvent(String email, String fullName, String mobile, int eventId, String note)
            throws SQLException {
        String sql = "INSERT INTO registration (event_id, register_id, full_name, email, mobile, status, note) "
                + "VALUES (?, NULL, ?, ?, ?, 'Pending', ?)";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, eventId);
            statement.setString(2, fullName);
            statement.setString(3, email);
            statement.setString(4, mobile);
            statement.setString(5, note);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error registering guest for event: " + e.getMessage());
            throw e;
        }
    }

    public int countPublicEventsByClubId(Integer clubId, String searchQuery) {
        int totalEvents = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM event WHERE is_public = 'Yes'");

        if (clubId != null) {
            sql.append(" AND club_id = ?");
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND LOWER(name) LIKE LOWER(?)");
        }

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (clubId != null) {
                ps.setInt(paramIndex++, clubId);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                ps.setString(paramIndex, "%" + searchQuery.toLowerCase() + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalEvents = rs.getInt(1); // Lấy số lượng sự kiện từ COUNT(*)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalEvents;
    }

    public List<Event> getAllPublicEvent(int page, int pageSize) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, start_time, "
                + "end_time, created_date, is_public, status, description "
                + "FROM event "
                + "WHERE is_public = 'Yes' "
                + "ORDER BY start_time DESC "
                + // Sắp xếp theo thời gian bắt đầu mới nhất
                "LIMIT ? OFFSET ?"; // Thêm phân trang

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEvent_id(rs.getInt("event_id"));
                    event.setClub_id(rs.getInt("club_id"));
                    event.setLeader_id(rs.getInt("leader_id"));
                    event.setName(rs.getString("name"));
                    event.setAvatar_url(rs.getString("avatar_url"));
                    event.setGuest(rs.getString("guest"));
                    event.setLocation(rs.getString("location"));

                    // Lấy dữ liệu thời gian từ ResultSet
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    event.setStart_time(sdf.format(rs.getTimestamp("start_time")));
                    event.setEnd_time(sdf.format(rs.getTimestamp("end_time")));
                    event.setCreated_date(sdf.format(rs.getTimestamp("created_date")));

                    event.setIs_public(rs.getString("is_public"));
                    event.setStatus(rs.getString("status"));
                    event.setDescription(rs.getString("description"));

                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<Event> getPublicEventsByClubId(Integer clubId, String searchQuery, int page, int pageSize) {
        List<Event> events = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, start_time, "
                + "end_time, created_date, is_public, status, description "
                + "FROM event WHERE is_public = 'Yes'");

        if (clubId != null) {
            sql.append(" AND club_id = ?");
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND LOWER(name) LIKE LOWER(?)"); // Chuyển cả 2 về chữ thường để so sánh
        }

        // Thêm phân trang
        sql.append(" ORDER BY created_date ASC LIMIT ? OFFSET ?");

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (clubId != null) {
                ps.setInt(paramIndex++, clubId);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchQuery.toLowerCase() + "%"); // Chuyển search về chữ thường
            }
            // Xử lý phân trang
            int offset = (page - 1) * pageSize; // Tính toán OFFSET
            ps.setInt(paramIndex++, pageSize); // Số bản ghi trên mỗi trang
            ps.setInt(paramIndex++, offset); // Offset để lấy đúng dữ liệu

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setEvent_id(rs.getInt("event_id"));
                event.setClub_id(rs.getInt("club_id"));
                event.setLeader_id(rs.getInt("leader_id"));
                event.setName(rs.getString("name"));
                event.setAvatar_url(rs.getString("avatar_url"));
                event.setGuest(rs.getString("guest"));
                event.setLocation(rs.getString("location"));

                // Định dạng thời gian
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                event.setStart_time(sdf.format(rs.getTimestamp("start_time")));
                event.setEnd_time(sdf.format(rs.getTimestamp("end_time")));
                event.setCreated_date(sdf.format(rs.getTimestamp("created_date")));

                event.setIs_public(rs.getString("is_public"));
                event.setStatus(rs.getString("status"));
                event.setDescription(rs.getString("description"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
