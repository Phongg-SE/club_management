/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import DAO.User.UserDAO;
import jakarta.servlet.ServletContext;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.entity.User;
import model.entity.UserRole;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author datkh
 */
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean validateEmailLogin(String email, String password) {
        return userDAO.validateEmailLogin(email, password);
    }

    public boolean validateLogin(String username, String password) {
        return userDAO.validateLogin(username, password);
    }

    public boolean checkExist(String email) {
        if (userDAO.isEmailExists(email) || userDAO.isUserExists(email)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkEmailExist(String email) {
        if (userDAO.isEmailExists(email)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExist(String email) {
        if (userDAO.isUserExists(email)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByField("email", email);
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByField("username", username);
    }

    public int getRoleIdByUserId(int userId) {
        return userDAO.getRoleIdByUserId(userId);
    }

    public List<Integer> getClubRoleByUserId(int userId) {
        return userDAO.getClubRoleByUserId(userId);
    }

    public boolean isTokenExpired(String email) {
        try {
            LocalDateTime expiry = userDAO.getTokenExpiry(email);
            return expiry == null || expiry.isBefore(LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean updateResetToken(String email, String token) {
        if (!checkEmailExist(email)) {
            return false;
        }
        return userDAO.updateResetToken(email, token);
    }

    public String getEmailByToken(String token) {
        return userDAO.getEmailByToken(token);
    }

    public boolean isValidResetToken(String token) {
        String email = getEmailByToken(token);
        if (isTokenExpired(email)) {
            return false;
        }
        return userDAO.isValidResetToken(email, token);
    }

    public boolean updatePassword(String email, String rawPassword) {
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
        return userDAO.updatePassword(email, hashedPassword);
    }

    public int getClubIdByUserId(int user_id) {
        return userDAO.getClubIdByUserId(user_id);
    }

    public boolean registerUser(User user, UserRole userRole) {
        if (userDAO.isEmailExists(user.getEmail())) {
            return false;
        } else {
            return userDAO.registerUser(user, userRole);
        }
    }

    public String validateResetPassword(String token, String newPassword, String rePassword) {
        if (token == null || newPassword == null || newPassword.isEmpty()) {
            return "Invalid request!";
        }
        if (!newPassword.equals(rePassword)) {
            return "Password not match!";
        }
        if (newPassword.length() < 8) {
            return "Password must be greater than 8!";
        }
        return null;
    }

    public boolean isFirstLogin(int userId, ServletContext context) {
        ArrayList<Integer> firstLoginUsers = (ArrayList<Integer>) context.getAttribute("firstLoginUsers");
        if (firstLoginUsers == null) {
            firstLoginUsers = new ArrayList<>();
            context.setAttribute("firstLoginUsers", firstLoginUsers);
        }
        return !firstLoginUsers.contains(userId);
    }

    public void markFirstLogin(int userId, ServletContext context) {
        ArrayList<Integer> firstLoginUsers = (ArrayList<Integer>) context.getAttribute("firstLoginUsers");
        if (!firstLoginUsers.contains(userId)) {
            firstLoginUsers.add(userId);
        }
        context.setAttribute("firstLoginUsers", firstLoginUsers);
    }

    public String validateRegister(String email, String password, String repassword, String username,String fullname) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (email.trim().isEmpty() || password.trim().isEmpty() || repassword.trim().isEmpty() || username.trim().isEmpty() || fullname.trim().isEmpty()) {
            return "Invalid request!";
        }
        if (!email.matches(emailRegex)) {
            return "Invalid Email!";
        }

        if (!password.equals(repassword)) {
            return "Password doesn't match!";
        }
        if (!isStrongPassword(password)) {
            return "Mật khẩu phải có tối thiểu 8 ký tự, bao gồm ít nhất 1 chữ in hoa, 1 chữ số và 1 ký tự đặc biệt!";
        }
        if (checkEmailExist(email) || checkUsernameExist(email)) {
            return "Email or Username already exists!";
        }

        return null;
    }

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public List<User> getAllUsers() {
        try {
            return userDAO.listAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<User> getAllUsersByClubID(int clubId) {
        try {
            return userDAO.getAllUsersByClubID(clubId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
