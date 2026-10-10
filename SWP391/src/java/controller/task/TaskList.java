/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.task;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.Event;
import model.entity.Task;
import model.entity.User;
import service.task.TaskService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "TaskList", urlPatterns = {"/taskList"})
public class TaskList extends HttpServlet {

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
        TaskService taskService = new TaskService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            request.getRequestDispatcher("login").forward(request, response);
        }
        String filterStatus = request.getParameter("statusFilter");
        String filterClub = request.getParameter("clubid");
        String filterEvent = request.getParameter("eventid");
        String filterAssignee = request.getParameter("assigneeeid");
        String search = request.getParameter("txt");
        String errorDeadline = (String) session.getAttribute("errorDeadline");
        String taskType = request.getParameter("type");

        if (errorDeadline != null) {
            request.setAttribute("errorDeadline", errorDeadline);
            session.removeAttribute("errorDeadline");
        }

        String errorTitle = (String) session.getAttribute("errorTitle");
        if (errorTitle != null) {
            request.setAttribute("errorTitle", errorTitle);
            session.removeAttribute("errorTitle");
        }

        List<Club> clubs = taskService.getClubIdByUserId(user.getUser_id());
        request.setAttribute("clubs", clubs);

        Integer clubIdInt = null;
        Integer eventIdInt = null;
        Integer assigneeIdInt = null;
        // Kiểm tra và chuyển đổi clubId
        if (filterClub != null && !filterClub.trim().isEmpty()) {
            try {
                clubIdInt = Integer.parseInt(filterClub);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid club ID");
                return;
            }
        }

        // Kiểm tra và chuyển đổi eventId
        if (filterEvent != null && !filterEvent.trim().isEmpty()) {
            try {
                eventIdInt = Integer.parseInt(filterEvent);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
                return;
            }
        }

        if (filterAssignee != null && !filterAssignee.trim().isEmpty()) {
            try {
                assigneeIdInt = Integer.parseInt(filterAssignee);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
                return;
            }
        }

        Integer oldClub = (Integer) session.getAttribute("filterclub");

        String oldType = (String) session.getAttribute("oldType");

        if (oldType == null) {
            oldType = "";
        }

        if (oldClub == null || !oldClub.equals(clubIdInt)) {
            eventIdInt = null;
            assigneeIdInt = null;
        }

        if (oldType == null || !oldType.equals(taskType)) {
            eventIdInt = null;
            assigneeIdInt = null;
        }

        if (taskType == null) {
            taskType = "Event";
        }

        if ("Event".equals(taskType) || taskType == null) {
            List<Event> events = (clubIdInt != null) ? taskService.getAllEventProjectByFieldNames(user.getUser_id(), clubIdInt, "Event") : new ArrayList<>();
            request.setAttribute("events", events);
        } else {
            List<Event> events = (clubIdInt != null) ? taskService.getAllEventProjectByFieldNames(user.getUser_id(), clubIdInt, "Project") : new ArrayList<>();
            request.setAttribute("events", events);
        }
        List<ClubMember> assignee = (clubIdInt != null) ? taskService.getAllClubMembersByClubId(clubIdInt) : new ArrayList<>();
        request.setAttribute("assignees", assignee);

        int page = 1, pageSize = 4;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            page = 1;
            pageSize = 10;
        }

        List<Task> tasks;
        if (clubIdInt != null || eventIdInt != null || filterStatus != null) {
            if ("Event".equals(taskType)) {
                int totalTasks = taskService.getTotalTask(user.getUser_id(), filterStatus, clubIdInt, eventIdInt, assigneeIdInt, search, false);
                int totalPages = (int) Math.ceil((double) totalTasks / pageSize);
                tasks = taskService.getTaskByFilter(user.getUser_id(), filterStatus, clubIdInt, eventIdInt, assigneeIdInt, search, page, pageSize, false);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
            } else {
                int totalTasks = taskService.getTotalTask(user.getUser_id(), filterStatus, clubIdInt, eventIdInt, assigneeIdInt, search, true);
                int totalPages = (int) Math.ceil((double) totalTasks / pageSize);
                tasks = taskService.getTaskByFilter(user.getUser_id(), filterStatus, clubIdInt, eventIdInt, assigneeIdInt, search, page, pageSize, true);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
            }
        } else {
            int totalTasks = taskService.getTotalTask(user.getUser_id(), filterStatus, clubIdInt, eventIdInt, assigneeIdInt, search, false);
            int totalPages = (int) Math.ceil((double) totalTasks / pageSize);
            tasks = taskService.getUserTasks(user.getUser_id(), page, pageSize);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
        }

        request.setAttribute("taskType", taskType);
        session.setAttribute("filterclub", clubIdInt);
        session.setAttribute("oldType", taskType);
        request.setAttribute("tasks", tasks);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("/WEB-INF/view/task/tasklist.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String eventId = request.getParameter("eventId");
        String clubId = request.getParameter("clubId");
        String assigneeId = request.getParameter("assigneeId");
        String deadlineStr = request.getParameter("deadline");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();
        TaskService taskService = new TaskService();

        List<Integer> leaderId = taskService.getUserRolesInClub(userId, Integer.parseInt(clubId));

        if (title.trim().isEmpty()) {
            session.setAttribute("errorTitle", "Title must not null");
            response.sendRedirect("taskList?showAdd=true");
            return;
        }

        if (title.length() > 50) {
            session.setAttribute("errorTitle", "Title must be lower than 50 character!");
            response.sendRedirect("taskList?showAdd=true");
            return;
        }

        if (description.length() > 255) {
            session.setAttribute("errorDescription", "Status must be lower than 255 character!");
            response.sendRedirect("taskList?showAdd=true");
            return;
        }

        if (deadlineStr == null || deadlineStr.isEmpty()) {
            session.setAttribute("errorDeadline", "Deadline không được để trống!");
            response.sendRedirect("taskList?showAdd=true");
            return;
        }

        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr);
            LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

            if (deadline.isBefore(now)) {
                session.setAttribute("errorDeadline", "Deadline must be in the future!");
                response.sendRedirect("taskList?showAdd=true");
                return;
            }

            Instant instant = deadline.atZone(ZoneId.systemDefault()).toInstant();
            Date deadlineDate = Date.from(instant);

            if (leaderId.contains(3)) {
                status = "Pending";
            }

            Task task = new Task(
                    0,
                    Integer.parseInt(eventId),
                    Integer.parseInt(clubId),
                    title,
                    userId,
                    Integer.parseInt(assigneeId),
                    deadlineDate,
                    status,
                    description
            );

            boolean check = taskService.addTask(task);
            if (check) {
                response.sendRedirect("taskList?message=" + "Add Task Success!");
            } else {
                response.sendRedirect("taskList?error=" + java.net.URLEncoder.encode("Có lỗi xảy ra khi thêm Task!", "UTF-8"));
            }
        } catch (DateTimeParseException e) {
            session.setAttribute("errorDeadline", "Định dạng deadline không hợp lệ!");
            response.sendRedirect("taskList?showAdd=true");
        } catch (NumberFormatException e) {
            session.setAttribute("errorDeadline", "Dữ liệu nhập vào không hợp lệ!");
            response.sendRedirect("taskList?showAdd=true");
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
