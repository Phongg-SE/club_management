/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.club;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.User;
import service.club.ClubService;
import service.clubmember.ClubMemberService;
import service.event.EventService;

/**
 *
 * @author admin
 */
@WebServlet(name = "PublicClubDetail", urlPatterns = {"/PublicClubDetail"})
public class PublicClubDetail extends HttpServlet {

    private ClubService clubService;
    private ClubMemberService clubMemberService;

    @Override
    public void init() {
        clubService = new ClubService();
        clubMemberService = new ClubMemberService();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        clubService = new ClubService();

        // Check if user is logged in
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        request.setAttribute("account", account);

        // Lấy tham số club_id từ request
        int clubId = Integer.parseInt(request.getParameter("id"));

        Club club = clubService.getPublicClubDetails(clubId);
        if (club == null){
            request.getRequestDispatcher("error").forward(request, response);
            return;
        }
        
        List<Club> relatedClubs = clubService.getAllClubsByCategoryId(club.getCategory_id());
        relatedClubs.remove(club);
        if (clubId == -1) {
            response.sendRedirect("error");
            return;
        }
        request.setAttribute("relatedClubs", relatedClubs);
        request.setAttribute("club", club);
        request.getRequestDispatcher("/WEB-INF/view/club/publicclubdetail.jsp").forward(request, response);
    }

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
        // Check if we're coming from a redirect with toast messages
        HttpSession session = request.getSession();

        // Clear any existing toast messages to prevent them from appearing multiple times
        // session.removeAttribute("toastMessage");
        // session.removeAttribute("toastType");
        // Process the request normally
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
            // Get the club ID from the request
            int clubId = Integer.parseInt(request.getParameter("clubId"));

            // Get the user from the session
            HttpSession session = request.getSession();
            User account = (User) session.getAttribute("account");

            if (account == null) {
                // User is not logged in, redirect to login page
                setToastMessage(request, "Please login to join clubs", "error");
                response.sendRedirect("login");
                return;
            }

            // Get form parameters
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String note = request.getParameter("note");

            // Validate note length
            if (note != null && note.length() > 255) {
                setToastMessage(request, "Note cannot exceed 255 characters", "warning");
                // Redirect back to the same club detail page
                response.sendRedirect("PublicClubDetail?id=" + clubId);
                return;
            }

            // Get the club details to check category
            Club club = clubService.getClubById2(clubId);
            if (club == null) {
                setToastMessage(request, "Club not found", "error");
                response.sendRedirect("PublicClub");
                return;
            }

            try {
                // boolean isJoinClub = clubService.isJoinClub(clubId, account.getUser_id());
                ClubMember clubMemberInDB = clubMemberService.getClubMemberByUserIdAndClubId(account.getUser_id(), clubId);
                if (clubMemberInDB != null) {
                    if (clubMemberInDB.getStatus().equals("Registered")) {
                        setToastMessage(request, "You are already registered for this club", "warning");
                    } else if (clubMemberInDB.getStatus().equals("Active") || clubMemberInDB.getStatus().equals("Inactive")) {
                        setToastMessage(request, "You are already a member of this club", "warning");
                    }
                    // Redirect back to the same club detail page
                    response.sendRedirect("PublicClubDetail?id=" + clubId);
                    return;
                }

            } catch (Exception e) {
                setToastMessage(request, e.getMessage(), "warning");
                // Redirect back to the same club detail page
                response.sendRedirect("PublicClubDetail?id=" + clubId);
                return;
            }

            // Check if the user is already a member of the club
//            ClubMember existingMembership = clubMemberService.getClubMemberByUserId(account.getUser_id());
            //tra ve 1ist club
            List<ClubMember> existingClubEnroll = clubMemberService.getAllClubOfUserById(account.getUser_id());
            boolean isRegister = false;
            boolean isMemberOfClub = false;
            boolean isActive = false;
            for (ClubMember clubMember : existingClubEnroll) {
                if (clubMember != null) {
                    if (clubMember.getClubId() == clubId) {
                        isMemberOfClub = true;
                    }
                    // Check if user is already a member of a club with the same category
                    Club existingClub = clubService.getClubById2(clubMember.getClubId());
                    if (existingClub != null && existingClub.getCategory_id() == club.getCategory_id()) {
                        if ("Registered".equals(clubMember.getStatus())) {
                            isRegister = true;

                        } else if ("Active".equals(clubMember.getStatus()) || "Inactive".equals(clubMember.getStatus())) {

                            isActive = true;
                        }

                    }
                }
            }
            if (isMemberOfClub) {
                setToastMessage(request, "You are already registered for this club", "warning");
                response.sendRedirect("PublicClubDetail?id=" + clubId);
                return;
            }
            if (isRegister) {
                setToastMessage(request, "You are already registered to a club with the same category", "warning");
                response.sendRedirect("PublicClubDetail?id=" + clubId);
                return;
            }
            if (isActive) {
                setToastMessage(request, "You are already a member of another club in the same category", "warning");
                response.sendRedirect("PublicClubDetail?id=" + clubId);
                return;
            }
            // Create a new ClubMember object
            ClubMember newMember = new ClubMember();
            newMember.setClubId(clubId);
            newMember.setUserId(account.getUser_id());

            // Get the default member role ID (assuming it's stored in the setting table)
            int defaultRoleId = 6; // Default member role ID, adjust as needed
            newMember.setRoleId(defaultRoleId);

            // Set the status to "Registed"
            newMember.setStatus("Registered");

            // Add the user to the club_member table
            boolean success = clubMemberService.addClubMember(newMember, note);

            if (success) {
                setToastMessage(request, "Your application to join the club has been submitted successfully!", "success");
            } else {
                setToastMessage(request, "Failed to submit your application. Please try again later.", "error");
            }

            // Redirect back to the same club detail page
            response.sendRedirect("PublicClubDetail?id=" + clubId);

        } catch (NumberFormatException e) {
            // Invalid club ID
            System.out.println("Error: " + e.getMessage());
            setToastMessage(request, "Invalid club ID", "error");
            response.sendRedirect("PublicClubList");
        } catch (Exception e) {
            // Other exceptions
            System.out.println("Error: " + e.getMessage());
            setToastMessage(request, "An error occurred while processing your request", "error");
            response.sendRedirect("PublicClubList");
        }
    }

    // Helper method to set toast message in session
    private void setToastMessage(HttpServletRequest request, String message, String type) {
        HttpSession session = request.getSession();
        session.setAttribute("toastMessage", message);
        session.setAttribute("toastType", type);
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
