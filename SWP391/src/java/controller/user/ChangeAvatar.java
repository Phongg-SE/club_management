/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

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
import model.entity.User;
import service.user.UserProfileService;

/**
 *
 * @author pc
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "ChangeAvatar", urlPatterns = {"/ChangeAvatar"})
public class ChangeAvatar extends HttpServlet {

    private UserProfileService userProfileService = new UserProfileService();

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
        response.setCharacterEncoding("UTF-8"); 
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account"); // Lấy user_id từ session
        if (user == null) {
            session.setAttribute("toastMessage", "Lỗi: Người dùng chưa đăng nhập!");
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu cần
            return;
        }
        int userId = user.getUser_id();
        Part filePart = request.getPart("avatar"); // Lấy file từ form
        if (filePart == null || filePart.getSize() == 0) {
            session.setAttribute("toastMessage", "Lỗi: Không có ảnh được chọn!");
            response.sendRedirect("userprofile");
            return;
        }

        // Lưu file vào thư mục trên server
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Lưu đường dẫn vào database
        String avatarUrl = "uploads/" + fileName;
        boolean success = userProfileService.changeAvatar(userId, avatarUrl);

       if (success) {
    session.setAttribute("toastMessage", "Avatar đã cập nhật thành công!");
} else {
    session.setAttribute("toastMessage", "Lỗi: Không thể cập nhật avatar!");
}

        
        User updatedUser = userProfileService.getUserById(userId); // Lấy user mới từ DB
        session.setAttribute("account", updatedUser); // Cập nhật vào session

        System.out.println("Toast message: " + session.getAttribute("toastMessage"));

        response.sendRedirect(request.getContextPath() + "/userprofile");
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
