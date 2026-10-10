package controller.member;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.Post;
import model.entity.Setting;
import model.entity.Task;
import model.entity.User;
import service.club.ClubService;
import service.clubmember.ClubMemberService;

@WebServlet(name = "MemberOfficialDetail", urlPatterns = {"/memberofficialdetail"})
public class MemberOfficialDetail extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ClubMemberService clubMemberService = new ClubMemberService();
    private final ClubService clubService = new ClubService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            HttpSession session = request.getSession(false);
            int sessioUser_id; // Mặc định userId = 1

            if (session == null || session.getAttribute("account") == null) {
                response.sendRedirect("login");
                return;
            }

            User user = (User) session.getAttribute("account");
            sessioUser_id = user.getUser_id();

            String club_idParam = request.getParameter("club_id");
            String userIdParam = request.getParameter("id");
            if (userIdParam == null || userIdParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is missing");
                return;
            }

            if (club_idParam == null || club_idParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is missing");
                return;
            }

            int club_id;
            int userId;
            try {
                userId = Integer.parseInt(userIdParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format");
                return;
            }

            try {
                club_id = Integer.parseInt(club_idParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format");
                return;
            }

            User member = clubMemberService.getMemberById(userId, club_id);
            if (member == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }

            List<User> randomMembers = clubMemberService.getRandomMembers(club_id);
            List<Post> posts = clubMemberService.getPostsByUserId(userId);
            List<Task> tasks = clubMemberService.getUserTasksWithUsers(userId);

            String userRole = clubMemberService.getRoleValueByUserAndClub(sessioUser_id, club_id);
            String leaderRole = clubMemberService.getRoleValueByUserAndClub(userId, club_id);

            System.out.println(userRole);
            System.out.println(leaderRole);

            request.setAttribute("leaderRole", leaderRole);
            request.setAttribute("userRole", userRole);

            request.setAttribute("posts", posts);
            request.setAttribute("tasks", tasks);

            request.setAttribute("member", member);
            request.setAttribute("randomMembers", randomMembers);

            request.setAttribute("selectedClub", club_id);
            request.getRequestDispatcher("/WEB-INF/view/member/memberofficialdetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing request.");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    try {
        int userId = Integer.parseInt(request.getParameter("id"));
        int clubId = Integer.parseInt(request.getParameter("club_id"));
        String status = request.getParameter("status");

        // Lấy danh sách role từ form
        String[] roleParams = request.getParameterValues("roles");
        List<Integer> roleIds = new ArrayList<>();
        if (roleParams != null) {
            for (String role : roleParams) {
                roleIds.add(Integer.parseInt(role));
            }
        }

        // Kiểm tra nếu danh sách role rỗng, tránh xóa nhầm
        if (roleIds.isEmpty()) {
            session.setAttribute("errorMessage", "Member must have at least one role.");
            response.sendRedirect("memberofficialdetail?id=" + userId + "&club_id=" + clubId);
            return;
        }

        // Gọi Service để cập nhật
        boolean updated = clubMemberService.updateMemberRoles(userId, clubId, roleIds, status);

        // Điều hướng sau khi cập nhật
        if (updated) {
            session.setAttribute("toastMessage", "Updated Successfully");
        } else {
            session.setAttribute("errorMessage", "Failed to update member");
        }

        // ✅ Chỉ gọi sendRedirect() một lần, không gọi processRequest()
        response.sendRedirect("memberofficialdetail?id=" + userId + "&club_id=" + clubId);

    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID or Role format");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating member.");
    }
}

    @Override
    public String getServletInfo() {
        return "Handles member details and updates.";
    }
}
