/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.club;

import java.io.IOException;
import java.io.PrintWriter;
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
import service.club.ClubService;

/**
 *
 * @author pc
 */
@WebServlet(name = "SearchClub", urlPatterns = {"/searchclub"})
public class SearchClub extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        // Lấy tham số tìm kiếm từ request
        String txtSearch = request.getParameter("txt");
        if (txtSearch == null) {
            txtSearch = ""; // Nếu không có giá trị, đặt mặc định là chuỗi rỗng
        }

        // Xử lý phân trang
        int limit = 5; // Số lượng CLB hiển thị mỗi trang
        int page = 1; // Mặc định trang đầu tiên
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1; // Nếu lỗi, quay về trang đầu tiên
            }
        }
        int offset = (page - 1) * limit;

        try {
            // Lấy danh sách CLB tìm kiếm theo tên
            List<Club> clubs = clubService.searchByName(txtSearch, offset, limit);
            int totalResults = clubService.countSearchResults(txtSearch);
            int totalPages = (int) Math.ceil((double) totalResults / limit);

            // Đưa dữ liệu vào request
            request.setAttribute("clubs", clubs);
            request.setAttribute("txt", txtSearch);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            // Chuyển hướng đến trang kết quả tìm kiếm
            request.getRequestDispatcher("/WEB-INF/view/club/clublist.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
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
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SearchClub.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SearchClub.class.getName()).log(Level.SEVERE, null, ex);
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
