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
import service.clubmember.ClubMemberService;

/**
 *
 * @author pc
 */
@WebServlet(name = "ChangeStatusServlet", urlPatterns = {"/changestatus"})
public class ChangeStatusServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
             HttpSession session = request.getSession();
            // Lấy các tham số từ form
            int userId = Integer.parseInt(request.getParameter("id"));  // Lấy user_id từ request
            int clubId = Integer.parseInt(request.getParameter("club_id"));  // Lấy club_id từ request
            String currentStatus = request.getParameter("status");  // Lấy trạng thái hiện tại từ form

            // Kiểm tra trạng thái hiện tại và xác định trạng thái mới
            String newStatus = "Active".equals(currentStatus) ? "Inactive" : "Active";

            // Cập nhật trạng thái trong cơ sở dữ liệu
            boolean success = clubMemberService.changeMemberStatus(clubId, userId, newStatus);

            // Chuyển hướng lại trang sau khi cập nhật thành công
            if (success) {
                session.setAttribute("toastMessage", "Change status to:" + newStatus );
                response.sendRedirect(request.getContextPath() + "/memberofficiallist?clubId=" + clubId);
                
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update status");
            }
        } catch(Exception e)  {
            e.printStackTrace();  // In ra lỗi để debug
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
