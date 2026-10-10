/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import model.entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author datkh
 */
@WebFilter(urlPatterns = {"/registrationEventList", "/registrationDetail"})
public class AuthenEventLeader implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            httpResponse.sendRedirect("home");
            return;
        }

        // Lấy danh sách vai trò của club từ session
        List<Integer> clubRole = (List<Integer>) session.getAttribute("clubRole");

        // Lấy role tổng thể
        Integer role = (Integer) session.getAttribute("role");
        if (role == null) {
            role = -1;
        }

        // Kiểm tra điều kiện role hợp lệ trước khi gọi contains()
        if (role == -1 || clubRole == null || !clubRole.contains(2) || !clubRole.contains(4)) {
            httpResponse.sendRedirect("home");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
