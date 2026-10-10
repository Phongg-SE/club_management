/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.club;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import model.entity.Club;
import model.entity.Setting;
import model.entity.User;
import service.club.ClubService;

/**
 *
 * @author pc
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 2mb
        maxFileSize = 1024 * 1024 * 20, // 10mb    
        maxRequestSize = 1024 * 1024 * 100) // 50mb

@WebServlet(name = "login", urlPatterns = {"/updateclub"})
public class UpdateClub extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            // Lấy dữ liệu từ form
            int clubId = Integer.parseInt(request.getParameter("id"));
            String clubName = request.getParameter("clubName");
            String slogan = request.getParameter("slogan");
            String clubEmail = request.getParameter("clubEmail");
            String clubFanpage = request.getParameter("clubFanpage");
            Part clubLogoPart = request.getPart("clubLogo"); // Lấy file upload
            String isPublic = request.getParameter("isPublic");
            String description = request.getParameter("description");
            String status = request.getParameter("status");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // Giữ ảnh cũ nếu không upload ảnh mới
            String clubLogoUrl = request.getParameter("oldLogoUrl");

            // Lấy leader ID cũ và mới
            int oldLeaderId = Integer.parseInt(request.getParameter("oldLeaderId"));
            int newLeaderId = Integer.parseInt(request.getParameter("clubLeaderId"));

            // Xử lý upload ảnh
            if (clubLogoPart != null && clubLogoPart.getSize() > 0) {
                String fileName = Paths.get(clubLogoPart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = request.getServletContext().getRealPath("/uploads");

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                File file = new File(uploadDir, fileName);
                clubLogoPart.write(file.getAbsolutePath());

                clubLogoUrl = "uploads/" + fileName;
            }

            // Tạo đối tượng Club
            Club updatedClub = new Club(clubId, clubName, slogan, clubEmail, clubFanpage, clubLogoUrl, isPublic, categoryId, status, description);
            // Gọi service để kiểm tra và cập nhật club
            List<String> validationErrors = clubService.validateAndUpdateClub(updatedClub);

            for (int i = 0; i < validationErrors.size(); i++) {
                System.out.println(validationErrors);
            }
            if (!validationErrors.isEmpty() && !validationErrors.contains("Club updated successfully.")) {
                request.setAttribute("msgList", validationErrors);
                // Lấy danh mục từ category_id của một CLB cụ thể
                List<User> users = clubService.getUsersByClubId(clubId);
                request.setAttribute("users", users);
                
                Club clubToGetCategoryID = clubService.getClubById(1);
                int type_id = clubService.getTypeIdBySettingId(clubToGetCategoryID.getCategory_id());
                List<Setting> categories = clubService.getSettingsByType(type_id);
                Club clubfail = new Club(clubId, clubName, slogan, clubEmail, clubFanpage, clubLogoUrl, isPublic, categoryId, status, "", "", "", newLeaderId);
                request.setAttribute("setting", categories);
                request.setAttribute("club", clubfail);
                request.getRequestDispatcher("/WEB-INF/view/club/updateclub.jsp").forward(request, response);
            } else {
                // Nếu leader thay đổi, cập nhật lại vai trò trong club_member
                if (oldLeaderId != newLeaderId) {
                    clubService.updateClubLeader(clubId, oldLeaderId, newLeaderId);
                }
                // Output the success message
                session.setAttribute("toastMessage", "Club updated successfully");
                System.out.println("Club updated successfully: " + updatedClub.getClub_id());
                response.sendRedirect(request.getContextPath() + "/clublist");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Enhanced logging
            response.getWriter().write("Lỗi định dạng số: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Enhanced logging
            response.getWriter().write("Lỗi: " + e.getMessage());
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

        int clubId = Integer.parseInt(request.getParameter("id"));
        Club club = clubService.getClubById(clubId);
        // Lấy danh mục từ category_id của một CLB cụ thể
        Club clubToGetCategoryID = clubService.getClubById(1);
        int type_id = clubService.getTypeIdBySettingId(clubToGetCategoryID.getCategory_id());
        List<Setting> categories = clubService.getSettingsByType(type_id);

        List<User> users = clubService.getUsersByClubId(clubId);
        request.setAttribute("users", users);

        request.setAttribute("setting", categories);
        request.setAttribute("club", club);

        request.getRequestDispatcher("/WEB-INF/view/club/updateclub.jsp").forward(request, response);

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
