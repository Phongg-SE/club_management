/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import DAO.User.UserProfileDAO;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import model.entity.ClubActitivies;
import model.entity.Post;
import model.entity.User;
import model.entity.UserActivities;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author pc
 */
public class UserProfileService {

    private UserProfileDAO userProfileDAO;

    public UserProfileService() {
        this.userProfileDAO = new UserProfileDAO();
    }

    public List<Post> getPostsByUserId(int userId) {
        return userProfileDAO.getPostsByUserId(userId);
    }

    public ClubActitivies getClubStatistics(int userId, int clubId) {
        return userProfileDAO.getClubStatistics(userId, clubId);
    }

    public UserActivities getUserStatistics(int userId, int clubId) {
        return userProfileDAO.getUserClubStatistics(userId, clubId);
    }

    public User getUserById(int userId) {
        return userProfileDAO.getUserById(userId);
    }

    public boolean changeAvatar(int userId, String avatarUrl) {
        return userProfileDAO.updateAvatar(userId, avatarUrl);
    }

    public boolean updateUser(User user) {
        if (user.getUser_id() <= 0) {
            System.out.println("User ID không hợp lệ!");
            return false;
        }

        // Kiểm tra dữ liệu trước khi update
        if (user.getFull_name() == null || user.getFull_name().trim().isEmpty()) {
            System.out.println("Tên không được để trống!");
            return false;
        }

        // Kiểm tra email có trùng không
        boolean isEmailDuplicate = userProfileDAO.isEmailDuplicateWhenUpdate(user.getEmail(), user.getUser_id());

        if (isEmailDuplicate) {
            System.out.println("Email đã tồn tại cho người dùng khác. Không thể cập nhật.");
            return false;
        }

        // Gọi DAO để update nếu email không trùng
        return userProfileDAO.updateUser(user);
    }

    public boolean isEmailDuplicateWhenUpdate(String email, int userId) {
        return userProfileDAO.isEmailDuplicateWhenUpdate(email, userId);
    }

    public Map<String, String> updateUser1(User user) {
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra các thông tin đầu vào
        validateUserId(errors, user.getUser_id());
        validateString(errors, "full_name", "Tên", user.getFull_name(), 3, 50, true);
        validateEmail(errors, user.getEmail(), user.getUser_id());
        validatePhone(errors, user.getPhone_number(), user.getUser_id());
        validateDateOfBirth(errors, user.getDate_of_birth());
        validateString(errors, "address", "Địa chỉ", user.getAddress(), 5, 150, true);
        validateString(errors, "city", "Thành phố", user.getCity(), 5, 150, true);
        validateString(errors, "district", "Quận/Huyện", user.getDistrict(), 5, 150, true);
        validateSocialMedia(errors, "facebook", "Facebook", user.getFacebook(), "^https:\\/\\/www\\.facebook\\.com\\/([a-zA-Z0-9\\.]+)$");
        validateSocialMedia(errors, "instargram", "Instagram", user.getInstargram(), "^https:\\/\\/www\\.instagram\\.com\\/.*$");

        // Nếu có lỗi -> Không cập nhật
        if (!errors.isEmpty()) {
            return errors;
        }

//        // Tiến hành cập nhật thông tin
//        boolean isUpdated = userProfileDAO.updateUser(user);
//
//        if (!isUpdated) {
//            errors.put("general", "Có lỗi xảy ra khi cập nhật dữ liệu!");
//        }
        return errors;
    }

    private void validateUserId(Map<String, String> errors, int userId) {
        if (userId <= 0) {
            errors.put("general", "User ID không hợp lệ!");
        }
    }

    private void validateString(Map<String, String> errors, String fieldKey, String fieldName, String value, int minLength, int maxLength, boolean required) {
        if (value == null || value.trim().isEmpty()) {
            if (required) {
                errors.put(fieldKey, fieldName + " không được để trống!");
            }
        } else {
            int length = value.trim().length();
            if (length < minLength) {
                errors.put(fieldKey, fieldName + " phải có ít nhất " + minLength + " ký tự!");
            }
            if (length > maxLength) {
                errors.put(fieldKey, fieldName + " không được vượt quá " + maxLength + " ký tự!");
            }
        }
    }

    private void validateEmail(Map<String, String> errors, String email, int userId) {
        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
            errors.put("email", "Email không hợp lệ!");
        } else if (userProfileDAO.isEmailDuplicateWhenUpdate(email, userId)) {
            errors.put("email", "Email đã tồn tại cho người dùng khác. Không thể cập nhật.");
        }
    }

    private void validatePhone(Map<String, String> errors, String phoneNumber, int userId) {
        if (phoneNumber == null || !phoneNumber.matches("^0\\d{9,10}$")) {
            errors.put("phone_number", "Số điện thoại phải bắt đầu bằng số 0 và có 10 hoặc 11 chữ số!");
        } else if (userProfileDAO.isPhoneDuplicateWhenUpdate(phoneNumber, userId)) {
            errors.put("phone_number", "Số điện thoại đã tồn tại cho người dùng khác!");
        }
    }

    private void validateDateOfBirth(Map<String, String> errors, String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
            errors.put("date_of_birth", "Ngày sinh không được để trống!");
            return;
        }

        try {
            LocalDate dob = LocalDate.parse(dateOfBirth);
            LocalDate today = LocalDate.now();

            if (dob.isAfter(today)) {
                errors.put("date_of_birth", "Ngày sinh không thể là ngày trong tương lai!");
            }
        } catch (DateTimeParseException e) {
            errors.put("date_of_birth", "Định dạng ngày sinh không hợp lệ! Định dạng đúng: YYYY-MM-DD.");
        }
    }

    private void validateSocialMedia(Map<String, String> errors, String fieldKey, String platform, String url, String regex) {
        if (url != null && !url.trim().isEmpty() && !url.matches(regex)) {
            errors.put(fieldKey, "Link " + platform + " không hợp lệ! Định dạng đúng: " + getSocialMediaFormat(platform));
        }
    }

    private String getSocialMediaFormat(String platform) {
        switch (platform) {
            case "Facebook":
                return "https://www.facebook.com/username";
            case "Instagram":
                return "https://www.instagram.com/username";
            default:
                return "";
        }
    }

    public Map<String, String> changePassword(String email, String oldPassword, String newPassword, String reNewPassword, Map<String, String> previousErrors) {
        Map<String, String> errors = new HashMap<>();

        // Check if the old password is correct
        if (!userProfileDAO.validateLogin(email, oldPassword)) {
            errors.put("oldPassword", "Incorrect password!");
            return errors; // ❌ Return immediately, no further checks needed
        }

        // Check if newPassword and reNewPassword match
        if (!newPassword.equals(reNewPassword)) {
            errors.put("reNewPassword", "New password does not match the confirmation!");
            return errors; // ❌ Return immediately, no need to check password strength
        }

        // Validate the strength of the new password
        if (newPassword.length() < 8) {
            errors.put("length", "Password must be at least 8 characters long.");
        }
        if (!newPassword.matches(".*[A-Z].*")) {
            errors.put("uppercase", "Password must contain at least one uppercase letter.");
        }
        if (!newPassword.matches(".*[a-z].*")) {
            errors.put("lowercase", "Password must contain at least one lowercase letter.");
        }
        if (!newPassword.matches(".*\\d.*")) {
            errors.put("digit", "Password must contain at least one digit.");
        }
        if (!newPassword.matches(".*[!@#$%^&*()_+\\-={}\\[\\]:\";'<>?,./].*")) {
            errors.put("specialChar", "Password must contain at least one special character.");
        }

        // Nếu có lỗi, kiểm tra trạng thái lỗi cũ để in lỗi mới
        if (!errors.isEmpty()) {
            Map<String, String> newErrors = new HashMap<>();
            for (String key : errors.keySet()) {
                if (!previousErrors.containsKey(key)) { // Chỉ lấy lỗi mới
                    newErrors.put(key, errors.get(key));
                }
            }
            return newErrors;
        }

        // Nếu không có lỗi, mã hóa mật khẩu mới và cập nhật vào DB
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        boolean isUpdated = userProfileDAO.updatePassword(email, hashedNewPassword);

        if (!isUpdated) {
            errors.put("updateFailed", "Cập nhật mật khẩu thất bại. Vui lòng thử lại!");
        }

        return errors;
    }

}
