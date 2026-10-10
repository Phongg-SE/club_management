/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.project;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.entity.Event;
import model.entity.User;
import service.club.ClubService;
import service.project.ProjectService;
import service.user.UserService;

/**
 *
 * @author Dell
 */
@WebServlet(name = "AddProject", urlPatterns = {"/AddProject"})
public class AddProject extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProjectService projectService = new ProjectService();
    private ClubService clubService = new ClubService();
    private UserService userService = new UserService();

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
        processRequest(request, response);
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
        // Kiểm tra session và đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy user_id từ session
        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();

        // Lấy club_id của user
        int clubId = clubService.getClubIdByUserId(userId);
        if (clubId == -1) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        List<User> users = projectService.getAllProjectLeaderByClubID(clubId);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/view/project/addproject.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Lấy dữ liệu từ request
            String eventName = request.getParameter("name");
            String location = request.getParameter("location");
            String isPublic = "No";
            String type = "Project";
            String status = request.getParameter("status");
            String description = request.getParameter("description");

            // Kiểm tra session
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Lấy user từ session
            User user = (User) session.getAttribute("account");
            int userId = user.getUser_id();

            Integer clubId = (Integer) request.getSession().getAttribute("selectedClubId");

            // Kiểm tra leader_id hợp lệ
            int leaderID;
            try {
                leaderID = Integer.parseInt(request.getParameter("leader_id"));
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", "Lỗi: leader_id không hợp lệ.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            // Kiểm tra dữ liệu hợp lệ
            if (eventName == null || eventName.trim().isEmpty() || eventName.length() < 3 || eventName.length() > 50) {
                request.getSession().setAttribute("errorMessage", "Tên dự án phải từ 3 đến 50 ký tự.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            if (location == null || location.trim().isEmpty() || location.length() < 3 || location.length() > 50) {
                request.getSession().setAttribute("errorMessage", "Địa điểm phải từ 3 đến 50 ký tự.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            if (description != null && description.length() > 250) {
                request.getSession().setAttribute("errorMessage", "Mô tả không được vượt quá 250 ký tự.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }
            // Định dạng ngày tháng phù hợp với input type="datetime-local"
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // lưu trữ

            String startTimeStr = request.getParameter("start_time");
            String endTimeStr = request.getParameter("end_time");

            Date startDate = null;
            Date endDate = null;

            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                startDate = inputFormat.parse(startTimeStr); // Chuyển đổi từ định dạng input
            }
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                endDate = inputFormat.parse(endTimeStr);
            }

            // Chuyển đổi lại thành chuỗi theo định dạng chuẩn để lưu vào database
            String startTime = (startDate != null) ? outputFormat.format(startDate) : null;
            String endTime = (endDate != null) ? outputFormat.format(endDate) : null;

            // Kiểm tra nếu startDate lớn hơn endDate
            if (startDate != null && endDate != null && startDate.after(endDate)) {
                request.getSession().setAttribute("errorMessage", "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
                response.sendRedirect(request.getHeader("referer"));
                return; 
            }

            // Tạo đối tượng Project
            Event newProject = new Event(0, clubId, type, leaderID, eventName, null, null, location, startTime, endTime, isPublic, status, description);

            // Gọi service để thêm project
            boolean isInserted = projectService.addProject(newProject);

            if (isInserted) {
                request.getSession().setAttribute("successMessage", "Dự án đã được thêm thành công!");
                response.sendRedirect(request.getContextPath() + "/ProjectList");
            } else {
                request.getSession().setAttribute("errorMessage", "Không thể thêm dự án.");
                response.sendRedirect(request.getHeader("referer"));
            }

        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
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
