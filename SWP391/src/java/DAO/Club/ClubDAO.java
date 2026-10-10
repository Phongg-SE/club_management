package DAO.Club;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.dto.DBContext;
import model.entity.Club;
import model.entity.Setting;
import model.entity.User;

public class ClubDAO extends DBContext {

    // Duy
    public Club getPublicClubDetails(int clubId) {
        Club club = null;
        String sql = "SELECT c.*, s.value AS category_name FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "WHERE c.club_id = ? AND c.is_public = 'Yes'";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clubId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                club = new Club();
                club.setClub_id(rs.getInt("club_id"));
                club.setName(rs.getString("name"));
                club.setSlogan(rs.getString("slogan"));
                club.setClub_email(rs.getString("club_email"));
                club.setClub_fanpage_url(rs.getString("club_fanpage_url"));
                club.setClub_logo_url(rs.getString("club_logo_url"));
                club.setIs_public(rs.getString("is_public"));
                club.setCategory_id(rs.getInt("category_id"));
                club.setStatus(rs.getString("status"));
                club.setDescription(rs.getString("description"));
                club.setCategory_name(rs.getString("category_name")); // Gán tên danh mục từ bảng setting
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return club;
    }

    //Duy
    public List<Club> getAllClubsByCategoryId(int categoryId) {
        List<Club> clubs = new ArrayList<>();
        String sql = "SELECT * FROM club WHERE category_id = ? AND is_public = 'Yes' LIMIT 4";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Club club = new Club();
                club.setClub_id(rs.getInt("club_id"));
                club.setName(rs.getString("name"));
                club.setSlogan(rs.getString("slogan"));
                club.setClub_email(rs.getString("club_email"));
                club.setClub_fanpage_url(rs.getString("club_fanpage_url"));
                club.setClub_logo_url(rs.getString("club_logo_url"));
                club.setIs_public(rs.getString("is_public"));
                club.setCategory_id(rs.getInt("category_id"));
                club.setStatus(rs.getString("status"));
                club.setDescription(rs.getString("description"));
                clubs.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    //-------------------------------------------------------------------------------------------------------
    // get all club 
    public List<Club> listAllClubs(int limit, int offset) throws SQLException {
        List<Club> listClubs = new ArrayList<>();
        String sql = "SELECT c.*, s.value AS category_value, u.full_name AS leader_name "
                + "FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting r ON cm.role_id = r.setting_id "
                + // Thêm join với bảng setting để lấy role
                "WHERE r.value = 'Club Leader' "
                + // Lọc role với giá trị 'Club Leader'
                "LIMIT ? OFFSET ?";  // Sử dụng LIMIT và OFFSET

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, limit);  // Thiết lập giới hạn số lượng bản ghi trả về
            statement.setInt(2, offset); // Thiết lập offset để bắt đầu từ vị trí nào

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null && connection != null) {
                    while (resultSet.next()) {
                        int clubId = resultSet.getInt("club_id");
                        String name = resultSet.getString("name");
                        String slogan = resultSet.getString("slogan");
                        String clubEmail = resultSet.getString("club_email");
                        String clubFanpageUrl = resultSet.getString("club_fanpage_url");
                        String clubLogoUrl = resultSet.getString("club_logo_url");
                        String isPublic = resultSet.getString("is_public");
                        int categoryId = resultSet.getInt("category_id");
                        String status = resultSet.getString("status");
                        String description = resultSet.getString("description");
                        String categoryValue = resultSet.getString("category_value");  // Lấy giá trị từ bảng setting
                        String leaderName = resultSet.getString("leader_name");  // Lấy tên leader từ bảng user

                        Club club = new Club(clubId, name, slogan, clubEmail, clubFanpageUrl, clubLogoUrl,
                                isPublic, categoryId, status, description, categoryValue, leaderName);
                        listClubs.add(club);
                    }
                } else {
                    System.out.println("No clubs found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return listClubs;
    }

    public int countTotalClubs() throws SQLException {
        String sql = "SELECT COUNT(DISTINCT c.club_id) AS total_clubs "
                + "FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting r ON cm.role_id = r.setting_id "
                + "WHERE r.value = 'Club Leader'";  // Lọc theo role 'Club Leader'

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    return resultSet.getInt("total_clubs");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return 0;
    }

    // filter status
    public List<Club> listClubsByFilters(String status, String isPublic, String categoryId, String sortField, String sortOrder, int offset, int limit) throws SQLException {
        List<Club> listClubs = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT c.*, s.value AS category_value, u.full_name AS leader_name "
                + "FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting r ON cm.role_id = r.setting_id "
                + "WHERE r.value = 'Club Leader' "); // Lọc role với giá trị 'Club Leader'

        // Thêm điều kiện lọc theo status nếu có
        if (status != null && !status.isEmpty()) {
            sql.append(" AND c.status = ? ");
        }

        // Thêm điều kiện lọc theo isPublic nếu có
        if (isPublic != null && !isPublic.isEmpty()) {
            sql.append(" AND c.is_public = ? ");
        }

        // Thêm điều kiện lọc theo category nếu có
        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append(" AND c.category_id = ? ");
        }

        // Thêm phần sắp xếp vào câu truy vấn
        if (sortField != null && !sortField.isEmpty()) {
            switch (sortField) {
                case "name":
                    sql.append(" ORDER BY c.name ");
                    break;
                case "category":
                    sql.append(" ORDER BY s.value ");
                    break;
                case "club_id":
                default:
                    sql.append(" ORDER BY c.club_id ");
                    break;
            }
        } else {
            sql.append(" ORDER BY c.club_id "); // Sắp xếp mặc định theo club_id
        }

        // Thêm thứ tự sắp xếp
        if ("desc".equals(sortOrder)) {
            sql.append(" DESC");
        } else {
            sql.append(" ASC");
        }

        // Thêm phần phân trang vào câu truy vấn
        sql.append(" LIMIT ? OFFSET ?");

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int index = 1;

            // Thêm điều kiện lọc theo status nếu có
            if (status != null && !status.isEmpty()) {
                statement.setString(index++, status);
            }

            // Thêm điều kiện lọc theo isPublic nếu có
            if (isPublic != null && !isPublic.isEmpty()) {
                statement.setString(index++, isPublic);
            }

            // Thêm điều kiện lọc theo category nếu có
            if (categoryId != null && !categoryId.isEmpty()) {
                statement.setString(index++, categoryId);  // Lọc theo category_id
            }

            // Thêm tham số phân trang
            statement.setInt(index++, limit);  // Số lượng kết quả trên mỗi trang
            statement.setInt(index++, offset); // Vị trí bắt đầu của trang

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Club club = new Club(
                            resultSet.getInt("club_id"),
                            resultSet.getString("name"),
                            resultSet.getString("slogan"),
                            resultSet.getString("club_email"),
                            resultSet.getString("club_fanpage_url"),
                            resultSet.getString("club_logo_url"),
                            resultSet.getString("is_public"),
                            resultSet.getInt("category_id"),
                            resultSet.getString("status"),
                            resultSet.getString("description"),
                            resultSet.getString("category_value"), // Lấy giá trị từ bảng setting
                            resultSet.getString("leader_name") // Lấy tên leader từ bảng user
                    );
                    listClubs.add(club);
                }
            }
        } catch (SQLException e) {
            // Log the exception (you can replace this with a logging framework)
            System.err.println("Error fetching clubs by filters: " + e.getMessage());
            // Optionally rethrow the exception if needed
            throw e;
        }

        return listClubs;
    }

    public int countTotalClubsByFilters(String status, String isPublic, String categoryId) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT c.club_id) AS total_clubs "
                + "FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting r ON cm.role_id = r.setting_id "
                + "WHERE r.value = 'Club Leader' "); // Lọc theo role 'Club Leader'

        // Thêm điều kiện lọc theo status nếu có
        if (status != null && !status.isEmpty()) {
            sql.append(" AND c.status = ? ");
        }

        // Thêm điều kiện lọc theo isPublic nếu có
        if (isPublic != null && !isPublic.isEmpty()) {
            sql.append(" AND c.is_public = ? ");
        }

        // Thêm điều kiện lọc theo category nếu có
        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append(" AND c.category_id = ? ");
        }

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            // Thêm điều kiện lọc theo status nếu có
            if (status != null && !status.isEmpty()) {
                statement.setString(index++, status);
            }

            // Thêm điều kiện lọc theo isPublic nếu có
            if (isPublic != null && !isPublic.isEmpty()) {
                statement.setString(index++, isPublic);
            }

            // Thêm điều kiện lọc theo category nếu có
            if (categoryId != null && !categoryId.isEmpty()) {
                statement.setString(index++, categoryId);  // Lọc theo category_id
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total_clubs");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total clubs by filters: " + e.getMessage());
            throw e;
        }

        return 0;  // Nếu không có kết quả, trả về 0
    }

    //search name
    public List<Club> searchByName(String txtSearch, int offset, int limit) {
        List<Club> list = new ArrayList<>();
        String sql = "SELECT c.*, s.value AS category_value, u.full_name AS leader_name "
                + "FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting r ON cm.role_id = r.setting_id "
                + "WHERE c.name LIKE ? AND r.value = 'Club Leader' "
                + "LIMIT ? OFFSET ?";  // Sử dụng LIMIT và OFFSET

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            // Thêm ký tự '%' vào trước và sau từ tìm kiếm
            statement.setString(1, "%" + txtSearch + "%");
            // Số lượng kết quả trên mỗi trang (limit)
            statement.setInt(2, limit);
            // Vị trí bắt đầu của trang (offset)
            statement.setInt(3, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(new Club(
                            resultSet.getInt("club_id"),
                            resultSet.getString("name"),
                            resultSet.getString("slogan"),
                            resultSet.getString("club_email"),
                            resultSet.getString("club_fanpage_url"),
                            resultSet.getString("club_logo_url"),
                            resultSet.getString("is_public"),
                            resultSet.getInt("category_id"),
                            resultSet.getString("status"),
                            resultSet.getString("description"),
                            resultSet.getString("category_value"), // Thêm category_value
                            resultSet.getString("leader_name") // Thêm leader_name
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSearchResults(String txtSearch) {
        String sql = "SELECT COUNT(DISTINCT c.club_id) AS total_clubs "
                + "FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "JOIN setting r ON cm.role_id = r.setting_id "
                + "WHERE c.name LIKE ? AND r.value = 'Club Leader'";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            // Thêm ký tự '%' vào trước và sau từ tìm kiếm
            statement.setString(1, "%" + txtSearch + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total_clubs");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;  // Nếu không có câu lạc bộ nào tìm thấy, trả về 0
    }

    public void updateClubVisibility(int clubId, String newVisibility) {
        String sql = "UPDATE club SET is_public = ? WHERE club_id = ?";
        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newVisibility);
            stmt.setInt(2, clubId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClubStatus(int clubId, String newStatus) {
        String sql = "UPDATE club SET status = ? WHERE club_id = ?";
        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, clubId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------------------------
    public boolean insertClub(Club club, int userId) {
        String insertClubSQL = "INSERT INTO club (name, club_logo_url, is_public, category_id, status) VALUES (?, ?, ?, ?, ?)";
        String getRoleIdSQL = "SELECT setting_id FROM setting WHERE value = 'Club Leader' LIMIT 1";
        String insertClubMemberSQL = "INSERT INTO club_member (club_id, user_id, role_id, status) VALUES (?, ?, ?, 'Active')";

        Connection connection = null;
        PreparedStatement clubStatement = null;
        PreparedStatement roleStatement = null;
        PreparedStatement memberStatement = null;
        ResultSet generatedKeys = null;
        ResultSet roleResult = null;

        try {
            connection = DBContext.getJDBCConnection();
            if (connection == null) {
                System.out.println("Kết nối cơ sở dữ liệu không thành công.");
                return false;
            }

            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // 1️⃣ Insert vào bảng club
            clubStatement = connection.prepareStatement(insertClubSQL, Statement.RETURN_GENERATED_KEYS);
            clubStatement.setString(1, club.getName());
            clubStatement.setString(2, club.getClub_logo_url() != null ? club.getClub_logo_url() : "");
            clubStatement.setString(3, club.getIs_public());
            clubStatement.setInt(4, club.getCategory_id());
            clubStatement.setString(5, club.getStatus());

            int affectedRows = clubStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Thêm club thất bại, không có hàng nào bị ảnh hưởng.");
            }

            // Lấy club_id vừa tạo
            generatedKeys = clubStatement.getGeneratedKeys();
            int clubId = -1;
            if (generatedKeys.next()) {
                clubId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Không thể lấy club_id sau khi insert.");
            }

            // 2️⃣ Lấy role_id của "Club Leader"
            roleStatement = connection.prepareStatement(getRoleIdSQL);
            roleResult = roleStatement.executeQuery();
            int roleId = -1;
            if (roleResult.next()) {
                roleId = roleResult.getInt("setting_id");
            } else {
                throw new SQLException("Không tìm thấy role_id cho 'Club Leader'.");
            }

            // 3️⃣ Insert vào bảng club_member
            memberStatement = connection.prepareStatement(insertClubMemberSQL);
            memberStatement.setInt(1, clubId);
            memberStatement.setInt(2, userId);
            memberStatement.setInt(3, roleId);
            memberStatement.executeUpdate();

            // Commit transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // Đóng tài nguyên
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (roleResult != null) {
                    roleResult.close();
                }
                if (clubStatement != null) {
                    clubStatement.close();
                }
                if (roleStatement != null) {
                    roleStatement.close();
                }
                if (memberStatement != null) {
                    memberStatement.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getActiveUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.* FROM user u "
                + "JOIN user_role ur ON u.user_id = ur.user_id "
                + "WHERE ur.status = 'Active'";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (resultSet.next()) {
                // Chuyển đổi date_of_birth từ Date -> String
                Date dob = resultSet.getDate("date_of_birth");
                String dobStr = (dob != null) ? dateFormat.format(dob) : null;

                User user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("gender"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("avatar_url"),
                        dobStr, // Truyền String thay vì Date
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("district"),
                        resultSet.getString("facebook"),
                        resultSet.getString("instargram")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    //check club exist
    public boolean isClubExist(String clubName) {
        String sql = "SELECT COUNT(*) FROM Club WHERE name = ?";
        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clubName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //=============================================================
    //get club detail by id
    public Club getClubById(int clubId) throws SQLException {
        Club club = null;
        String sql = "SELECT c.*, u.user_id AS leader_id, u.full_name AS leader_name, u.email AS leader_email "
                + "FROM club c "
                + "LEFT JOIN club_member cm ON c.club_id = cm.club_id "
                + "LEFT JOIN user u ON cm.user_id = u.user_id "
                + "LEFT JOIN setting s ON cm.role_id = s.setting_id "
                + "WHERE c.club_id = ? AND s.value = 'Club Leader'"
                + "GROUP BY u.user_id";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, clubId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String slogan = resultSet.getString("slogan");
                    String clubEmail = resultSet.getString("club_email");
                    String clubFanpageUrl = resultSet.getString("club_fanpage_url");
                    String clubLogoUrl = resultSet.getString("club_logo_url");
                    String isPublic = resultSet.getString("is_public");
                    int categoryId = resultSet.getInt("category_id");
                    String status = resultSet.getString("status");
                    String description = resultSet.getString("description");

                    // Thông tin leader
                    int leaderId = resultSet.getInt("leader_id");
                    String leaderName = resultSet.getString("leader_name");

                    club = new Club(clubId, name, slogan, clubEmail, clubFanpageUrl, clubLogoUrl, isPublic, categoryId, status, description);
                    club.setLeader_id(leaderId);
                    club.setClub_leader(leaderName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching club by ID: " + e.getMessage());
            throw e;
        }

        return club;
    }

    //test 
//    public static void main(String[] args) {
//        ClubDAO clubDAO = new ClubDAO();
//
//        int testClubId = 1; // Thay đổi ID để test với dữ liệu thực tế
//        try {
//            Club club = clubDAO.getClubById(testClubId);
//
//            if (club != null) {
//                System.out.println("Club ID: " + club.getClub_id());
//                System.out.println("Name: " + club.getName());
//                System.out.println("Slogan: " + club.getSlogan());
//                System.out.println("Email: " + club.getClub_email());
//                System.out.println("Fanpage: " + club.getClub_fanpage_url());
//                System.out.println("Logo URL: " + club.getClub_logo_url());
//                System.out.println("Public: " + club.getIs_public());
//                System.out.println("Category ID: " + club.getCategory_id());
//                System.out.println("Status: " + club.getStatus());
//                System.out.println("Description: " + club.getDescription());
//                System.out.println("Leader ID: " + club.getLeader_id());
//                System.out.println("Leader Name: " + club.getClub_leader());
//            } else {
//                System.out.println("Không tìm thấy câu lạc bộ với ID: " + testClubId);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Lỗi khi lấy dữ liệu câu lạc bộ: " + e.getMessage());
//        }
//    }
    public Club getClubById2(int clubId) throws SQLException {
        Club club = null;
        String sql = "SELECT * FROM club WHERE club_id = ?";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, clubId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String slogan = resultSet.getString("slogan");
                    String clubEmail = resultSet.getString("club_email");
                    String clubFanpageUrl = resultSet.getString("club_fanpage_url");
                    String clubLogoUrl = resultSet.getString("club_logo_url");
                    String isPublic = resultSet.getString("is_public");
                    int categoryId = resultSet.getInt("category_id");
                    String status = resultSet.getString("status");
                    String description = resultSet.getString("description");

                    club = new Club(clubId, name, slogan, clubEmail, clubFanpageUrl, clubLogoUrl, isPublic, categoryId,
                            status, description);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching club by ID: " + e.getMessage());
            throw e;
        }

        return club;
    }

    //update club
    public boolean updateClub(Club club) {
        if (club == null) {
            System.err.println("Club object is null, update failed.");
            return false;
        }

        String sql = "UPDATE club SET name = ?, slogan = ?, club_email = ?, club_fanpage_url = ?, "
                + "club_logo_url = ?, is_public = ?, category_id = ?, status = ?, description = ? WHERE club_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, club.getName());

            // Kiểm tra giá trị NULL cho slogan
            if (club.getSlogan() != null) {
                pstmt.setString(2, club.getSlogan());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }

            pstmt.setString(3, club.getClub_email());

            // Kiểm tra NULL cho fanpage URL
            if (club.getClub_fanpage_url() != null) {
                pstmt.setString(4, club.getClub_fanpage_url());
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }

            // Kiểm tra NULL cho logo URL
            if (club.getClub_logo_url() != null) {
                pstmt.setString(5, club.getClub_logo_url());
            } else {
                pstmt.setNull(5, Types.VARCHAR);
            }

            // Xử lý is_public: BOOLEAN hoặc VARCHAR ('Yes', 'No')
            if (club.getIs_public().equalsIgnoreCase("Yes")) {
                pstmt.setString(6, "Yes");
            } else {
                pstmt.setString(6, "No");
            }

            pstmt.setInt(7, club.getCategory_id());
            pstmt.setString(8, club.getStatus());

            // Kiểm tra NULL cho description
            if (club.getDescription() != null) {
                pstmt.setString(9, club.getDescription());
            } else {
                pstmt.setNull(9, Types.VARCHAR);
            }

            pstmt.setInt(10, club.getClub_id());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Club updated successfully: " + club.getClub_id());
                return true;
            } else {
                System.err.println("No rows updated, check if club_id exists: " + club.getClub_id());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error updating club (ID: " + club.getClub_id() + "): " + e.getMessage());
            return false;
        }
    }

    public void updateClubLeader(int clubId, int oldLeaderId, int newLeaderId) throws SQLException {
        String getRoleIdQuery = "SELECT setting_id FROM setting WHERE value = ?";
        String removeOldLeader = "UPDATE club_member SET role_id = ? WHERE club_id = ? AND user_id = ?";
        String addNewLeader = "UPDATE club_member SET role_id = ? WHERE club_id = ? AND user_id = ?";

        int clubLeaderRoleId = -1;
        int clubMemberRoleId = -1;

        try (Connection connection = DBContext.getJDBCConnection()) {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Lấy role_id của Club Leader
            try (PreparedStatement stmt = connection.prepareStatement(getRoleIdQuery)) {
                stmt.setString(1, "Club Leader");
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        clubLeaderRoleId = rs.getInt("setting_id");
                    }
                }
            }

            // Lấy role_id của Club Member
            try (PreparedStatement stmt = connection.prepareStatement(getRoleIdQuery)) {
                stmt.setString(1, "Club Member");
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        clubMemberRoleId = rs.getInt("setting_id");
                    }
                }
            }

            // Kiểm tra nếu lấy được cả hai role_id
            if (clubLeaderRoleId == -1 || clubMemberRoleId == -1) {
                throw new SQLException("Không tìm thấy role_id của Club Leader hoặc Club Member.");
            }

            // Cập nhật leader cũ thành Club Member
            try (PreparedStatement removeStmt = connection.prepareStatement(removeOldLeader)) {
                removeStmt.setInt(1, clubMemberRoleId);
                removeStmt.setInt(2, clubId);
                removeStmt.setInt(3, oldLeaderId);
                removeStmt.executeUpdate();
            }

            // Cập nhật leader mới thành Club Leader
            try (PreparedStatement addStmt = connection.prepareStatement(addNewLeader)) {
                addStmt.setInt(1, clubLeaderRoleId);
                addStmt.setInt(2, clubId);
                addStmt.setInt(3, newLeaderId);
                addStmt.executeUpdate();
            }

            connection.commit(); // Xác nhận thay đổi
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<User> getUsersByClubId(int clubId) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, u.username, u.avatar_url "
                + "FROM club_member cm "
                + "JOIN user u ON cm.user_id = u.user_id "
                + "WHERE cm.club_id = ?";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setAvatar_url(rs.getString("avatar_url"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    //check club exist in update
    public boolean isClubExistforUpdate(int clubId, String clubName) {
        String sql = "SELECT COUNT(*) FROM Club WHERE name = ? AND club_id != ?";
        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clubName);
            statement.setInt(2, clubId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //check duplicate email
    public boolean isEmailExistWithDifferentId(int clubId, String clubEmail) {
        String sql = "SELECT COUNT(*) FROM Club WHERE club_email = ? AND club_id != ?";
        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clubEmail);
            statement.setInt(2, clubId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //---------------------------------------------------------------------------------------------------------
    // Method to get the value of a setting by its ID
    public static String getSettingValueById(int settingId) throws SQLException {
        String value = null;
        String sql = "SELECT value FROM setting WHERE setting_id = ?";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, settingId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    value = resultSet.getString("value");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching setting value by ID: " + e.getMessage());
            throw e;
        }

        return value;
    }

    public int getTypeIdBySettingId(int settingId) throws SQLException {
        String sql = "SELECT type_id FROM setting WHERE setting_id = ?";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, settingId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("type_id"); // Lấy giá trị type_id
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return -1; // Trả về -1 nếu không tìm thấy setting_id
    }

    // Lấy danh sách setting_id và value theo typeId (VD: typeId = 1 cho category)
    public List<Setting> getSettingsByType(int typeId) throws SQLException {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT setting_id, value FROM setting WHERE type_id = ? AND status = 'Active' ORDER BY priority ASC";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, typeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Setting setting = new Setting();
                setting.setSettingId(rs.getInt("setting_id"));
                setting.setValue(rs.getString("value"));
                settings.add(setting);
            }
        }
        return settings;
    }

    // Lấy một setting theo ID
    public Setting getSettingById(int settingId) throws SQLException {
        String sql = "SELECT setting_id, value FROM setting WHERE setting_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, settingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Setting setting = new Setting();
                setting.setSettingId(rs.getInt("setting_id"));
                setting.setValue(rs.getString("value"));
                return setting;
            }
        }
        return null;
    }

    //lay clubID theo user
    public int getClubIdByUserId(int userId) throws SQLException {
        String sql = "SELECT club_id FROM club_member WHERE user_id = ?";
        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("club_id");
            }
        }
        return -1; // Trả về -1 nếu user không thuộc câu lạc bộ nào
    }

    // son add them 
    public boolean isClubLeader(int clubId, int userId) {
        // Truy vấn SQL để kiểm tra role_id của người dùng trong câu lạc bộ
        String sql = "SELECT role_id FROM club_member WHERE club_id = ? AND user_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set giá trị cho câu lệnh SQL
            stmt.setInt(1, clubId);
            stmt.setInt(2, userId);

            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Nếu tìm thấy người dùng, kiểm tra role_id
                    int roleId = rs.getInt("role_id");
                    return roleId == 4; // Kiểm tra nếu role_id là 4 (leader)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu không tìm thấy người dùng hoặc role_id không phải là 4 (leader)
    }

//      public static void main(String[] args) {
//          ClubDAO clubDAO = new ClubDAO();
//          // Kiểm tra người dùng có phải là club leader hay không
//          int clubId = 1; // ID của câu lạc bộ cần kiểm tra
//          int userId = 1; // ID của người dùng cần kiểm tra (người dùng này có thể là leader hoặc không)
//          boolean isLeader = clubDAO.isClubLeader(clubId, userId);
//          if (isLeader) {
//              System.out.println("User " + userId + " is the club leader of club " + clubId);
//          } else {
//              System.out.println("User " + userId + " is NOT the club leader of club " + clubId);
//          }
//    }
    public List<Club> listPublicClubs() throws SQLException {
        List<Club> listClubs = new ArrayList<>();
        String sql = "SELECT * FROM club WHERE is_public = 'Yes'";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            if (connection != null) {
                while (resultSet.next()) {
                    int clubId = resultSet.getInt("club_id");
                    String name = resultSet.getString("name");
                    String slogan = resultSet.getString("slogan");
                    String clubEmail = resultSet.getString("club_email");
                    String clubFanpageUrl = resultSet.getString("club_fanpage_url");
                    String clubLogoUrl = resultSet.getString("club_logo_url");
                    String isPublic = resultSet.getString("is_public");
                    int categoryId = resultSet.getInt("category_id");
                    String status = resultSet.getString("status");
                    String description = resultSet.getString("description");

                    Club club = new Club(clubId, name, slogan, clubEmail, clubFanpageUrl, clubLogoUrl, isPublic, categoryId, status, description);
                    listClubs.add(club);
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return listClubs;
    }

//    public static void main(String[] args) {
//        try {
//            // Create an instance of the class that contains listPublicClubs method (assuming it's ClubService)
//            ClubDAO clubDAO = new ClubDAO();
//
//            // Call the listPublicClubs method to get the list of public clubs
//            List<Club> publicClubs = clubDAO.listPublicClubs();
//
//            // Print out the list of public clubs
//            if (publicClubs.isEmpty()) {
//                System.out.println("No public clubs found.");
//            } else {
//                for (Club club : publicClubs) {
//                    System.out.println("Club ID: " + club.getClub_id());
//                    System.out.println("Name: " + club.getName());
//                    System.out.println("Slogan: " + club.getSlogan());
//                    System.out.println("Email: " + club.getClub_email());
//                    System.out.println("Fanpage URL: " + club.getClub_fanpage_url());
//                    System.out.println("Logo URL: " + club.getClub_logo_url());
//                    System.out.println("Category ID: " + club.getCategory_id());
//                    System.out.println("Status: " + club.getStatus());
//                    System.out.println("Description: " + club.getDescription());
//                    System.out.println("---------------------------------------");
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public String getUserMobileNumber(int userId) throws SQLException {
        String sql = "SELECT phone_number FROM user WHERE user_id = ?";
        String mobileNumber = null;

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    mobileNumber = resultSet.getString("phone_number");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting user mobile number: " + e.getMessage());
            throw e;
        }

        return mobileNumber;
    }

//    public static void main(String[] args) {
//        ClubDAO clubDAO = new ClubDAO();
//
//        try {
//            int userId = 11; // Thay đổi userId để test với các giá trị khác nhau
//            int clubId = clubDAO.getClubIdByUserId(userId);
//
//            if (clubId != -1) {
//                System.out.println("User ID " + userId + " thuộc Club ID: " + clubId);
//            } else {
//                System.out.println("User ID " + userId + " không thuộc câu lạc bộ nào.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public boolean isJoinClub(int clubId, int userId) {
        String sql = "club_member as cm,\n"
                + " user as u\n"
                + " where\n"
                + " cm.user_id = u.user_id\n"
                + " AND cm.club_id = c.club_id\n"
                + " AND u.user_id = ? \n"
                + " AND c.club_id != ?";
        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, userId);
            statement.setObject(2, clubId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //=== nam
    // get all club 
    public List<Club> listAllClubs() throws SQLException {
        List<Club> listClubs = new ArrayList<>();
        String sql = "SELECT * FROM club";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            if (connection != null) {
                while (resultSet.next()) {
                    int clubId = resultSet.getInt("club_id");
                    String name = resultSet.getString("name");
                    String slogan = resultSet.getString("slogan");
                    String clubEmail = resultSet.getString("club_email");
                    String clubFanpageUrl = resultSet.getString("club_fanpage_url");
                    String clubLogoUrl = resultSet.getString("club_logo_url");
                    String isPublic = resultSet.getString("is_public");
                    int categoryId = resultSet.getInt("category_id");
                    String status = resultSet.getString("status");
                    String description = resultSet.getString("description");

                    Club club = new Club(clubId, name, slogan, clubEmail, clubFanpageUrl, clubLogoUrl, isPublic, categoryId, status, description);
                    listClubs.add(club);
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return listClubs;
    }

    public static void main(String[] args) {
        ClubDAO clubDAO = new ClubDAO(); // Giả sử bạn có một lớp DAO chứa phương thức listAllClubs
        int limit = 3;  // Số lượng câu lạc bộ tối đa lấy về
        int offset = 4;  // Vị trí bắt đầu

        try {
            List<Club> clubs = clubDAO.listAllClubs(limit, offset);

            if (clubs.isEmpty()) {
                System.out.println("No clubs found.");
            } else {
                for (Club club : clubs) {
                    System.out.println("Club ID: " + club.getClub_id());
                    System.out.println("Name: " + club.getName());
                    System.out.println("Slogan: " + club.getSlogan());
                    System.out.println("Email: " + club.getClub_email());
                    System.out.println("Fanpage: " + club.getClub_fanpage_url());
                    System.out.println("Logo URL: " + club.getClub_logo_url());
                    System.out.println("Public: " + club.getIs_public());
                    System.out.println("Category: " + club.getCategory_name());
                    System.out.println("Leader: " + club.getLeader_id());
                    System.out.println("Status: " + club.getStatus());
                    System.out.println("Description: " + club.getDescription());
                    System.out.println("-----------------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving clubs: " + e.getMessage());
        }
    }

    //son
    public List<Club> getClubsByIds(List<Integer> clubIds) throws SQLException {
        List<Club> clubs = new ArrayList<>();
        for (Integer clubId : clubIds) {
            Club club = getClubById(clubId);
            if (club != null) {
                clubs.add(club);
            }
        }
        return clubs;
    }
}
