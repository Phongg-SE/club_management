/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Post;

import DAO.Post.PostDAO;
import model.dto.DBContext;
import model.entity.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author nam
 */
public class BlogDAO {

public List<Post> getAllBlogs() {
    List<Post> postList = new ArrayList<>();
     String query = "SELECT p.post_id, p.user_id, p.club_id, p.image_url, p.tittle, "
                 + "p.status, p.description, p.post_at, u.full_name, s.value AS category, c.name AS club_name "
                 + "FROM post p "
                 + "JOIN user u ON p.user_id = u.user_id "
                 + "LEFT JOIN setting s ON p.category_id = s.setting_id "
                 + "LEFT JOIN club c ON p.club_id = c.club_id "
                 + "WHERE p.status = 'Published'";  // Thêm điều kiện WHERE để chỉ lấy bài viết đã được đăng
    try (Connection con = DBContext.getJDBCConnection();
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Post post = new Post();
            post.setPostId(rs.getInt("post_id"));
            post.setUserId(rs.getInt("user_id"));
            post.setClubId(rs.getInt("club_id"));
            post.setImageUrl(rs.getString("image_url"));
            post.setTitle(rs.getString("tittle"));
            post.setStatus(rs.getString("status"));
            post.setDescription(rs.getString("description"));
            post.setPostedAt(rs.getTimestamp("post_at"));
            post.setAuthor(rs.getString("full_name"));
            post.setCategory(rs.getString("category")); // Lấy category từ setting
            post.setClubName(rs.getString("club_name")); // Lấy name của club
            postList.add(post);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return postList;
}





    
    
   public List<Post> getRecentBlogs(int limit) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT p.post_id, p.user_id, p.club_id, p.image_url, p.tittle, p.status, p.description, p.post_at, u.full_name " +
                       "FROM post p JOIN user u ON p.user_id = u.user_id " +
                       "WHERE p.status = 'Published' ORDER BY p.post_at DESC LIMIT ?";

        try (Connection con = DBContext.getJDBCConnection(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getInt("user_id"));
                post.setClubId(rs.getInt("club_id"));
                post.setImageUrl(rs.getString("image_url"));
                post.setTitle(rs.getString("tittle"));
                post.setStatus(rs.getString("status"));
                post.setDescription(rs.getString("description"));
                post.setPostedAt(rs.getTimestamp("post_at"));
                post.setAuthor(rs.getString("full_name"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

     public Post getBlogById(int postId) {
    Post post = null;
    String query = "SELECT p.post_id, p.user_id, p.club_id, p.image_url, p.tittle, "
                 + "p.status, p.description, p.post_at, u.full_name, "
                 + "s.value AS category, c.name AS club_name "
                 + "FROM post p "
                 + "JOIN user u ON p.user_id = u.user_id "
                 + "LEFT JOIN setting s ON p.category_id = s.setting_id "
                 + "LEFT JOIN club c ON p.club_id = c.club_id "
                 + "WHERE p.post_id = ?";

    try (Connection conn = DBContext.getJDBCConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

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
            post.setAuthor(rs.getString("full_name"));
            post.setCategory(rs.getString("category")); // Lấy category
            post.setClubName(rs.getString("club_name")); // Lấy club name
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return post;
}

    public static void main(String[] args) {
        BlogDAO blogDAO = new BlogDAO(); // Giả sử PostDAO là lớp chứa phương thức getAllBlogs()
        List<Post> posts = blogDAO.getAllBlogs(); 
        
        if (posts.isEmpty()) {
            System.out.println("Không có bài viết nào trong database.");
        } else {
            for (Post post : posts) {
                System.out.println("Post ID: " + post.getPostId());
                System.out.println("User ID: " + post.getUserId());
                System.out.println("Club ID: " + post.getClubId());
                System.out.println("Title: " + post.getTitle());
                System.out.println("Status: " + post.getStatus());
                System.out.println("Description: " + post.getDescription());
                System.out.println("Posted At: " + post.getPostedAt());
                System.out.println("Author: " + post.getAuthor());
                System.out.println("--------------------------------------");
            }
        }
    }
}
