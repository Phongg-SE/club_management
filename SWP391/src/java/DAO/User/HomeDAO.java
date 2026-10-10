/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.User;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import model.dto.DBContext;
import model.entity.Club;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entity.Event;
import model.entity.Post;

/**
 *
 * @author datkh
 */
public class HomeDAO {

    Connection con = DBContext.getJDBCConnection();

    public List<Club> getTopClubs() {
        List<Club> clubList = new ArrayList<>();
        String sql = "SELECT c.club_id, c.name, c.slogan, c.club_email, c.club_logo_url, c.club_fanpage_url, "
                + "COUNT(DISTINCT cm.user_id) AS member_count "
                + "FROM club c "
                + "JOIN club_member cm ON c.club_id = cm.club_id "
                + "GROUP BY c.club_id, c.name, c.slogan, c.club_email, c.club_logo_url, c.club_fanpage_url "
                + "ORDER BY member_count DESC "
                + "LIMIT 6";

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Club club = new Club();
                club.setClub_id(rs.getInt("club_id"));
                club.setName(rs.getString("name"));
                club.setSlogan(rs.getString("slogan"));
                club.setClub_email(rs.getString("club_email"));
                club.setClub_logo_url(rs.getString("club_logo_url"));
                club.setClub_fanpage_url(rs.getString("club_fanpage_url"));
                club.setMemberCount(rs.getInt("member_count"));

                clubList.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubList;
    }

    public List<Event> getRecentEventUpcomingEvent() {
        List<Event> eventList = new ArrayList<>();
        String sql = "(SELECT event_id, club_id, leader_id, name, avatar_url, guest, location, start_time, end_time, "
                + "created_date, is_public, status, description "
                + "FROM event "
                + "WHERE start_time > NOW() AND is_public ='Yes' AND type = 'Event' "
                + "ORDER BY start_time ASC "
                + "LIMIT 3)";

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event event = new Event();
                event.setEvent_id(rs.getInt("event_id"));
                event.setClub_id(rs.getInt("club_id"));
                event.setLeader_id(rs.getInt("leader_id"));
                event.setName(rs.getString("name"));
                event.setAvatar_url(rs.getString("avatar_url"));
                event.setGuest(rs.getString("guest"));
                event.setLocation(rs.getString("location"));
                event.setStart_time(rs.getString("start_time"));
                event.setEnd_time(rs.getString("end_time"));
                event.setCreated_date(rs.getString("created_date"));
                event.setIs_public(rs.getString("is_public"));
                event.setStatus(rs.getString("status"));
                event.setDescription(rs.getString("description"));

                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    public int CountActiveClub() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM swp391.club WHERE status = 'Active'";

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int CountActiveEvent() {
        int count = 0;
        String sql = "SELECT count(*) FROM swp391.event where type = 'Event' ";

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int CountUser() {
        int count = 0;
        String sql = "SELECT Count(*) FROM swp391.user ";

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<Post> getRecentPosts() {
        List<Post> postList = new ArrayList<>();
        String query = "SELECT p.post_id, p.user_id, p.club_id, p.image_url, p.tittle, "
                + "p.status, p.description, p.post_at, u.full_name, s.value AS category, c.name AS club_name "
                + "FROM post p "
                + "JOIN user u ON p.user_id = u.user_id "
                + "LEFT JOIN setting s ON p.category_id = s.setting_id "
                + "LEFT JOIN club c ON p.club_id = c.club_id "
                + "WHERE p.status = 'Published' "
                + "ORDER BY p.post_at DESC " // Sắp xếp bài viết theo thời gian đăng mới nhất
                + "LIMIT 5";  // Chỉ lấy 5 bài mới nhất

        try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getInt("user_id"));
                post.setClubId(rs.getInt("club_id"));
                post.setImageUrl(rs.getString("image_url"));
                post.setTitle(rs.getString("tittle"));
                post.setStatus(rs.getString("status"));
                post.setDescription(rs.getString("description"));
                post.setPostedAt(rs.getTimestamp("post_at"));
                post.setAuthor(rs.getString("full_name"));
                post.setCategory(rs.getString("category"));
                post.setClubName(rs.getString("club_name"));
                postList.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public static void main(String[] args) {
        HomeDAO postDAO = new HomeDAO();  // Tạo đối tượng DAO
        List<Post> recentPosts = postDAO.getRecentPosts(); // Gọi phương thức lấy 5 bài viết mới nhất

        if (recentPosts.isEmpty()) {
            System.out.println("Không có bài viết nào.");
        } else {
            System.out.println("Top 5 bài viết gần đây:");
            for (Post post : recentPosts) {
                System.out.println("Post ID: " + post.getPostId());
                System.out.println("Title: " + post.getTitle());
                System.out.println("Author: " + post.getAuthor());
                System.out.println("Category: " + post.getCategory());
                System.out.println("Club: " + post.getClubName());
                System.out.println("Posted At: " + post.getPostedAt());
                System.out.println("Imaged At: " + post.getImageUrl());

                System.out.println("--------------------------------------");
            }
        }
    }

}
