/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.event;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.entity.Club;
import model.entity.Event;
import model.entity.User;
import service.club.ClubService;
import service.event.EventService;
import service.project.ProjectService;
import service.user.UserService;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
@WebServlet(name = "EventList", urlPatterns = {"/EventList"})
public class EventList extends HttpServlet {

    private EventService eventService = new EventService();
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

        projectService.autoUpdate();

        // Lấy HttpSession từ request
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy user từ session
        User user = (User) session.getAttribute("account");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = user.getUser_id(); // Lấy userId từ user object
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
        // Lấy các tham số lọc từ request
        String status = request.getParameter("status");
        String isPublic = request.getParameter("isPublic");
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

        request.getSession().setAttribute("selectedClubId2", selectedClubId);

        // *** Thêm phần phân trang ***
        int page = 1; // Mặc định trang đầu tiên
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // Nếu có lỗi, mặc định là trang 1
            }
        }

        // Lấy danh sách dự án theo bộ lọc và phân trang
        List<Event> events;
        int totalPages;
        try {
            events = eventService.listEventsByFilters(selectedClubId, status, isPublic, searchName, page);
            totalPages = eventService.getTotalPages(selectedClubId, status, isPublic, searchName);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage");
            return;
        }
        List<User> users = projectService.getAllEventLeaderByClubID(selectedClubId);
        request.setAttribute("users", users);
        request.setAttribute("isLeader", isLeader);
        request.setAttribute("selectedClub", selectedClub);
        request.setAttribute("clubs", clubs);
        request.setAttribute("status", status);
        request.setAttribute("isPublic", isPublic);
        request.setAttribute("searchName", searchName);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("events", events);
        request.getRequestDispatcher("/WEB-INF/view/event/eventlist.jsp").forward(request, response);
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
