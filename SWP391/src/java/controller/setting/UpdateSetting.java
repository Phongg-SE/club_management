/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.setting;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.entity.Setting;
import service.setting.SettingService;

/**
 *
 * @author admin
 */
@WebServlet(name = "UpdateSetting", urlPatterns = {"/updateSetting"})
public class UpdateSetting extends HttpServlet {

    private SettingService settingService;

    @Override
    public void init() {
        settingService = new SettingService();
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateSetting</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateSetting at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String settingId = request.getParameter("id");
        try {
            int id = Integer.parseInt(settingId);
            Setting setting = settingService.findById(id);
            if (!settingService.isSettingExist(setting)) {
                // Nếu không tìm thấy setting, gửi thông báo lỗi
                setToastMessage(request, "Can not find the setting with that id", "error");
                return;
            }

            // Nếu tìm thấy, đẩy setting lên request scope
            request.setAttribute("setting", setting);

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format: " + e.getMessage());
            return;
        }

        // Chuyển hướng đến trang chi tiết
        request.getRequestDispatcher("/WEB-INF/view/setting/setting_details.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response protected void doGet(HttpServletRequest
     * request, HttpServletResponse response) throws ServletException,
     * IOException { String settingId = request.getParameter("id"); int id; try
     * { if (settingId != null && !settingId.trim().isEmpty()) { id =
     * Integer.parseInt(settingId); Setting setting =
     * settingService.findById(id); if(setting != null){
     * request.setAttribute("setting", setting); } }
     *
     * } catch (NumberFormatException e) { System.out.println(e.getMessage()); }
     * request.getRequestDispatcher("/WEB-INF/view/setting/setting_details.jsp").forward(request,
     * response);
     *
     * }
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Lấy dữ liệu từ form
            Integer settingId = Integer.parseInt(request.getParameter("id"));
            Integer typeId = Integer.parseInt(request.getParameter("type_id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String value = request.getParameter("value");
            Integer priority = Integer.parseInt(request.getParameter("priority"));
            String status = request.getParameter("status");

            // Tạo đối tượng Setting
            Setting setting = new Setting();
            setting.setSettingId(settingId);
            setting.setTypeId(typeId);
            setting.setName(name);
            setting.setDescription(description);
            setting.setValue(value);
            setting.setPriority(priority);
            setting.setStatus(status);

            // Kiểm tra trùng lặp value (không tính setting hiện tại)
            if (settingService.isDuplicateValue(settingId, value)) {
                setToastMessage(request, "Value already exists", "error");
                request.setAttribute("setting", setting);
                request.getRequestDispatcher("/WEB-INF/view/setting/setting_details.jsp").forward(request, response);
                return;
            }

            // Cập nhật setting nếu không bị trùng value
            boolean updated = settingService.updateSetting(setting);
            if (updated) {
                setToastMessage(request, "Setting updated successfully", "success");
            } else {
                setToastMessage(request, "Failed to update setting", "error");
            }

            // Quay lại trang updateSetting với ID cũ
            response.sendRedirect(request.getContextPath() + "/updateSetting?id=" + settingId);

        } catch (NumberFormatException e) {
            setToastMessage(request, "Invalid number format: " + e.getMessage(), "error");
            response.sendRedirect(request.getContextPath() + "/updateSetting?id=" + request.getParameter("id"));
        } catch (Exception e) {
            setToastMessage(request, "Error: " + e.getMessage(), "error");
            response.sendRedirect(request.getContextPath() + "/updateSetting?id=" + request.getParameter("id"));
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

    private void setToastMessage(HttpServletRequest request, String message, String type) {
        HttpSession session = request.getSession();
        session.setAttribute("toastMessage", message);
        session.setAttribute("toastType", type);
        System.out.println("Toast message set: " + message + " | Type: " + type);
    }
}
