package model.entity;

public class ClubMember {

    private int club_id;
    private int user_id;
    private int role_id;
    private String status;

    private String club_name;
    private String fullname;
    private String note;

    // Constructors
    public ClubMember() {
    }

    public ClubMember(int club_id, int user_id, int role_id, String status) {
        this.club_id = club_id;
        this.user_id = user_id;
        this.role_id = role_id;
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    // Getters and Setters
    public int getClubId() {
        return club_id;
    }

    public void setClubId(int club_id) {
        this.club_id = club_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getRoleId() {
        return role_id;
    }

    public void setRoleId(int role_id) {
        this.role_id = role_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
