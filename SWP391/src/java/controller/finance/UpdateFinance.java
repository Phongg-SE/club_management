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
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import model.entity.Club;
import model.entity.Finance;
import model.entity.User;
import service.club.ClubService;
import service.finance.FinanceService;

/**
 *
 * @author Dell
 */
@WebServlet(name = "UpdateFinance", urlPatterns = {"/UpdateFinance"})
public class UpdateFinance extends HttpServlet {

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
        processRequest(request, response);
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
        try {

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Lấy user từ session
            User user = (User) session.getAttribute("account");
            int userId = user.getUser_id();

            // Lấy dữ liệu từ request
            int financeId = Integer.parseInt(request.getParameter("financeId"));
            String financeName = request.getParameter("financeName");
            String eventIdStr = request.getParameter("eventId");
            Integer eventId = (eventIdStr == null || eventIdStr.isEmpty()) ? null : Integer.parseInt(eventIdStr);
            int memberId = Integer.parseInt(request.getParameter("memberId"));
            String approverIdStr = request.getParameter("approverId");
            Integer approverId = (approverIdStr == null || approverIdStr.isEmpty()) ? null : Integer.parseInt(approverIdStr);
            String isExpense = request.getParameter("isExpense");
            double amount = Double.parseDouble(request.getParameter("amount"));
            String financeDateStr = request.getParameter("financeDate");
            String details = request.getParameter("details");

            // Lấy thông tin bản ghi tài chính cần cập nhật
            Finance existingFinance = financeService.getFinanceById(financeId);

            if (existingFinance == null) {
                request.getSession().setAttribute("errorMessage", "Bản ghi tài chính không tồn tại.");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }

            if (financeName.trim().isEmpty() || details.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Không được để trống trường !");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }

            // Lấy clubId của bản ghi
            int clubId = existingFinance.getClubId();

            // Kiểm tra quyền
            boolean isClubLeader = clubService.isClubLeader(clubId, userId);
            boolean isOwner = (existingFinance.getMemberId() == userId);

            // Chỉ cho phép cập nhật nếu là chủ bài viết hoặc là club leader
            if (!isOwner && !isClubLeader) {
                request.getSession().setAttribute("errorMessage", "Bạn không có quyền cập nhật.");
                response.sendRedirect(request.getContextPath() + "/FinanceList");
                return;
            }
            String status;
            if (isClubLeader) {
                // Nếu là Club Leader, lấy status từ request
                status = request.getParameter("status");
            } else {
                // Nếu không phải Club Leader, giữ nguyên status hiện tại
                status = existingFinance.getStatus();
            }

            if (!status.equals("Approve") && !status.equals("Reject") && !status.equals("Pending")) {
                request.getSession().setAttribute("errorMessage", "Trạng thái không hợp lệ.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            // Kiểm tra dữ liệu nhập vào
            if (financeName == null || financeName.trim().length() > 50) {
                request.getSession().setAttribute("errorMessage", "Tên tài chính phải có độ dài không quá 50 ký tự.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            if (amount <= 0 || amount >= 1_000_000_000) {
                request.getSession().setAttribute("errorMessage", "Số tiền phải lớn hơn 0 và nhỏ hơn 1 tỷ.");
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            // Kiểm tra details có thể null
            if (details != null) {
                details = details.trim();
                if (details.isEmpty()) {
                    details = null;
                } else if (details.length() > 1000) {
                    request.getSession().setAttribute("errorMessage", "Mô tả chi tiết phải có từ 0 đến 1000 ký tự.");
                    response.sendRedirect(request.getHeader("referer"));
                    return;
                }
            }

            // Kiểm tra và format lại financeDate (giữ nguyên kiểu String)
            String formattedFinanceDate = (financeDateStr != null && !financeDateStr.isEmpty()) ? financeDateStr : null;

            // Lấy thời gian hiện tại làm last_updated (format thành chuỗi)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lastUpdated = sdf.format(new Date());

            // Tạo đối tượng Finance
            Finance updatedFinance = new Finance(financeId, financeName, clubId, eventId, memberId, approverId, isExpense, amount, status, formattedFinanceDate, lastUpdated, details);

            // Cập nhật tài chính
            boolean isUpdated = financeService.updateFinance(updatedFinance);

            if (isUpdated) {
                request.getSession().setAttribute("successMessage", "Cập nhật thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Không thể cập nhật thông tin tài chính.");
            }
            response.sendRedirect(request.getContextPath() + "/FinanceList");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Lỗi định dạng số: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
        } catch (DateTimeParseException e) {
            request.getSession().setAttribute("errorMessage", "Lỗi định dạng ngày: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            response.sendRedirect(request.getHeader("referer"));
        }

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
