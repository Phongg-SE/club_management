/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Task;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.dto.DBContext;
import static model.dto.DBContext.getJDBCConnection;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.Event;
import model.entity.Task;
import model.entity.User;
import model.entity.UserRole;

/**
 *
 * @author datkh
 */
public class TaskDAO {

    //task thuoc event
    public static List<Integer> getEventsWhereUserIsEventLeader(int userId) {
        List<Integer> eventIds = new ArrayList<>();
        String sql = "SELECT event_id FROM event WHERE leader_id = ? AND type = 'Event' ";

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    eventIds.add(rs.getInt("event_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventIds;
    }

    public static List<Integer> getClubsWhereUserIsLeader(int userId) {
        List<Integer> clubIds = new ArrayList<>();
        String sql = "SELECT club_id FROM club_member WHERE user_id = ? AND role_id = 4 ";
        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clubIds.add(rs.getInt("club_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubIds;
    }

    public static List<Task> getUserTasks(int userId, int page, int pageSize) {
        List<Task> tasks = new ArrayList<>();
        Set<Integer> seenTaskIds = new HashSet<>();
        int offset = (page - 1) * pageSize;

        List<Integer> leaderClubs = getClubsWhereUserIsLeader(userId);
        List<Integer> leaderEvents = getEventsWhereUserIsEventLeader(userId);
        String userTasksSQL = "SELECT t.*, "
                + "a.full_name AS assigner_name, a.email AS assigner_email, "
                + "b.full_name AS assignee_name, b.email AS assignee_email, "
                + "e.name AS event_name, c.name AS club_name, "
                + "COALESCE(sa.value, 'Member') AS assigner_role, "
                + "COALESCE(sb.value, 'Member') AS assignee_role "
                + "FROM task t "
                + "JOIN user a ON t.assigner_id = a.user_id "
                + "JOIN user b ON t.assignee_id = b.user_id "
                + "JOIN club c ON t.club_id = c.club_id "
                + "LEFT JOIN club_member cma ON a.user_id = cma.user_id AND cma.club_id = t.club_id "
                + "LEFT JOIN setting sa ON cma.role_id = sa.setting_id "
                + "LEFT JOIN club_member cmb ON b.user_id = cmb.user_id AND cmb.club_id = t.club_id "
                + "LEFT JOIN setting sb ON cmb.role_id = sb.setting_id "
                + "JOIN event e ON e.event_id = t.event_id "
                + "WHERE (t.assigner_id = ? OR t.assignee_id = ?) AND e.type = 'Event'";

        // Nếu user là leader, thêm điều kiện club_id vào truy vấn
        if (!leaderClubs.isEmpty()) {
            String clubFilter = leaderClubs.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")"));
            userTasksSQL += " OR (t.club_id IN " + clubFilter + " AND e.type = 'Event')";
        }

        if (!leaderEvents.isEmpty()) {
            String eventFilter = leaderEvents.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")"));
            userTasksSQL += " OR (e.event_id IN " + eventFilter + " AND e.leader_id = " + userId + "  AND e.type = 'Event') ";
        }

        userTasksSQL += " ORDER BY t.deadline DESC LIMIT ? OFFSET ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(userTasksSQL)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, pageSize);
            stmt.setInt(4, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int taskId = rs.getInt("task_id");
                    if (!seenTaskIds.contains(taskId)) {
                        Task task = new Task(
                                taskId,
                                rs.getInt("event_id"),
                                rs.getInt("club_id"),
                                rs.getString("title"),
                                rs.getInt("assigner_id"),
                                rs.getInt("assignee_id"),
                                rs.getTimestamp("deadline"),
                                rs.getString("status"),
                                rs.getString("description")
                        );
                        task.setEventname(rs.getString("event_name"));
                        task.setClubname(rs.getString("club_name"));
                        task.setAssignerName(rs.getString("assigner_name"));
                        task.setAssignerEmail(rs.getString("assigner_email"));
                        task.setAssignerRole(rs.getString("assigner_role"));
                        task.setAssigneeName(rs.getString("assignee_name"));
                        task.setAssigneeEmail(rs.getString("assignee_email"));
                        task.setAssigneeRole(rs.getString("assignee_role"));

                        tasks.add(task);
                        seenTaskIds.add(taskId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public static Task getTaskByTaskId(int taskId) {
        Task task = null;
        String sql = "SELECT t.*, c.name AS club_name, "
                + "a.full_name AS assigner_name, a.email AS assigner_email, a.avatar_url AS assigner_avatar, "
                + "b.full_name AS assignee_name, b.email AS assignee_email, b.avatar_url AS assignee_avatar, "
                + "e.name AS event_name, e.type AS type, "
                + "COALESCE(sa.value, 'Member') AS assigner_role, "
                + "COALESCE(sb.value, 'Member') AS assignee_role "
                + "FROM task t "
                + "JOIN club c ON t.club_id = c.club_id "
                + "JOIN user a ON t.assigner_id = a.user_id "
                + "LEFT JOIN user b ON t.assignee_id = b.user_id " // Đảm bảo JOIN LEFT tránh NULL lỗi
                + "LEFT JOIN club_member cma ON a.user_id = cma.user_id AND cma.club_id = t.club_id "
                + "LEFT JOIN setting sa ON cma.role_id = sa.setting_id "
                + "LEFT JOIN club_member cmb ON b.user_id = cmb.user_id AND cmb.club_id = t.club_id "
                + "LEFT JOIN setting sb ON cmb.role_id = sb.setting_id "
                + "LEFT JOIN event e ON e.event_id = t.event_id " // Đổi từ INNER JOIN sang LEFT JOIN
                + "WHERE t.task_id = ? " // Sửa WHERE clause để tìm đúng task_id
                + "ORDER BY t.deadline ASC LIMIT 100";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    task = new Task(
                            rs.getInt("task_id"),
                            rs.getInt("event_id"),
                            rs.getInt("club_id"),
                            rs.getString("title"),
                            rs.getInt("assigner_id"),
                            rs.getInt("assignee_id"),
                            rs.getTimestamp("deadline"),
                            rs.getString("status"),
                            rs.getString("description")
                    );
                    // Thêm thông tin bổ sung
                    task.setEventname(rs.getString("event_name"));
                    task.setClubname(rs.getString("club_name"));
                    task.setNote(rs.getString("note"));
                    task.setType(rs.getString("type"));
                    task.setAssignerName(rs.getString("assigner_name"));
                    task.setAssignerEmail(rs.getString("assigner_email"));
                    task.setAssignerRole(rs.getString("assigner_role"));
                    task.setAssigner_avatar(rs.getString("assigner_avatar"));

                    task.setAssigneeName(rs.getString("assignee_name"));
                    task.setAssigneeEmail(rs.getString("assignee_email"));
                    task.setAssigneeRole(rs.getString("assignee_role"));
                    task.setAssignee_avatar(rs.getString("assignee_avatar"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public static boolean addTask(Task task) {
        String sql = "INSERT INTO task (event_id, club_id, title, assigner_id, assignee_id, deadline, status, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, task.getEventId());
            stmt.setInt(2, task.getClubId());
            stmt.setString(3, task.getTitle());
            stmt.setInt(4, task.getAssignerId());
            stmt.setInt(5, task.getAssigneeId());
            stmt.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            stmt.setString(7, task.getStatus());
            stmt.setString(8, task.getDescription());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Club> getClubIdByUserId(int userId) {
        List<Club> clubs = new ArrayList<>();
        String sql = "SELECT DISTINCT c.club_id, c.name "
                + "FROM club c "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "WHERE cm.user_id = ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Club c = new Club();
                    c.setClub_id(rs.getInt("club_id"));
                    c.setName(rs.getString("name"));
                    clubs.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    public static List<ClubMember> getAllClubMembersByEventOrProjectId(int eventId, String type) {
        List<ClubMember> clubMembers = new ArrayList<>();

        String sql = "SELECT DISTINCT u.user_id, u.full_name "
                + "FROM user u "
                + "JOIN project_event_member pem ON u.user_id = pem.user_id "
                + "JOIN event e ON pem.event_id = e.event_id OR pem.project_id = e.event_id "
                + // JOIN để lấy thông tin event
                "WHERE e.event_id = ? AND e.type = ?";          // Lọc theo event_id và type

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            stmt.setString(2, type);  // Gán giá trị cho type

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClubMember clubMember = new ClubMember();
                    clubMember.setUserId(rs.getInt("user_id"));
                    clubMember.setFullname(rs.getString("full_name"));
                    clubMembers.add(clubMember);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubMembers;
    }

    public static List<ClubMember> getAllClubMembersByClubId(int clubId) {
        List<ClubMember> clubMembers = new ArrayList<>();

        String sql = "SELECT DISTINCT c.club_id, u.user_id, u.full_name "
                + "FROM user u "
                + "JOIN club_member cm ON u.user_id = cm.user_id "
                + "JOIN club c ON c.club_id = cm.club_id "
                + "WHERE cm.club_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clubId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClubMember clubMember = new ClubMember();
                    clubMember.setClubId(rs.getInt("club_id"));
                    clubMember.setUserId(rs.getInt("user_id"));
                    clubMember.setFullname(rs.getString("full_name"));  // Đã sửa lỗi
                    clubMembers.add(clubMember);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubMembers;
    }
//lọc theo event

    public static List<Event> getAllActiveEventByClubId(int clubId) {
        List<Event> events = new ArrayList<>();

        String sql = "SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, "
                + "start_time, end_time, is_public, status, description "
                + "FROM event "
                + "WHERE club_id = ? AND type = 'Event' ";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clubId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEvent_id(rs.getInt("event_id"));
                    event.setClub_id(rs.getInt("club_id"));
                    event.setLeader_id(rs.getInt("leader_id"));
                    event.setName(rs.getString("name"));
                    event.setAvatar_url(rs.getString("avatar_url"));
                    event.setGuest(rs.getString("guest"));
                    event.setLocation(rs.getString("location"));
                    event.setStart_time(rs.getString("start_time"));
                    event.setEnd_time(rs.getString("end_time"));
                    event.setIs_public(rs.getString("is_public"));
                    event.setStatus(rs.getString("status"));
                    event.setDescription(rs.getString("description"));

                    // Thêm vào danh sách kết quả
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;  // Trả về danh sách các sự kiện đang hoạt động
    }

    public static boolean isUserClubLeader(int userId, int clubId) {
        String sql = "SELECT COUNT(*) FROM club_member WHERE user_id = ? AND club_id = ? AND role_id = 4 ";
        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, clubId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // User là club leader
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserEventLeader(int userId, int clubId) {
        String sql = "SELECT COUNT(*) FROM event WHERE leader_id = ? AND club_id = ? AND type = 'Event'";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, clubId); // Thêm điều kiện club_id

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // User là event leader của club đó
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserEventLeaderOfEvent(int userId, int eventId) {
        String sql = "SELECT COUNT(*) FROM event WHERE leader_id = ? AND event_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, eventId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // User là leader của sự kiện
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Không phải leader hoặc có lỗi xảy ra
    }

    public static boolean isUserProjectLeader(int userId, int clubId) {
        String sql = "SELECT COUNT(*) FROM event WHERE leader_id = ? AND club_id = ? AND type = 'Project'";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, clubId); // Thêm điều kiện club_id

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // User là project leader của club đó
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Event> getAllEventProjectByFieldName(int clubId, String fieldName) {
        List<Event> events = new ArrayList<>();

        String sql = "SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, "
                + "start_time, end_time, is_public, status, description "
                + "FROM event "
                + "WHERE club_id = ? AND type = ?";  // Điều kiện type = 'Event' hoặc 'Project'

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clubId);
            stmt.setString(2, fieldName);  // Truyền giá trị type ('Event' hoặc 'Project')

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEvent_id(rs.getInt("event_id"));
                    event.setClub_id(rs.getInt("club_id"));
                    event.setLeader_id(rs.getInt("leader_id"));
                    event.setName(rs.getString("name"));
                    event.setAvatar_url(rs.getString("avatar_url"));
                    event.setGuest(rs.getString("guest"));
                    event.setLocation(rs.getString("location"));
                    event.setStart_time(rs.getString("start_time"));
                    event.setEnd_time(rs.getString("end_time"));
                    event.setIs_public(rs.getString("is_public"));
                    event.setStatus(rs.getString("status"));
                    event.setDescription(rs.getString("description"));

                    // Thêm vào danh sách kết quả
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;  // Trả về danh sách kết quả
    }

    public static List<Event> getAllEventProjectByFieldNames(int userId, int clubId, String fieldName) {
        List<Event> events = new ArrayList<>();

        // Kiểm tra quyền của user
        boolean isClubLeader = isUserClubLeader(userId, clubId);
        boolean isEventLeader = isUserEventLeader(userId, clubId);
        boolean isProjectLeader = isUserProjectLeader(userId, clubId);

        String sql;

        if (isClubLeader) {
            // Club Leader thấy tất cả sự kiện/dự án
            sql = "SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, "
                    + "start_time, end_time, is_public, status, description "
                    + "FROM event WHERE club_id = ? AND type = ?";
        } else if (isEventLeader || isProjectLeader) {
            // Nếu là EventLeader hoặc ProjectLeader, chỉ thấy các event mà họ làm leader
            sql = "SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, "
                    + "start_time, end_time, is_public, status, description "
                    + "FROM event WHERE club_id = ? AND type = ? AND leader_id = ?";
        } else {
            // Nếu chỉ là member, chỉ thấy các event mà họ tham gia
            sql = "SELECT e.event_id, e.club_id, e.leader_id, e.name, e.avatar_url, e.guest, e.location, "
                    + "e.start_time, e.end_time, e.is_public, e.status, e.description "
                    + "FROM event e "
                    + "LEFT JOIN project_event_member pem ON e.event_id = pem.event_id OR e.event_id = pem.project_id "
                    + "WHERE e.club_id = ? AND e.type = ? AND pem.user_id = ?";
        }

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clubId);
            stmt.setString(2, fieldName);

            if (!isClubLeader) {
                stmt.setInt(3, userId); // Nếu không phải Club Leader, cần lọc theo user_id hoặc leader_id
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEvent_id(rs.getInt("event_id"));
                    event.setClub_id(rs.getInt("club_id"));
                    event.setLeader_id(rs.getInt("leader_id"));
                    event.setName(rs.getString("name"));
                    event.setAvatar_url(rs.getString("avatar_url"));
                    event.setGuest(rs.getString("guest"));
                    event.setLocation(rs.getString("location"));
                    event.setStart_time(rs.getString("start_time"));
                    event.setEnd_time(rs.getString("end_time"));
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

    public List<Integer> getUserRolesInClub(int userId, int clubId) {
        List<Integer> roles = new ArrayList<>();
        String sql = "SELECT role_id FROM club_member WHERE user_id = ? AND club_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, clubId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(rs.getInt("role_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    public static boolean updateTask(Task task) {
        String sql = "UPDATE task SET event_id = ?, club_id = ?, title = ?, assigner_id = ?, "
                + "assignee_id = ?, deadline = ?, status = ?, description = ?, note = ? WHERE task_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, task.getEventId());
            stmt.setInt(2, task.getClubId());
            stmt.setString(3, task.getTitle());
            stmt.setInt(4, task.getAssignerId());
            stmt.setInt(5, task.getAssigneeId());
            stmt.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            stmt.setString(7, task.getStatus());
            stmt.setString(8, task.getDescription());
            stmt.setString(9, task.getNote());
            stmt.setInt(10, task.getTaskId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Task> getTaskByFilter(
            int userId, String status, Integer clubId, Integer eventId, Integer assigneeId,
            String search, int page, int pageSize, boolean isProject) {

        List<Task> tasks = new ArrayList<>();
        Set<Integer> seenTaskIds = new HashSet<>();

        int offset = (page - 1) * pageSize;

        // Lấy danh sách các club mà user là leader
        List<Integer> leaderEvents = isProject ? getEventsWhereUserIsProjectLeader(userId)
                : getEventsWhereUserIsEventLeader(userId);
        List<Integer> leaderClubs = getClubsWhereUserIsLeader(userId);
        boolean isLeader = !leaderClubs.isEmpty();
        boolean isEventLeader = !leaderEvents.isEmpty();

        StringBuilder sql = new StringBuilder(
                "SELECT t.*, "
                + "a.full_name AS assigner_name, a.email AS assigner_email, "
                + "b.full_name AS assignee_name, b.email AS assignee_email, "
                + "e.name AS event_name, c.name AS club_name, "
                + "COALESCE(sa.value, 'Member') AS assigner_role, "
                + "COALESCE(sb.value, 'Member') AS assignee_role "
                + "FROM task t "
                + "JOIN user a ON t.assigner_id = a.user_id "
                + "JOIN user b ON t.assignee_id = b.user_id "
                + "JOIN club c ON t.club_id = c.club_id "
                + "LEFT JOIN club_member cma ON a.user_id = cma.user_id AND cma.club_id = t.club_id "
                + "LEFT JOIN setting sa ON cma.role_id = sa.setting_id "
                + "LEFT JOIN club_member cmb ON b.user_id = cmb.user_id AND cmb.club_id = t.club_id "
                + "LEFT JOIN setting sb ON cmb.role_id = sb.setting_id "
                + "JOIN event e ON e.event_id = t.event_id "
                + "WHERE (t.assigner_id = ? OR t.assignee_id = ?"
        );

        if (isLeader) {
            sql.append(" OR t.club_id IN ").append(leaderClubs.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")")));
        }

        if (isEventLeader) {
            sql.append(" OR e.event_id IN ").append(leaderEvents.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")")))
                    .append(" AND e.leader_id = " + userId + "");
        }
        sql.append(")");

        // Thêm các bộ lọc khác
        if (status != null && !status.isEmpty()) {
            sql.append(" AND t.status = ?");
        }
        if (clubId != null && clubId > 0) {
            sql.append(" AND t.club_id = ?");
        }
        if (eventId != null && eventId > 0) {
            sql.append(" AND t.event_id = ?");
        }
        if (assigneeId != null && assigneeId > 0) {
            sql.append(" AND t.assignee_id = ?");
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (t.title LIKE ? OR t.description LIKE ?)");
        }

        sql.append(" AND e.type = ?");  // Thay vì cứng nhắc 'Event' hoặc 'Project'
        sql.append(" ORDER BY t.deadline DESC LIMIT ? OFFSET ?");

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            stmt.setInt(paramIndex++, userId);
            stmt.setInt(paramIndex++, userId);

            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            if (clubId != null && clubId > 0) {
                stmt.setInt(paramIndex++, clubId);
            }
            if (eventId != null && eventId > 0) {
                stmt.setInt(paramIndex++, eventId);
            }
            if (assigneeId != null && assigneeId > 0) {
                stmt.setInt(paramIndex++, assigneeId);
            }
            if (search != null && !search.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
                stmt.setString(paramIndex++, "%" + search + "%");
            }

            stmt.setString(paramIndex++, isProject ? "Project" : "Event");  // Set kiểu Event/Project
            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex++, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int taskId = rs.getInt("task_id");
                    if (!seenTaskIds.contains(taskId)) {
                        Task task = new Task(
                                taskId,
                                rs.getInt("event_id"),
                                rs.getInt("club_id"),
                                rs.getString("title"),
                                rs.getInt("assigner_id"),
                                rs.getInt("assignee_id"),
                                rs.getTimestamp("deadline"),
                                rs.getString("status"),
                                rs.getString("description")
                        );
                        task.setEventname(rs.getString("event_name"));
                        task.setClubname(rs.getString("club_name"));
                        task.setAssignerName(rs.getString("assigner_name"));
                        task.setAssignerEmail(rs.getString("assigner_email"));
                        task.setAssignerRole(rs.getString("assigner_role"));
                        task.setAssigneeName(rs.getString("assignee_name"));
                        task.setAssigneeEmail(rs.getString("assignee_email"));
                        task.setAssigneeRole(rs.getString("assignee_role"));

                        tasks.add(task);
                        seenTaskIds.add(taskId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public static int getTotalTask(
            int userId, String status, Integer clubId, Integer eventId, Integer assigneeId,
            String search, boolean isProject) {

        int totalTasks = 0;

        List<Integer> leaderEvents = isProject ? getEventsWhereUserIsProjectLeader(userId)
                : getEventsWhereUserIsEventLeader(userId);
        List<Integer> leaderClubs = getClubsWhereUserIsLeader(userId);
        boolean isLeader = !leaderClubs.isEmpty();
        boolean isEventLeader = !leaderEvents.isEmpty();

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT t.task_id) AS total FROM task t "
                + "JOIN event e ON e.event_id = t.event_id "
                + "WHERE (t.assigner_id = ? OR t.assignee_id = ?"
        );

        if (isLeader) {
            sql.append(" OR t.club_id IN ").append(leaderClubs.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")")));
        }

        if (isEventLeader) {
            sql.append(" OR e.event_id IN ").append(leaderEvents.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")")));
        }
        sql.append(")");

        if (status != null && !status.isEmpty()) {
            sql.append(" AND t.status = ?");
        }
        if (clubId != null && clubId > 0) {
            sql.append(" AND t.club_id = ?");
        }
        if (eventId != null && eventId > 0) {
            sql.append(" AND t.event_id = ?");
        }
        if (assigneeId != null && assigneeId > 0) {
            sql.append(" AND t.assignee_id = ?");
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (t.title LIKE ? OR t.description LIKE ?)");
        }

        sql.append(" AND e.type = ?");

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            stmt.setInt(paramIndex++, userId);
            stmt.setInt(paramIndex++, userId);

            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            if (clubId != null && clubId > 0) {
                stmt.setInt(paramIndex++, clubId);
            }
            if (eventId != null && eventId > 0) {
                stmt.setInt(paramIndex++, eventId);
            }
            if (assigneeId != null && assigneeId > 0) {
                stmt.setInt(paramIndex++, assigneeId);
            }
            if (search != null && !search.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
                stmt.setString(paramIndex++, "%" + search + "%");
            }

            stmt.setString(paramIndex++, isProject ? "Project" : "Event");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalTasks = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalTasks;
    }

    //task thuoc project
    public static List<Integer> getEventsWhereUserIsProjectLeader(int userId) {
        List<Integer> eventIds = new ArrayList<>();
        String sql = "SELECT event_id FROM event WHERE leader_id = ? AND type = 'Project' ";

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    eventIds.add(rs.getInt("event_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventIds;
    }

    public static List<Event> getAllActiveProjectByClubId(int clubId) {
        List<Event> events = new ArrayList<>();

        String sql = "SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, "
                + "start_time, end_time, is_public, status, description "
                + "FROM event "
                + "WHERE club_id = ? AND type = 'Project' ";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clubId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEvent_id(rs.getInt("event_id"));
                    event.setClub_id(rs.getInt("club_id"));
                    event.setLeader_id(rs.getInt("leader_id"));
                    event.setName(rs.getString("name"));
                    event.setAvatar_url(rs.getString("avatar_url"));
                    event.setGuest(rs.getString("guest"));
                    event.setLocation(rs.getString("location"));
                    event.setStart_time(rs.getString("start_time"));
                    event.setEnd_time(rs.getString("end_time"));
                    event.setIs_public(rs.getString("is_public"));
                    event.setStatus(rs.getString("status"));
                    event.setDescription(rs.getString("description"));

                    // Thêm vào danh sách kết quả
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;  // Trả về danh sách các sự kiện đang hoạt động
    }

     public static void main(String[] args) {
        int testTaskId = 1; // Thay bằng một task_id thực tế trong database

        // Gọi phương thức để lấy Task
        Task task = TaskDAO.getTaskByTaskId(testTaskId);

        // Kiểm tra kết quả và in ra màn hình
        if (task != null) {
            System.out.println("Task ID: " + task.getTaskId());
            System.out.println("Event ID: " + task.getEventId());
            System.out.println("Club ID: " + task.getClubId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Deadline: " + task.getDeadline());

            // Thông tin bổ sung
            System.out.println("Event Name: " + task.getEventname());
            System.out.println("Club Name: " + task.getClubname());
            System.out.println("Assigner: " + task.getAssignerName() + " (" + task.getAssignerEmail() + ")");
            System.out.println("Assigner Role: " + task.getAssignerRole());
            System.out.println("Assigner Avatar: " + task.getAssigner_avatar());
            System.out.println("Assignee: " + task.getAssigneeName() + " (" + task.getAssigneeEmail() + ")");
            System.out.println("Assignee Role: " + task.getAssigneeRole());
            System.out.println("Assignee Avatar: " + task.getAssignee_avatar());
            System.out.println(task.getType());
        } else {
            System.out.println("Task not found or error occurred.");
        }
    }
}
