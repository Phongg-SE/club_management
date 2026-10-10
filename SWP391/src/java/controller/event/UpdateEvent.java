/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.event;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import model.entity.Event;
import model.entity.User;
import service.club.ClubService;
import service.event.EventService;

/**
 *
 * @author Dell
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 2mb
        maxFileSize = 1024 * 1024 * 20, // 10mb    
        maxRequestSize = 1024 * 1024 * 100) // 50mb
@WebServlet(name = "UpdateEvent", urlPatterns = {"/UpdateEvent"})
public class UpdateEvent extends HttpServlet {

    private EventService eventService = new EventService();
    private ClubService clubService = new ClubService();

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
        processRequest(request, response);
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Lấy dữ liệu từ request
            int eventID = Integer.parseInt(request.getParameter("event_id"));
            String eventName = request.getParameter("name");
            String location = request.getParameter("location");
            String guest = request.getParameter("guest");
            String isPublic = request.getParameter("is_public");
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            int leaderID = Integer.parseInt(request.getParameter("leader_id"));

            // Lấy session và kiểm tra đăng nhập
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Lấy user_id từ session
            User user = (User) session.getAttribute("account");
            int userId = user.getUser_id();

            Event event1 = eventService.getEventById(eventID);
            int ClubID = event1.getClub_id();

            Integer clubId1 = (Integer) request.getSession().getAttribute("selectedClubId2");
            boolean isLeader = false;
            boolean isEventLeader = false;
            if (user != null) {
                isLeader = clubService.isClubLeader(clubId1, user.getUser_id()); // Kiểm tra quyền Leader
                isEventLeader = eventService.isEventLeader(user.getUser_id(), clubId1);
            }

            if (!isLeader && !isEventLeader) {
                request.getSession().setAttribute("errorMessage", "Bạn không có quyền cập nhật.");
                response.sendRedirect(request.getContextPath() + "/EventList");
                return;
            }

            // Kiểm tra tên sự kiện (không được null, từ 5 đến 50 ký tự)
            if (eventName == null || eventName.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên sự kiện không được để trống.");
            }
            if (eventName.length() < 3 || eventName.length() > 50) {
                throw new IllegalArgumentException("Tên sự kiện phải từ 5 đến 50 ký tự.");
            }

            // Kiểm tra địa điểm (không được null, từ 5 đến 250 ký tự)
            if (location == null || location.trim().isEmpty()) {
                throw new IllegalArgumentException("Địa điểm không được để trống.");
            }
            if (location.length() < 3 || location.length() > 250) {
                throw new IllegalArgumentException("Địa điểm phải từ 5 đến 250 ký tự.");
            }

            // Kiểm tra mô tả (có thể null, nhưng nếu có thì không quá 1000 ký tự)
            if (description != null && description.length() > 1000) {
                throw new IllegalArgumentException("Mô tả không được vượt quá 1000 ký tự.");
            }

            // Định dạng ngày tháng phù hợp với input type="datetime-local"
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // lưu trữ

            String startTimeStr = request.getParameter("start_time");
            String endTimeStr = request.getParameter("end_time");

            Date startDate = null;
            Date endDate = null;

            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                startDate = inputFormat.parse(startTimeStr); // Chuyển đổi từ định dạng input
            }
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                endDate = inputFormat.parse(endTimeStr);
            }

            // Chuyển đổi lại thành chuỗi theo định dạng chuẩn để lưu vào database
            String startTime = (startDate != null) ? outputFormat.format(startDate) : null;
            String endTime = (endDate != null) ? outputFormat.format(endDate) : null;

            // Kiểm tra nếu startDate lớn hơn endDate
            if (startDate != null && endDate != null && startDate.after(endDate)) {
                request.getSession().setAttribute("errorMessage", "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            // Giữ ảnh cũ nếu không upload ảnh mới
            String eventImageUrl = request.getParameter("oldEventImage");
            Part eventImagePart = request.getPart("eventImage");

            // Xử lý upload ảnh mới nếu có
            if (eventImagePart != null && eventImagePart.getSize() > 0) {
                String fileName = Paths.get(eventImagePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = request.getServletContext().getRealPath("/uploads");

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs(); // Dùng mkdirs() để tạo cả thư mục cha nếu cần
                }

                File file = new File(uploadDir, fileName);
                eventImagePart.write(file.getAbsolutePath());

                eventImageUrl = "uploads/" + fileName;  // Cập nhật ảnh mới
            }

            // Kiểm tra giá trị null
            eventImageUrl = (eventImageUrl != null && !eventImageUrl.trim().isEmpty()) ? eventImageUrl : null;
            guest = (guest != null && !guest.trim().isEmpty()) ? guest : null;
            description = (description != null && !description.trim().isEmpty()) ? description : null;

            // Tạo đối tượng Event
            Event newEvent = new Event(eventID, ClubID, leaderID, eventName, eventImageUrl, guest, location, startTime, endTime, isPublic, status, description);

            // Cập nhật sự kiện
            boolean isUpdated = eventService.updateEvent(newEvent);
            if (isUpdated) {
                request.getSession().setAttribute("successMessage", "Update thành công!");
                response.sendRedirect(request.getContextPath() + "/EventList");
            } else {
                request.getSession().setAttribute("errorMessage", "Không thể cập nhật event");
                response.sendRedirect(request.getHeader("referer"));
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Lỗi định dạng số: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
        } catch (DateTimeParseException e) {
            request.getSession().setAttribute("errorMessage", "Lỗi định dạng ngày: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
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
