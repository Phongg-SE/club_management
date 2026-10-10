/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.entity.ClubMember;
import model.entity.User;
import service.task.TaskService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "GetAssignees", urlPatterns = {"/getAssignees"})
public class GetAssignees extends HttpServlet {

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
            out.println("<title>Servlet GetAssignees</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetAssignees at " + request.getContextPath() + "</h1>");
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String clubId = request.getParameter("clubId");
        String eventIdStr = request.getParameter("eventId");
        String taskType = request.getParameter("taskType");

        // Kiểm tra eventId có hợp lệ không
        if (eventIdStr == null || eventIdStr.trim().isEmpty()) {
            response.getWriter().write("{}");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);

            // Lấy session và user đăng nhập
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("account");

            if (user == null) {
                response.getWriter().write("{\"error\": \"User not logged in\"}");
                return;
            }

            // Lấy danh sách thành viên và vai trò
            TaskService taskService = new TaskService();
            List<ClubMember> members = taskService.getAllClubMembersByEventOrProjectId(eventId, taskType);
            List<Integer> role = taskService.getUserRolesInClub(user.getUser_id(), Integer.parseInt(clubId));
            boolean leader = taskService.isUserEventLeaderOfEvent(user.getUser_id(), eventId);
            // Chuyển đổi danh sách thành JSON
            JsonObject jsonResponse = new JsonObject();
            JsonArray membersArray = new JsonArray();
            for (ClubMember member : members) {
                JsonObject memberJson = new JsonObject();
                memberJson.addProperty("user_id", member.getUserId());
                memberJson.addProperty("fullname", member.getFullname());
                membersArray.add(memberJson);
            }

            jsonResponse.add("members", membersArray);
            
            JsonArray roleArray1 = new JsonArray();
            for (Integer r : role) {
                roleArray1.add(r);
            }
            jsonResponse.add("role1", roleArray1);

            jsonResponse.addProperty("leader", leader); // Gửi về true hoặc false
            response.getWriter().write(jsonResponse.toString());

        } catch (NumberFormatException e) {
            response.getWriter().write("{\"error\": \"Invalid event ID\"}");
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
