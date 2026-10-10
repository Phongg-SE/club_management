package service.club;

import DAO.Club.ClubDAO;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Club;
import model.entity.Setting;
import model.entity.User;

public class ClubService {

    private ClubDAO clubDAO;

    public ClubService() {
        this.clubDAO = new ClubDAO();
    }

    //Duy
    public Club getPublicClubDetails(int clubId) {
        return clubDAO.getPublicClubDetails(clubId);
    }

    public List<Club> getAllClubsByCategoryId(int categoryId) {
        return clubDAO.getAllClubsByCategoryId(categoryId);
    }

    //===========================
//   list club
    public List<Club> getAllClubs(int limit, int offset) {
        try {
            return clubDAO.listAllClubs(limit, offset);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int countTotalClubs() throws SQLException {
        return clubDAO.countTotalClubs();
    }

    //filter status and public
    public List<Club> getClubsByFilters(String status, String isPublic, String categoryId, String sortField, String sortOrder, int offset, int limit) {

        try {
            return clubDAO.listClubsByFilters(status, isPublic, categoryId, sortField, sortOrder, offset, limit);
        } catch (SQLException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public int countTotalClubsByFilters(String status, String isPublic, String categoryId) throws SQLException {
        return clubDAO.countTotalClubsByFilters(status, isPublic, categoryId);
    }
//    get category club

    // Phương thức tìm kiếm câu lạc bộ theo tên
    public List<Club> searchByName(String txtSearch, int offset, int limit) throws SQLException {
        return clubDAO.searchByName(txtSearch, offset, limit);
    }

    public int countSearchResults(String txtSearch) {
        return clubDAO.countSearchResults(txtSearch);
    }

    public String getSettingValueById(int settingId) {
        try {
            return clubDAO.getSettingValueById(settingId);
        } catch (SQLException e) {
            System.err.println("Error in SettingService: " + e.getMessage());
            return null;
        }
    }

    public void updateClubVisibility(int clubId, String newVisibility) {
        clubDAO.updateClubVisibility(clubId, newVisibility);
    }

    public void updateClubStatus(int clubId, String newStatus) {
        clubDAO.updateClubStatus(clubId, newStatus);
    }

    //=================
    //get club by id
    public Club getClubById(int clubId) {
        try {
            return clubDAO.getClubById(clubId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
     public Club getClubById2(int clubId){
         try {
            return clubDAO.getClubById2(clubId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
     }

    public int getTypeIdBySettingId(int settingId) {
        try {
            return clubDAO.getTypeIdBySettingId(settingId);
        } catch (SQLException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    // Lấy danh sách setting theo type_id (VD: lấy danh sách category)
    public List<Setting> getSettingsByType(int typeId) {
        try {
            return clubDAO.getSettingsByType(typeId);
        } catch (SQLException e) {
            System.err.println("Error in SettingService.getSettingsByType: " + e.getMessage());
            return List.of(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    // Lấy một setting theo ID
    public Setting getSettingById(int settingId) {
        try {
            return clubDAO.getSettingById(settingId);
        } catch (SQLException e) {
            System.err.println("Error in SettingService.getSettingById: " + e.getMessage());
            return null; // Trả về null nếu có lỗi
        }
    }
//===
    public List<String> validateAndUpdateClub(Club club) {
        List<String> errors = new ArrayList<>();

        if (club == null) {
            errors.add("Club object is null.");
            return errors;
        }

        // Kiểm tra club_id hợp lệ
        if (club.getClub_id() <= 0) {
            errors.add("Invalid club ID.");
        }

        // Kiểm tra Name
        if (club.getName() == null || club.getName().trim().isEmpty()) {
            errors.add("Club name cannot be empty or spaces only.");
        }
        if (club.getName() != null && club.getName().length() > 50) {
            errors.add("Club name must not exceed 50 characters.");
        }
        if (club.getName() != null && clubDAO.isClubExistforUpdate(club.getClub_id(), club.getName())) {
            errors.add("Club name already exists.");
        }

        if (club.getName() == null || club.getSlogan().trim().isEmpty()) {
            errors.add("Sologan cannot be empty or spaces only.");
        }
        // Kiểm tra Slogan
        if (club.getSlogan() != null && club.getSlogan().length() > 150) {
            errors.add("Slogan must not exceed 150 characters.");
        }

        // Kiểm tra Email
        if (club.getClub_email() == null || !club.getClub_email().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.add("Invalid email address.");
        }

        if (clubDAO.isEmailExistWithDifferentId(club.getClub_id(), club.getClub_email())) {
            errors.add("Email is already in use by another club.");
        }

        // Kiểm tra Fanpage URL
        if (club.getClub_fanpage_url() != null && !club.getClub_fanpage_url().matches("^(https?:\\/\\/)?(www\\.)?facebook\\.com\\/.*$")) {
            errors.add("Invalid fanpage URL. It must be a valid Facebook link.");
        }

        // Kiểm tra Description
        if (club.getDescription() == null || club.getDescription().trim().isEmpty()) {
            errors.add("Description cannot be empty or spaces only.");
        }

        // Kiểm tra is_public
        if (club.getIs_public() == null || (!club.getIs_public().equalsIgnoreCase("Yes") && !club.getIs_public().equalsIgnoreCase("No"))) {
            errors.add("Is Public must be 'Yes' or 'No'.");
        }

        // Kiểm tra Status
        if (club.getStatus() == null || (!club.getStatus().equalsIgnoreCase("Active") && !club.getStatus().equalsIgnoreCase("Inactive"))) {
            errors.add("Status must be 'Active' or 'Inactive'.");
        }

        // Kiểm tra Category ID hợp lệ
        if (club.getCategory_id() <= 0) {
            errors.add("Category must be selected.");
        }

        // Nếu không có lỗi, tiến hành cập nhật club
        if (errors.isEmpty()) {
            boolean updated = clubDAO.updateClub(club);
            if (!updated) {
                errors.add("Failed to update club.");
            } else {
                errors.add("Club updated successfully.");
            }
        }
        return errors;
    }
///===
    public List<User> getUsersByClubId(int clubId) {
        return clubDAO.getUsersByClubId(clubId);
    }

    public void updateClubLeader(int clubId, int oldLeaderId, int newLeaderId) {
        try {
            clubDAO.updateClubLeader(clubId, oldLeaderId, newLeaderId);
        } catch (SQLException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
///===

    public List<String> validateAndInsertClub(Club club, Part filePart, int user_id) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra tên CLB
        if (club.getName() == null || club.getName().trim().isEmpty()) {
            errors.add("Club name is required.");
        }

        if (club.getName().length() > 100) {
            errors.add("Club name is too long. Maximum length is 100 characters.");
        }
        if (clubDAO.isClubExist(club.getName())) {
            errors.add("Club already exists.");
        }

        // Kiểm tra danh mục
        if (club.getCategory_id() <= 0) {
            errors.add("Invalid category ID.");
        }

        // Kiểm tra trạng thái công khai
        if (!"Yes".equalsIgnoreCase(club.getIs_public()) && !"No".equalsIgnoreCase(club.getIs_public())) {
            errors.add("Invalid value for isPublic. It must be 'Yes' or 'No'.");
        }

        // Kiểm tra trạng thái hoạt động
        if (!"Active".equalsIgnoreCase(club.getStatus()) && !"Inactive".equalsIgnoreCase(club.getStatus())) {
            errors.add("Invalid value for status. It must be 'Active' or 'Inactive'.");
        }

        // Kiểm tra file ảnh (logo)
        if (filePart == null || filePart.getSize() == 0) {
            errors.add("Logo image is required.");
        } else {
            String fileName = filePart.getSubmittedFileName();
            long fileSize = filePart.getSize();
            String contentType = filePart.getContentType();

            // Kiểm tra định dạng ảnh hợp lệ
            if (!contentType.matches("image/(jpeg|png|gif|bmp|jpg)")) {
                errors.add("Invalid image format. Only JPG, PNG, GIF, and BMP are allowed.");
            }

            // Kiểm tra kích thước file (tối đa 5MB)
            if (fileSize > 5 * 1024 * 1024) {
                errors.add("File size must not exceed 5MB.");
            }
        }

        // Nếu có lỗi, trả về danh sách lỗi
        if (!errors.isEmpty()) {
            return errors;
        }

        // Thêm CLB vào cơ sở dữ liệu
        boolean success = clubDAO.insertClub(club, user_id);

        if (!success) {
            errors.add("Failed to add the club to the database.");
        }

        return errors;
    }
///===

    public List<User> getActiveUsers() {
        return clubDAO.getActiveUsers();
    }

    public boolean isClubLeader(int clubId, int userId) {
        return clubDAO.isClubLeader(clubId, userId);
    }

    public List<Club> listPublicClubs() {
        try {
            return clubDAO.listPublicClubs();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int getClubIdByUserId(int userId) {
        try {
            return clubDAO.getClubIdByUserId(userId);
        } catch (SQLException e) {
            System.err.println("Error in SettingService.getSettingById: " + e.getMessage());
            return -1;
        }
    }

    public boolean isJoinClub(int clubId, int userId) throws Exception {
        try {
            return clubDAO.isJoinClub(clubId, userId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    
    //== nam
    public List<Club> getAllClubs() {
        try {
            return clubDAO.listAllClubs();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    //son
    public List<Club> getClubsById(List<Integer> clubIds) {
        try {
            return clubDAO.getClubsByIds(clubIds);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
