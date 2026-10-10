package model.entity;

public class Setting {

    private int setting_id;
    private String name;
    private int type_id;
    private String value;
    private int priority;
    private String status;
    private String description;

    // Constructors
    public Setting() {
    }

    public Setting(String name, int type_id, String value, int priority, String status, String description) {
        this.name = name;
        this.type_id = type_id;
        this.value = value;
        this.priority = priority;
        this.status = status;
        this.description = description;
    }

    public Setting(int setting_id, String name, int type_id, String value, int priority, String status, String description) {
        this.setting_id = setting_id;
        this.name = name;
        this.type_id = type_id;
        this.value = value;
        this.priority = priority;
        this.status = status;
        this.description = description;
    }
    
    public Setting(int setting_id, String value) {
        this.setting_id = setting_id;
        this.value = value;
    }

    public int getSetting_id() {
        return setting_id;
    }

    public void setSetting_id(int setting_id) {
        this.setting_id = setting_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    // Getters and Setters
    public int getSettingId() {
        return setting_id;
    }

    public void setSettingId(int setting_id) {
        this.setting_id = setting_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return type_id;
    }

    public void setTypeId(int type_id) {
        this.type_id = type_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
}
