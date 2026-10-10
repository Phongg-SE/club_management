/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author pc
 */
public class RegistrationEvent {

    private int registration_id;
    private int event_id;
    private int register_id;
    private String full_name;
    private String email;
    private String mobile;
    private String status;
    private String note;
    private String eventName;
    private String clubName;
    private String eventStatus;
    private int club_Id;

    public int getClub_Id() {
        return club_Id;
    }

    public void setClub_Id(int club_Id) {
        this.club_Id = club_Id;
    }
    
    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
    
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public RegistrationEvent(int registration_id, int event_id, int register_id, String full_name, String email, String mobile, String status, String note, String eventName) {
        this.registration_id = registration_id;
        this.event_id = event_id;
        this.register_id = register_id;
        this.full_name = full_name;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.note = note;
        this.eventName = eventName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Default constructor
    public RegistrationEvent() {
    }

    // Parameterized constructor
    public RegistrationEvent(int registration_id, int event_id, int register_id, String full_name,
            String email, String mobile, String status) {
        this.registration_id = registration_id;
        this.event_id = event_id;
        this.register_id = register_id;
        this.full_name = full_name;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
    }

    // Getters and Setters
    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getRegister_id() {
        return register_id;
    }

    public void setRegister_id(int register_id) {
        this.register_id = register_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }
public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RegistrationEvent{" + "registration_id=" + registration_id + ", event_id=" + event_id + ", register_id=" + register_id + ", full_name=" + full_name + ", email=" + email + ", mobile=" + mobile + ", status=" + status + '}';
    }

}