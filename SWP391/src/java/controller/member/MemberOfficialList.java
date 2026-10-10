/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.Setting;
import model.entity.User;
import service.club.ClubService;
import service.clubmember.ClubMemberService;

/**
 *
 * @author pc
 */
@WebServlet(name = "MemberOfficialList", urlPatterns = {"/memberofficiallist"})
public class MemberOfficialList extends HttpServlet {

    private ClubMemberService clubMemberService = new ClubMemberService();
    private ClubService clubService = new ClubService();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int limit = 5; // Số lượng thành viên mỗi trang
        int offset = 0; // Vị trí bắt đầu của trang

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        int userId = 41; // Mặc định userId = 1

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("account");
        userId = user.getUser_id();

        // Lấy danh sách CLB mà user tham gia
        List<Club> userClubs = clubMemberService.getUserClubs(userId);

        if (userClubs == null || userClubs.isEmpty()) {
            request.setAttribute("message", "You are not a member of any club.");
            request.getRequestDispatcher("/WEB-INF/view/member/memberofficiallist.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin clubId từ request (nếu có)
        String clubIdParam = request.getParameter("clubId");
        int clubId = (clubIdParam != null && !clubIdParam.isEmpty()) ? Integer.parseInt(clubIdParam) : userClubs.get(0).getClub_id();

        // Lấy thông tin lọc từ request
        String roleFilter = request.getParameter("roleFilter");
        String statusFilter = request.getParameter("statusFilter");

        // Lấy thông tin sắp xếp từ request
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");

        // Lấy thông tin trang hiện tại từ request (bắt đầu từ trang 1)
        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        String pageRegistrationParam = request.getParameter("pageRegistration");
        int pageRegistration = (pageRegistrationParam != null && !pageRegistrationParam.isEmpty()) ? Integer.parseInt(pageRegistrationParam) : 1;

        int offsetRegistration = 0;
        // Tính toán offset dựa trên trang hiện tại
        offset = (page - 1) * limit;
        offsetRegistration = (pageRegistration - 1) * limit;

        List<User> members = null;
        List<User> memberRE = null;
        memberRE = clubMemberService.getRegistedMembers(clubId, limit, offsetRegistration);

        // Kiểm tra nếu có bộ lọc, gọi phương thức với filter và sort
        if ((roleFilter != null && !roleFilter.isEmpty()) || (statusFilter != null && !statusFilter.isEmpty())
                || (sortField != null && !sortField.isEmpty())) {
            // Nếu có bộ lọc hoặc có sortField, gọi hàm lấy dữ liệu có sắp xếp
            members = clubMemberService.getFilteredMembers(clubId, roleFilter, statusFilter, sortField, sortOrder, offset, limit);
        } else {
            // Nếu không có bộ lọc và cũng không có sắp xếp, gọi hàm lấy dữ liệu mặc định
            members = clubMemberService.getMembersWithPagination(clubId, offset, limit);
        }

//        The ceil() method rounds a number UP to the nearest integer.
        int totalMemberRegister = clubMemberService.getTotalRegistedMembers(clubId);
        int totalPageRegister = (int) Math.ceil((double) totalMemberRegister / limit);

        // Lấy tổng số thành viên (theo clubId và các bộ lọc nếu có)
        int totalMembers = clubMemberService.getTotalFilteredMembers(clubId, roleFilter, statusFilter);
        int totalPages = (int) Math.ceil((double) totalMembers / limit);

        String userRole = clubMemberService.getRoleValueByUserAndClub(userId, clubId);

        request.setAttribute("userRole", userRole);

        // Đặt dữ liệu lên request
        request.setAttribute("club", clubService.getClubById(clubId));
        request.setAttribute("members", members);
        request.setAttribute("userClubs", userClubs);

        request.setAttribute("selectedClubId", clubId);  // Để đánh dấu câu lạc bộ đã chọn

        request.setAttribute("selectedRole", roleFilter);
        request.setAttribute("selectedStatus", statusFilter);

        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.setAttribute("selectedSortField", sortField);
        request.setAttribute("selectedSortOrder", sortOrder);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);

        request.setAttribute("currentPageRegistration", pageRegistration);
        request.setAttribute("totalPagesRegistration", totalPageRegister);

        request.setAttribute("membersre", memberRE);
        request.getRequestDispatcher("/WEB-INF/view/member/memberofficiallist.jsp").forward(request, response);

        // Chuyển tiếp tới trang JSP để hiển thị
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
