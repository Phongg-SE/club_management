/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.finance;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.entity.Event;
import model.entity.Finance;
import model.entity.User;
import service.club.ClubService;
import service.event.EventService;
import service.finance.FinanceService;
import service.user.UserService;
import java.sql.SQLException;
import java.util.ArrayList;
import model.entity.Club;
import service.project.ProjectService;

/**
 *
 * @author Dell
 */
@WebServlet(name = "FinanceList", urlPatterns = {"/FinanceList"})
public class FinanceList extends HttpServlet {

    private FinanceService financeService = new FinanceService();
    private ClubService clubService = new ClubService();
    private EventService eventService = new EventService();
    private UserService userService = new UserService();
    private ProjectService projectService = new ProjectService();

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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();

        // Lấy danh sách Club của User
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

        // Lấy danh sách Clubs theo clubIds
        List<Club> clubs = new ArrayList<>();
        for (Integer clubId : clubIds) {
            Club club = clubService.getClubById(clubId);
            if (club != null) {
                clubs.add(club);
            }
        }

        // Xử lý tham số clubId từ request
        String clubIdParam = request.getParameter("clubId");
        Integer selectedClubId = null;

        if (clubIdParam != null && !clubIdParam.trim().isEmpty()) {
            try {
                int clubIdFromRequest = Integer.parseInt(clubIdParam);
                if (clubIds.contains(clubIdFromRequest)) { // Chỉ cho phép chọn club mà user thuộc về
                    selectedClubId = clubIdFromRequest;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        // Nếu không có clubId từ request, chọn club đầu tiên (nếu có)
        if (selectedClubId == null && !clubs.isEmpty()) {
            selectedClubId = clubs.get(0).getClub_id();
        }

        // Nếu vẫn không có club nào hợp lệ
        if (selectedClubId == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Club selectedClub = clubService.getClubById(selectedClubId);

        // Lấy các bộ lọc từ request
        String isExpense = request.getParameter("isExpense");
        if (isExpense == null || isExpense.trim().isEmpty()) {
            isExpense = null;
        }

        String status = request.getParameter("status");
        if (status == null || status.trim().isEmpty()) {
            status = null;
        }

        String searchName = request.getParameter("searchName");
        if (searchName == null || searchName.trim().isEmpty()) {
            searchName = null;
        }

        // *** Phân trang ***
        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Lấy danh sách tài chính theo bộ lọc và phân trang
        List<Finance> financeList;
        int totalPages;
        try {
            financeList = financeService.listFinanceByFilter(selectedClubId, isExpense, status, searchName, page);
            totalPages = financeService.getTotalPages(selectedClubId, isExpense, status, searchName);

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
                financeList = financeService.listFinanceByFilter(selectedClubId, isExpense, status, searchName, page);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage");
            return;
        }
        List<Event> events = eventService.getEventByClubId(selectedClubId);

        // Gửi dữ liệu qua JSP
        request.setAttribute("clubs", clubs);
        request.setAttribute("events", events);
        request.setAttribute("selectedClub", selectedClub);
        request.setAttribute("selectedClubId", selectedClubId);
        request.setAttribute("finances", financeList);
        request.setAttribute("isExpense", isExpense);
        request.setAttribute("status", status);
        request.setAttribute("searchName", searchName);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/WEB-INF/view/finance/financelist.jsp").forward(request, response);
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
