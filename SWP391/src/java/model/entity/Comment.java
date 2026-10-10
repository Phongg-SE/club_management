package model.entity;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private int userId;
    private Integer postId;
    private Integer eventId;
    private String content;
    private Integer parentId;
    private Timestamp createdAt;
    
    // Display fields
    private String authorName;
    private String authorAvatar;

    public Comment() {
    }

    public Comment(int commentId, int userId, Integer postId, Integer eventId, String content, Integer parentId, Timestamp createdAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.eventId = eventId;
        this.content = content;
        this.parentId = parentId;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }
}
