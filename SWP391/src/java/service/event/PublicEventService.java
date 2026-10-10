package service.event;

import DAO.Club.ClubDAO;
import DAO.Event.EventDAO;
import DAO.Event.PublicEventDAO;
import java.sql.SQLException;
import java.util.List;
import model.entity.Club;
import model.entity.Event;
import model.entity.RegistrationEvent;

public class PublicEventService {

    // Tạo đối tượng DAO để sử dụng phương thức lấy sự kiện theo ID
    private PublicEventDAO eventDAO = new PublicEventDAO();
    private ClubDAO clubDAO = new ClubDAO();

    // Phương thức để lấy sự kiện theo event_id
    public Event getEventById(int eventId) {
        // Gọi phương thức getEventById của DAO
        return eventDAO.getEventById(eventId);
    }

    // Lấy 3 sự kiện ngẫu nhiên theo club_id
    public List<Event> getRandomEventsByClubId(int clubId) {
        return eventDAO.getRandomEventsByClubId(clubId);
    }

    public List<Event> getAllPublicEvent() {
        return eventDAO.getAllPublicEvent();
    }

    public List<Event> getUpcomingEvents() {
        return eventDAO.getUpcomingEvents(5);
    }
    public List<Event> getAllPublicEvent(int page, int pageSize) {
        return eventDAO.getAllPublicEvent(page, pageSize);
    }

    public RegistrationResult registerForEvent(RegistrationEvent registration) {
        try {
            // Get the event to find its club_id
            Event event = eventDAO.getEventById(registration.getEvent_id());
            if (event == null) {
                return new RegistrationResult(false, "Event not found");
            }

            int clubId = event.getClub_id();
            int userId = registration.getRegister_id();

            // Check if user is already a member of the club organizing the event
            int userClubId = clubDAO.getClubIdByUserId(userId);

            if (userClubId == clubId) {
                return new RegistrationResult(false, "You are a member of this club, no need to register for the event");
            }

            // Check if user is already registered for this event
            if (isUserRegisteredForEvent(userId, registration.getEvent_id())) {
                return new RegistrationResult(false, "You have already registered for this event");
            }

            // If all checks pass, proceed with registration
            boolean success = eventDAO.registerForEvent(registration);
            if (success) {
                return new RegistrationResult(true, "Registration successful");
            } else {
                return new RegistrationResult(false, "Registration failed. Please try again later");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new RegistrationResult(false, "An error occurred: " + e.getMessage());
        }
    }

    public boolean isUserRegisteredForEvent(int userId, int eventId) {
        try {
            EventDAO eventDAO = new EventDAO();
            return eventDAO.isUserRegisteredForEvent(userId, eventId);
        } catch (SQLException e) {
            System.out.println("Error checking if user is registered: " + e.getMessage());
            return false;
        }
    }

    public boolean isUserJoinedEvent(int userId, int eventId) throws SQLException {
        try {
            EventDAO eventDAO = new EventDAO();
            return eventDAO.isUserJoinedEvent(userId, eventId);
        } catch (SQLException e) {
            System.out.println("Error checking if user is registered: " + e.getMessage());
            return false;
        }
    }

    public boolean registerUserForEvent(int userId, int eventId, String note) {
        try {
            EventDAO eventDAO = new EventDAO();
            return eventDAO.registerUserForEvent(userId, eventId, note);
        } catch (SQLException e) {
            System.out.println("Error registering user for event: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inner class to represent the result of a registration attempt
     */
    public class RegistrationResult {

        private boolean success;
        private String message;

        public RegistrationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Registers a user for an event with comprehensive validation and error
     * handling This method also retrieves the user's mobile number from the
     * user table
     *
     * @param userId The ID of the user registering for the event
     * @param eventId The ID of the event to register for
     * @param note Any notes provided by the user for the registration
     * @return A RegistrationResult object containing success status and a
     * message
     */
    public RegistrationResult registerUserForEventWithValidation(int userId, int eventId, String note, String mobile) {
        try {
            // Get the event to find its club_id
            Event event = getEventById(eventId);
            if (event == null) {
                return new RegistrationResult(false, "Event not found");
            }

            int clubId = event.getClub().getClub_id();

            // Check if user is already a member of the club organizing the event
            int userClubId = clubDAO.getClubIdByUserId(userId);

            if (userClubId == clubId) {
                return new RegistrationResult(false, "You are a member of this club, no need to register for the event");
            }

            // Check if user is already registered for this event
            if (isUserRegisteredForEvent(userId, eventId)) {
                return new RegistrationResult(false, "You have already registered for this event");
            }

            // If all checks pass, proceed with registration
            boolean success = registerUserForEventWithMobile(userId, eventId, note, mobile);
            if (success) {
                return new RegistrationResult(true, "Registration successful! You have been registered for this event.");
            } else {
                return new RegistrationResult(false, "Registration failed. Please try again later");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RegistrationResult(false, "An error occurred: " + e.getMessage());
        }
    }

    /**
     * Gets the mobile number for a user from the user table
     *
     * @param userId The ID of the user
     * @return The user's mobile number, or null if not found
     */
    private String getUserMobileNumber(int userId) {
        try {
            // You'll need to implement this method to query the user table
            // This is a placeholder implementation
            return clubDAO.getUserMobileNumber(userId);
        } catch (Exception e) {
            System.out.println("Error getting user mobile number: " + e.getMessage());
            return null; // Return null if there's an error
        }
    }

    /**
     * Registers a user for an event with their mobile number
     *
     * @param userId The ID of the user registering for the event
     * @param eventId The ID of the event to register for
     * @param note Any notes provided by the user for the registration
     * @param mobile The user's mobile number (can be null)
     * @return true if registration was successful, false otherwise
     */
    public boolean registerUserForEventWithMobile(int userId, int eventId, String note, String mobile) {
        try {
            EventDAO eventDAO = new EventDAO();
            return eventDAO.registerUserForEventWithMobile(userId, eventId, note, mobile);
        } catch (SQLException e) {
            System.out.println("Error registering user for event: " + e.getMessage());
            return false;
        }
    }

    public List<Club> getClubsFromPublicEvents() {
        return eventDAO.getClubsFromPublicEvents();
    }

    public List<Event> getPublicEventsByClubId(Integer clubId, String searchQuery, int page, int pageSize) {
        return eventDAO.getPublicEventsByClubId(clubId, searchQuery, page, pageSize);
    }

    public RegistrationResult registerGuestForEvent(String email, String fullName, String mobile, int eventId, String note) {
        try {
            // Get the event to verify it exists
            Event event = getEventById(eventId);
            if (event == null) {
                return new RegistrationResult(false, "Event not found");
            }

            // Check if this email is already registered for this event
            if (eventDAO.isEmailRegisteredForEvent(email, eventId)) {
                return new RegistrationResult(false, "This email is already registered for this event");
            }

            // If all checks pass, proceed with guest registration
            boolean success = eventDAO.registerGuestForEvent(email, fullName, mobile, eventId, note);
            if (success) {
                return new RegistrationResult(true, "Registration successful! You have been registered for this event.");
            } else {
                return new RegistrationResult(false, "Registration failed. Please try again later");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RegistrationResult(false, "An error occurred: " + e.getMessage());
        }
    }
    public int countPublicEventsByClubId(Integer clubId, String searchQuery) {
        return eventDAO.countPublicEventsByClubId(clubId, searchQuery);
    }

    public boolean isUserMemberOfEventClub(int userId, int eventId) {
        return eventDAO.isUserMemberOfEventClub(userId, eventId);
    }

}
