package service.event;

import DAO.Event.EventDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Event;

public class EventService {

    private EventDAO eventDAO;

    public EventService() {
        this.eventDAO = new EventDAO();
    }

    public List<Event> getAllEvents() {
        try {
            return eventDAO.listAllEvents();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Event getEventById(int eventID) {
        try {
            return eventDAO.getEventById(eventID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Event> getEventByClubId(int clubID) {
        try {
            return eventDAO.getEventByClubId(clubID);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean isEventLeader(int userId, int clubId) {
        return eventDAO.isEventLeader(userId, clubId);
    }

    public boolean updateEvent(Event event) throws SQLException {
        return eventDAO.updateEvent(event);
    }

    public boolean addEvent(Event event) {
        if (eventDAO.isEventExist(event.getName(), event.getClub_id())) {
            return false; // Nếu sự kiện đã tồn tại, không thêm mới
        }
        return eventDAO.addEvent(event);
    }

    public List<Event> listEventsByFilters(int clubId, String status, String isPublic, String searchName, int page) throws SQLException {
        return eventDAO.listEventByFilter(clubId, status, isPublic, searchName, page);
    }

    public int getTotalPages(int clubId, String status, String isPublic, String searchName) throws SQLException {
        return eventDAO.getTotalPages(clubId, status, isPublic, searchName);
    }

}
