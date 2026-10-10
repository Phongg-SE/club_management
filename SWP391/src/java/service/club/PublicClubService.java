/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.club;

import DAO.Club.PublicClubDAO;
import java.util.List;
import model.entity.Club;
import java.sql.*;
import model.entity.Setting;

/**
 *
 * @author Dell
 */
public class PublicClubService {

    private PublicClubDAO publicclubDAO;

    public PublicClubService() {
        this.publicclubDAO = new PublicClubDAO();
    }

    public List<Club> listPublicClubByFilter(String search, int categoryId, int page) throws SQLException {
        return publicclubDAO.listPublicClubs(search, categoryId, page);
    }

    public int getTotalPages(String search, int categoryId) throws SQLException {
        return publicclubDAO.getTotalPages(search, categoryId);
    }

    public List<Setting> getAllCategories() {
        try {
            return publicclubDAO.getCategories();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }

}
