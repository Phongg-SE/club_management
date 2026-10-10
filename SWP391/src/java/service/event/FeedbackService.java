package service.event;

import DAO.Event.FeedbackDAO;
import model.entity.EventFeedback;

import java.util.List;

public class FeedbackService {
    private final FeedbackDAO feedbackDAO;

    public FeedbackService() {
        this.feedbackDAO = new FeedbackDAO();
    }

    public boolean submitFeedback(int eventId, int userId, int rating, String comment) {
        // Validation: rating must be 1-5
        if (rating < 1 || rating > 5) {
            return false;
        }

        // Business Rule: Only 1 feedback per user per event
        if (feedbackDAO.hasUserSubmittedFeedback(eventId, userId)) {
            return false;
        }

        String sanitizedComment = sanitizeHtml(comment);

        EventFeedback fb = new EventFeedback();
        fb.setEventId(eventId);
        fb.setUserId(userId);
        fb.setRating(rating);
        fb.setComment(sanitizedComment);

        return feedbackDAO.addFeedback(fb);
    }

    public double getAverageRating(int eventId) {
        return feedbackDAO.getAverageRating(eventId);
    }

    public List<EventFeedback> getEventFeedbacks(int eventId) {
        return feedbackDAO.getFeedbacksByEventId(eventId);
    }

    public boolean hasUserSubmitted(int eventId, int userId) {
        return feedbackDAO.hasUserSubmittedFeedback(eventId, userId);
    }

    private String sanitizeHtml(String input) {
        if (input == null) return "";
        return input.trim()
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#039;");
    }
}
