/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.club;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Club;
import model.entity.Setting;
import model.entity.User;
import service.club.ClubService;

/**
 *
 * @author pc
 */
@WebServlet(name = "ClubList", urlPatterns = {"/clublist"})
public class ClubList extends HttpServlet {
    
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
        response.setContentType("text/html;charset=UTF-8");
        
        String statusFilter = request.getParameter("statusFilter");
        String isPublicFilter = request.getParameter("isPublicFilter");
        String categoryFilter = request.getParameter("categoryFilter");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        
        int limit = 5; // Số lượng CLB trên mỗi trang
        int currentPage = 1; // Trang hiện tại mặc định là 1
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        int offset = (currentPage - 1) * limit;
        
        int totalClubs = 0;
        try {
            totalClubs = clubService.countTotalClubsByFilters(statusFilter, isPublicFilter, categoryFilter);
        } catch (SQLException ex) {
            Logger.getLogger(ClubList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int totalPages = (int) Math.ceil((double) totalClubs / limit);
        
        List<Club> clubs = clubService.getClubsByFilters(statusFilter, isPublicFilter, categoryFilter, sortField, sortOrder, offset, limit);
        
        List<User> user = clubService.getActiveUsers();

        // Lấy danh mục từ category_id của một CLB cụ thể
        Club clubToGetCategoryID = clubService.getClubById(1);
        int type_id = clubService.getTypeIdBySettingId(clubToGetCategoryID.getCategory_id());
        List<Setting> categories = clubService.getSettingsByType(type_id);
        request.setAttribute("user", user);
        request.setAttribute("categories", categories);
        request.setAttribute("clubs", clubs);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("isPublicFilter", isPublicFilter);
        request.setAttribute("categoryFilter", categoryFilter);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage); // Gán currentPage để JSP hiển thị active đúng

        request.getRequestDispatcher("/WEB-INF/view/club/clublist.jsp").forward(request, response);
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
