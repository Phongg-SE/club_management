/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.project;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.entity.Club;
import model.entity.Event;
import model.entity.User;
import service.club.ClubService;
import service.project.ProjectService;
import java.sql.SQLException;

@WebServlet(name = "ProjectList", urlPatterns = {"/ProjectList"})
public class ProjectList extends HttpServlet {

    private ProjectService projectService = new ProjectService();
    private ClubService clubService = new ClubService();

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
        
        projectService.autoUpdate();
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();
        boolean isLeader = false;

        List<Integer> clubIds;
        try {
            clubIds = projectService.getClubIdsByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage");
            return;
        }

        if (clubIds.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Lấy danh sách clubs theo clubIds
        List<Club> clubs = new ArrayList<>();
        for (Integer clubId : clubIds) {
            Club club = clubService.getClubById(clubId);
            if (club != null) {
                clubs.add(club);
            }
        }

        // Kiểm tra tham số lọc clubId
        String clubIdParam = request.getParameter("clubId");
        // Lọc theo status và searchName
        String status = request.getParameter("status");
        String searchName = request.getParameter("searchName");

        // Kiểm tra nếu giá trị là "null" thì chuyển thành null thực sự
        if (clubIdParam == null || "null".equalsIgnoreCase(clubIdParam.trim()) || clubIdParam.trim().isEmpty()) {
            clubIdParam = null;
        }

        int selectedClubId;
        if (clubIdParam != null) {
            try {
                selectedClubId = Integer.parseInt(clubIdParam);
            } catch (NumberFormatException e) {
                selectedClubId = (clubs.isEmpty()) ? 0 : clubIds.get(0); // Lấy giá trị mặc định
            }
        } else {
            selectedClubId = (clubs.isEmpty()) ? 0 : clubs.get(0).getClub_id();
        }

        Club selectedClub = clubService.getClubById(selectedClubId);

        if (user != null) {
            isLeader = clubService.isClubLeader(selectedClubId, user.getUser_id()); // Kiểm tra quyền Leader
        }

        // *** Thêm phần phân trang ***
        int page = 1; // Mặc định trang đầu tiên
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // Nếu có lỗi, mặc định là trang 1
            }
        }

        // Lưu clubId vào session
        request.getSession().setAttribute("selectedClubId", selectedClubId);

        // Lấy danh sách dự án theo bộ lọc và phân trang
        List<Event> projects;
        int totalPages;
        try {
            projects = projectService.listProjectsByFilters(selectedClubId, status, searchName, page);
            totalPages = projectService.getTotalPages(selectedClubId, status, searchName);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage");
            return;
        }

        // Gửi dữ liệu qua JSP
        request.setAttribute("isLeader", isLeader);
        request.setAttribute("clubs", clubs);
        request.setAttribute("selectedClub", selectedClub);
        request.setAttribute("projects", projects);
        request.setAttribute("status", status);
        request.setAttribute("searchName", searchName);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/WEB-INF/view/project/projectlist.jsp").forward(request, response);
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
