/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.task;

import DAO.Task.TaskDAO;
import model.entity.Task;
import java.util.List;
import model.entity.Club;
import model.entity.ClubMember;
import model.entity.Event;

/**
 *
 * @author datkh
 */
public class TaskService {

    private TaskDAO taskDAO;

    public TaskService() {
        this.taskDAO = new TaskDAO();
    }

    public List<Task> getUserTasks(int userId, int page, int pageSize) {
        return taskDAO.getUserTasks(userId, page, pageSize);
    }

    public Task getTaskWithTaskID(int taskid) {
        return taskDAO.getTaskByTaskId(taskid);
    }

    public List<ClubMember> getAllClubMembersByClubId(int club_id) {
        return taskDAO.getAllClubMembersByClubId(club_id);
    }

    public List<Event> getAllActiveEventByClubId(int club_id) {
        return taskDAO.getAllActiveEventByClubId(club_id);
    }

    public boolean addTask(Task task) {
        return taskDAO.addTask(task);
    }

    public boolean updateTask(Task task) {
        return taskDAO.updateTask(task);
    }

    public List<Event> getAllEventProjectByFieldName(int clubId, String fieldName) {
        return taskDAO.getAllEventProjectByFieldName(clubId, fieldName);
    }

    public List<Club> getClubIdByUserId(int userId) {
        return taskDAO.getClubIdByUserId(userId);
    }

    public List<Integer> getUserRolesInClub(int userId, int clubId) {
        return taskDAO.getUserRolesInClub(userId, clubId);
    }

    public boolean isUserEventLeaderOfEvent(int userid, int eventid) {
        return taskDAO.isUserEventLeaderOfEvent(userid, eventid);
    }

    public List<Integer> getClubsWhereUserIsLeader(int userId) {
        return taskDAO.getClubsWhereUserIsLeader(userId);
    }

    public List<ClubMember> getAllClubMembersByEventOrProjectId(int eventId, String field) {
        return taskDAO.getAllClubMembersByEventOrProjectId(eventId, field);
    }

    public List<Task> getTaskByFilter(int userId, String status, Integer clubId, Integer eventId, Integer assigneeId, String search, int page, int pageSize, boolean b) {
        return taskDAO.getTaskByFilter(userId, status, clubId, eventId, assigneeId, search, page, pageSize, b);
    }

    public List<Event> getAllEventProjectByFieldNames(int userId, int clubId, String fieldName) {
        return taskDAO.getAllEventProjectByFieldNames(userId, clubId, fieldName);
    }

    public int getTotalTask(int userId, String status, Integer clubId, Integer eventId, Integer assigneeId, String search, boolean isProject) {
        return taskDAO.getTotalTask(userId, status, clubId, eventId, assigneeId, search, isProject);
    }

}
