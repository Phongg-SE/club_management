package controller.club;

import constant.EmailUtility;
import filter.CSRFTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;

import java.io.IOException;

@WebServlet(name = "BulkEmailController", urlPatterns = {"/bulk-email"})
public class BulkEmailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("csrfToken", CSRFTokenUtil.getToken(session));
        request.getRequestDispatcher("/WEB-INF/view/club/bulkEmail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        String recipientGroup = request.getParameter("recipientGroup");
        String subject = request.getParameter("subject");
        String messageContent = request.getParameter("messageContent");

        if (subject == null || subject.trim().isEmpty() || messageContent == null || messageContent.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Tiêu đề và nội dung email không được để trống!");
            request.setAttribute("csrfToken", CSRFTokenUtil.getToken(session));
            request.getRequestDispatcher("/WEB-INF/view/club/bulkEmail.jsp").forward(request, response);
            return;
        }

        // Simulate dispatching email queue
        request.setAttribute("successMessage", "Đã gửi thông báo email thành công đến nhóm: " + recipientGroup + "!");
        request.setAttribute("csrfToken", CSRFTokenUtil.getToken(session));
        request.getRequestDispatcher("/WEB-INF/view/club/bulkEmail.jsp").forward(request, response);
    }
}
