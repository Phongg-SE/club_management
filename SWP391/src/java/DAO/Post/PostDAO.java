/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Post;

import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import model.dto.DBContext;
import model.entity.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Club;
import model.entity.Setting;
import service.post.PostService;

/**
 *
 * @author nam
 */
public class PostDAO {

    private Connection conn;

    public PostDAO() {
        try {
            this.conn = DBContext.getJDBCConnection();
        } catch (Exception e) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<Post> getPostsByUserId(int userId) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.post_id, p.user_id, p.club_id, p.image_url, p.tittle, p.status, "
                + "p.description, p.post_at, u.full_name AS author, c.name AS club_name, "
                + "s.value AS category "
                + "FROM post p "
                + "JOIN user u ON p.user_id = u.user_id "
                + "JOIN club c ON p.club_id = c.club_id "
                + "JOIN setting s ON c.category_id = s.setting_id " // Thêm JOIN với setting để lấy Category
                + "WHERE p.user_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post(rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getInt("club_id"),
                        rs.getString("image_url"),
                        rs.getString("tittle"),
                        rs.getString("status"),
                        rs.getString("description"),
                        rs.getTimestamp("post_at"),
                        rs.getString("club_name"), // Lấy full_name từ bảng user làm author
                        rs.getString("category"), // Lấy tên club từ bảng club
                        rs.getString("author") // Lấy tên category từ bảng setting
                );
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

//    public static void main(String[] args) {
//        PostDAO postDAO = new PostDAO(); // Giả sử PostDAO là class chứa phương thức getPostsByUserId
//        int testUserId = 52; // Thay đổi ID này để kiểm tra với dữ liệu thật trong DB
//        
//        List<Post> posts = postDAO.getPostsByUserId(testUserId);
//        
//        if (posts.isEmpty()) {
//            System.out.println("Không có bài viết nào cho userId = " + testUserId);
//        } else {
//            for (Post post : posts) {
//                System.out.println("Post ID: " + post.getPostId());
//                System.out.println("User ID: " + post.getUserId());
//                System.out.println("Club ID: " + post.getClubId());
//                System.out.println("Image URL: " + post.getImageUrl());
//                System.out.println("Title: " + post.getTitle());
//                System.out.println("Status: " + post.getStatus());
//                System.out.println("Description: " + post.getDescription());
//                System.out.println("Post At: " + post.getPostedAt());
//                System.out.println("Author: " + post.getAuthor());
//                System.out.println("Club Name: " + post.getClubName());
//                System.out.println("-----------------------------------");
//            }
//        }
//    }
    public Post getPostById(int postId) {
        Post post = null;
        String query = """
        SELECT p.post_id, p.user_id, p.club_id, p.image_url, p.tittle, p.status, 
               p.description, p.post_at, u.full_name AS author, 
               c.name AS club_name, s.value AS category
        FROM post p
        JOIN user u ON p.user_id = u.user_id
        JOIN club c ON p.club_id = c.club_id
        JOIN setting s ON p.category_id = s.setting_id
        WHERE p.post_id = ?
    """;

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getInt("user_id"));
                post.setClubId(rs.getInt("club_id"));
                post.setImageUrl(rs.getString("image_url"));
                post.setTitle(rs.getString("tittle"));
                post.setStatus(rs.getString("status"));
                post.setDescription(rs.getString("description"));
                post.setPostedAt(rs.getTimestamp("post_at"));
                post.setAuthor(rs.getString("author"));
                post.setClubName(rs.getString("club_name"));
                post.setCategory(rs.getString("category")); // Lấy giá trị category
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public boolean updatePost(Post post) {
        try (Connection conn = DBContext.getJDBCConnection()) {
            String updatePostSQL = """
            UPDATE post 
            SET tittle = ?, image_url = ?, description = ?, status = ?, post_at = NOW() 
            WHERE post_id = ?
        """;

            try (PreparedStatement psPost = conn.prepareStatement(updatePostSQL)) {
                psPost.setString(1, post.getTitle());
                psPost.setString(2, post.getImageUrl());
                psPost.setString(3, post.getDescription());
                psPost.setString(4, post.getStatus());
                psPost.setInt(5, post.getPostId()); // Đổi vị trí tham số vì bỏ postedAt

                int rowsUpdated = psPost.executeUpdate();
                if (rowsUpdated > 0) {
                    String updateClubSQL = "UPDATE club SET name = ? WHERE club_id = ?";
                    try (PreparedStatement psClub = conn.prepareStatement(updateClubSQL)) {
                        psClub.setString(1, post.getClubName());
                        psClub.setInt(2, post.getClubId());
                        psClub.executeUpdate();
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

  public boolean addPost(int userId, int clubId, String title, String imageUrl,
                       String description, int categoryId, String status) {
    boolean isSuccess = false;
    String insertSql = "INSERT INTO post (user_id, club_id, tittle, image_url, description, category_id, status, post_at) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

    try (Connection conn = DBContext.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(insertSql)) {

        // 🔹 Thực hiện INSERT
        ps.setInt(1, userId);
        ps.setInt(2, clubId);
        ps.setString(3, title);
        ps.setString(4, imageUrl);
        ps.setString(5, description);
        ps.setInt(6, categoryId);
        ps.setString(7, status);

        int rowsInserted = ps.executeUpdate();
        if (rowsInserted > 0) {
            isSuccess = true;
        } else {
            System.err.println("⚠ Không có dữ liệu nào được thêm.");
        }
    } catch (SQLException e) {
        System.err.println("❌ Thêm bài viết thất bại. Lỗi: " + e.getMessage());
        e.printStackTrace();
    }
    return isSuccess;
}


    public List<Setting> getAllCategories2() {
        List<Setting> categories = new ArrayList<>();
        String sql = "SELECT setting_id, value FROM setting WHERE type_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, 2); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(new Setting(rs.getInt("setting_id"), rs.getString("value")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT s.value AS category FROM setting s WHERE s.type_id = 2"; // Kiểm tra type_id chính xác

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String category = rs.getString("category");
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Club> getAllClubs(int userId) {
    List<Club> clubs = new ArrayList<>();
    String sql = "SELECT distinct c.club_id, c.name " +
                 "FROM club c " +
                 "INNER JOIN club_member cm ON c.club_id = cm.club_id " +
                 "WHERE cm.user_id = ?";

    try (Connection conn = DBContext.getJDBCConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, userId); // Gán giá trị userId vào câu lệnh SQL
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clubs.add(new Club(rs.getInt("club_id"), rs.getString("name")));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return clubs;
}


    public List<Post> getFilteredPosts(String status, String category, String clubName, int userId, int pageNumber, int pageSize) {
    List<Post> posts = new ArrayList<>();
    StringBuilder sql = new StringBuilder(
            "SELECT p.post_id, p.tittle, c.name AS club_name, s.value AS category, " +
            "u.full_name AS author, p.status, p.post_at " +
            "FROM post p " +
            "JOIN club c ON p.club_id = c.club_id " +
            "JOIN setting s ON p.category_id = s.setting_id " +
            "JOIN user u ON p.user_id = u.user_id " +
            "WHERE u.user_id = ?"
    );

    if (status != null && !status.isEmpty()) {
        sql.append(" AND p.status = ?");
    }
    if (category != null && !category.isEmpty()) {
        sql.append(" AND s.value = ?");
    }
    if (clubName != null && !clubName.isEmpty()) {
        sql.append(" AND c.name LIKE ?");
    }

    // Thêm phần LIMIT và OFFSET để phân trang
    sql.append(" ORDER BY p.post_at DESC LIMIT ? OFFSET ?");

    try (Connection conn = DBContext.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        int paramIndex = 1;
        ps.setInt(paramIndex++, userId);
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }
        if (category != null && !category.isEmpty()) {
            ps.setString(paramIndex++, category);
        }
        if (clubName != null && !clubName.isEmpty()) {
            ps.setString(paramIndex++, "%" + clubName + "%");
        }

        // Thêm LIMIT và OFFSET
        ps.setInt(paramIndex++, pageSize);
        ps.setInt(paramIndex++, (pageNumber - 1) * pageSize);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("tittle"));
                post.setClubName(rs.getString("club_name"));
                post.setCategory(rs.getString("category"));
                post.setAuthor(rs.getString("author"));
                post.setStatus(rs.getString("status"));
                post.setPostedAt(rs.getTimestamp("post_at"));
                posts.add(post);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return posts;
}

    public int countFilteredPosts(String status, String category, String clubName, int userId) {
    StringBuilder sql = new StringBuilder(
        "SELECT COUNT(*) FROM post p " +
        "JOIN club c ON p.club_id = c.club_id " +
        "JOIN setting s ON p.category_id = s.setting_id " +
        "JOIN user u ON p.user_id = u.user_id " +
        "WHERE u.user_id = ?"
    );

    if (status != null && !status.isEmpty()) {
        sql.append(" AND p.status = ?");
    }
    if (category != null && !category.isEmpty()) {
        sql.append(" AND s.value = ?");
    }
    if (clubName != null && !clubName.isEmpty()) {
        sql.append(" AND c.name LIKE ?");
    }

    try (Connection conn = DBContext.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        int paramIndex = 1;
        ps.setInt(paramIndex++, userId);
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }
        if (category != null && !category.isEmpty()) {
            ps.setString(paramIndex++, category);
        }
        if (clubName != null && !clubName.isEmpty()) {
            ps.setString(paramIndex++, "%" + clubName + "%");
        }

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}


    public static void main(String[] args) {
         
       
    }
}
    


//    public static void main(String[] args) {
//        PostDAO postDAO = new PostDAO(); // Giả sử PostDAO là lớp chứa phương thức updatePost()
//
//        // Tạo một đối tượng Post với dữ liệu cập nhật
//        Post postToUpdate = new Post();
//        postToUpdate.setPostId(1); // Đặt ID của bài viết cần cập nhật
//        postToUpdate.setTitle("Cập nhật tiêu đề mới");
//        postToUpdate.setImageUrl("https://example.com/new-image.jpg");
//        postToUpdate.setDescription("Mô tả bài viết đã được cập nhật.");
//        postToUpdate.setStatus("Published");
//        postToUpdate.setClubId(2); // ID của câu lạc bộ liên quan
//        postToUpdate.setClubName("Câu lạc bộ công nghệ");
//
//        // Gọi phương thức cập nhật
//        boolean isUpdated = postDAO.updatePost(postToUpdate);
//
//        // Kiểm tra kết quả
//        if (isUpdated) {
//            System.out.println("Cập nhật bài viết thành công!");
//        } else {
//            System.out.println("Cập nhật bài viết thất bại.");
//        }
//    }

