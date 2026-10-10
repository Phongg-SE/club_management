/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.club;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.entity.Club;
import service.club.ClubService;

/**
 *
 * @author pc
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 2mb
        maxFileSize = 1024 * 1024 * 20, // 10mb    
        maxRequestSize = 1024 * 1024 * 100) // 50mb

@WebServlet(name = "AddClub", urlPatterns = {"/addclub"})
public class AddClub extends HttpServlet {

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
        List<String> errors = new ArrayList<>(); 
        HttpSession session = request.getSession();

        try {
            String clubName = request.getParameter("clubName");
            String category = request.getParameter("categoryId");
            String isPublic = request.getParameter("isPublic");
            String status = request.getParameter("status");
            String user_idStr = request.getParameter("leaderId");
            int user_id = Integer.parseInt(user_idStr);

            // Xử lý upload file logo
            Part filePart = request.getPart("clubLogo");

            if (filePart != null && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + "uploads" + File.separator + fileName;
                filePart.write(uploadPath);
                String clubLogoUrl = "uploads/" + fileName;

                // Tạo đối tượng Club
                Club club = new Club(clubName, clubLogoUrl, isPublic, Integer.parseInt(category), status);

                // Gọi service với validation và thêm vào cơ sở dữ liệu
                errors = clubService.validateAndInsertClub(club, filePart, user_id);

                if (errors.isEmpty()) {
                    session.setAttribute("toastMessage", "Add Club Successfully");
                    response.sendRedirect(request.getContextPath() + "/clublist");
                } else {
                    // Nếu có lỗi, gửi lỗi về JSP
                    System.out.println(errors);
                    request.setAttribute("errors", errors);
                    request.setAttribute("club", club);  // Trả lại dữ liệu đã nhập để không mất khi reload
                    request.setAttribute("openModal", true); // Mở lại modal khi có lỗi
                request.getRequestDispatcher("/WEB-INF/view/club/clublist.jsp").forward(request, response);
                    return;
                }
            } else {
                errors.add("Logo image is required.");
                request.setAttribute("errors", errors);
                request.setAttribute("openModal", true);
                request.getRequestDispatcher("/WEB-INF/view/club/clublist.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            errors.add("Error: " + e.getMessage());
            request.setAttribute("errors", errors);
            request.setAttribute("openModal", true);
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/view/club/clublist.jsp").forward(request, response);
        }
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
