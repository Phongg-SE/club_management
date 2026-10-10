/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.blog;

import DAO.Post.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.entity.Post;
import service.post.BlogService;

/**
 *
 * @author nam
 */
@WebServlet(name = "BlogList", urlPatterns = {"/BlogList"})
public class BlogList extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 5; // Số bài viết mỗi trang
    private BlogService blogService;

    @Override
    public void init() throws ServletException {
        blogService = new BlogService(); // Khởi tạo BlogService
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
//         BlogDAO blogDAO = new BlogDAO();
//        List<Post> blogList = blogDAO.getAllBlogs();
//        
//        request.setAttribute("blogs", blogList);
//        request.getRequestDispatcher("/WEB-INF/view/blog/BlogList.jsp").forward(request, response);
        List<Post> blogList = blogService.getAllBlogs(); // Gọi service để lấy danh sách bài viết

        int totalRecords = blogList.size();
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
        int currentPage = 1;

        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, totalRecords);
        List<Post> paginatedPosts = blogList.subList(startIndex, endIndex);

        List<Post> recentBlogs = blogService.getRecentBlogs();
        request.setAttribute("recentBlogs", recentBlogs);
        request.setAttribute("blogs", paginatedPosts);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/WEB-INF/view/blog/BlogList.jsp").forward(request, response);

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
