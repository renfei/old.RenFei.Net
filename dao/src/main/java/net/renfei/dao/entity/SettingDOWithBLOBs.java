package net.renfei.dao.entity;

public class SettingDOWithBLOBs extends SettingDO {
    private String keys;

    private String values;

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys == null ? null : keys.trim();
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values == null ? null : values.trim();
    }
}