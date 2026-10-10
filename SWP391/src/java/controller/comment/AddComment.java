package controller.comment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;
import service.comment.CommentService;

import java.io.IOException;

@WebServlet(name = "AddComment", urlPatterns = {"/AddComment"})
public class AddComment extends HttpServlet {
    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = new CommentService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;

        // Ensure user is logged in
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String postIdParam = request.getParameter("post_id");
        String eventIdParam = request.getParameter("event_id");
        String content = request.getParameter("content");
        String parentIdParam = request.getParameter("parent_id");

        Integer postId = (postIdParam != null && !postIdParam.isEmpty()) ? Integer.parseInt(postIdParam) : null;
        Integer eventId = (eventIdParam != null && !eventIdParam.isEmpty()) ? Integer.parseInt(eventIdParam) : null;
        Integer parentId = (parentIdParam != null && !parentIdParam.isEmpty()) ? Integer.parseInt(parentIdParam) : null;

        if (content != null && !content.trim().isEmpty()) {
            commentService.addComment(user.getUser_id(), postId, eventId, content, parentId);
        }

        if (postId != null) {
            response.sendRedirect("PostDetail?post_id=" + postId);
        } else if (eventId != null) {
            response.sendRedirect("EventDetail?event_id=" + eventId);
        } else {
            response.sendRedirect("home");
        }
    }
}
