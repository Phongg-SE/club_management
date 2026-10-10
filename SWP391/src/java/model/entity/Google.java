/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author pc
 */
public class Google {

    private String id, email, name, firstName, givenName, picture, gender;

    public Google() {
    }

    public Google(String id, String email, String name, String firstName, String givenName, String picture, String gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.firstName = firstName;
        this.givenName = givenName;
        this.picture = picture;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Google{" + "id=" + id + ", email=" + email + ", name=" + name + ", firstName=" + firstName + ", givenName=" + givenName + ", picture=" + picture + ", gender=" + gender + '}';
    }
}
