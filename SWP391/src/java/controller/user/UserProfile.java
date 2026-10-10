/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.entity.Club;
import model.entity.ClubActitivies;
import model.entity.Post;
import model.entity.User;
import model.entity.UserActivities;
import service.clubmember.ClubMemberService;
import service.user.UserProfileService;

/**
 *
 * @author pc
 */
@WebServlet(name = "UserProfile", urlPatterns = {"/userprofile"})
public class UserProfile extends HttpServlet {

    private final UserProfileService userProfileService = new UserProfileService();
    private ClubMemberService clubMemberService = new ClubMemberService();

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
        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        // Lấy thông tin user từ session
        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();

        // Lấy danh sách CLB mà user tham gia
        List<Club> userClubs = clubMemberService.getUserClubs(userId);

        if (userClubs == null || userClubs.isEmpty()) {
            request.setAttribute("message", "You are not a member of any club.");
            request.getRequestDispatcher("/WEB-INF/view/userprofile/userprofile.jsp").forward(request, response);
            return;
        }

        // Xác định clubId từ request hoặc lấy CLB đầu tiên trong danh sách
        String clubIdParam = request.getParameter("clubId");
        int clubId = (clubIdParam != null && !clubIdParam.isEmpty()) ? Integer.parseInt(clubIdParam) : userClubs.get(0).getClub_id();

        // Lấy dữ liệu hoạt động của CLB và user
        ClubActitivies clubActivities = userProfileService.getClubStatistics(userId, clubId);
        UserActivities userActivities = userProfileService.getUserStatistics(userId, clubId);
        User userProfile = userProfileService.getUserById(userId);
        List<Post> posts = userProfileService.getPostsByUserId(userId);

        // Đưa dữ liệu vào request để hiển thị trên JSP
        request.setAttribute("info", userProfile);
        request.setAttribute("useractivities", userActivities != null ? userActivities : new UserActivities());
        request.setAttribute("clubactivities", clubActivities != null ? clubActivities : new ClubActitivies());
        request.setAttribute("userClubs", userClubs);
        request.setAttribute("selectedClubId", clubId);
        request.setAttribute("posts", posts);

        // Chuyển hướng về trang user profile
        request.getRequestDispatcher("/WEB-INF/view/userprofile/userprofile.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
