/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.account;

import DAO.User.AccountDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entity.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author nam
 */
public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public List<User> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public List<User> getAccountsByPageAndFilter(int start, int total, String status, String role, String username) {
        return accountDAO.getAccountsByPageAndFilter(start, total, status, role, username);
    }

    public int getTotalAccountsWithFilter(String status, String role, String username) {
        return accountDAO.getTotalAccountsWithFilter(status, role, username);
    }

    public int getTotalAccounts() {
        return accountDAO.getTotalAccounts();
    }

    public User getAccountDetail(int userId) {
        return accountDAO.getAccountDetail(userId);
    }

    public boolean updateAccount(int userId, String fullName, String email, String username,
            String gender, String avatarUrl, String phoneNumber,
            int roleId, String status) {
        return accountDAO.updateAccount(userId, fullName, email, username,
                gender, avatarUrl, phoneNumber,
                roleId, status);
    }

   public boolean addAccount(String fullName, String email, String username, String gender, String phoneNumber, String avatarUrl, int roleId, String status) {
        try {
            // Tạo mật khẩu tại Service
            String password = accountDAO.generateRandomPassword(8);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

            if (accountDAO.addAccount(fullName, email, username, hashedPassword, gender, phoneNumber, avatarUrl, roleId, status)) {
                return accountDAO.sendEmail(email, password);
            }
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public Map<String, String> validateAccount(int userId, String fullName, String email, String username, String phoneNumber) {
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra full name
        if (fullName == null || fullName.length() < 3 || fullName.length() > 20) {
            errors.put("fullNameError", "Full Name must be between 3 and 20 characters.");
        }

        // Kiểm tra username
        if (username == null || username.length() < 3 || username.length() > 20) {
            errors.put("usernameError", "Username must be between 3 and 20 characters.");
        } else if (accountDAO.isUsernameExists(username, userId)) {
            errors.put("usernameError", "Username already exists.");
        }

        // Kiểm tra email không trùng
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.put("emailError", "Email must be a valid email address.");
        } else if (accountDAO.isEmailExists(email, userId)) {
            errors.put("emailError", "Email already exists.");
        }

        // Kiểm tra số điện thoại
        if (phoneNumber == null || !phoneNumber.matches("^\\d{10,11}$")) {
            errors.put("phoneNumberError", "Phone number must be 10 or 11 digits.");
        } else if (accountDAO.isPhoneNumberExists(phoneNumber, userId)) {
            errors.put("phoneNumberError", "Phone number already exists.");
        }

        return errors;
    }

}
