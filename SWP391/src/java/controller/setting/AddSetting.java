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
@WebServlet(name = "AddSetting", urlPatterns = {"/addSetting"})
public class AddSetting extends HttpServlet {

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
            out.println("<title>Servlet AddSetting</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddSetting at " + request.getContextPath() + "</h1>");
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
            Integer typeId = Integer.parseInt(request.getParameter("type_id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String value = request.getParameter("value");
            Integer priority = Integer.parseInt(request.getParameter("priority"));
            String status = request.getParameter("status");

            Setting setting = new Setting(name, typeId, value, priority, status, description);

            // Gọi service để thêm setting
            boolean added = settingService.addSetting(setting);

            if (added) {
                setToastMessage(request, "Setting added successfully", "success");
            } else {
                setToastMessage(request, "Failed to add setting. Please check input values.", "error");
            }

        } catch (NumberFormatException e) {
            setToastMessage(request, "Invalid number format: " + e.getMessage(), "error");
        } catch (Exception e) {
            setToastMessage(request, "Error: " + e.getMessage(), "error");
        }

        response.sendRedirect("settingList");
    }

    private void setToastMessage(HttpServletRequest request, String message, String type) {
        HttpSession session = request.getSession();
        session.setAttribute("toastMessage", message);
        session.setAttribute("toastType", type);
        System.out.println("Toast message set: " + message + " | Type: " + type);
    }

}
