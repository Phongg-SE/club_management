/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.RegistrationEvent;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import model.entity.Club;
import model.entity.Event;
import model.entity.RegistrationEvent;
import model.entity.User;
import service.RegistrationEvent.RegistrationEventService;
import service.post.BlogService;

/**
 *
 * @author admin
 */
@WebServlet(name = "RegistrationEventList", urlPatterns = {"/registrationEventList"})
public class RegistrationEventList extends HttpServlet {

    RegistrationEventService registrationService = new RegistrationEventService();

    @Override
    public void init() throws ServletException {
        registrationService = new RegistrationEventService();
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistrationEventList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistrationEventList at " + request.getContextPath() + "</h1>");
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
        // Lấy User về từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        // Lấy giá trị từ request
        String clubIdParam = request.getParameter("clubid");
        String eventIdParam = request.getParameter("eventId");
        String statusFilter = request.getParameter("statusFilter");
        String searchParam = request.getParameter("search");

        // Lấy danh sách clubs của user
        List<Club> clubsToFilter = registrationService.getClubByUserIdToFilter(user.getUser_id());
        request.setAttribute("clubsToFilter", clubsToFilter);

        Integer clubIdInt = null;
        Integer eventIdInt = null;
        // Lấy giá trị cũ từ session để lưu filter hợp lí
        Integer oldClub = (Integer) session.getAttribute("clubIdParam");
        // Kiểm tra và chuyển đổi clubId
        if (clubIdParam != null && !clubIdParam.trim().isEmpty()) {
            try {
                clubIdInt = Integer.parseInt(clubIdParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid club ID");
                return;
            }
        }
        // Kiểm tra và chuyển đổi eventId
        if (eventIdParam != null && !eventIdParam.trim().isEmpty()) {
            try {
                eventIdInt = Integer.parseInt(eventIdParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
                return;
            }
        }
        // Xử lý phân trang
        int page = 1;
        int pageSize = 3; // Số bản ghi trên mỗi trang
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // Mặc định về trang đầu nếu có lỗi
            }
        }

        // Nếu club thay đổi thì reset event
        if (!Objects.equals(oldClub, clubIdInt)) {
            eventIdInt = null;
        }
        // Nếu statusFilter quay về mặc định, sử dụng giá trị ban đầu
        if (statusFilter == null || statusFilter.equals("Select Status")) {
            statusFilter = null;
        }

        // Lấy danh sách events của user là leader
        if (clubIdInt != null) {
            List<Event> eventsToFilter = registrationService.getEventsByClubIdAndLeaderId(clubIdInt, user.getUser_id());
            request.setAttribute("eventsToFilter", eventsToFilter);
        } else {
            List<Event> eventsToFilter = registrationService.getEventsByLeaderId(user.getUser_id());
            request.setAttribute("eventsToFilter", eventsToFilter);
        }
        // Tính tổng số bản ghi
        int totalRecords = registrationService.countRegistrations(user.getUser_id(), clubIdInt, eventIdInt, statusFilter, searchParam);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Lấy ra danh sách sau khi filter và phân trang
        if (clubIdInt != null || eventIdInt != null || statusFilter != null || searchParam != null) {
            List<RegistrationEvent> registrationEvents = registrationService.getRegistrationsByFilter(user.getUser_id(), clubIdInt, eventIdInt, statusFilter, searchParam, page, pageSize);
            request.setAttribute("registrationEvents", registrationEvents);
        } else {
            List<RegistrationEvent> registrationEvents = registrationService.getRegistrationByLeaderId(user.getUser_id(), page, pageSize);
            request.setAttribute("registrationEvents", registrationEvents);
        }
        // Lưu lại trạng thái filter vào session
        session.setAttribute("clubIdParam", clubIdInt);
        session.setAttribute("eventIdParam", eventIdInt);
        // Gửi thông tin phân trang đến JSP
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/WEB-INF/view/event/registrationlist.jsp").forward(request, response);
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
