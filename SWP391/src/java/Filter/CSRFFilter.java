package filter;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = {"/*"})
public class CSRFFilter implements Filter {

    // Endpoints that require strict CSRF validation on POST
    private static final Set<String> PROTECTED_POST_PATHS = new HashSet<>(Arrays.asList(
        "/updateSetting", "/updateclub", "/AddPost", "/UpdatePost", "/TaskUpdate",
        "/finance_update", "/AddNewPost"
    ));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(true);
        String token = CSRFTokenUtil.getToken(session);
        // Expose token to JSPs as request attribute
        httpRequest.setAttribute("csrfToken", token);

        String path = httpRequest.getServletPath();
        String method = httpRequest.getMethod();

        if ("POST".equalsIgnoreCase(method) && PROTECTED_POST_PATHS.contains(path)) {
            if (!CSRFTokenUtil.isValidToken(httpRequest)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, 
                        "CSRF verification failed! Yêu cầu bị từ chối do thiếu hoặc sai CSRF Token.");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
