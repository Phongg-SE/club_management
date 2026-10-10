/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.event;

import DAO.Event.PublicEventDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.entity.Event;
import service.event.PublicEventService;
import model.entity.User;
import jakarta.servlet.http.HttpSession;
import model.entity.RegistrationEvent;

/**
 *
 * @author pc
 */
@WebServlet(name = "PublicEventDetail", urlPatterns = {"/publiceventdetail"})
public class PublicEventDetail extends HttpServlet {

    PublicEventService publicEventService = new PublicEventService();

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

        // Lấy user ID từ request
        String publiceventid = request.getParameter("event_id");

        // Kiểm tra xem id có null hoặc rỗng không
        if (publiceventid == null || publiceventid.isEmpty()) {
            // Set toast message and redirect
            setToastMessage(request, "Event ID is missing", "error");
            response.sendRedirect("PublicEventList");
            return;
        }

        // Kiểm tra nếu ID có phải là một số hợp lệ không
        int publiceventID = -1;
        try {
            publiceventID = Integer.parseInt(publiceventid);
        } catch (NumberFormatException e) {
            // Set toast message and redirect
            setToastMessage(request, "Invalid event ID format", "error");
            response.sendRedirect("PublicEventList");
            return;
        }

        // Lấy thông tin sự kiện từ service
        Event event = publicEventService.getEventById(publiceventID);
        
        // Kiểm tra nếu sự kiện không tồn tại
        if (event == null) {
            // Set toast message and redirect
            setToastMessage(request, "Event not found", "error");
            response.sendRedirect("PublicEventList");
            return;
        }
        
        // Check for message parameter
        String message = request.getParameter("message");
        if (message != null) {
            if (message.equals("success")) {
                setToastMessage(request, "Your registration has been submitted successfully!", "success");
            } else if (message.equals("failed")) {
                setToastMessage(request, "Failed to submit your registration. Please try again later.", "error");
            } else if (message.equals("already_registered")) {
                setToastMessage(request, "You have already registered for this event.", "warning");
            }
        }
        
        int clubid = event.getClub().getClub_id();
        List<Event> eventrandom = publicEventService.getRandomEventsByClubId(clubid);

        // Đặt thông tin sự kiện vào request
        request.setAttribute("event", event);
        request.setAttribute("random", eventrandom);

        // Get user from session
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        request.setAttribute("account", account);

        request.getRequestDispatcher("/WEB-INF/view/event/publiceventdetail.jsp").forward(request, response);
    }
    

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
        try {
            // Get the event ID from the request
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            
            // Get form data
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String mobile = request.getParameter("mobile");
            String note = request.getParameter("note");
                    
            // Get the user from the session (might be null for guest users)
            HttpSession session = request.getSession();
            User account = (User) session.getAttribute("account");
            
            // Get the event details
            Event event = publicEventService.getEventById(eventId);
            if (event == null) {
                // Event not found, redirect to event list
                setToastMessage(request, "Event not found", "error");
                response.sendRedirect("PublicEventList");
                return;
            }

            // If user is logged in, check club membership and existing registration
            if (account != null) {
                              
                // Check if user is already a member of the club organizing the event
                if (publicEventService.isUserMemberOfEventClub(account.getUser_id(), eventId)) {
                    setToastMessage(request,
                            "You are already a member of the club organizing this event. Club members cannot register for their own club's events.",
                            "warning");
                    response.sendRedirect("publiceventdetail?event_id=" + eventId);
                    return;
                }

                // Check if user is already registered for this event
                if (publicEventService.isUserRegisteredForEvent(account.getUser_id(), eventId)) {
                    setToastMessage(request, "You have already registered for this event.", "warning");
                    response.sendRedirect("publiceventdetail?event_id=" + eventId);
                    return;
                }
                if (publicEventService.isUserJoinedEvent(account.getUser_id(), eventId)){
                    setToastMessage(request, "You have already joined this event.", "warning");
                    response.sendRedirect("publiceventdetail?event_id=" + eventId);
                    return;
                }
                
                // Register logged-in user
                PublicEventService.RegistrationResult result = publicEventService
                        .registerUserForEventWithValidation(account.getUser_id(), eventId, note, mobile);
                
                if (result.isSuccess()) {
                    setToastMessage(request, result.getMessage(), "success");
                } else {
                    setToastMessage(request, result.getMessage(), "error");
                }
            } else {
                // Register guest user
                PublicEventService.RegistrationResult result = publicEventService
                        .registerGuestForEvent(email, fullName, mobile, eventId, note);
                
                if (result.isSuccess()) {
                    setToastMessage(request, result.getMessage(), "success");
                } else {
                    setToastMessage(request, result.getMessage(), "error");
                }
            }
            
            // Redirect back to event detail page
            response.sendRedirect("publiceventdetail?event_id=" + eventId);

        } catch (NumberFormatException e) {
            // Invalid event ID, redirect to event list
            setToastMessage(request, "Invalid event ID", "error");
            response.sendRedirect("PublicEventList");
        } catch (Exception e) {
            // Other exceptions, redirect to event list
            System.out.println("Error in PublicEventDetail.doPost: " + e.getMessage());
            setToastMessage(request, e.getMessage(), "error");
            response.sendRedirect("PublicEventList");
        }
    }

    // Helper method to set toast message in session
    private void setToastMessage(HttpServletRequest request, String message, String type) {
        HttpSession session = request.getSession();
        session.setAttribute("toastMessage", message);
        session.setAttribute("toastType", type);
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
