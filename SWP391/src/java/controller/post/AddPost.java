package controller.post;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import service.post.PostService;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@MultipartConfig(maxFileSize = 16177215)
@WebServlet(name = "AddPost", urlPatterns = {"/AddPost"})
public class AddPost extends HttpServlet {

    private PostService postService;

    @Override
    public void init() {
        postService = new PostService();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));

            // Kiểm tra clubId
            String clubIdStr = request.getParameter("clubId");
            int clubId = (clubIdStr != null && !clubIdStr.isEmpty()) ? Integer.parseInt(clubIdStr) : -1;

            String title = request.getParameter("title").trim();
            int categoryId = Integer.parseInt(request.getParameter("category"));
            
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            String titleError = postService.validatePostInput(title);
            request.setAttribute("userId", userId);
            request.setAttribute("error", titleError);
            request.setAttribute("title", title);
            request.setAttribute("description", description); // Giữ lại mô tả
            request.setAttribute("category", categoryId); // Giữ lại category
            request.setAttribute("status", status); // Giữ lại status
            request.setAttribute("clubs", postService.getAllClubs(userId)); // Giả sử bạn có method getClubs() để lấy danh sách các câu lạc bộ
            request.setAttribute("categories", postService.getAllCategories2()); // Giả sử bạn có method getCategories() để lấy danh sách các category

            if (titleError != null) {

                request.getRequestDispatcher("/WEB-INF/view/post/AddPost.jsp").forward(request, response); // Quay lại trang JSP
                return;
            }
            // Xử lý ảnh
            Part filePart = request.getPart("imageFile"); // Lấy file từ form
            String imageUrl = "default.jpg"; // Giá trị mặc định nếu không có ảnh được tải lên

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName(); // Tạo tên file duy nhất
                String uploadPath = request.getServletContext().getRealPath("/uploads"); // Thư mục lưu ảnh trong project

                // Tạo thư mục nếu chưa tồn tại
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Đường dẫn đầy đủ để lưu file
                File fileSavePath = new File(uploadPath + File.separator + fileName);

                // Ghi file ảnh vào thư mục
                try (InputStream fileContent = filePart.getInputStream(); FileOutputStream fos = new FileOutputStream(fileSavePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileContent.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }

                // Cập nhật đường dẫn ảnh để lưu vào DB
                imageUrl = "uploads/" + fileName; // Đường dẫn tương đối
            }
            request.setAttribute("imageUrl", imageUrl);

            // Gọi service để thêm bài viết
            if (titleError == null) {
                boolean success = postService.addPost(userId, clubId, title, imageUrl, description, categoryId, status);

                System.out.println("DEBUG: addPost result = " + success);

                if (success) {
                     response.sendRedirect("PostList?toastSuccess=true");
                } else {
                    request.setAttribute("error", "Failed to add post. Please try again.");
                    request.getRequestDispatcher("/WEB-INF/view/post/AddPost.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid number format for userId or clubId.");
            response.sendRedirect(request.getContextPath() + "/AddNewPost");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/AddNewPost");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles adding new posts";
    }
}
