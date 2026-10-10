/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.util.Date;

/**
 *
 * @author pc
 */
public class Task {

    private int taskId;
    private int eventId;
    private int clubId;
    private String title;
    private int assignerId;
    private int assigneeId;
    private Date deadline;
    private String status;
    private String description;
    private String assigneeName, assigneeEmail, assigneeRole, assignee_avatar;
    private String assignerName, assignerEmail, assignerRole, assigner_avatar;
    private String eventname;
    private String clubname;
    private String note;
    private String type;
    
    public Task() {
    }

    public Task(int taskId, int eventId, int clubId, String title, int assignerId, int assigneeId, Date deadline, String status, String description) {
        this.taskId = taskId;
        this.eventId = eventId;
        this.clubId = clubId;
        this.title = title;
        this.assignerId = assignerId;
        this.assigneeId = assigneeId;
        this.deadline = deadline;
        this.status = status;
        this.description = description;
    }

    public String getAssignee_avatar() {
        return assignee_avatar;
    }

    public void setAssignee_avatar(String assignee_avatar) {
        this.assignee_avatar = assignee_avatar;
    }

    public String getAssigner_avatar() {
        return assigner_avatar;
    }

    public void setAssigner_avatar(String assigner_avatar) {
        this.assigner_avatar = assigner_avatar;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    public String getAssigneeRole() {
        return assigneeRole;
    }

    public void setAssigneeRole(String assigneeRole) {
        this.assigneeRole = assigneeRole;
    }

    public String getAssignerName() {
        return assignerName;
    }

    public void setAssignerName(String assignerName) {
        this.assignerName = assignerName;
    }

    public String getAssignerEmail() {
        return assignerEmail;
    }

    public void setAssignerEmail(String assignerEmail) {
        this.assignerEmail = assignerEmail;
    }

    public String getAssignerRole() {
        return assignerRole;
    }

    public void setAssignerRole(String assignerRole) {
        this.assignerRole = assignerRole;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(int assignerId) {
        this.assignerId = assignerId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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

}
