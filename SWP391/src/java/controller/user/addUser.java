package controller.user;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import service.account.AccountService;
import java.io.PrintWriter;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "addUser", urlPatterns = {"/addUser"})
public class addUser extends HttpServlet {

    private final AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> responseData = new HashMap<>();

        try {
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phonenumber");
            String status = request.getParameter("status");
            String gender = request.getParameter("gender");

            // Kiểm tra roleId hợp lệ
            int roleId;
            try {
                roleId = Integer.parseInt(request.getParameter("role"));
            } catch (NumberFormatException e) {
                responseData.put("status", "error");
                responseData.put("message", "Invalid role ID format.");
                out.write(new Gson().toJson(responseData));
                out.close();
                return;
            }

            // Validate dữ liệu
            Map<String, String> errors = accountService.validateAccount(0,fullName, email, username, phoneNumber);
            if (!errors.isEmpty()) {

                responseData.put("status", "error");
                responseData.put("errors", errors);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                out.write(new Gson().toJson(responseData));
                out.flush();  // Đảm bảo dữ liệu được gửi đầy đủ
                out.close();
                return;
            }

            // Xử lý upload ảnh
            Part filePart = request.getPart("avatar");
            String avatarPath = "";
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (!fileName.isEmpty()) {
                    String uploadPath = getServletContext().getRealPath("/") + "uploads";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String filePath = uploadPath + File.separator + fileName;
                    filePart.write(filePath);
                    avatarPath = "uploads/" + fileName;
                }
            }

            // Lưu vào database
            boolean success = accountService.addAccount(fullName, email, username, gender, phoneNumber, avatarPath, roleId, status);

            if (success) {
                responseData.put("status", "success");
                responseData.put("message", "Account added successfully!");
            } else {
                responseData.put("status", "error");
                responseData.put("message", "Failed to add account. Please try again.");
            }
        } catch (Exception e) {
            responseData.put("status", "error");
            responseData.put("message", "Server error: " + e.getMessage());
        }

        // Trả về phản hồi JSON
        out.write(new Gson().toJson(responseData));
        out.close();
    }
}
