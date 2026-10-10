package controller.export;

import DAO.ClubMember.ClubMemberDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ExportMemberCSV", urlPatterns = {"/ExportMemberCSV"})
public class ExportMemberCSV extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("account") : null;

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String clubIdParam = request.getParameter("club_id");
        int clubId = 1;
        if (clubIdParam != null && !clubIdParam.isEmpty()) {
            try {
                clubId = Integer.parseInt(clubIdParam);
            } catch (NumberFormatException ignored) {
            }
        }

        ClubMemberDAO memberDAO = new ClubMemberDAO();
        List<User> members = memberDAO.getMembersByClubIdWithOffset(clubId, 0, 1000);

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"club_members_" + clubId + ".csv\"");

        PrintWriter writer = response.getWriter();
        // Write UTF-8 BOM for Microsoft Excel compatibility
        writer.write('\uFEFF');

        // CSV Header
        writer.println("User ID,Full Name,Email,Phone,Gender,Status");

        for (User u : members) {
            writer.printf("\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                    u.getUser_id(),
                    escapeCsv(u.getFull_name()),
                    escapeCsv(u.getEmail()),
                    escapeCsv(u.getPhone_number()),
                    escapeCsv(u.getGender()),
                    escapeCsv(u.getStatus())
            );
        }
        writer.flush();
    }

    private String escapeCsv(String text) {
        if (text == null) return "";
        return text.replace("\"", "\"\"");
    }
}
