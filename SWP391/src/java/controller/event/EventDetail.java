/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.event;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.entity.Event;
import model.entity.User;
import service.event.EventService;
import service.project.ProjectService;
import service.user.UserService;

/**
 *
 * @author Dell
 */
@WebServlet(name = "EventDetail", urlPatterns = {"/EventDetail"})
public class EventDetail extends HttpServlet {

    private EventService eventService = new EventService();
    private UserService userService = new UserService();
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
        String idParam = request.getParameter("eventID");
        if (idParam == null) {
            idParam = request.getParameter("event_id");
        }
        int eventID = Integer.parseInt(idParam);
        Event event = eventService.getEventById(eventID);
        int clubId = event.getClub_id();
        List<User> users = projectService.getAllEventLeaderByClubID(clubId);
        request.setAttribute("users", users);
        request.setAttribute("event", event);

        // Load Event Feedback & Comments
        service.event.FeedbackService feedbackService = new service.event.FeedbackService();
        service.comment.CommentService commentService = new service.comment.CommentService();
        request.setAttribute("feedbacks", feedbackService.getEventFeedbacks(eventID));
        request.setAttribute("avgRating", feedbackService.getAverageRating(eventID));
        request.setAttribute("comments", commentService.getCommentsByEventId(eventID));

        request.getRequestDispatcher("/WEB-INF/view/event/eventdetail.jsp").forward(request, response);
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
