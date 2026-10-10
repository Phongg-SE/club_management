/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author pc
 */
public class UserRole{
    User user;
    int role_id;
    String role_name, status;

    public UserRole() {
    }

    public UserRole(User user, int role_id, String role_name, String status) {
        this.user = user;
        this.role_id = role_id;
        this.role_name = role_name;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserRole{" + "user=" + user + ", role_id=" + role_id + ", role_name=" + role_name + ", status=" + status + '}';
    }

   
    
}
