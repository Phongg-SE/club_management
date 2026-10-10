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
import model.entity.Event;
import service.project.ProjectService;

/**
 *
 * @author nam
 */
@WebServlet(name="DeleteProjectMember", urlPatterns={"/DeleteProjectMember"})
public class DeleteProjectMember extends HttpServlet {
   private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        projectService = new ProjectService();
    }
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Lấy member_id từ request
            int memberId = Integer.parseInt(request.getParameter("member_id"));
             int eventId = Integer.parseInt(request.getParameter("event_Id"));
            // Gọi service để xóa thành viên
            boolean isDeleted = projectService.deleteProjectMember(memberId);

            if (isDeleted) {
                request.setAttribute("message", "Xóa thành viên thành công!");
            } else {
                request.setAttribute("error", "Không thể xóa thành viên!");
            }
 
            Event project = projectService.getProjectDetail(eventId);
            int clubID = project.getClub_id();
            
            
            // Chuyển hướng về trang danh sách thành viên
            response.sendRedirect(request.getContextPath() + "/ProjectDetail?event_id=" + eventId + "&club_id=" + clubID);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Lỗi: ID không hợp lệ!");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
