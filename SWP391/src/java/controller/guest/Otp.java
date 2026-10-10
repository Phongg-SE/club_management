/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import constant.EmailUtility;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.entity.User;
import model.entity.UserRole;
import org.mindrot.jbcrypt.BCrypt;
import service.user.UserService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "Otp", urlPatterns = {"/otp"})
public class Otp extends HttpServlet {

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
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("pendingOTPEmail");
        String otpType = (String) session.getAttribute("otpType");

        if (email == null) {
            response.sendRedirect("login");
            return;
        }
        // Xóa OTP cũ để tránh lỗi khi người dùng quay lại

        String action = request.getParameter("action");
        boolean isResend = "resend".equals(action);

        if (isResend || session.getAttribute("otpCode") == null) {
            String otp = EmailUtility.generateOTP();
            session.setAttribute("otpCode", otp);
            session.setAttribute("otpTime", System.currentTimeMillis());

            EmailUtility.sendOTP(email, otp);
            request.setAttribute("msg", "A new OTP has been sent.");
        }

        request.getRequestDispatcher("/WEB-INF/view/guest/otp.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        String inputOtp = request.getParameter("otp");
        String correctOtp = (String) session.getAttribute("otpCode");
        Long otpTime = (Long) session.getAttribute("otpTime");
        String otpType = (String) session.getAttribute("otpType");

        if (correctOtp == null || otpTime == null) {
            response.sendRedirect("login");
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - otpTime > 60000) { 
            session.removeAttribute("otpCode");
            session.removeAttribute("otpTime");
            request.setAttribute("msg", "OTP expired. Please request a new one.");
            request.getRequestDispatcher("/WEB-INF/view/guest/otp.jsp").forward(request, response);
            return;
        }

        if (inputOtp != null && inputOtp.equals(correctOtp)) {
            session.removeAttribute("otpCode");
            session.removeAttribute("otpTime");

            if ("register".equals(otpType)) {
                String email = (String) session.getAttribute("pendingOTPEmail");
                String fullname = (String) session.getAttribute("tempFullname");
                String username = (String) session.getAttribute("tempUsername");
                String password = (String) session.getAttribute("tempPassword");

                User user = new User();
                user.setEmail(email);
                user.setFull_name(fullname);
                user.setUsername(username);
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                user.setPassword(hashedPassword);

                UserRole userRole = new UserRole();
                userRole.setRole_id(6);
                userRole.setStatus("Active");
                userRole.setUser(user);

                UserService userService = new UserService();
                boolean register = userService.registerUser(user, userRole);
                if (register) {
                    ServletContext context = session.getServletContext();
                    ArrayList<Integer> firstLoginUsers = (ArrayList<Integer>) context.getAttribute("firstLoginUsers");
                    User u = userService.getUserByEmail(user.getEmail());

                    if (firstLoginUsers == null) {
                        firstLoginUsers = new ArrayList<>();
                    }

                    if (!firstLoginUsers.contains(u.getUser_id())) {
                        firstLoginUsers.add(u.getUser_id());
                    }

                    context.setAttribute("firstLoginUsers", firstLoginUsers);

                    session.removeAttribute("pendingOTPEmail");
                    session.removeAttribute("tempFullname");
                    session.removeAttribute("tempUsername");
                    session.removeAttribute("tempPassword");
                    session.removeAttribute("otpType");
                    request.getSession().setAttribute("success", "Register Successfully");
                    response.sendRedirect("login");
                    return;
                } else {
                    request.getRequestDispatcher("error").forward(request, response);
                }
            } else {
                Integer userId = (Integer) session.getAttribute("pendingOTPUser");
                ArrayList<Integer> firstLoginUsers = (ArrayList<Integer>) session.getServletContext().getAttribute("firstLoginUsers");
                if (firstLoginUsers == null) {
                    firstLoginUsers = new ArrayList<>();
                }
                if (!firstLoginUsers.contains(userId)) {
                    firstLoginUsers.add(userId);
                }

                String email = (String) session.getAttribute("pendingOTPEmail");
                UserService userService = new UserService();
                User user = userService.getUserByEmail(email);
                session.setAttribute("account", user);
                int accountClub = userService.getClubIdByUserId(user.getUser_id());
                session.setAttribute("accountClub", accountClub);
                session.getServletContext().setAttribute("firstLoginUsers", firstLoginUsers);
                session.removeAttribute("pendingOTPUser");

                response.sendRedirect("home");
            }
        } else {
            request.setAttribute("msg", "Invalid OTP. Please try again.");
            request.getRequestDispatcher("/WEB-INF/view/guest/otp.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
