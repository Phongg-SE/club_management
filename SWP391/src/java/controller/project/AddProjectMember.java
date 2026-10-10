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
import service.project.ProjectService;
import java.sql.SQLException;
import model.entity.Event;
import model.entity.User;

/**
 *
 * @author nam
 */
@WebServlet(name = "AddProjectMember", urlPatterns = {"/AddProjectMember"})
public class AddProjectMember extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddProjectMember</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProjectMember at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //    String MemberIdStr = request.getParameter("member_id");
        String projectIdStr = request.getParameter("event_id");
        String userID = request.getParameter("user_id");
        int userId = Integer.parseInt(userID);
        int projectId = Integer.parseInt(projectIdStr);
        Event project = projectService.getProjectDetail(projectId);
        int clubID = project.getClub_id();
        try {
//            int userId = Integer.parseInt(userID);
//            int projectId = Integer.parseInt(projectIdStr);
//            Event project = projectService.getProjectDetail(projectId);
//            int clubID = project.getClub_id();
            // Gọi service để thêm thành viên vào dự án
            boolean isAdded = projectService.addMemberToProject(userId, projectId);

            if (isAdded) {
                request.setAttribute("successMessage", "Member added successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to add member. Please try again.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID or project ID.");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        // Chuyển hướng về trang chi tiết dự án
        response.sendRedirect(request.getContextPath() + "/ProjectDetail?event_id=" + projectId + "&club_id=" + clubID);
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
