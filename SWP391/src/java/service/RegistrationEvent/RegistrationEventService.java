/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.RegistrationEvent;

import DAO.Registration.RegistrationDAO;
import java.util.List;
import model.entity.Club;
import model.entity.Event;
import model.entity.RegistrationEvent;

/**
 *
 * @author admin
 */
public class RegistrationEventService {

    RegistrationDAO registrationDAO = new RegistrationDAO();

    public List<RegistrationEvent> getRegistrationByLeaderId(int leaderId) {
        return registrationDAO.getRegistrationByLeaderId(leaderId);
    }

    public List<Event> getEventsByClubIdAndLeaderId(int clubId, int leaderId) {
        return registrationDAO.getEventsByClubIdAndLeaderId(clubId, leaderId);
    }

    public List<RegistrationEvent> getRegistrationsByFilter(int leaderId, Integer clubId, Integer eventId, String statusFilter, String searchQuery, int page, int pageSize) {
        return registrationDAO.getRegistrationsByFilter(leaderId, clubId, eventId, statusFilter, searchQuery, page, pageSize);
    }

    public int getTotalPages(int leaderId, Integer clubId, Integer eventId, String statusFilter, String searchQuery, int pageSize) {
        int totalRecords = registrationDAO.countRegistrations(leaderId, clubId, eventId, statusFilter, searchQuery);
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    public int countRegistrations(int leaderId, Integer clubId, Integer eventId, String statusFilter, String searchQuery) {
        return registrationDAO.countRegistrations(leaderId, clubId, eventId, statusFilter, searchQuery);
    }

    public List<Event> getEventsByLeaderId(int leaderId) {
        return registrationDAO.getEventsByLeaderId(leaderId);
    }

    public List<Club> getClubByUserIdToFilter(int userId) {
        return registrationDAO.getClubByUserIdToFilter(userId);
    }

    public RegistrationEvent getRegistrationByRegistrationId(int registrationId) {
        return registrationDAO.getRegistrationByRegistrationId(registrationId);
    }

    public boolean updateRegistrationStatus(int registrationId, String newStatus, String note) {
        return registrationDAO.updateRegistrationStatus(registrationId, newStatus, note);
    }

    // thêm mới
    public List<RegistrationEvent> getRegistrationByLeaderId(int leaderId, int page, int pageSize) {
        return registrationDAO.getRegistrationByLeaderId(leaderId, page, pageSize);
    }

}
