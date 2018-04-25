package com.international.wtw.lottery.model;

import java.io.Serializable;

public class Announcement implements Serializable {

    /**
     * isNewRecord : true
     * content : 公告1
     * updatetime : 1508557719
     * showupdatetime : 2017-10-21 03:48:39
     */

    private boolean isNewRecord;
    private String content;
    private int updatetime;
    private String showupdatetime;

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public String getShowupdatetime() {
        return showupdatetime;
    }

    public void setShowupdatetime(String showupdatetime) {
        this.showupdatetime = showupdatetime;
    }
}
