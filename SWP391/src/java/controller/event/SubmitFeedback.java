package controller.event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;
import service.event.FeedbackService;

import java.io.IOException;

@WebServlet(name = "SubmitFeedback", urlPatterns = {"/SubmitFeedback"})
public class SubmitFeedback extends HttpServlet {
    private FeedbackService feedbackService;

    @Override
    public void init() throws ServletException {
        feedbackService = new FeedbackService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String eventIdParam = request.getParameter("event_id");
        String ratingParam = request.getParameter("rating");
        String comment = request.getParameter("comment");

        if (eventIdParam != null && ratingParam != null) {
            try {
                int eventId = Integer.parseInt(eventIdParam);
                int rating = Integer.parseInt(ratingParam);

                boolean success = feedbackService.submitFeedback(eventId, user.getUser_id(), rating, comment);
                if (success) {
                    session.setAttribute("feedbackMsg", "Cảm ơn bạn! Đánh giá đã được ghi nhận.");
                } else {
                    session.setAttribute("feedbackError", "Không thể gửi đánh giá (đã đánh giá hoặc dữ liệu không hợp lệ).");
                }
                response.sendRedirect("EventDetail?event_id=" + eventId);
                return;
            } catch (NumberFormatException ignored) {
            }
        }
        response.sendRedirect("home");
    }
}
