/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.post;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.Map;
import service.post.PostService;

/**
 *
 * @author nam
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 2mb
        maxFileSize = 1024 * 1024 * 20, // 10mb    
        maxRequestSize = 1024 * 1024 * 100) // 50mb
@WebServlet(name = "UpdatePost", urlPatterns = {"/UpdatePost"})
public class UpdatePost extends HttpServlet {

    private final PostService postService = new PostService();

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
        try {
            // Nhận dữ liệu từ form
            int postId = Integer.parseInt(request.getParameter("postId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            String title = request.getParameter("title");
            String imageUrl = request.getParameter("imageUrl");
            String description = request.getParameter("description");
            String author = request.getParameter("author");
            String status = request.getParameter("status");
            String clubName = request.getParameter("clubName");
            String category = request.getParameter("category");
            Part filePart = request.getPart("imageFile");
            Map<String, String> errors = postService.validatePost(title, description);
            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);

                request.setAttribute("title", title);
                request.setAttribute("description", description);
                request.setAttribute("author", author);
                request.setAttribute("status", status);
                request.setAttribute("clubName", clubName);
                request.setAttribute("category", category);
                request.setAttribute("postId", postId);
                request.getRequestDispatcher("/WEB-INF/view/post/PostDetail.jsp").forward(request, response);
                return;
            }
            //   String imageUrl = request.getParameter("imageUrl"); // Đường dẫn ảnh cũ
            if (filePart != null && filePart.getSize() > 0) {
                // Lấy tên file gốc
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                // Định nghĩa thư mục lưu ảnh
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                // Lưu file ảnh vào thư mục uploads
                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);

                // Cập nhật đường dẫn ảnh mới
                imageUrl = "uploads/" + fileName;
            }
            // Gọi service để update
            boolean success = postService.updatePost(postId, title, imageUrl, description, author, status, clubName, category);

            // Redirect hoặc hiển thị thông báo
            if (success) {
                //request.getRequestDispatcher("/WEB-INF/view/post/PostList.jsp").forward(request, response);
                response.sendRedirect("PostList?user_Id=" + userId);
            } else {
                response.sendRedirect("PostDetail?post_Id=" + postId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("editPost.jsp?error=Invalid data");
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
