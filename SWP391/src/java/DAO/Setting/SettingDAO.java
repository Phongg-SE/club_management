package DAO.Setting;

import java.sql.Connection;
import model.entity.Setting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dto.DBContext;
import static model.dto.DBContext.getJDBCConnection;

public class SettingDAO extends DBContext {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public List<Setting> findAll() {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT setting_id, type_id, name, description, value, priority, status FROM setting";
        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                settings.add(getFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            System.out.println("Error finding settings: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return settings;
    }
    public List<Setting> findAllWithPagination(int page, int pageSize) {
    List<Setting> settings = new ArrayList<>();
    String sql = "SELECT setting_id, type_id, name, description, value, priority, status " +
                 "FROM setting LIMIT ? OFFSET ?";
    try {
        connection = getJDBCConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, pageSize);
        statement.setInt(2, (page - 1) * pageSize);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            settings.add(getFromResultSet(resultSet));
        }
    } catch (SQLException ex) {
        System.out.println("Error finding settings with pagination: " + ex.getMessage());
    } finally {
        closeResources();
    }
    return settings;
}

    public boolean delete(Setting setting) {
        String sql = "DELETE FROM setting WHERE setting_id = ?";
        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, setting.getSettingId());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            System.out.println("Error deleting setting: " + ex.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    public int insert(Setting setting) {
        String sql = "INSERT INTO setting (type_id, name, description, value, priority, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, setting.getTypeId());
            statement.setString(2, setting.getName());
            statement.setString(3, setting.getDescription());
            statement.setString(4, setting.getValue());
            statement.setInt(5, setting.getPriority());
            statement.setString(6, setting.getStatus());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating setting failed, no rows affected.");
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("Creating setting failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            System.out.println("Error inserting setting: " + ex.getMessage());
            return -1;
        } finally {
            closeResources();
        }
    }

    public boolean update(Setting setting) {
        String sql = "UPDATE setting SET type_id = ?, name = ?, description = ?, value = ?, priority = ?, status = ? WHERE setting_id = ?";
        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, setting.getTypeId());
            statement.setString(2, setting.getName());
            statement.setString(3, setting.getDescription());
            statement.setString(4, setting.getValue());
            statement.setInt(5, setting.getPriority());
            statement.setString(6, setting.getStatus());
            statement.setInt(7, setting.getSettingId());
            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException ex) {
            System.out.println("Error updating setting: " + ex.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    private Setting getFromResultSet(ResultSet rs) throws SQLException {
        return new Setting(
                rs.getInt("setting_id"),
                rs.getString("name"),
                rs.getInt("type_id"),
                rs.getString("value"),
                rs.getInt("priority"),
                rs.getString("status"),
                rs.getString("description")
        );
    }

    public List<Setting> findByType(int typeId) {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT setting_id, type_id, name, description, value, priority, status FROM setting WHERE type_id = ?";
        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, typeId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                settings.add(getFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            System.out.println("Error finding settings by type: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return settings;
    }

    public Setting findById(int id) {
        String sql = "SELECT setting_id, type_id, name, description, value, priority, status FROM setting WHERE setting_id = ?";
        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            System.out.println("Error finding setting by ID: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    public List<Setting> findSettingsWithFilters(String typeFilter, String statusFilter,
            String searchFilter, int page, int pageSize) {
        List<Setting> settings = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT setting_id, type_id, name, description, value, priority, status FROM setting WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (typeFilter != null && !typeFilter.isEmpty()) {
            sql.append("AND type_id = ? ");
            params.add(Integer.parseInt(typeFilter));
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append("AND status = ? ");
            params.add(statusFilter);
        }
        if (searchFilter != null && !searchFilter.trim().isEmpty()) {
            sql.append("AND (LOWER(name) LIKE ? OR LOWER(value) LIKE ?) ");
            String searchPattern = "%" + searchFilter.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
        }

        sql.append("ORDER BY setting_id LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                settings.add(getFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            System.out.println("Error finding filtered settings: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return settings;
    }

    public int getTotalFilteredSettings(String typeFilter, String statusFilter, String searchFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM setting WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (typeFilter != null && !typeFilter.isEmpty()) {
            sql.append("AND type_id = ? ");
            params.add(Integer.parseInt(typeFilter));
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append("AND status = ? ");
            params.add(statusFilter);
        }
        if (searchFilter != null && !searchFilter.trim().isEmpty()) {
            sql.append("AND (name LIKE ? OR description LIKE ?) ");
            String searchPattern = "%" + searchFilter.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
        }

        try {
            connection = getJDBCConnection();
            statement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error counting filtered settings: " + ex.getMessage());
        } finally {
            closeResources();
        }
        return 0;
    }

    public void closeResources() {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
