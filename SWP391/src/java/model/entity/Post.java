/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 *
 * @author pc
 */
public class Post {

    private int postId;
    private int userId;
    private int clubId;
    private String imageUrl;
    private String title;
    private String status;
    private String description;
    private Timestamp postedAt;
    private String clubName;
    private String category;
    private String author;
    private String post_at;

    public Post(int postId, int userId, int clubId, String imageUrl, String title, String status, String description, Timestamp postedAt, String clubName, String author) {
        this.postId = postId;
        this.userId = userId;
        this.clubId = clubId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.status = status;
        this.description = description;
        this.postedAt = postedAt;
        this.clubName = clubName;
        this.author = author;
    }

    public Post(int postId, int userId, int clubId, String imageUrl, String title, String status, String description, Timestamp postedAt, String clubName, String category, String author) {
        this.postId = postId;
        this.userId = userId;
        this.clubId = clubId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.status = status;
        this.description = description;
        this.postedAt = postedAt;
        this.clubName = clubName;
        this.category = category;
        this.author = author;
    }

    public String getPost_at() {
        return post_at;
    }

    public void setPost_at(String post_at) {
        this.post_at = post_at;
    }

    public String getClubName() {
        return clubName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Post() {
    }

    public Post(int postId, int userId, int clubId, String imageUrl, String title, String status, String description, Timestamp postedAt) {
        this.postId = postId;
        this.userId = userId;
        this.clubId = clubId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.status = status;
        this.description = description;
        this.postedAt = postedAt;
    }

    public Post(int postId, int userId, int clubId, String imageUrl, String title, String status, String description, String post_at, String club_name) {
        this.postId = postId;
        this.userId = userId;
        this.clubId = clubId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.status = status;
        this.description = description;
        this.post_at = post_at;
        this.clubName = club_name;

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Timestamp postedAt) {
        this.postedAt = postedAt;
    }

    public String getShortDescription(int wordLimit) {
        if (description == null) {
            return "";
        }
        String[] words = description.split("\\s+");
        if (words.length <= wordLimit) {
            return description;
        }
        return String.join(" ", Arrays.copyOfRange(words, 0, wordLimit)) + "..."; 
    }
}
