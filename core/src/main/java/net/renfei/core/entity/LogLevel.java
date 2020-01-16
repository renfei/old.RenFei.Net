package net.renfei.core.entity;

public enum LogLevel {
    ACCESS("ACCESS"), DEBUG("DEBUG"),

    INFO("INFO"), WARN("WARN"),

    ERROR("ERROR"), FATAL("FATAL"),
    WECHAT("WECHAT");

    private String level;

    private LogLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
