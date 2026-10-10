package controller.user;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import model.entity.User;
import service.user.UserProfileService;

@WebServlet(name = "UpdateProfile", urlPatterns = {"/updateprofile"})
public class UpdateProfile extends HttpServlet {

    private final UserProfileService userProfileService = new UserProfileService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("account");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            String userIdParam = request.getParameter("user_id");
            if (userIdParam == null || userIdParam.isEmpty()) {
                request.setAttribute("errors", List.of("User ID không hợp lệ!"));
                request.getRequestDispatcher("/WEB-INF/view/userprofile/userprofile.jsp").forward(request, response);
                return;
            }

            int userId = Integer.parseInt(userIdParam);
            if (userId != currentUser.getUser_id()) {
                request.setAttribute("errors", List.of("Không thể cập nhật thông tin người dùng khác!"));
                request.getRequestDispatcher("/WEB-INF/view/userprofile/userprofile.jsp").forward(request, response);
                return;
            }

            // Lấy thông tin email cũ và email mới
            String currentEmail = currentUser.getEmail();
            String newEmail = request.getParameter("email");

            // Lấy dữ liệu từ form
            User updatedUser = new User();
            updatedUser.setUser_id(userId);
            updatedUser.setFull_name(request.getParameter("full_name"));
            updatedUser.setEmail(newEmail); 
            updatedUser.setPhone_number(request.getParameter("phone_number"));
            updatedUser.setDate_of_birth(request.getParameter("date_of_birth"));
            updatedUser.setAddress(request.getParameter("address"));
            updatedUser.setCity(request.getParameter("city"));
            updatedUser.setDistrict(request.getParameter("district"));
            updatedUser.setFacebook(request.getParameter("facebook"));
            updatedUser.setInstargram(request.getParameter("instargram"));

            // Kiểm tra dữ liệu hợp lệ
            Map<String, String> validationErrors = userProfileService.updateUser1(updatedUser);

            if (!validationErrors.isEmpty()) {
                session.setAttribute("errors", validationErrors);
                session.setAttribute("toastMessage", "FAIL!");
                session.setAttribute("userInput", updatedUser); // Giữ lại dữ liệu nhập sai
                response.sendRedirect(request.getContextPath() + "/userprofile"); // Chuyển hướng lại form
                return;
            } else {
                // Kiểm tra xem email có thay đổi không
                if (!newEmail.equals(currentEmail)) {
                    // Gửi OTP xác thực email
                    session.setAttribute("pendingOTPEmail", newEmail);
                    session.setAttribute("otpType", "updateEmail");
                    // Cập nhật thông tin người dùng đã thay đổi vào session để lưu lại
                    session.setAttribute("updatedUser", updatedUser);

                    // Chuyển hướng người dùng đến trang xác thực OTP
                    response.sendRedirect(request.getContextPath() + "/sendOtp");
                    return;
                }

                // Cập nhật thông tin
                boolean isUpdated = userProfileService.updateUser(updatedUser);
                if (isUpdated) {
                    // Nếu không có lỗi, xóa thông tin lỗi và dữ liệu nhập
                    session.removeAttribute("errors");
                    session.removeAttribute("userInput");
                    session.setAttribute("account", userProfileService.getUserById(userId));
                    session.setAttribute("toastMessage", "Cập nhật thành công!");
                    response.sendRedirect(request.getContextPath() + "/userprofile");
                } else {
                    // Chỉ hiển thị lỗi trên trang, không đặt toastMessage
                    request.setAttribute("errors", List.of("Cập nhật thất bại! Vui lòng thử lại."));
                    request.setAttribute("userInput", updatedUser);
                    request.getRequestDispatcher("/WEB-INF/view/userprofile/userprofile.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errors", List.of("Lỗi định dạng user_id!"));
            request.getRequestDispatcher("/WEB-INF/view/userprofile/userprofile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating user profile";
    }
}
