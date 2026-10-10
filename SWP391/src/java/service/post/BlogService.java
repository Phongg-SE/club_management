/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.post;

import DAO.Post.BlogDAO;
import java.util.List;
import model.entity.Post;

/**
 *
 * @author nam
 */
public class BlogService {

    private BlogDAO blogDAO;

    public BlogService() {
        blogDAO = new BlogDAO();
    }

    public List<Post> getAllBlogs() {
        return blogDAO.getAllBlogs();
    }
    
    
   
    public List<Post> getRecentBlogs() {
        return blogDAO.getRecentBlogs(4);
    }
    public Post getBlogById(int postId) {
        return blogDAO.getBlogById(postId);
    }

}
