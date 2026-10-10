/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;
import service.clubmember.ClubMemberService;

/**
 *
 * @author pc
 */
@WebServlet(name = "ProcessRegisted", urlPatterns = {"/processregisted"})
public class ProcessRegisted extends HttpServlet {

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
        HttpSession session = request.getSession();
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String action = request.getParameter("action");
        User userRegisted = clubMemberService.getMemberById(userId, clubId);
        if ("approve".equalsIgnoreCase(action)) {
            boolean success = clubMemberService.approveMember(clubId, userId);
            if (success) {
                session.setAttribute("toastMessage", "Approved Member: " + userRegisted.getFull_name());
                response.sendRedirect("memberofficiallist?clubId=" + clubId);
            } else {
                response.sendRedirect("memberregistedlist?clubId=" + clubId + "&message=ApproveFailed");
            }
        } else if ("reject".equalsIgnoreCase(action)) {
            boolean success = clubMemberService.rejectMember(clubId, userId);
            if (success) {
                session.setAttribute("toastMessage", "Rejected Member: " + userRegisted.getFull_name());
                response.sendRedirect("memberofficiallist?clubId=" + clubId);
            } else {
                response.sendRedirect("memberregistedlist?clubId=" + clubId + "&message=RejectFailed");
            }
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
