package DAO.Event;

import model.dto.DBContext;
import model.entity.EventFeedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbackDAO {

    public boolean hasUserSubmittedFeedback(int eventId, int userId) {
        String sql = "SELECT feedback_id FROM event_feedback WHERE event_id = ? AND user_id = ?";
        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean addFeedback(EventFeedback feedback) {
        String sql = "INSERT INTO event_feedback (event_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedback.getEventId());
            ps.setInt(2, feedback.getUserId());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComment());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public double getAverageRating(int eventId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM event_feedback WHERE event_id = ?";
        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

    public List<EventFeedback> getFeedbacksByEventId(int eventId) {
        List<EventFeedback> list = new ArrayList<>();
        String sql = "SELECT ef.feedback_id, ef.event_id, ef.user_id, ef.rating, ef.comment, ef.created_at, "
                   + "u.full_name, u.avatar_url "
                   + "FROM event_feedback ef "
                   + "JOIN user u ON ef.user_id = u.user_id "
                   + "WHERE ef.event_id = ? "
                   + "ORDER BY ef.created_at DESC";

        try (Connection conn = DBContext.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EventFeedback fb = new EventFeedback();
                    fb.setFeedbackId(rs.getInt("feedback_id"));
                    fb.setEventId(rs.getInt("event_id"));
                    fb.setUserId(rs.getInt("user_id"));
                    fb.setRating(rs.getInt("rating"));
                    fb.setComment(rs.getString("comment"));
                    fb.setCreatedAt(rs.getTimestamp("created_at"));
                    fb.setUserName(rs.getString("full_name"));
                    fb.setUserAvatar(rs.getString("avatar_url"));
                    list.add(fb);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
