package model.entity;

import DAO.Club.ClubDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Club {

    private int club_id;
    private String name;
    private String slogan;
    private String club_email;
    private String club_fanpage_url;
    private String club_logo_url;
    private String is_public;
    private int category_id;
    private String status;
    private String description;
    //duy
    private String category_name;
    private String club_leader;
    private int leader_id;
    private int MemberCount;
    // Constructors
    public Club() {
    }

    public Club(int club_id, String name) {
        this.club_id = club_id;
        this.name = name;
    }

    // ✅ Constructor đầy đủ (Full)
    public Club(int club_id, String name, String slogan, String club_email,
            String club_fanpage_url, String club_logo_url, String is_public,
            int category_id, String status, String description) {
        this.club_id = club_id;
        this.name = name;
        this.slogan = slogan;
        this.club_email = club_email;
        this.club_fanpage_url = club_fanpage_url;
        this.club_logo_url = club_logo_url;
        this.is_public = is_public;
        this.category_id = category_id;
        this.status = status;
        this.description = description;
    }

    public Club(String name, String club_logo_url, String is_public, int category_id, String status) {
        this.name = name;
        this.club_logo_url = club_logo_url;
        this.is_public = is_public;
        this.category_id = category_id;
        this.status = status;
    }

    public Club(int clubId, String name, String slogan, String clubEmail, String clubFanpageUrl,
            String clubLogoUrl, String isPublic, int categoryId, String status, String description,
            String categoryValue, String leaderName) {
        this.club_id = clubId;
        this.name = name;
        this.slogan = slogan;
        this.club_email = clubEmail;
        this.club_fanpage_url = clubFanpageUrl;
        this.club_logo_url = clubLogoUrl;
        this.is_public = isPublic;
        this.category_id = categoryId;
        this.status = status;
        this.description = description;
        this.category_name = categoryValue;
        this.club_leader = leaderName;
    }

    public Club(int club_id, String name, String slogan, String club_email, String club_fanpage_url, String club_logo_url, String is_public, int category_id, String status, String description, String category_name, String club_leader, int leader_id) {
        this.club_id = club_id;
        this.name = name;
        this.slogan = slogan;
        this.club_email = club_email;
        this.club_fanpage_url = club_fanpage_url;
        this.club_logo_url = club_logo_url;
        this.is_public = is_public;
        this.category_id = category_id;
        this.status = status;
        this.description = description;
        this.category_name = category_name;
        this.club_leader = club_leader;
        this.leader_id = leader_id;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    public String getClub_leader() {
        return club_leader;
    }

    public void setClub_leader(String club_leader) {
        this.club_leader = club_leader;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getClub_email() {
        return club_email;
    }

    public void setClub_email(String club_email) {
        this.club_email = club_email;
    }

    public String getClub_fanpage_url() {
        return club_fanpage_url;
    }

    public void setClub_fanpage_url(String club_fanpage_url) {
        this.club_fanpage_url = club_fanpage_url;
    }

    public String getClub_logo_url() {
        return club_logo_url;
    }

    public void setClub_logo_url(String club_logo_url) {
        this.club_logo_url = club_logo_url;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(int MemberCount) {
        this.MemberCount = MemberCount;
    }
    
    

    // Duy
    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Club club = (Club) o;
        return club_id == club.club_id;
    }
}
