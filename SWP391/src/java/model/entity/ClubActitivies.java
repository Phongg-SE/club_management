/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author pc
 */
public class ClubActitivies {

    private int numberMember;
    private int numberEvent;
    private int numberProject;
    private double totalExpense;
    private double totalIncome;
    private int numberRegistration;
    private int numberRejection;

    public ClubActitivies() {
    }

    public ClubActitivies(int numberMember, int numberEvent, int numberProject, double totalExpense, double totalIncome, int numberRegistration, int numberRejection) {
        this.numberMember = numberMember;
        this.numberEvent = numberEvent;
        this.numberProject = numberProject;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.numberRegistration = numberRegistration;
        this.numberRejection = numberRejection;
    }

    public int getNumberMember() {
        return numberMember;
    }

    public void setNumberMember(int numberMember) {
        this.numberMember = numberMember;
    }

    public int getNumberEvent() {
        return numberEvent;
    }

    public void setNumberEvent(int numberEvent) {
        this.numberEvent = numberEvent;
    }

    public int getNumberProject() {
        return numberProject;
    }

    public void setNumberProject(int numberProject) {
        this.numberProject = numberProject;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getNumberRegistration() {
        return numberRegistration;
    }

    public void setNumberRegistration(int numberRegistration) {
        this.numberRegistration = numberRegistration;
    }

    public int getNumberRejection() {
        return numberRejection;
    }

    public void setNumberRejection(int numberRejection) {
        this.numberRejection = numberRejection;
    }

}
