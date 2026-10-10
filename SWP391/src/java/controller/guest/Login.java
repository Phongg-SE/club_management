/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import DAO.User.UserDAO;
import constant.EmailUtility;
import constant.GoogleLogin;
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
import java.util.List;
import model.entity.Google;
import model.entity.User;
import model.entity.UserRole;
import org.mindrot.jbcrypt.BCrypt;
import service.user.UserService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/view/guest/login.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        if (code != null) {
            GoogleLogin gg = new GoogleLogin();
            String accessToken = gg.getToken(code);
            Google acc = gg.getUserInfo(accessToken);
            UserService userService = new UserService();
            System.out.println(acc);
            if (userService.checkEmailExist(acc.getEmail())) {
                ServletContext context = session.getServletContext();
                ArrayList<Integer> firstLoginUsers = (ArrayList<Integer>) context.getAttribute("firstLoginUsers");
                User u = userService.getUserByEmail(acc.getEmail());
                if (firstLoginUsers == null) {
                    firstLoginUsers = new ArrayList<>();
                }
                if (!firstLoginUsers.contains(u.getUser_id())) {
                    firstLoginUsers.add(u.getUser_id());
                }
                context.setAttribute("firstLoginUsers", firstLoginUsers);
                User user = userService.getUserByEmail(acc.getEmail());
                session.setAttribute("account", user);
                int role = userService.getRoleIdByUserId(user.getUser_id());
                List<Integer> clubRole = userService.getClubRoleByUserId(user.getUser_id());
                session.setAttribute("role", role);
                session.setAttribute("clubRole", clubRole);
                int accountClub = userService.getClubIdByUserId(user.getUser_id());
                session.setAttribute("accountClub", accountClub);
                response.sendRedirect("home");
                return;
            } else {
                request.getRequestDispatcher("register").forward(request, response);
            }
        }
        request.getRequestDispatcher("/WEB-INF/view/guest/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserService userService = new UserService();

        String emailOrUsername = request.getParameter("email");
        String password = request.getParameter("password");

        if (!userService.checkExist(emailOrUsername)) {
            request.setAttribute("msg", "Account does not exist!");
            request.setAttribute("email", emailOrUsername);
            request.getRequestDispatcher("/WEB-INF/view/guest/login.jsp").forward(request, response);
            return;
        }

        boolean check = userService.checkExist(emailOrUsername);

        if (check) {
            User user = null;
            if (userService.validateEmailLogin(emailOrUsername, password)) {
                user = userService.getUserByEmail(emailOrUsername);
            } else if (userService.validateLogin(emailOrUsername, password)) {
                user = userService.getUserByUsername(emailOrUsername);
            }

            if (user == null) {
                request.setAttribute("msg", "Email or Username invalid!");
                request.setAttribute("email", emailOrUsername);
                request.getRequestDispatcher("/WEB-INF/view/guest/login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            ServletContext context = session.getServletContext();

            if (userService.isFirstLogin(user.getUser_id(), context)) {
                session.setAttribute("pendingOTPUser", user.getUser_id());
                session.setAttribute("pendingOTPEmail", user.getEmail());
                response.sendRedirect("otp");
            } else {
                session.setAttribute("account", user);
                int accountClub = userService.getClubIdByUserId(user.getUser_id());
                int role = userService.getRoleIdByUserId(user.getUser_id());
                List<Integer> clubRole = userService.getClubRoleByUserId(user.getUser_id());
                session.setAttribute("role", role);
                session.setAttribute("clubRole", clubRole);
                session.setAttribute("accountClub", accountClub);
                response.sendRedirect("home");
            }
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
