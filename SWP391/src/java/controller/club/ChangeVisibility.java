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
import jakarta.servlet.http.HttpSession;
import model.entity.Club;
import service.club.ClubService;

/**
 *
 * @author pc
 */
@WebServlet(name = "ChangeVisibility", urlPatterns = {"/changevisibility"})
public class ChangeVisibility extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            int clubId = Integer.parseInt(request.getParameter("id"));

            // Lấy thông tin club hiện tại
            Club club = clubService.getClubById(clubId);
            if (club != null) {
                // Đảo trạng thái Public/Private
                String newVisibility = club.getIs_public().equalsIgnoreCase("Yes") ? "No" : "Yes";

                // Cập nhật trạng thái trong database
                clubService.updateClubVisibility(clubId, newVisibility);
                HttpSession session = request.getSession();
                String news = newVisibility.equalsIgnoreCase("Yes") ? "Public" : "Private";
                session.setAttribute("toastMessage", "Made " + news);
            }

            response.sendRedirect("clublist"); // Chuyển hướng về danh sách CLB
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
