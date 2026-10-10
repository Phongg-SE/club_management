package controller.user;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import service.account.AccountService;

@WebServlet(name = "UpdateAccount", urlPatterns = {"/UpdateAccount"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class UpdateAccount extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AccountService accountService;

    public UpdateAccount() {
        super();
        accountService = new AccountService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy userId từ request và kiểm tra lỗi
            int userId = -1;
            try {
                userId = Integer.parseInt(request.getParameter("userid"));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid user ID format.");
                request.getRequestDispatcher("/WEB-INF/view/account/AccountDetail.jsp").forward(request, response);
                return;
            }

            // Lấy dữ liệu từ request (tránh null)
            String fullName = request.getParameter("fullname") != null ? request.getParameter("fullname") : "";
            String email = request.getParameter("gmail") != null ? request.getParameter("gmail") : "";
            String username = request.getParameter("username") != null ? request.getParameter("username") : "";
            String gender = request.getParameter("gender") != null ? request.getParameter("gender") : "Unknown";
            String phoneNumber = request.getParameter("phonenumber") != null ? request.getParameter("phonenumber") : "";
            String roleName = request.getParameter("role") != null ? request.getParameter("role") : "Web User";
            String status = request.getParameter("status") != null ? request.getParameter("status") : "Inactive";
            String avatarUrl = request.getParameter("avatarUrl") != null ? request.getParameter("avatarUrl") : "";

            // Gọi hàm validateAccount để kiểm tra lỗi
            Map<String, String> errors = accountService.validateAccount(userId, fullName, email, username, phoneNumber);
            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("user_id", userId); // Gán lại userId vào request
                request.setAttribute("fullname", fullName);
                request.setAttribute("gmail", email);
                request.setAttribute("username", username);
                request.setAttribute("gender", gender);
                request.setAttribute("phonenumber", phoneNumber);
                request.setAttribute("role", roleName);
                request.setAttribute("status", status);
                request.setAttribute("avatarUrl", avatarUrl);
                request.getRequestDispatcher("/WEB-INF/view/account/AccountDetail.jsp").forward(request, response);
                return;
            }

            // Xử lý file upload avatar
            Part filePart = request.getPart("avatarFile");
            if (filePart != null && filePart.getSize() > 0) { // Nếu có file mới
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);
                avatarUrl = "uploads/" + fileName; // Cập nhật avatar mới
            }

            // Ánh xạ role name thành role_id
            Map<String, Integer> roleMap = new HashMap<>();
            roleMap.put("Admin", 1);
            roleMap.put("Web User", 6);

            if (!roleMap.containsKey(roleName)) {
                request.setAttribute("errorMessage", "Invalid role selected!");
                request.getRequestDispatcher("/WEB-INF/view/account/AccountDetail.jsp").forward(request, response);
                return;
            }

            int roleId = roleMap.get(roleName);

            // Debug log kiểm tra dữ liệu trước khi update
            System.out.println("Updating user with ID: " + userId);
            System.out.println("Full Name: " + fullName);
            System.out.println("Email: " + email);
            System.out.println("Username: " + username);
            System.out.println("Gender: " + gender);
            System.out.println("Phone: " + phoneNumber);
            System.out.println("Avatar URL: " + avatarUrl);
            System.out.println("Role ID: " + roleId);
            System.out.println("Status: " + status);

            // Cập nhật tài khoản nếu không có lỗi
            boolean isUpdated = accountService.updateAccount(userId, fullName, email, username,
                    gender, avatarUrl, phoneNumber, roleId, status);

            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/AccountList");
            } else {
                System.out.println("Update failed! Check SQL Query.");
                request.setAttribute("errorMessage", "Failed to update account. Please check input data.");
                request.getRequestDispatcher("/WEB-INF/view/account/AccountDetail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the account!");
            request.getRequestDispatcher("/WEB-INF/view/account/AccountDetail.jsp").forward(request, response);
        }
    }
}
