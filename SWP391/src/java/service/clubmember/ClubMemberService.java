package service.clubmember;

import DAO.ClubMember.ClubMemberDAO;
import java.sql.SQLException;
import java.util.List;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.Post;
import model.entity.Task;
import model.entity.User;

public class ClubMemberService {

    private ClubMemberDAO clubMemberDAO;

    public ClubMemberService() {
        clubMemberDAO = new ClubMemberDAO();
    }

    // Các phương thức nghiệp vụ khác liên quan đến thành viên câu lạc bộ
    public ClubMember getClubMemberByUserId(int userId) {
        return clubMemberDAO.getClubMemberByUserId(userId);
    }

    public List<Club> getUserClubs(int userId) {
        return clubMemberDAO.getClubsByUserId(userId);
    }

    /**
     * Lấy danh sách ngẫu nhiên 5 thành viên của CLB
     */
    public List<User> getRandomMembers(int clubId) {
        return clubMemberDAO.getRandomMembers(clubId);
    }

    /**
     * Lấy thông tin chi tiết một thành viên theo ID
     */
    public User getMemberById(int userId, int clubid) {
        return clubMemberDAO.getMemberById(userId, clubid);
    }

    public List<Post> getPostsByUserId(int userId) {
        return clubMemberDAO.getPostsByUserId(userId);
    }

    public List<Task> getUserTasksWithUsers(int userId) {
        return clubMemberDAO.getUserTasksWithUsers(userId);
    }

    public boolean updateMemberRoleAndStatus(int memberId, int roleId, String status) {
        // Gọi DAO để cập nhật dữ liệu
        return clubMemberDAO.updateMemberRoleAndStatus(memberId, roleId, status);
    }

    public boolean rejectMember(int clubId, int userId) {
        return clubMemberDAO.rejectMember(clubId, userId);
    }

    public boolean approveMember(int clubId, int userId) {
        return clubMemberDAO.approveMember(clubId, userId);
    }

    public boolean addClubMember(ClubMember clubMember, String note) {
        return clubMemberDAO.addClubMember(clubMember, note);
    }

    // Phương thức gọi DAO để lấy số lượng thành viên
    public int getTotalMembers(int clubId) {
        return clubMemberDAO.getMemberCountByClubId(clubId);
    }

    public List<User> getMembersWithPagination(int clubId, int offset, int limit) {
        return clubMemberDAO.getMembersByClubIdWithOffset(clubId, offset, limit);
    }

    public List<User> getFilteredMembers(int clubId, String roleFilter, String statusFilter, String sortField, String sortOrder, int offset, int limit) {
        // Gọi DAO để lấy danh sách thành viên với phân trang, lọc và sắp xếp
        return clubMemberDAO.getOfficialMembersByFilter(clubId, roleFilter, statusFilter, sortField, sortOrder, offset, limit);
    }

    public int getTotalFilteredMembers(int clubId, String roleFilter, String statusFilter) {
        // Gọi DAO để lấy tổng số thành viên đã lọc
        return clubMemberDAO.getTotalFilteredMembers(clubId, roleFilter, statusFilter);
    }

    public boolean changeMemberStatus(int clubId, int userId, String newStatus) {
        return clubMemberDAO.updateMemberStatus(clubId, userId, newStatus);
    }

    public int getTotalMembersByName(String search, int club_id) {
        return clubMemberDAO.getTotalMembersByName(search, club_id);
    }

    // Phương thức tìm kiếm thành viên
    public List<User> searchMembersByName(String keyword, int limit, int offset, int club_id) {
        // Gọi DAO để lấy danh sách thành viên
        return clubMemberDAO.searchMembersByName(keyword, limit, offset, club_id);
    }

    public List<User> getRegistedMembers(int clubId, int limit, int offset) {
        return clubMemberDAO.getRegistedMembersByClubId(clubId, limit, offset);
    }

    public int getTotalRegistedMembers(int clubId) {
        return clubMemberDAO.getTotalRegistedMembersByClubId(clubId);
    }

    public String getRoleValueByUserAndClub(int userId, int clubId) {
        return clubMemberDAO.getRoleValueByUserAndClub(userId, clubId);
    }

    public boolean updateMemberRoles(int userId, int clubId, List<Integer> roleIds, String status) {
        try {
            clubMemberDAO.updateUserRoles(userId, clubId, roleIds, status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ClubMember getClubMemberByUserIdAndClubId(int user_id, int clubId) {
        return clubMemberDAO.getClubMemberByUserIdAndClubId(clubId, user_id);
    }

    public List<ClubMember> getAllClubOfUserById(int user_id) {
        return clubMemberDAO.getAllClubOfUserById(user_id);
    }
}
