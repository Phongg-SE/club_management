/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.RegistrationEvent;

import constant.EmailUtility;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.RegistrationEvent;
import model.entity.User;
import service.RegistrationEvent.RegistrationEventService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "RegistrationDetail", urlPatterns = {"/registrationDetail"})
public class RegistrationDetail extends HttpServlet {

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
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            request.getRequestDispatcher("login").forward(request, response);
        }
        int userId = user.getUser_id();
        RegistrationEventService registrationEventService = new RegistrationEventService();
        if (id == null) {
            response.sendRedirect("registrationEventList");
            return;
        } else {
            RegistrationEvent registrationEvent = registrationEventService.getRegistrationByRegistrationId(Integer.parseInt(id));
            request.setAttribute("registration", registrationEvent);
        }

        request.getRequestDispatcher("/WEB-INF/view/event/registrationdetail.jsp").forward(request, response);
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
        String status = request.getParameter("status");
        String reason = request.getParameter("reason");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        RegistrationEventService registrationEventService = new RegistrationEventService();
        RegistrationEvent re = registrationEventService.getRegistrationByRegistrationId(Integer.parseInt(id));
        boolean success = registrationEventService.updateRegistrationStatus(Integer.parseInt(id), status, reason);
        if (success) {
            if ("Rejected".equals(status)) {
                EmailUtility.sendReason(re.getEmail(), reason, re.getEventName());
            } else if ("Confirmed".equals(status)) {
                EmailUtility.sendSuccessNotification(re.getEmail(), re.getEventName());
            }
            session.setAttribute("success", "Registration form update successfully!");
            response.sendRedirect("registrationDetail?id=" + id);
            return;
        } else {
            request.getRequestDispatcher("/WEB-INF/view/event/registrationdetail.jsp").forward(request, response);
        }
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
