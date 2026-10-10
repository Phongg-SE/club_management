/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;
import model.entity.UserRole;
import service.user.UserService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "Register", urlPatterns = {"/register"})
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/view/guest/register.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/guest/register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        UserService userService = new UserService();
        String validationError = userService.validateRegister(email, password, repassword, username,fullname);

        if (validationError != null && !validationError.isEmpty()) {
            request.setAttribute("msg", validationError);
            request.setAttribute("email", email);
            request.setAttribute("fullname", fullname);
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/view/guest/register.jsp").forward(request, response);
            return;
        }

        // Lưu thông tin tạm vào session trước khi xác thực OTP
        HttpSession session = request.getSession();
        session.setAttribute("pendingOTPEmail", email);
        session.setAttribute("tempFullname", fullname);
        session.setAttribute("tempUsername", username);
        session.setAttribute("tempPassword", password);
        session.setAttribute("otpType", "register"); // Xác định đây là OTP đăng ký

        response.sendRedirect("otp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
