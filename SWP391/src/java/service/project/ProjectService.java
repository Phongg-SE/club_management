/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.project;

import DAO.Project.ProjectDAO;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Event;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import model.entity.ProjectMember;
import model.entity.User;

public class ProjectService {

    private ProjectDAO projectDAO;

    public ProjectService() {
        this.projectDAO = new ProjectDAO();
    }

    public List<Event> getProjectByClubId(int clubID) {
        try {
            return projectDAO.getProjectsByClubId(clubID);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean addProject(Event event) {
        if (projectDAO.isEventExist(event.getName(), event.getClub_id())) {
            return false; // Nếu sự kiện đã tồn tại, không thêm mới
        }
        return projectDAO.addProject(event);
    }

    public void autoUpdate() {
        // Tạo ScheduledExecutorService để chạy task mỗi 5 phút
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
        Runnable task = () -> {
            try {
                projectDAO.updateEventStatusAutomatically();
                //projectDAO.updateMemberRolesAutomatically();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        // Chạy task ngay lập tức và lặp lại mỗi 1 phút
        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.MINUTES);
    }

    public List<Integer> getClubIdsByUserId(int userId) throws SQLException {
        return projectDAO.getClubIdsByUserId(userId);
    }

    public boolean isClubLeader(int userId) {
        return projectDAO.isClubLeader(userId);
    }

    public List<Event> listProjectsByFilters(int clubId, String status, String searchName, int page) throws SQLException {
        return projectDAO.listProjectByFilter(clubId, status, searchName, page);
    }

    public int getTotalPages(int clubId, String status, String searchName) throws SQLException {
        return projectDAO.getTotalPages(clubId, status, searchName);
    }

    public List<User> getAllEventLeaderByClubID(int clubId) {
        try {
            return projectDAO.getAllEventLeaderByClubID(clubId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<User> getAllProjectLeaderByClubID(int clubId) {
        try {
            return projectDAO.getAllProjectLeaderByClubID(clubId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean isProjectLeader(int userId, int clubId) {
        return projectDAO.isProjectLeader(userId, clubId);
    }

    //nam
    public Event getProjectDetail(int eventID) {
        try {
            return projectDAO.getProjectDetail(eventID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateProject(Event event) {
        try {
            return projectDAO.updateProject(event);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ProjectMember> getProjectMembers(int eventId) {
        return projectDAO.getProjectMembers(eventId);
    }

    public boolean deleteProjectMember(int memberId) {
        return projectDAO.deleteProjectMember(memberId);
    }

    public Map<String, String> validateProject(Event event) {
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra tên
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            errors.put("name", "Tên dự án không được để trống.");
        } else if (event.getName().length() > 50) {
            errors.put("name", "Tên dự án không được quá 50 ký tự.");
        }

        // Kiểm tra mô tả
        if (event.getDescription() == null || event.getDescription().trim().isEmpty()) {
            errors.put("description", "Mô tả không được để trống.");
        }

        // Kiểm tra thời gian
        if (event.getStart_time() == null || event.getEnd_time() == null) {
            errors.put("start_time", "Thời gian không hợp lệ.");
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startTime = dateFormat.parse(event.getStart_time());
                Date endTime = dateFormat.parse(event.getEnd_time());

                if (startTime.after(endTime)) {
                    errors.put("start_time", "Start time phải nhỏ hơn End time.");
                }
            } catch (ParseException e) {
                errors.put("start_time", "Định dạng ngày không hợp lệ.");
            }
        }

        return errors;
    }

    public List<User> getClubMembers(int clubId) {
        return projectDAO.listClubMembers(clubId);
    }

    public boolean addMemberToProject(int projectId, int userId) throws SQLException {
        ProjectDAO projectDAO = new ProjectDAO();
        return projectDAO.addMemberToProject(projectId, userId);
    }

    public List<User> getAvailableClubMembers(int clubId, int id) throws SQLException {
        return projectDAO.getAvailableClubMembers(clubId, id);
    }

}
