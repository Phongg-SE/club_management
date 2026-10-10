package controller.notification;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.Notification;
import model.entity.User;
import service.notification.NotificationService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NotificationController", urlPatterns = {"/Notification", "/Notification/mark-all-read"})
public class NotificationController extends HttpServlet {
    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        notificationService = new NotificationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Notification> list = notificationService.getUserNotifications(user.getUser_id());
        int unreadCount = notificationService.getUnreadCount(user.getUser_id());

        request.setAttribute("notifications", list);
        request.setAttribute("unreadCount", unreadCount);
        request.getRequestDispatcher("/WEB-INF/view/notification/notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;

        if (user != null) {
            String path = request.getServletPath();
            if ("/Notification/mark-all-read".equals(path) || "markAll".equals(request.getParameter("action"))) {
                notificationService.markAllAsRead(user.getUser_id());
            }
        }
        response.sendRedirect(request.getContextPath() + "/Notification");
    }
}
