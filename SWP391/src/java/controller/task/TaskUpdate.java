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
import java.util.Date;
import java.util.List;
import model.entity.ClubMember;
import model.entity.Event;
import model.entity.Task;
import model.entity.User;
import service.task.TaskService;

/**
 *
 * @author datkh
 */
@WebServlet(name = "UpdateTask", urlPatterns = {"/updateTask"})
public class TaskUpdate extends HttpServlet {

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
        String taskIdParam = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();
        if (taskIdParam == null || taskIdParam.isEmpty()) {
            response.sendRedirect("taskList");
            return;
        } else {
            TaskService taskService = new TaskService();
            Task task = taskService.getTaskWithTaskID(Integer.parseInt(taskIdParam));
            request.setAttribute("task", task);

            List<ClubMember> alluser = taskService.getAllClubMembersByEventOrProjectId(task.getEventId(), task.getType());
            request.setAttribute("alluser", alluser);
            alluser.removeIf(member -> member.getUserId() == task.getAssignerId());

            List<ClubMember> alluser1 = taskService.getAllClubMembersByEventOrProjectId(task.getEventId(), task.getType());
            request.setAttribute("alluser1", alluser1);
            alluser1.removeIf(member -> member.getUserId() == task.getAssigneeId());

            List<Event> allevent = taskService.getAllEventProjectByFieldName(task.getClubId(), task.getType());
            request.setAttribute("allevent", allevent);

            List<Integer> leaderId = taskService.getUserRolesInClub(userId, task.getClubId());
            session.setAttribute("clubroles", leaderId);

            request.getRequestDispatcher("/WEB-INF/view/task/updatetask.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String eventId = request.getParameter("eventId");
        String assigneeId = request.getParameter("assigneeId");
        String assignerId = request.getParameter("assignerId");
        String deadlineStr = request.getParameter("deadline");
        String status = request.getParameter("status");
        String taskIdParam = request.getParameter("id");
        String note = request.getParameter("note");
        TaskService taskService = new TaskService();
        Task t = taskService.getTaskWithTaskID(Integer.parseInt(taskIdParam));
        User user = (User) session.getAttribute("account");
        int userId = user.getUser_id();

        if (title.trim().isEmpty()) {
            session.setAttribute("errorTitle", "Title and description must not null");
            response.sendRedirect("taskList?showAdd=true");
            return;
        }

        if (title.length() > 50) {
            session.setAttribute("errorTitle", "Title Must be lower than 50 characters!");
            response.sendRedirect("updateTask?id=" + taskIdParam);
            return;
        }

        if (description.length() > 255) {
            session.setAttribute("errordescription", "Description Must be lower than 255 characters!");
            response.sendRedirect("updateTask?id=" + taskIdParam);
            return;
        }

        if (note.length() > 255) {
            session.setAttribute("errornote", "Note Must be lower than 255 characters!");
            response.sendRedirect("updateTask?id=" + taskIdParam);
            return;
        }

        if (deadlineStr == null || deadlineStr.isEmpty()) {
            request.setAttribute("error", "Deadline không được để trống!");
            request.getRequestDispatcher("taskList").forward(request, response);
            return;
        }

        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr);

            Instant instant = deadline.atZone(ZoneId.systemDefault()).toInstant();
            Date deadlineDate = Date.from(instant);

            Task task = new Task(
                    Integer.parseInt(taskIdParam),
                    Integer.parseInt(eventId),
                    t.getClubId(),
                    title,
                    Integer.parseInt(assignerId),
                    Integer.parseInt(assigneeId),
                    deadlineDate,
                    status,
                    description
            );
            task.setNote(note);

            boolean check = taskService.updateTask(task);
            if (check) {
                session.setAttribute("success", "Update Success!");
                response.sendRedirect("taskDetail?id=" + taskIdParam);
                session.removeAttribute("clubroles");
                return;
            } else {
                request.setAttribute("error", "Có lỗi xảy ra khi cập nhật Task!");
                request.getRequestDispatcher("taskDetail?id=" + taskIdParam).forward(request, response);
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Định dạng ngày không hợp lệ! Hãy nhập đúng định dạng MM/dd/yyyy h:mm a.");
            request.getRequestDispatcher("taskDetail?id=" + taskIdParam).forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Event ID hoặc Assignee ID không hợp lệ!");
            request.getRequestDispatcher("taskDetail?id=" + taskIdParam).forward(request, response);
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
