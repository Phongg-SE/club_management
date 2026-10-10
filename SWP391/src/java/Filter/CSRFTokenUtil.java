package filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;

public class CSRFTokenUtil {
    public static final String CSRF_SESSION_ATTR = "CSRF_TOKEN";
    public static final String CSRF_PARAM_NAME = "_csrf";
    public static final String CSRF_HEADER_NAME = "X-CSRF-Token";

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String getToken(HttpSession session) {
        if (session == null) return null;
        String token = (String) session.getAttribute(CSRF_SESSION_ATTR);
        if (token == null) {
            byte[] bytes = new byte[32];
            secureRandom.nextBytes(bytes);
            token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
            session.setAttribute(CSRF_SESSION_ATTR, token);
        }
        return token;
    }

    public static boolean isValidToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        String sessionToken = (String) session.getAttribute(CSRF_SESSION_ATTR);
        if (sessionToken == null || sessionToken.isEmpty()) {
            return false;
        }

        // Check header first, then request parameter
        String clientToken = request.getHeader(CSRF_HEADER_NAME);
        if (clientToken == null || clientToken.isEmpty()) {
            clientToken = request.getParameter(CSRF_PARAM_NAME);
        }
        if (clientToken == null || clientToken.isEmpty()) {
            clientToken = request.getParameter("csrf_token");
        }

        return sessionToken.equals(clientToken);
    }
}
