/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.task;

import com.google.gson.Gson;
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
import model.entity.Event;
import model.entity.User;
import service.task.TaskService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "GetClubDetailsServlet", urlPatterns = {"/getClubDetails"})
public class GetClubDetailsServlet extends HttpServlet {

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String clubId = request.getParameter("clubId");
        String taskType = request.getParameter("taskType");
        if (clubId == null || clubId.isEmpty()) {
            response.getWriter().write("{}");
            return;
        }

        TaskService taskService = new TaskService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        List<Event> events = taskService.getAllEventProjectByFieldNames(user.getUser_id(),Integer.parseInt(clubId),taskType);
        List<Integer> role = taskService.getUserRolesInClub(user.getUser_id(), Integer.parseInt(clubId));

        // Tạo JSON response
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("events", gson.toJsonTree(events));
        JsonArray roleArray = new JsonArray();
        for (Integer r : role) {
            roleArray.add(r);
        }
        jsonResponse.add("role", roleArray);

        response.getWriter().write(jsonResponse.toString());
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
