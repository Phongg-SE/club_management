/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.util.List;

/**
 *
 * @author pc
 */
public class User {

    int user_id;
    String full_name, email, username, password, gender, avatar_url;
    String phone_number;
    int role_id;
    String value;
    String date_of_birth, city, district, address, facebook, instargram;
    String role_name;
    String status;
    int event_id;
    List<Integer> eventIds;
    int member_id;

    private List<String> role_names;
    private List<Integer> role_ids;

    public List<String> getRole_names() {
        return role_names;
    }

    public void setRole_names(List<String> role_names) {
        this.role_names = role_names;
    }

    public List<Integer> getRole_ids() {
        return role_ids;
    }

    public void setRole_ids(List<Integer> role_ids) {
        this.role_ids = role_ids;
    }

    public User(int user_id, String full_name, String email, String username, String password, String gender, String avatar_url, String phone_number, int role_id, String city, String district, String address, String facebook, String instargram, String status, List<String> role_names, List<Integer> role_ids) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.phone_number = phone_number;
        this.role_id = role_id;
        this.city = city;
        this.district = district;
        this.address = address;
        this.facebook = facebook;
        this.instargram = instargram;
        this.status = status;
        this.role_names = role_names;
        this.role_ids = role_ids;
    }

    public User() {
    }

    public User(int user_id, String full_name, String email, String username, String password, String gender, String avatar_url, String phone_number) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.phone_number = phone_number;
    }

    public User(int user_id, String full_name, String email, String username, String password, String gender, String avatar_url, String phone_number, int role_id, String value) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.phone_number = phone_number;
        this.role_id = role_id;
        this.value = value;
    }

    public User(int user_id, String full_name, String email, String username, String password, String gender, String avatar_url, String phone_number, int role_id, String value, String date_of_birth, String city, String district, String address, String facebook, String instargram) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.phone_number = phone_number;
        this.role_id = role_id;
        this.value = value;
        this.date_of_birth = date_of_birth;
        this.city = city;
        this.district = district;
        this.address = address;
        this.facebook = facebook;
        this.instargram = instargram;
    }

    public User(int user_id, String full_name, String email, String username, String password, String gender, String avatar_url, String phone_number, String date_of_birth, String city, String district, String address, String facebook, String instargram) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.city = city;
        this.district = district;
        this.address = address;
        this.facebook = facebook;
        this.instargram = instargram;
    }

    public User(int user_id, String full_name, String email, String username, String gender, String avatar_url, String phone_number, int role_id, String value, String status) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.phone_number = phone_number;
        this.role_id = role_id;
        this.value = value;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstargram() {
        return instargram;
    }

    public void setInstargram(String instargram) {
        this.instargram = instargram;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public List<Integer> getEventIds() {
        return eventIds;
    }

    public void setEventIds(List<Integer> eventIds) {
        this.eventIds = eventIds;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", full_name=" + full_name + ", email=" + email + ", username=" + username + ", password=" + password + ", gender=" + gender + ", avatar_url=" + avatar_url + ", phone_number=" + phone_number + ", role_id=" + role_id + ", value=" + value + ", date_of_birth=" + date_of_birth + ", city=" + city + ", district=" + district + ", address=" + address + ", facebook=" + facebook + ", instargram=" + instargram + ", role_name=" + role_name + '}';
    }

}
