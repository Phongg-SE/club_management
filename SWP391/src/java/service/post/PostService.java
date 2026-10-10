/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.post;

import DAO.Post.PostDAO;
import java.util.List;
import model.entity.Post;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import model.entity.Club;
import model.entity.Setting;

/**
 *
 * @author nam
 */
public class PostService {

    private PostDAO postDAO;

    public PostService() {
        this.postDAO = new PostDAO();
    }

    public List<Post> getPostsByUserId(int userId) {
        return postDAO.getPostsByUserId(userId);
    }

    public Post getPostById(int postId) {
        return postDAO.getPostById(postId);
    }

    public boolean updatePost(Post post) {
        return postDAO.updatePost(post);
    }

    public boolean updatePost(int postId, String title, String imageUrl, String description, String author, String status, String clubName, String category) {
        // Tạo một đối tượng Post với thông tin cập nhật
        Post post = new Post();
        post.setPostId(postId);
        post.setTitle(title);
        post.setImageUrl(imageUrl);
        post.setDescription(description);
        post.setAuthor(author);
        post.setStatus(status);
        post.setPostedAt(new Timestamp(System.currentTimeMillis())); // Lấy thời gian hiện tại
        post.setClubName(clubName);
        post.setCategory(category);

        // Gọi DAO để cập nhật
        return postDAO.updatePost(post);
    }
//    public boolean addPost(Post post) {
//        if (post == null) {
//            System.out.println("❌ Bài đăng không hợp lệ!");
//            return false;
//        }
//        return postDAO.addPost(post);
//    }

   public Map<String, String> validatePost(String title, String description) {
    Map<String, String> errors = new HashMap<>();

    if (title == null || title.trim().isEmpty()) {
        errors.put("title", "Title is required.");
    } else if (title.length() < 3 || title.length() > 100) {
        errors.put("title", "Title must be between 3 and 100 characters!");
    }

    if (description == null || description.trim().isEmpty()) {
        errors.put("description", "Description is required.");
    }

    return errors;
}


    public List<String> getAllCategories() {
        return postDAO.getAllCategories();
    }

    public List<Setting> getAllCategories2() {
        return postDAO.getAllCategories2();
    }

    public List<Post> getFilteredPosts(String status, String category, String clubName, int userId, int pageNumber, int pageSize) {
        return postDAO.getFilteredPosts(status, category, clubName, userId, pageNumber, pageSize);
    }

    public int countFilteredPosts(String status, String category, String clubName, int userId) {
        return postDAO.countFilteredPosts(status, category, clubName, userId);
    }

    public List<Club> getAllClubs(int userId) {
        return postDAO.getAllClubs(userId);
    }

    public boolean addPost(int userId, int clubId, String title, String imageUrl,
            String description, int categoryId, String status) {
        return postDAO.addPost(userId, clubId, title, imageUrl, description, categoryId, status);
    }

    public String validatePostInput(String title) {
        if (title == null || title.trim().isEmpty()) {
            return "Title cannot be empty!";
        }
        if (title.length() < 3 || title.length() > 100) {
            return "Title must be between 3 and 100 characters!";
        }
        return null; // No errors
    }

    public String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "Desctiption cannot be empty!";
        }
        return null;
    }

}
