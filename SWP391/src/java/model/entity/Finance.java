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
public class Finance {

    private int financeId;
    private String finance_name;
    private int clubId;
    private int eventId;
    private String eventName;
    private int memberId;
    private String memberName;
    private int approverId;
    private String approverName;
    private String isExpense;
    private double amount;
    private String status;
    private String financeDate;
    private String last_updated;
    private String details;

    public Finance() {
    }

    public Finance(int financeId, String finance_name, int clubId, int eventId, String eventName, int memberId, String memberName, int approverId, String approverName, String isExpense, double amount, String status, String financeDate, String last_updated, String details) {
        this.financeId = financeId;
        this.finance_name = finance_name;
        this.clubId = clubId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.memberId = memberId;
        this.memberName = memberName;
        this.approverId = approverId;
        this.approverName = approverName;
        this.isExpense = isExpense;
        this.amount = amount;
        this.status = status;
        this.financeDate = financeDate;
        this.last_updated = last_updated;
        this.details = details;
    }

    public Finance(int financeId, String finance_name, int clubId, int eventId, int memberId, int approverId, String isExpense, double amount, String status, String financeDate, String last_updated, String details) {
        this.financeId = financeId;
        this.finance_name = finance_name;
        this.clubId = clubId;
        this.eventId = eventId;
        this.memberId = memberId;
        this.approverId = approverId;
        this.isExpense = isExpense;
        this.amount = amount;
        this.status = status;
        this.financeDate = financeDate;
        this.last_updated = last_updated;
        this.details = details;
    }
    
    public Finance(int financeId, String finance_name, int clubId, int eventId, int memberId, int approverId, String isExpense, double amount, String status, String financeDate, String details) {
        this.financeId = financeId;
        this.finance_name = finance_name;
        this.clubId = clubId;
        this.eventId = eventId;
        this.memberId = memberId;
        this.approverId = approverId;
        this.isExpense = isExpense;
        this.amount = amount;
        this.status = status;
        this.financeDate = financeDate;
        this.details = details;
    }

    public String getFinance_name() {
        return finance_name;
    }

    public void setFinance_name(String finance_name) {
        this.finance_name = finance_name;
    }

    public int getFinanceId() {
        return financeId;
    }

    public void setFinanceId(int financeId) {
        this.financeId = financeId;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getIsExpense() {
        return isExpense;
    }

    public void setIsExpense(String isExpense) {
        this.isExpense = isExpense;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinanceDate() {
        return financeDate;
    }

    public void setFinanceDate(String financeDate) {
        this.financeDate = financeDate;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Finance{" + "financeId=" + financeId + ", finance_name=" + finance_name + ", clubId=" + clubId + ", eventId=" + eventId + ", eventName=" + eventName + ", memberId=" + memberId + ", memberName=" + memberName + ", approverId=" + approverId + ", approverName=" + approverName + ", isExpense=" + isExpense + ", amount=" + amount + ", status=" + status + ", financeDate=" + financeDate + ", last_updated=" + last_updated + ", details=" + details + '}';
    }

    
}
