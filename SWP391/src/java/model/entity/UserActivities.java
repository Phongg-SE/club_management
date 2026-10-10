/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author pc
 */
public class UserActivities {

    private double myDonation;
    private int numberTask;
    private int doneTask;
    private int doingTask;
    private int assignedTask;
    private int eventParticipation;
    private int projectParticipation;

    public UserActivities() {
    }

    public UserActivities(double myDonation, int numberTask, int doneTask, int doingTask, int assignedTask, int eventParticipation, int projectParticipation) {
        this.myDonation = myDonation;
        this.numberTask = numberTask;
        this.doneTask = doneTask;
        this.doingTask = doingTask;
        this.assignedTask = assignedTask;
        this.eventParticipation = eventParticipation;
        this.projectParticipation = projectParticipation;
    }

    public double getMyDonation() {
        return myDonation;
    }

    public void setMyDonation(double myDonation) {
        this.myDonation = myDonation;
    }

    public int getNumberTask() {
        return numberTask;
    }

    public void setNumberTask(int numberTask) {
        this.numberTask = numberTask;
    }

    public int getDoneTask() {
        return doneTask;
    }

    public void setDoneTask(int doneTask) {
        this.doneTask = doneTask;
    }

    public int getDoingTask() {
        return doingTask;
    }

    public void setDoingTask(int doingTask) {
        this.doingTask = doingTask;
    }

    public int getAssignedTask() {
        return assignedTask;
    }

    public void setAssignedTask(int assignedTask) {
        this.assignedTask = assignedTask;
    }

    public int getEventParticipation() {
        return eventParticipation;
    }

    public void setEventParticipation(int eventParticipation) {
        this.eventParticipation = eventParticipation;
    }

    public int getProjectParticipation() {
        return projectParticipation;
    }

    public void setProjectParticipation(int projectParticipation) {
        this.projectParticipation = projectParticipation;
    }

}
