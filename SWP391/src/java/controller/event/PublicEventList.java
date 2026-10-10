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
import model.entity.Club;
import model.entity.Event;
import service.event.PublicEventService;

/**
 *
 * @author admin
 */
@WebServlet(name = "PublicEventList", urlPatterns = {"/PublicEventList"})
public class PublicEventList extends HttpServlet {

    private PublicEventService eventService;

    @Override
    public void init() throws ServletException {
        eventService = new PublicEventService(); // Khởi tạo PublicEventService
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
        // Lấy giá trị về từ request
        String clubIdParam = request.getParameter("clubid");
        String searchParam = request.getParameter("search");

        // Lấy danh sách clubs thông qua public event
        List<Club> clubsToFilter = eventService.getClubsFromPublicEvents();
        request.setAttribute("clubsToFilter", clubsToFilter);

        Integer clubIdInt = null;
        // Kiểm tra và chuyển đổi clubId
        if (clubIdParam != null && !clubIdParam.trim().isEmpty()) {
            try {
                clubIdInt = Integer.parseInt(clubIdParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid club ID");
                return;
            }
        }
        // Xử lí phân trang 
        int page = 1;
        int pageSize = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // Mặc định về trang đầu nếu có lỗi
            }
        }
        // Tính tổng số bản ghi
        int totalRecords = eventService.countPublicEventsByClubId(clubIdInt, searchParam);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Tạo danh sách event
        List<Event> publicEventList;
        if (clubIdInt != null || searchParam != null) {
            publicEventList = eventService.getPublicEventsByClubId(clubIdInt, searchParam, page, pageSize);
        } else {
            publicEventList = eventService.getAllPublicEvent(page, pageSize);
        }

        List<Event> upcomingEvent = eventService.getUpcomingEvents();
        request.setAttribute("events", publicEventList);
        request.setAttribute("upcomingEvent", upcomingEvent);
//        request.setAttribute("events", );
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/WEB-INF/view/event/publiceventlist.jsp").forward(request, response);
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
