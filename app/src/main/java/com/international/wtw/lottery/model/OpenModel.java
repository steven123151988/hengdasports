package com.international.wtw.lottery.model;

public class OpenModel {


    /**
     * isNewRecord : true
     * round : 20171005-006
     * number : 1,2,3,4,5,6,7,8,9,10,11,22,33,44,55,66,77,12,13,14
     */

    private boolean isNewRecord;
    private String round;
    private String number;
    private int lotterygamesId;
    private String openTime;
    private String sysTime;
    private Integer isClose;

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getLotterygamesId() {
        return lotterygamesId;
    }

    public void setLotterygamesId(int lotterygamesId) {
        this.lotterygamesId = lotterygamesId;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public Integer getIsClose() {
        return isClose;
    }

    public void setIsClose(Integer isClose) {
        this.isClose = isClose;
    }
}
