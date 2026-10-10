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
@WebServlet(name = "SettingList", urlPatterns = {"/settingList"})
public class SettingList extends HttpServlet {

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
            out.println("<title>Servlet SettingList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SettingList at " + request.getContextPath() + "</h1>");
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
        String typeFilter = request.getParameter("type");
        String statusFilter = request.getParameter("status");
        String searchFilter = request.getParameter("searchName");
        int page = 1;
        int pageSize = 6;

        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        List<Setting> settings;
        boolean isFiltered = settingService.isFilted(typeFilter, statusFilter, searchFilter);
        if (isFiltered) {
            settings = settingService.findSettingsWithFilters(typeFilter, statusFilter, searchFilter, page, pageSize);
            request.setAttribute("settings", settings);
        } else {
            settings = settingService.findAllWithPagination(page, pageSize);
            request.setAttribute("settings", settings);
        }

        int totalSettings = settingService.getTotalFilteredSettings(typeFilter, statusFilter, searchFilter);
        int totalPages = (int) Math.ceil((double) totalSettings / pageSize);

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalSettings", totalSettings);
        request.setAttribute("typeFilter", typeFilter);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("searchFilter", searchFilter);

        request.getRequestDispatcher("/WEB-INF/view/setting/setting_list.jsp").forward(request, response);
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
        String settingIdStr = request.getParameter("id");
        String action = request.getParameter("action");
        // Lấy lại các tham số filter, pagination, search để giữ trạng thái trang
        String queryString = request.getQueryString();
        if (settingIdStr != null && !settingIdStr.isEmpty()) {
            if (action.equals("activate")) {
                try {
                    int settingId = Integer.parseInt(settingIdStr);
                    boolean activated = settingService.activateSetting(settingId);
                    if (activated) {
                        setToastMessage(request, "Setting activated successfully", "success");
                    } else {
                        setToastMessage(request, "Failed to activate setting", "error");
                    }
                } catch (NumberFormatException e) {
                    setToastMessage(request, "Invalid setting ID format", "error");
                }
            } else {
                try {
                    int settingId = Integer.parseInt(settingIdStr);
                    boolean deactivated = settingService.deactivateSetting(settingId);
                    if (deactivated) {
                        setToastMessage(request, "Setting deactivated successfully", "success");
                    } else {
                        setToastMessage(request, "Failed to deactivate setting", "error");
                    }
                } catch (NumberFormatException e) {
                    setToastMessage(request, "Invalid setting ID format", "error");
                }
            }

        } else {
            setToastMessage(request, "Invalid setting ID", "error");
        }
        response.sendRedirect("settingList?" + queryString);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private void setToastMessage(HttpServletRequest request, String message, String type) {
        HttpSession session = request.getSession();
        session.setAttribute("toastMessage", message);
        session.setAttribute("toastType", type);
        System.out.println("Toast message set: " + message + " | Type: " + type);
    }
}
