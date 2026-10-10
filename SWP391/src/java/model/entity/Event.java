package model.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Event {

    private int event_id;
    private int club_id;
    private int leader_id;
    private String leader_name;
    private String name;
    private String avatar_url;
    private String guest;
    private String location;
    private String start_time;
    private String end_time;
    private String created_date;
    private String is_public;
    private String status;
    private String description;
    private Club club;
    private User user;
    private int total_member;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event() {
    }

    public Event(int event_id, int club_id, int leader_id, String name, String avatar_url, String guest, String location, String start_time, String end_time, String created_date, String is_public, String status, String description) {
        this.event_id = event_id;
        this.club_id = club_id;
        this.leader_id = leader_id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_date = created_date;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
    }

    public Event(int event_id, int club_id, int leader_id, String leader_name, String name, String avatar_url, String guest, String location, String start_time, String end_time, String created_date, String is_public, String status, String description) {
        this.event_id = event_id;
        this.club_id = club_id;
        this.leader_id = leader_id;
        this.leader_name = leader_name;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_date = created_date;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
    }

    public Event(int event_id, int club_id, int leader_id, String leader_name, String name, String guest, String location, String start_time, String end_time, String created_date, String is_public, String status, String description, int total_member) {
        this.event_id = event_id;
        this.club_id = club_id;
        this.leader_id = leader_id;
        this.leader_name = leader_name;
        this.name = name;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_date = created_date;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
        this.total_member = total_member;
    }

    public Event(int event_id, int leader_id, String name, String avatar_url, String guest, String location, String start_time, String end_time, String created_date, String is_public, String status, String description) {
        this.event_id = event_id;
        this.leader_id = leader_id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_date = created_date;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
    }

    public Event(int event_id, int leader_id, String leader_name, String name, String avatar_url, String guest, String location, String start_time, String end_time, String created_date, String is_public, String status, String description) {
        this.event_id = event_id;
        this.leader_id = leader_id;
        this.leader_name = leader_name;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_date = created_date;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
    }

    public Event(int event_id, int club_id, int leader_id, String name, String avatar_url, String guest, String location, String start_time, String end_time, String is_public, String status, String description) {
        this.event_id = event_id;
        this.club_id = club_id;
        this.leader_id = leader_id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
    }

    public Event(int event_id, int club_id, int leader_id, String name, String avatar_url, String guest, String location, String start_time, String end_time, String created_date, String is_public, String status, String description, Club club) {
        this.event_id = event_id;
        this.club_id = club_id;
        this.leader_id = leader_id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_date = created_date;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
        this.club = club;
    }

    public Event(int event_id, int club_id, int leader_id, String leader_name, String name, String avatar_url, String guest, String location, String start_time, String end_time, String created_date, String is_public, String status, String description, int total_member) {
        this.event_id = event_id;
        this.club_id = club_id;
        this.leader_id = leader_id;
        this.leader_name = leader_name;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_date = created_date;
        this.is_public = is_public;
        this.status = status;
        this.description = description;
        this.total_member = total_member;
    }


    public Event(int eventId, String name, int leaderId, String leaderName,
            String startTime, String endTime, String status, String description,
            String createdDate, String location) { // Thêm vào constructor
        this.event_id = eventId;
        this.name = name;
        this.leader_id = leaderId;
        this.leader_name = leaderName;
        this.start_time = startTime;
        this.end_time = endTime;
        this.status = status;
        this.description = description;
        this.created_date = createdDate;
        this.location = location;
    }

    public Event(int eventId, int clubId, String name, int leaderId, String leaderName,
            String startTime, String endTime, String status,
            String description, String createdDate, String location) {
        this.event_id = eventId;
        this.club_id = clubId;
        this.name = name;
        this.leader_id = leaderId;
        this.leader_name = leaderName;
        this.start_time = startTime;
        this.end_time = endTime;
        this.status = status;
        this.description = description;
        this.created_date = createdDate;
        this.location = location;
    }

    public Event(int event_id, int club_id, String type, int leader_id, String name, String avatar_url, String guest, String location, String start_time, String end_time, String is_public, String status, String description) {
        this.event_id = event_id;
        this.club_id = club_id;
        this.type = type;
        this.leader_id = leader_id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.guest = guest;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.is_public = is_public;
        this.status = status;
        this.description = description;

    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getTotal_member() {
        return total_member;
    }

    public void setTotal_member(int total_member) {
        this.total_member = total_member;
    }

    public String getEventDate() {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd"); // Lấy ngày
            Date date = originalFormat.parse(start_time);
            return dateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public String getEventMonth() {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM"); // Lấy tháng
            Date date = originalFormat.parse(start_time);
            return monthFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public String getShortDescription(int wordLimit) {
        if (description == null) {
            return "";
        }
        String[] words = description.split("\\s+"); // Tách chuỗi theo khoảng trắng
        if (words.length <= wordLimit) {
            return description; // Nếu số từ nhỏ hơn giới hạn thì trả về toàn bộ
        }
        return String.join(" ", Arrays.copyOfRange(words, 0, wordLimit)) + "..."; // Ghép lại số từ giới hạn
    }

}
