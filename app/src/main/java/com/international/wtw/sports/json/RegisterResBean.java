package com.international.wtw.sports.json;

/**
 * Created by XIAOYAN on 2017/11/3.
 */

public class RegisterResBean {

    private String id;
    private boolean isNewRecord;
    private String username;
    private String sessionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public RegisterResBean(String id, boolean isNewRecord, String username, String sessionId) {
        this.id = id;
        this.isNewRecord = isNewRecord;
        this.username = username;
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "RegisterResBean{" +
                "id='" + id + '\'' +
                ", isNewRecord=" + isNewRecord +
                ", username='" + username + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
