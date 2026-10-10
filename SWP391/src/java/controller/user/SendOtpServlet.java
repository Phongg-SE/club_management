package controller.user;

import constant.EmailUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import model.entity.User;
import service.user.UserProfileService;

@WebServlet(name = "SendOtpServlet", urlPatterns = {"/sendOtp"})
public class SendOtpServlet extends HttpServlet {

    private final UserProfileService userProfileService = new UserProfileService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("pendingOTPEmail");

        if (email == null || email.isEmpty()) {
            response.sendRedirect("login");
            return;
        }

        // Tạo OTP mới
        String otp = EmailUtility.generateOTP();
        session.setAttribute("otpCode", otp);
        session.setAttribute("otpTime", System.currentTimeMillis());

        try {
            EmailUtility.sendOTP(email, otp);
            session.setAttribute("toastMessage", "OTP đã được gửi đến email của bạn. Vui lòng kiểm tra hộp thư.");
        } catch (Exception e) {
            session.setAttribute("toastMessage", "Không thể gửi OTP. Vui lòng thử lại.");
        }

        // Chuyển hướng đến trang nhập OTP
        request.getRequestDispatcher("/WEB-INF/view/guest/otpverify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String inputOtp = request.getParameter("otp");
        String correctOtp = (String) session.getAttribute("otpCode");
        Long otpTime = (Long) session.getAttribute("otpTime");
        String newEmail = (String) session.getAttribute("pendingOTPEmail");
        User currentUser = (User) session.getAttribute("updatedUser"); // Đảm bảo đúng tên

        if (correctOtp == null || otpTime == null || currentUser == null || newEmail == null) {
            response.sendRedirect("login");
            return;
        }

        // Xóa OTP khỏi session ngay khi kiểm tra
        session.removeAttribute("otpCode");
        session.removeAttribute("otpTime");

        long currentTime = System.currentTimeMillis();
        if (currentTime - otpTime > 60000) {
            session.setAttribute("toastMessage", "OTP đã hết hạn. Vui lòng yêu cầu lại.");
            request.getRequestDispatcher("/WEB-INF/view/guest/otpverify.jsp").forward(request, response);
            return;
        }

        if (inputOtp != null && inputOtp.equals(correctOtp)) {
            // Cập nhật email cho người dùng
            currentUser.setEmail(newEmail);
            boolean updateResult = userProfileService.updateUser(currentUser);

            if (updateResult) {
                session.setAttribute("account", userProfileService.getUserById(currentUser.getUser_id())); // Cập nhật lại thông tin người dùng trong session
                session.setAttribute("toastMessage", "Cập nhật email thành công!");
            } else {
                session.setAttribute("toastMessage", "Cập nhật email thất bại. Vui lòng thử lại.");
            }

            response.sendRedirect(request.getContextPath() + "/userprofile");
        } else {
            session.setAttribute("toastMessage", "Mã OTP không hợp lệ. Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/view/guest/otpverify.jsp").forward(request, response);
        }
    }

}
