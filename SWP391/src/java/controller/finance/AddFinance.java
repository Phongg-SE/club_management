/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.finance;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.entity.Finance;
import model.entity.User;
import service.club.ClubService;
import service.finance.FinanceService;

/**
 *
 * @author Dell
 */
@WebServlet(name = "AddFinance", urlPatterns = {"/AddFinance"})
public class AddFinance extends HttpServlet {

    private FinanceService financeService = new FinanceService();
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
        try {
            // Lấy session và kiểm tra đăng nhập
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Lấy user_id từ session
            User user = (User) session.getAttribute("account");
            int userId = user.getUser_id();

            // Nhận clubId từ form (input hidden)
            String clubIdStr = request.getParameter("clubId");
            int clubId = (clubIdStr != null && !clubIdStr.isEmpty()) ? Integer.parseInt(clubIdStr) : -1;

            // Kiểm tra nếu không có clubId hợp lệ
            if (clubId == -1) {
                session.setAttribute("errorMessage", "Vui lòng chọn một câu lạc bộ.");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }

            // Lấy dữ liệu từ form
            String financeName = request.getParameter("financeName");
            String eventIdStr = request.getParameter("eventId");
            String amountStr = request.getParameter("amount");
            String isExpense = request.getParameter("isExpense");
            String financeDateStr = request.getParameter("financeDate");
            String details = request.getParameter("details");

            // Kiểm tra dữ liệu bắt buộc
            if (amountStr == null || amountStr.isEmpty()) {
                session.setAttribute("errorMessage", "Amount không được để trống.");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }

            // Validate financeName
            if (financeName == null || financeName.length() > 50) {
                session.setAttribute("errorMessage", "Finance Name phải có độ dài không quá 50 ký tự.");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }

            // Validate details
            if (details != null && details.length() > 255) {
                session.setAttribute("errorMessage", "Details không được quá 255 ký tự.");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }

            // Validate amount
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0 || amount >= 1_000_000_000) {
                    session.setAttribute("errorMessage", "Amount phải lớn hơn 0 và nhỏ hơn 1 tỷ.");
                    response.sendRedirect(request.getContextPath() + "/FinanceList");
                    return;
                }
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Amount không hợp lệ.");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }

            // Xử lý dữ liệu đầu vào
            Integer eventId = (eventIdStr == null || eventIdStr.isEmpty()) ? 0 : Integer.parseInt(eventIdStr);
            Integer approverId = 0; // Mặc định là null
            String status = "Pending";

            String formattedFinanceDate = (financeDateStr != null && !financeDateStr.isEmpty()) ? financeDateStr : null;

            // Tạo đối tượng Finance với memberId = userId
            Finance newFinance = new Finance(0, financeName, clubId, eventId, userId, approverId, isExpense, amount, status, formattedFinanceDate, null, details);

            boolean isInserted = financeService.addFinance(newFinance);

            if (isInserted) {
                request.getSession().setAttribute("successMessage", "Thêm thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Thêm thất bại.");
            }
            response.sendRedirect(request.getContextPath() + "/FinanceList");

        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
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
