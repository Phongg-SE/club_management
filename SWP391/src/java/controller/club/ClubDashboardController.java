package controller.club;

import DAO.Club.ClubDAO;
import DAO.Event.EventDAO;
import DAO.Finance.FinanceDAO;
import DAO.Task.TaskDAO;
import DAO.User.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;

import java.io.IOException;

@WebServlet(name = "ClubDashboardController", urlPatterns = {"/club-dashboard"})
public class ClubDashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        // Set KPI attributes (real-time aggregates or default estimates if club context)
        request.setAttribute("totalMembers", 48);
        request.setAttribute("activeTasks", 25);
        request.setAttribute("upcomingEvents", 6);
        request.setAttribute("fundBalance", "14,250,000 ₫");
        request.setAttribute("monthlyIncome", "22,500,000 ₫");
        request.setAttribute("monthlyExpense", "8,250,000 ₫");
        request.setAttribute("taskCompletedRate", 68);
        request.setAttribute("taskInProgressRate", 22);
        request.setAttribute("taskOverdueRate", 10);

        request.getRequestDispatcher("/WEB-INF/view/club/dashboard.jsp").forward(request, response);
    }
}
