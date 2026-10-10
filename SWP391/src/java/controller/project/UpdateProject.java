/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.project;

import java.sql.Timestamp;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import model.entity.Event;
import service.project.ProjectService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.entity.ProjectMember;
import model.entity.User;

/**
 *
 * @author nam
 */
@WebServlet(name = "UpdateProject", urlPatterns = {"/UpdateProject"})
public class UpdateProject extends HttpServlet {

    private ProjectService projectService;

    @Override
    public void init() {
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateProject</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProject at " + request.getContextPath() + "</h1>");
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
    private Timestamp convertStringToTimestamp(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(dateStr);
        return new Timestamp(parsedDate.getTime());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();

        List<Integer> clubIds;
        try {
            clubIds = projectService.getClubIdsByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage");
            return;
        }

        if (clubIds.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            String eventIdStr = request.getParameter("project_id");
            String name = request.getParameter("name");
            String location = request.getParameter("location");
            String startTimeStr = request.getParameter("start_time");
            String endTimeStr = request.getParameter("end_time");
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            int leaderId = Integer.parseInt(request.getParameter("leader_id"));
            int eventId;
            try {
                eventId = Integer.parseInt(eventIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Project ID không hợp lệ.");
                request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);
                return;
            }

            // Chuyển đổi ngày tháng
            Timestamp startTime = null;
            Timestamp endTime = null;
            try {
                startTime = convertStringToTimestamp(startTimeStr);
                endTime = convertStringToTimestamp(endTimeStr);
            } catch (ParseException e) {
                request.setAttribute("error", "Định dạng ngày không hợp lệ!");
                request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);
                return;
            }

            // Lấy dữ liệu cũ
            Event oldEvent = projectService.getProjectDetail(eventId);
            if (oldEvent == null) {
                request.setAttribute("error", "Project not found.");
                request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);
                return;
            }

            // Lấy danh sách thành viên của tất cả các câu lạc bộ mà user đang quản lý
            List<User> clubMembers = new ArrayList<>();
            for (int clubId : clubIds) {
                clubMembers.addAll(projectService.getClubMembers(clubId));
            }
            String leaderName = null;
            for (User member : clubMembers) {
                if (member.getUser_id() == leaderId) {
                    leaderName = member.getFull_name();
                    break;
                }
            }
            List<ProjectMember> projectMembers = projectService.getProjectMembers(eventId);
              request.setAttribute("projectMembers", projectMembers);
            // Tạo đối tượng Event giữ nguyên club_id và leader_id
            Event event = new Event(eventId, oldEvent.getClub_id(), name, leaderId, leaderName,
                    startTimeStr, endTimeStr, status, description, oldEvent.getCreated_date(), location);
            // Kiểm tra lỗi
            Map<String, String> errors = projectService.validateProject(event);
            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("event", event);
                request.setAttribute("members", clubMembers);
               // List<ProjectMember> projectMembers = projectService.getProjectMembers(eventId);
                request.setAttribute("projectMembers", projectMembers);
                request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);
                return;
            }

            // Cập nhật dự án
            boolean success = projectService.updateProject(event);
            if (success) {
                response.sendRedirect("ProjectList");
            } else {
                request.setAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
                request.setAttribute("event", event);
                request.setAttribute("members", clubMembers);
                request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/project/ProjectDetail.jsp").forward(request, response);
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
