package DAO.ClubMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.dto.DBContext;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.Post;
import model.entity.Task;
import model.entity.User;

/**
 *
 * @author pc
 */
public class ClubMemberDAO extends DBContext {

    public ClubMember getClubMemberByUserId(int userId) {
        ClubMember clubMember = null;
        String sql = "SELECT club_id, user_id, role_id, status "
                + "FROM club_member "
                + "WHERE user_id = ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    clubMember = new ClubMember();
                    clubMember.setClubId(rs.getInt("club_id"));
                    clubMember.setUserId(rs.getInt("user_id"));
                    clubMember.setRoleId(rs.getInt("role_id"));
                    clubMember.setStatus(rs.getString("status"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubMember;
    }

    public boolean addClubMember(ClubMember clubMember, String note) {
        String sql = "INSERT INTO club_member (club_id, user_id, role_id, status, note) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clubMember.getClubId());
            ps.setInt(2, clubMember.getUserId());
            ps.setInt(3, clubMember.getRoleId());
            ps.setString(4, clubMember.getStatus());
            ps.setString(5, note);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    ========================MEMBER OFFICIAL LIST     =======================================//
    public List<Club> getClubsByUserId(int userId) {
        String sql = "SELECT DISTINCT c.club_id, c.name, c.description "
                + "FROM club c "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "WHERE cm.user_id = ?";
        List<Club> clubs = new ArrayList<>();

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Club club = new Club();
                    club.setClub_id(rs.getInt("club_id"));
                    club.setName(rs.getString("name"));
                    club.setDescription(rs.getString("description"));
                    clubs.add(club);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    public int getMemberCountByClubId(int clubId) {
        String sql = "SELECT COUNT(*) AS total_members FROM club_member "
                + "WHERE club_id = ? AND status != 'Registed' AND status != 'Rejected'";

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, clubId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total_members");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ hoặc log theo yêu cầu
        }

        return 0; // Trả về 0 nếu không tìm thấy thành viên nào
    }

    public List<User> getMembersByClubIdWithOffset(int clubId, int offset, int limit) {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, cm.status, "
                + "GROUP_CONCAT(cm.role_id) AS role_ids, "
                + "GROUP_CONCAT(s.value) AS role_names "
                + "FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE cm.club_id = ? AND cm.status NOT IN ('Registed', 'Rejected') "
                + "GROUP BY u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, cm.status "
                + "LIMIT ? OFFSET ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, clubId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setGender(rs.getString("gender"));
                    user.setPhone_number(rs.getString("phone_number"));
                    user.setAvatar_url(rs.getString("avatar_url"));
                    user.setStatus(rs.getString("status"));

                    // Chuyển đổi role_id thành danh sách List<Integer>
                    String roleIdsStr = rs.getString("role_ids");
                    List<Integer> roleIds = Arrays.stream(roleIdsStr.split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    // Chuyển đổi role_name thành danh sách List<String>
                    String roleNamesStr = rs.getString("role_names");
                    List<String> roleNames = Arrays.asList(roleNamesStr.split(","));

                    user.setRole_ids(roleIds);
                    user.setRole_names(roleNames);

                    members.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public List<User> getOfficialMembersByFilter(int clubId, String roleFilter, String statusFilter, String sortField, String sortOrder, int offset, int limit) {
        Map<Integer, User> userMap = new HashMap<>();

        // Tạo SQL động với điều kiện lọc nếu có
        StringBuilder sql = new StringBuilder(
                "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, cm.status, s.value AS role_name "
                + "FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE cm.club_id = ? AND cm.status != 'Registed'"
        );

        // Thêm điều kiện lọc nếu có
        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append(" AND cm.role_id = ?");
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append(" AND cm.status = ?");
        }

        // Sắp xếp dữ liệu
        if ("name".equals(sortField)) {
            sql.append(" ORDER BY u.full_name ");
        } else {
            sql.append(" ORDER BY u.user_id ");
        }
        sql.append(" ").append("desc".equals(sortOrder) ? "DESC" : "ASC");

        // Thêm phân trang
        sql.append(" LIMIT ? OFFSET ?");

        // Thực thi truy vấn
        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            int index = 1;
            statement.setInt(index++, clubId);
            if (roleFilter != null && !roleFilter.isEmpty()) {
                statement.setInt(index++, Integer.parseInt(roleFilter));
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                statement.setString(index++, statusFilter);
            }
            statement.setInt(index++, limit);  // Số lượng bản ghi trên mỗi trang
            statement.setInt(index++, offset); // Bắt đầu từ bản ghi thứ `offset`

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");

                    // Nếu user đã tồn tại, thêm role vào danh sách role của user đó
                    if (userMap.containsKey(userId)) {
                        userMap.get(userId).getRole_names().add(rs.getString("role_name"));
                    } else {
                        // Nếu user chưa có trong map, tạo mới và thêm role
                        User user = new User();
                        user.setUser_id(userId);
                        user.setFull_name(rs.getString("full_name"));
                        user.setEmail(rs.getString("email"));
                        user.setUsername(rs.getString("username"));
                        user.setGender(rs.getString("gender"));
                        user.setPhone_number(rs.getString("phone_number"));
                        user.setAvatar_url(rs.getString("avatar_url"));
                        user.setStatus(rs.getString("status"));

                        List<String> roles = new ArrayList<>();
                        roles.add(rs.getString("role_name"));
                        user.setRole_names(roles);

                        userMap.put(userId, user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(userMap.values());
    }

    public int getTotalFilteredMembers(int clubId, String roleFilter, String statusFilter) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT u.user_id) FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "WHERE cm.club_id = ? AND cm.status != 'Registed'"
        );

        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append(" AND cm.role_id = ?");
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append(" AND cm.status = ?");
        }

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            int index = 1;
            statement.setInt(index++, clubId);

            if (roleFilter != null && !roleFilter.isEmpty()) {
                statement.setInt(index++, Integer.parseInt(roleFilter));
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                statement.setString(index++, statusFilter);
            }

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<User> searchMembersByName(String keyword, int limit, int offset, int clubId) {
        Map<Integer, User> userMap = new HashMap<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, cm.status, s.value AS role_name "
                + "FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE cm.club_id = ? AND u.full_name LIKE ? "
                + "ORDER BY u.full_name ASC "
                + "LIMIT ? OFFSET ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clubId);
            stmt.setString(2, "%" + keyword + "%");
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");

                    // Nếu user đã tồn tại, thêm role vào danh sách role của user đó
                    if (userMap.containsKey(userId)) {
                        userMap.get(userId).getRole_names().add(rs.getString("role_name"));
                    } else {
                        // Nếu user chưa có trong map, tạo mới và thêm role
                        User user = new User();
                        user.setUser_id(userId);
                        user.setFull_name(rs.getString("full_name"));
                        user.setEmail(rs.getString("email"));
                        user.setUsername(rs.getString("username"));
                        user.setGender(rs.getString("gender"));
                        user.setPhone_number(rs.getString("phone_number"));
                        user.setAvatar_url(rs.getString("avatar_url"));
                        user.setStatus(rs.getString("status"));

                        List<String> roles = new ArrayList<>();
                        roles.add(rs.getString("role_name"));
                        user.setRole_names(roles);

                        userMap.put(userId, user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Query Error: " + sql);
        }

        return new ArrayList<>(userMap.values());
    }

    public int getTotalMembersByName(String search, int clubId) {
        int totalMembers = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM user u JOIN club_member cm ON u.user_id = cm.user_id WHERE cm.club_id = ?");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND u.full_name LIKE ?");
        }

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setInt(1, clubId);

            if (search != null && !search.trim().isEmpty()) {
                stmt.setString(2, "%" + search + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalMembers = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Query Error: " + sql);
        }

        return totalMembers;
    }

    public boolean updateMemberStatus(int clubId, int userId, String newStatus) {
        String sql = "UPDATE club_member SET status = ? WHERE club_id = ? AND user_id = ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newStatus);   // Trạng thái mới (Active / Inactive)
            statement.setInt(2, clubId);         // ID câu lạc bộ
            statement.setInt(3, userId);         // ID người dùng

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng (nghĩa là cập nhật thành công)
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi thì trả về false
        }
    }
    //====================================END MEMBER OFFICIAL LIST ===============================//

    //==================== MEMBER REGISTED LIST ===========//
    public List<User> getRegistedMembersByClubId(int clubId, int limit, int offset) {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, cm.status, cm.role_id, COALESCE(s.value, 'No Role') AS role_name "
                + "FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "LEFT JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE cm.club_id = ? AND cm.status = 'Registed' "
                + "ORDER BY u.user_id ASC "
                + "LIMIT ? OFFSET ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, clubId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setGender(rs.getString("gender"));
                    user.setPhone_number(rs.getString("phone_number"));
                    user.setAvatar_url(rs.getString("avatar_url"));
                    user.setStatus(rs.getString("status"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setValue(rs.getString("role_name"));
                    members.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public int getTotalRegistedMembersByClubId(int clubId) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM club_member WHERE club_id = ? AND status = 'Registed'";

        try (Connection conn = getJDBCConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, clubId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    //reject member
    public boolean rejectMember(int clubId, int userId) {
        String sql = "UPDATE club_member SET status = ? WHERE club_id = ? AND user_id = ?";
        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "Rejected"); // Hoặc "Inactive" tùy hệ thống của bạn
            ps.setInt(2, clubId);
            ps.setInt(3, userId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Trả về true nếu có ít nhất 1 dòng được update
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu lỗi hoặc không update được
    }

    //approve member
    public boolean approveMember(int clubId, int userId) {
        String getRoleSql = "SELECT setting_id FROM setting WHERE value = 'Club Member'";
        String updateMemberSql = "UPDATE club_member SET status = ?, role_id = ? WHERE club_id = ? AND user_id = ?";

        try (Connection conn = getJDBCConnection()) {
            // Lấy setting_id của role 'Member'
            int memberRoleId = -1;
            try (PreparedStatement ps = conn.prepareStatement(getRoleSql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        memberRoleId = rs.getInt("setting_id");
                    } else {
                        System.out.println("Role 'Member' không tồn tại trong bảng setting.");
                        return false;
                    }
                }
            }

            // Cập nhật status và role_id cho member
            try (PreparedStatement ps = conn.prepareStatement(updateMemberSql)) {
                ps.setString(1, "Active");
                ps.setInt(2, memberRoleId);
                ps.setInt(3, clubId);
                ps.setInt(4, userId);

                int affectedRows = ps.executeUpdate();
                return affectedRows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //=============================END REGISTED LIST==========================//
    //========================== MEMBER DETAIL =====================//
    public List<User> getRandomMembers(int clubId) {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, cm.status, cm.role_id, s.value AS role_name "
                + "FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE cm.club_id = ? AND cm.status != 'Registed' AND cm.status != 'Rejected' "
                + "ORDER BY RAND() LIMIT 5"; // Lấy ngẫu nhiên 5 user

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clubId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setGender(rs.getString("gender"));
                    user.setPhone_number(rs.getString("phone_number"));
                    user.setAvatar_url(rs.getString("avatar_url"));
                    user.setStatus(rs.getString("status"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setValue(rs.getString("role_name"));

                    members.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public List<Post> getPostsByUserId(int userId) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.*, u.full_name, u.email, c.name AS club_name "
                + "FROM post p "
                + "INNER JOIN user u ON p.user_id = u.user_id "
                + "INNER JOIN club c ON p.club_id = c.club_id "
                + "WHERE p.user_id = ? AND p.status = 'Published' "
                + "ORDER BY p.post_at DESC";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Định dạng lại post_at
                    String formattedPostAt = dateFormat.format(rs.getTimestamp("post_at"));

                    posts.add(new Post(
                            rs.getInt("post_id"),
                            rs.getInt("user_id"),
                            rs.getInt("club_id"),
                            rs.getString("image_url"),
                            rs.getString("tittle"),
                            rs.getString("status"),
                            rs.getString("description"),
                            formattedPostAt, // Gán chuỗi đã được định dạng
                            rs.getString("club_name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static List<Task> getUserTasksWithUsers(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT t.*, "
                + "a.full_name AS assigner_name, a.email AS assigner_email, "
                + "b.full_name AS assignee_name, b.email AS assignee_email, "
                + "COALESCE(sa.value, 'Member') AS assigner_role, "
                + "COALESCE(sb.value, 'Member') AS assignee_role "
                + "FROM task t "
                + "JOIN user a ON t.assigner_id = a.user_id "
                + "JOIN user b ON t.assignee_id = b.user_id "
                + "LEFT JOIN club_member cma ON a.user_id = cma.user_id "
                + "LEFT JOIN setting sa ON cma.role_id = sa.setting_id "
                + "LEFT JOIN club_member cmb ON b.user_id = cmb.user_id "
                + "LEFT JOIN setting sb ON cmb.role_id = sb.setting_id "
                + "WHERE t.assigner_id = ? OR t.assignee_id = ? "
                + "ORDER BY t.deadline ASC";

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(
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
                    // Thêm thông tin về người giao và người nhận
                    task.setAssignerName(rs.getString("assigner_name"));
                    task.setAssignerEmail(rs.getString("assigner_email"));
                    task.setAssignerRole(rs.getString("assigner_role"));

                    task.setAssigneeName(rs.getString("assignee_name"));
                    task.setAssigneeEmail(rs.getString("assignee_email"));
                    task.setAssigneeRole(rs.getString("assignee_role"));

                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean updateMemberRoleAndStatus(int memberId, int roleId, String status) {
        String sql = "UPDATE club_member SET role_id = ?, status = ? WHERE user_id = ?";

        // Kiểm tra input hợp lệ trước khi cập nhật
        if (memberId <= 0 || roleId <= 0 || status == null || status.trim().isEmpty()) {
            System.out.println("Invalid input data for updating member.");
            return false;
        }

        try (Connection conn = getJDBCConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roleId);
            pstmt.setString(2, status);
            pstmt.setInt(3, memberId);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;  // Trả về true nếu có dòng bị ảnh hưởng

        } catch (SQLException e) {
            System.err.println("Error updating member role and status: " + e.getMessage());
            return false;
        }
    }

    public User getMemberById(int userId, int clubId) {
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, u.date_of_birth, u.address, u.city, u.district, u.facebook, u.instargram, "
                + "cm.status, "
                + "GROUP_CONCAT(cm.role_id) AS role_ids, GROUP_CONCAT(s.value) AS role_names "
                + "FROM user u "
                + "LEFT JOIN club_member cm ON u.user_id = cm.user_id "
                + "LEFT JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE u.user_id = ? AND cm.club_id = ? "
                + "GROUP BY u.user_id, u.full_name, u.email, u.username, u.gender, u.phone_number, "
                + "u.avatar_url, u.date_of_birth, u.address, u.city, u.district, u.facebook, u.instargram, cm.status";

        try (Connection conn = getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, clubId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setGender(rs.getString("gender"));
                    user.setPhone_number(rs.getString("phone_number"));
                    user.setAvatar_url(rs.getString("avatar_url"));
                    user.setAddress(rs.getString("address"));
                    user.setCity(rs.getString("city"));
                    user.setDistrict(rs.getString("district"));
                    user.setFacebook(rs.getString("facebook"));
                    user.setInstargram(rs.getString("instargram"));
                    user.setStatus(rs.getString("status"));

                    // Xử lý date_of_birth
                    java.sql.Date dob = rs.getDate("date_of_birth");
                    if (dob != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        user.setDate_of_birth(sdf.format(dob));
                    } else {
                        user.setDate_of_birth("");
                    }

                    // Lấy danh sách role_ids
                    String roleIdsStr = rs.getString("role_ids");
                    String roleNamesStr = rs.getString("role_names");

                    List<Integer> roleIds = new ArrayList<>();
                    List<String> roleNames = new ArrayList<>();

                    if (roleIdsStr != null) {
                        for (String id : roleIdsStr.split(",")) {
                            roleIds.add(Integer.parseInt(id));
                        }
                    }

                    if (roleNamesStr != null) {
                        roleNames.addAll(Arrays.asList(roleNamesStr.split(",")));
                    }

                    user.setRole_ids(roleIds);
                    user.setRole_names(roleNames);

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserRoles(int userId, int clubId, List<Integer> roleIds, String status) throws SQLException {
        String deleteSql = "DELETE FROM club_member WHERE club_id = ? AND user_id = ?";
        String insertSql = "INSERT INTO club_member (club_id, user_id, role_id, status) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement deleteStmt = null;
        PreparedStatement insertStmt = null;

        try {
            conn = getJDBCConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Xóa tất cả roles của user trong club
            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, clubId);
            deleteStmt.setInt(2, userId);
            deleteStmt.executeUpdate();

            // Thêm lại các roles mới nếu có
            if (roleIds != null && !roleIds.isEmpty()) {
                insertStmt = conn.prepareStatement(insertSql);
                for (int roleId : roleIds) {
                    insertStmt.setInt(1, clubId);
                    insertStmt.setInt(2, userId);
                    insertStmt.setInt(3, roleId);
                    insertStmt.setString(4, status);
                    insertStmt.addBatch(); // Thêm vào batch
                }
                insertStmt.executeBatch(); // Chạy batch insert
            }

            conn.commit(); // Commit nếu mọi thứ thành công

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback nếu có lỗi
            }
            throw e;
        } finally {
            if (deleteStmt != null) {
                deleteStmt.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    //============================END MEMBER DETAIL============================///    
    public String getRoleValueByUserAndClub(int userId, int clubId) {
        String sql = "SELECT s.value FROM club_member cm "
                + "JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE cm.user_id = ? AND cm.club_id = ?";

        try (Connection conn = getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, clubId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("value");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy
    }
    public ClubMember getClubMemberByUserIdAndClubId(int clubId, int userId) {
        String sql = " SELECT cm.* FROM\n" +
"                club_member as cm,\n" +
"                user as u,\n" +
"                club as c\n" +
"                WHERE\n" +
"                cm.user_id = u.user_id\n" +
"                AND cm.club_id = c.club_id\n" +
"                AND u.user_id = ?\n" +
"                AND c.club_id = ?";
        try {
            Connection connection = DBContext.getJDBCConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, userId);
            statement.setObject(2, clubId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                ClubMember clubMember = new ClubMember();
                clubMember.setClubId(resultSet.getInt("club_id"));
                clubMember.setUserId(resultSet.getInt("user_id"));
                clubMember.setRoleId(resultSet.getInt("role_id"));
                clubMember.setStatus(resultSet.getString("status"));
                clubMember.setNote(resultSet.getString("note"));
                return clubMember;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<ClubMember> getAllClubOfUserById(int user_id) {
        List<ClubMember> clubMembers = new ArrayList<>();
        String sql = "SELECT cm.club_id, cm.user_id, cm.role_id, cm.status, cm.note, "
                + "c.name as club_name, c.club_logo_url, s.value as role_name "
                + "FROM club_member cm "
                + "JOIN club c ON cm.club_id = c.club_id "
                + "JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE cm.user_id = ?";
        
        try (Connection conn = getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, user_id);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClubMember clubMember = new ClubMember();
                    clubMember.setClubId(rs.getInt("club_id"));
                    clubMember.setUserId(rs.getInt("user_id"));
                    clubMember.setRoleId(rs.getInt("role_id"));
                    clubMember.setStatus(rs.getString("status"));
                    clubMember.setNote(rs.getString("note"));
                    
                    // Thêm thông tin bổ sung về club
                    clubMember.setClub_name(rs.getString("club_name"));
                    // Lưu ý: ClubMember không có thuộc tính clubLogoUrl và roleName
                    // Cần thêm vào ClubMember class hoặc bỏ qua các trường này
                    
                    clubMembers.add(clubMember);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clubMembers;
    }
}
