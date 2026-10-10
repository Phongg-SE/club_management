/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Club;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.dto.DBContext;
import model.entity.Club;
import model.entity.Setting;
import model.entity.User;

public class PublicClubDAO extends DBContext {

    public List<Club> listPublicClubs(String search, int categoryId, int page) throws SQLException {
        List<Club> clubList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT c.club_id, c.name, c.slogan, c.club_email, c.club_fanpage_url, c.club_logo_url, "
                + "c.is_public, c.category_id, s.value as category, c.status, c.description "
                + "FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "WHERE c.is_public = 'Yes' "
        );

        List<Object> params = new ArrayList<>();

        if (categoryId > 0) {
            sql.append(" AND c.category_id = ?");
            params.add(categoryId);
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND c.name LIKE ?");
            params.add("%" + search.trim() + "%");
        }

        sql.append(" LIMIT 5 OFFSET ?");
        params.add((page - 1) * 5);

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) params.get(i));
                } else {
                    statement.setString(i + 1, (String) params.get(i));
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clubList.add(new Club(
                            resultSet.getInt("club_id"),
                            resultSet.getString("name"),
                            resultSet.getString("slogan"),
                            resultSet.getString("club_email"),
                            resultSet.getString("club_fanpage_url"),
                            resultSet.getString("club_logo_url"),
                            resultSet.getString("is_public"),
                            resultSet.getInt("category_id"),
                            resultSet.getString("status"),
                            resultSet.getString("description")
                    ));
                }
            }
        }
        return clubList;
    }

    public int getTotalPages(String search, int categoryId) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM club c "
                + "JOIN setting s ON c.category_id = s.setting_id "
                + "WHERE c.is_public = 'Yes'"
        );

        List<Object> params = new ArrayList<>();

        if (categoryId > 0) {
            sql.append(" AND c.category_id = ?");
            params.add(categoryId);
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND c.name LIKE ?");
            params.add("%" + search.trim() + "%");
        }

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) params.get(i));
                } else {
                    statement.setString(i + 1, (String) params.get(i));
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int totalRecords = resultSet.getInt(1);
                    return (int) Math.ceil((double) totalRecords / 5);
                }
            }
        }
        return 1;
    }

//    //test list
//    public static void main(String[] args) {
//        PublicClubDAO publicclubDAO = new PublicClubDAO(); 
//
//        // Thiết lập các giá trị để test
//        String search = ""; // Tìm kiếm club có chứa từ "tech"
//        int categoryId = 7; // Lọc theo category có ID = 2
//        int page = 1; // Trang đầu tiên
//
//        try {
//            // Lấy danh sách câu lạc bộ theo bộ lọc
//            List<Club> clubs = publicclubDAO.listPublicClubs(search, categoryId, page);
//            int totalPages = publicclubDAO.getTotalPages(search, categoryId);
//
//            // Hiển thị kết quả
//            System.out.println("Danh sách các câu lạc bộ công khai (Trang " + page + "/" + totalPages + "):");
//            for (Club club : clubs) {
//                System.out.println("ID: " + club.getClub_id() + " | Name: " + club.getName() + " | Category: " + club.getCategory_name());
//            }
//
//            System.out.println("Tổng số trang: " + totalPages);
//        } catch (SQLException e) {
//            System.err.println("Lỗi truy vấn database: " + e.getMessage());
//        }
//    }
    public List<Setting> getCategories() throws SQLException {
        List<Setting> categories = new ArrayList<>();
        String sql = "SELECT setting_id, value FROM setting WHERE type_id = 2";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                categories.add(new Setting(
                        resultSet.getInt("setting_id"),
                        resultSet.getString("value")
                ));
            }
        }
        return categories;
    }
    
    public static void main(String[] args) {
        PublicClubDAO publicclubDAO = new PublicClubDAO();
        try {
            // Lấy danh sách category từ database
            List<Setting> categoryList = publicclubDAO.getCategories();
            
            // In danh sách category ra console
            System.out.println("Danh sách category:");
            for (Setting category : categoryList) {
                System.out.println("ID: " + category.getSettingId()+ ", Name: " + category.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn database: " + e.getMessage());
        }
    }

}
