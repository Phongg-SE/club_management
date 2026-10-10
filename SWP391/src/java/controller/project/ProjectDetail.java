/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.project;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.entity.Event;
import model.entity.ProjectMember;
import model.entity.User;

import service.project.ProjectService;

/**
 *
 * @author nam
 */
@WebServlet(name = "ProjectDetail", urlPatterns = {"/ProjectDetail"})
public class ProjectDetail extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProjectService projectService;

    public ProjectDetail() {
        super();
        projectService = new ProjectService();
    }
    

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
    try {
        // Lấy event_id từ request
        String eventIDParam = request.getParameter("event_id");
        String clubIDParam = request.getParameter("club_id");

        if (eventIDParam == null || eventIDParam.isEmpty()) {
            request.setAttribute("error", "event_id is missing!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        if (clubIDParam == null || clubIDParam.isEmpty()) {
            request.setAttribute("error", "club_id is missing!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        int eventID = Integer.parseInt(eventIDParam);
        int clubID = Integer.parseInt(clubIDParam);

        // Lấy danh sách thành viên trong CLB
       List<User> clubMembers = projectService.getAvailableClubMembers(clubID, eventID);
        if (clubMembers == null || clubMembers.isEmpty()) {
            request.setAttribute("error", "No members found for this club.");
        }

        // Lấy thông tin dự án
        Event event = projectService.getProjectDetail(eventID);
        if (event == null) {
            request.setAttribute("error", "Project not found.");
            request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);
            return;
        }

        // Lấy danh sách thành viên của dự án
        List<ProjectMember> members = projectService.getProjectMembers(eventID);

        // Đưa dữ liệu vào request để truyền sang JSP
        request.setAttribute("event", event);
        request.setAttribute("projectMembers", members);
        request.setAttribute("clubMembers", clubMembers);

        // Chuyển hướng tới JSP ProjectDetail.jsp
        request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);

    } catch (NumberFormatException e) {
        request.setAttribute("error", "Invalid event_id or club_id format!");
        request.getRequestDispatcher("error.jsp").forward(request, response);
    } catch (Exception e) {
        request.setAttribute("error", "An unexpected error occurred!");
        request.getRequestDispatcher("error.jsp").forward(request, response);
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
