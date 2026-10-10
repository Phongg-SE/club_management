package DAO.Comment;

import model.dto.DBContext;
import model.entity.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentDAO {

    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT c.comment_id, c.user_id, c.post_id, c.event_id, c.content, c.parent_id, c.created_at, "
                   + "u.full_name, u.avatar_url "
                   + "FROM comment c "
                   + "JOIN user u ON c.user_id = u.user_id "
                   + "WHERE c.post_id = ? "
                   + "ORDER BY c.created_at ASC";

        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(rs.getInt("comment_id"));
                    comment.setUserId(rs.getInt("user_id"));
                    comment.setPostId(rs.getInt("post_id"));
                    comment.setContent(rs.getString("content"));
                    int parentId = rs.getInt("parent_id");
                    comment.setParentId(rs.wasNull() ? null : parentId);
                    comment.setCreatedAt(rs.getTimestamp("created_at"));
                    comment.setAuthorName(rs.getString("full_name"));
                    comment.setAuthorAvatar(rs.getString("avatar_url"));
                    list.add(comment);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Comment> getCommentsByEventId(int eventId) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT c.comment_id, c.user_id, c.post_id, c.event_id, c.content, c.parent_id, c.created_at, "
                   + "u.full_name, u.avatar_url "
                   + "FROM comment c "
                   + "JOIN user u ON c.user_id = u.user_id "
                   + "WHERE c.event_id = ? "
                   + "ORDER BY c.created_at ASC";

        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(rs.getInt("comment_id"));
                    comment.setUserId(rs.getInt("user_id"));
                    comment.setPostId(rs.getInt("post_id"));
                    comment.setEventId(rs.getInt("event_id"));
                    comment.setContent(rs.getString("content"));
                    int parentId = rs.getInt("parent_id");
                    comment.setParentId(rs.wasNull() ? null : parentId);
                    comment.setCreatedAt(rs.getTimestamp("created_at"));
                    comment.setAuthorName(rs.getString("full_name"));
                    comment.setAuthorAvatar(rs.getString("avatar_url"));
                    list.add(comment);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO comment (user_id, post_id, event_id, content, parent_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, comment.getUserId());
            if (comment.getPostId() != null) {
                ps.setInt(2, comment.getPostId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            if (comment.getEventId() != null) {
                ps.setInt(3, comment.getEventId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setString(4, comment.getContent());
            if (comment.getParentId() != null) {
                ps.setInt(5, comment.getParentId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
