package service.comment;

import DAO.Comment.CommentDAO;
import model.entity.Comment;
import java.util.List;

public class CommentService {
    private final CommentDAO commentDAO;

    public CommentService() {
        this.commentDAO = new CommentDAO();
    }

    public List<Comment> getCommentsByPostId(int postId) {
        return commentDAO.getCommentsByPostId(postId);
    }

    public List<Comment> getCommentsByEventId(int eventId) {
        return commentDAO.getCommentsByEventId(eventId);
    }

    public boolean addComment(int userId, Integer postId, Integer eventId, String content, Integer parentId) {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        // Sanitize content against XSS (Security hardening)
        String sanitizedContent = sanitizeHtml(content.trim());

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setEventId(eventId);
        comment.setContent(sanitizedContent);
        comment.setParentId(parentId);

        return commentDAO.addComment(comment);
    }

    private String sanitizeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#039;");
    }
}
