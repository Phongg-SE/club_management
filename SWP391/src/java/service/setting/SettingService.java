/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.setting;

import DAO.Setting.SettingDAO;
import model.entity.Setting;
import java.util.List;

public class SettingService {

    private SettingDAO settingDAO;

    public SettingService() {
        settingDAO = new SettingDAO();
    }

    public List<Setting> findAll() {
        return settingDAO.findAll();
    }

    public List<Setting> findSettingsWithFilters(String typeFilter, String statusFilter,
            String searchFilter, int page, int pageSize) {
        return settingDAO.findSettingsWithFilters(typeFilter, statusFilter, searchFilter, page, pageSize);
    }

    public List<Setting> findAllWithPagination(int page, int pageSize) {
        return settingDAO.findAllWithPagination(page, pageSize);
    }

    public int getTotalFilteredSettings(String typeFilter, String statusFilter, String searchFilter) {
        return settingDAO.getTotalFilteredSettings(typeFilter, statusFilter, searchFilter);
    }

    public Setting findById(int id) {
        return settingDAO.findById(id);
    }

    public boolean updateSetting(Setting setting) {
        return settingDAO.update(setting);
    }

    public boolean deactivateSetting(int settingId) {
        try {
            Setting setting = settingDAO.findById(settingId);
            if (setting != null) {
                setting.setStatus("Inactive");
                return settingDAO.update(setting);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSetting(Setting setting) throws Exception {
        // Validate required fields
        if (setting.getTypeId() <= 0
                || setting.getName() == null || setting.getName().trim().isEmpty()
                || setting.getDescription() == null || setting.getDescription().trim().isEmpty()
                || setting.getValue() == null || setting.getValue().trim().isEmpty()
                || setting.getPriority() <= 0 || setting.getStatus() == null) {
            throw new Exception("Invalid input values");
        }

        // Kiểm tra trùng lặp value
        List<Setting> settingList = settingDAO.findAll();
        for (Setting existingSetting : settingList) {
            if (setting.getValue().equalsIgnoreCase(existingSetting.getValue())) {
                throw new Exception("Value already exists");
            }
        }

        // Set default status if not provided
        if (setting.getStatus().trim().isEmpty()) {
            setting.setStatus("Active");
        }

        return settingDAO.insert(setting) > 0;
    }

    public boolean activateSetting(int settingId) {
        try {
            Setting setting = settingDAO.findById(settingId);
            if (setting != null) {
                setting.setStatus("Active");
                return settingDAO.update(setting);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Setting> findByType(int typeId) {
        return settingDAO.findByType(typeId);
    }

    public boolean isFilted(String typeFilter, String statusFilter, String searchFilter) {
        if (typeFilter != null || statusFilter != null || searchFilter != null) {
            return true;
        } else {
            return false;
        }
    }
    // Hàm kiểm tra trùng lặp value của update 
    public boolean isDuplicateValue(int settingId, String value) {
        List<Setting> settingList = settingDAO.findAll();
        for (Setting existingSetting : settingList) {
            if (existingSetting.getSettingId() != settingId &&
                value.equalsIgnoreCase(existingSetting.getValue())) {
                return true; // Value bị trùng
            }
        }
        return false; // Không trùng
    }
    public boolean isSettingExist(Setting setting) {
        return setting != null;
    }
}
