/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import DAO.User.HomeDAO;
import java.util.List;
import model.entity.Club;
import model.entity.Event;
import model.entity.Post;

/**
 *
 * @author datkh
 */
public class HomeService {

    private HomeDAO homeDAO = new HomeDAO();

    public HomeService() {
    }

    public List<Club> getTopClubs() {
        return homeDAO.getTopClubs();
    }

    public List<Event> getRecentEventUpcomingEvent() {
        return homeDAO.getRecentEventUpcomingEvent();
    }

    public int CountActiveClub() {
        return homeDAO.CountActiveClub();
    }

    public int CountUser() {
        return homeDAO.CountUser();
    }

    public int CountActiveEvent() {
        return homeDAO.CountActiveEvent();
    }

    public List<Post> getRecentPosts() {
        return homeDAO.getRecentPosts();
    }

}
