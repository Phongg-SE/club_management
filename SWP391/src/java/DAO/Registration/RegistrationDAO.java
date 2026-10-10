/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Registration;

import java.sql.Connection;
import java.util.List;
import model.dto.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.entity.Club;
import model.entity.Event;

import model.entity.RegistrationEvent;

/**
 *
 * @author admin
 */
public class RegistrationDAO {

    Connection con = DBContext.getJDBCConnection();

    public List<Club> getClubByUserIdToFilter(int userId) {
        List<Club> clubs = new ArrayList<>();
        String query = "SELECT DISTINCT c.club_id, c.name "
                + "FROM club c "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "JOIN event e ON c.club_id = e.club_id "
                + "WHERE cm.user_id = ? AND cm.status = 'Active' "
                + "AND e.leader_id = ?";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Club club = new Club();
                    club.setClub_id(rs.getInt("club_id"));
                    club.setName(rs.getString("name"));
                    clubs.add(club);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubs;
    }

    public List<Event> getEventsByClubIdAndLeaderId(int clubId, int leaderId) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT event_id, name FROM event WHERE club_id = ? AND leader_id = ? AND type = 'Event'";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, clubId);
            ps.setInt(2, leaderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int eventID = rs.getInt("event_id");
                String eventName = rs.getString("name");
                Event event = new Event();
                event.setEvent_id(eventID);
                event.setName(eventName);
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<Event> getEventsByLeaderId(int leaderId) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT event_id, name, club_id FROM event WHERE leader_id = ? AND type = 'Event' ";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, leaderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int eventID = rs.getInt("event_id");
                String eventName = rs.getString("name");
                int clubId = rs.getInt("club_id");

                Event event = new Event();
                event.setEvent_id(eventID);
                event.setName(eventName);
                event.setClub_id(clubId);
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    // DAO để filter
    public List<RegistrationEvent> getRegistrationsByFilter(int leaderId, Integer clubId, Integer eventId,
            String statusFilter, String searchQuery,
            int page, int pageSize) {
        List<RegistrationEvent> registrations = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT r.registration_id, e.event_id, e.name AS event_name, "
                + "c.club_id, c.name AS club_name, r.register_id, r.full_name, r.mobile, r.email, "
                + "r.status, r.note "
                + "FROM registration r "
                + "JOIN event e ON r.event_id = e.event_id "
                + "JOIN club c ON e.club_id = c.club_id "
                + "WHERE e.leader_id = ?");

        if (clubId != null) {
            sql.append(" AND c.club_id = ?");
        }
        if (eventId != null) {
            sql.append(" AND e.event_id = ?");
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append(" AND r.status = ?");
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND (r.full_name LIKE ? OR r.email LIKE ? OR r.mobile LIKE ?)");
        }

        // Thêm phần phân trang (ORDER BY + LIMIT + OFFSET)
        sql.append(" ORDER BY r.registration_id ASC LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            stmt.setInt(paramIndex++, leaderId);

            if (clubId != null) {
                stmt.setInt(paramIndex++, clubId);
            }
            if (eventId != null) {
                stmt.setInt(paramIndex++, eventId);
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                stmt.setString(paramIndex++, statusFilter);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String searchPattern = "%" + searchQuery + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            // Xử lý phân trang
            int offset = (page - 1) * pageSize; // Tính toán OFFSET
            stmt.setInt(paramIndex++, pageSize); // Số bản ghi trên mỗi trang
            stmt.setInt(paramIndex++, offset); // Offset để lấy đúng dữ liệu

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RegistrationEvent registrationEvent = new RegistrationEvent();
                registrationEvent.setRegistration_id(rs.getInt("registration_id"));
                registrationEvent.setEvent_id(rs.getInt("event_id"));
                registrationEvent.setEventName(rs.getString("event_name"));
                registrationEvent.setClub_Id(rs.getInt("club_id"));
                registrationEvent.setClubName(rs.getString("club_name"));
                registrationEvent.setFull_name(rs.getString("full_name"));
                registrationEvent.setMobile(rs.getString("mobile"));
                registrationEvent.setEmail(rs.getString("email"));
                registrationEvent.setStatus(rs.getString("status"));
                registrations.add(registrationEvent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    // DAO để tính tổng record
    public int countRegistrations(int leaderId, Integer clubId, Integer eventId, String statusFilter, String searchQuery) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM registration r "
                + "JOIN event e ON r.event_id = e.event_id "
                + "JOIN club c ON e.club_id = c.club_id "
                + "WHERE e.leader_id = ?");

        if (clubId != null) {
            sql.append(" AND c.club_id = ?");
        }
        if (eventId != null) {
            sql.append(" AND e.event_id = ?");
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append(" AND r.status = ?");
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND (r.full_name LIKE ? OR r.email LIKE ? OR r.mobile LIKE ?)");
        }

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            stmt.setInt(paramIndex++, leaderId);

            if (clubId != null) {
                stmt.setInt(paramIndex++, clubId);
            }
            if (eventId != null) {
                stmt.setInt(paramIndex++, eventId);
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                stmt.setString(paramIndex++, statusFilter);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String searchPattern = "%" + searchQuery + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<RegistrationEvent> getRegistrationByLeaderId(int leaderId) {
        List<RegistrationEvent> registrations = new ArrayList<>();
        String sql = "SELECT r.registration_id, e.name AS event_name, r.register_id, r.full_name,r.mobile , r.email, r.status, r.note "
                + "FROM registration r "
                + "JOIN event e ON r.event_id = e.event_id "
                + "WHERE e.leader_id = ?"; // Chỉ lấy đơn đăng ký của sự kiện do leaderId quản lý

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, leaderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RegistrationEvent registrationEvent = new RegistrationEvent();
                registrationEvent.setRegistration_id(rs.getInt("registration_id"));
                registrationEvent.setEmail(rs.getString("email"));
                registrationEvent.setEventName(rs.getString("event_name"));
                registrationEvent.setFull_name(rs.getString("r.full_name"));
                registrationEvent.setMobile(rs.getString("r.mobile"));
                registrationEvent.setStatus(rs.getString("r.status"));
                registrations.add(registrationEvent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    public RegistrationEvent getRegistrationByRegistrationId(int registrationId) {
        String sql = "SELECT r.registration_id, e.name AS event_name, c.name AS club_name, r.register_id, r.full_name, r.mobile, r.email, r.status, r.note, e.status AS eventstatus,r.event_id "
                + "FROM registration r "
                + "JOIN event e ON r.event_id = e.event_id "
                + "JOIN club c ON e.club_id = c.club_id "
                + "WHERE r.registration_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, registrationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                RegistrationEvent registrationEvent = new RegistrationEvent();
                registrationEvent.setRegistration_id(rs.getInt("registration_id"));
                registrationEvent.setEvent_id(rs.getInt("event_id"));
                registrationEvent.setEmail(rs.getString("email"));
                registrationEvent.setEventName(rs.getString("event_name"));
                registrationEvent.setClubName(rs.getString("club_name"));
                registrationEvent.setFull_name(rs.getString("full_name"));
                registrationEvent.setMobile(rs.getString("mobile"));
                registrationEvent.setStatus(rs.getString("status"));
                registrationEvent.setNote(rs.getString("note"));
                registrationEvent.setEventStatus(rs.getString("eventstatus"));
                return registrationEvent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateRegistrationStatus(int registrationId, String newStatus, String reason) {
        String sql = "UPDATE registration SET status = ?, reason =? WHERE registration_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, reason);
            stmt.setInt(3, registrationId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        boolean result = registrationDAO.updateRegistrationStatus(1, "Confirmed", "User requested cancellation");
        System.out.println(result ? "Update successful!" : "Update failed!");
    }

    private static void printResults(List<RegistrationEvent> registrations) {
        if (registrations.isEmpty()) {
            System.out.println("Không có dữ liệu.");
        } else {
            for (RegistrationEvent reg : registrations) {
                System.out.println("ID: " + reg.getRegistration_id() + ", Event: " + reg.getEventName() + ", Club: " + reg.getClubName() + ", Name: " + reg.getFull_name() + ", Status: " + reg.getStatus());
            }
        }
        System.out.println("------------------------------------------------");
    }

    public List<RegistrationEvent> getRegistrationByLeaderId(int leaderId, int page, int pageSize) {
        List<RegistrationEvent> registrations = new ArrayList<>();
        String sql = "SELECT r.registration_id, e.name AS event_name, r.register_id, r.full_name, r.mobile, r.email, r.status, r.note "
                + "FROM registration r "
                + "JOIN event e ON r.event_id = e.event_id "
                + "WHERE e.leader_id = ? "
                + "ORDER BY r.registration_id " // Sắp xếp theo registration_id
                + "LIMIT ? OFFSET ?"; // Thêm phân trang

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, leaderId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RegistrationEvent registrationEvent = new RegistrationEvent();
                registrationEvent.setRegistration_id(rs.getInt("registration_id"));
                registrationEvent.setEmail(rs.getString("email"));
                registrationEvent.setEventName(rs.getString("event_name"));
                registrationEvent.setFull_name(rs.getString("full_name"));
                registrationEvent.setMobile(rs.getString("mobile"));
                registrationEvent.setStatus(rs.getString("status"));
                registrations.add(registrationEvent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }
}
