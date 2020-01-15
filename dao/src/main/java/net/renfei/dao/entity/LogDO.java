package net.renfei.dao.entity;

import java.util.Date;

public class LogDO {
    private String uuid;

    private Date datetime;

    private String level;

    private String inorout;

    private String remoteIp;

    private String remoteUser;

    private String remoteAgent;

    private String requestUrl;

    private String requestMethod;

    private Integer statusCode;

    private String logValue;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getInorout() {
        return inorout;
    }

    public void setInorout(String inorout) {
        this.inorout = inorout == null ? null : inorout.trim();
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp == null ? null : remoteIp.trim();
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser == null ? null : remoteUser.trim();
    }

    public String getRemoteAgent() {
        return remoteAgent;
    }

    public void setRemoteAgent(String remoteAgent) {
        this.remoteAgent = remoteAgent == null ? null : remoteAgent.trim();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod == null ? null : requestMethod.trim();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getLogValue() {
        return logValue;
    }

    public void setLogValue(String logValue) {
        this.logValue = logValue == null ? null : logValue.trim();
    }
}