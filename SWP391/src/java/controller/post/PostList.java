/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.post;

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
import model.entity.Post;
import model.entity.User;
import service.club.ClubService;
import service.post.PostService;

/**
 *
 * @author nam
 */
@WebServlet(name = "PostList", urlPatterns = {"/PostList"})
public class PostList extends HttpServlet {
    
    private PostService postService;
    private ClubService clubService = new ClubService();
    
    @Override
    public void init() throws ServletException {
        postService = new PostService();
    }

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

        List<Club> clubs = clubService.getAllClubs();
        request.setAttribute("clubs", clubs);

        List<String> categories = postService.getAllCategories();
        request.setAttribute("categories", categories);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        // Lấy các tham số lọc
        String statusFilter = request.getParameter("statusFilter");
        String categoryFilter = request.getParameter("categoryFilter");
        String clubNameSearch = request.getParameter("clubNameSearch");

        // Xử lý tham số phân trang
        int pageNumber = 1;
        int pageSize = 8; // Số bài viết mỗi trang

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                pageNumber = Integer.parseInt(pageParam);
                if (pageNumber < 1) {
                    pageNumber = 1;
                }
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        }

        // Lấy danh sách bài viết theo bộ lọc và phân trang
        List<Post> posts = postService.getFilteredPosts(statusFilter, categoryFilter, clubNameSearch, user.getUser_id(), pageNumber, pageSize);

        // Đếm tổng số bài viết để tính tổng số trang
        int totalPosts = postService.countFilteredPosts(statusFilter, categoryFilter, clubNameSearch, user.getUser_id());
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        // Đặt dữ liệu vào request
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("categoryFilter", categoryFilter);
        request.setAttribute("clubNameSearch", clubNameSearch);
        request.setAttribute("posts", posts);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);

        // Chuyển hướng tới JSP
        request.getRequestDispatcher("/WEB-INF/view/post/PostList.jsp").forward(request, response);
    
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
