/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.member;

import com.google.gson.Gson;
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
import model.entity.User;
import service.club.ClubService;
import service.clubmember.ClubMemberService;

/**
 *
 * @author pc
 */
@WebServlet(name = "MemberSearch", urlPatterns = {"/searchMembers"})
public class SearchMemberServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        int limit = 5; // Số lượng thành viên mỗi trang
        int offset = 0; // Vị trí bắt đầu của trang

        HttpSession session = request.getSession(false);
        int userId = 41; // Mặc định userId = 1 nếu không có session

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
        String clubIdParam = request.getParameter("club_id");
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
        offset = (page - 1) * limit;  // Tính toán offset

        // Lấy tham số tìm kiếm từ request
        String search = request.getParameter("search");
        if (search == null) {
            search = "";  // Nếu không có từ khóa tìm kiếm, tìm tất cả
        }

        List<User> members = null;
        // Kiểm tra nếu có bộ lọc, gọi phương thức với filter và sort

        members = clubMemberService.searchMembersByName(search, limit, offset, clubId);

        // Lấy tổng số thành viên (theo clubId và các bộ lọc nếu có)
        int totalMembers = clubMemberService.getTotalMembersByName(search, clubId);
        int totalPages = (int) Math.ceil((double) totalMembers / limit);

        // Đặt dữ liệu lên request
        request.setAttribute("club", clubService.getClubById(clubId)); // Thông tin câu lạc bộ đã chọn
        request.setAttribute("members", members);
        request.setAttribute("userClubs", userClubs);
        request.setAttribute("selectedClubId", clubId);  // Để đánh dấu câu lạc bộ đã chọn

        request.setAttribute("selectedRole", roleFilter);
        request.setAttribute("selectedStatus", statusFilter);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("search", search); // Truyền từ khóa tìm kiếm vào request

        // Chuyển hướng đến trang hiển thị thành viên
        request.getRequestDispatcher("/WEB-INF/view/member/memberofficiallist.jsp").forward(request, response);
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
