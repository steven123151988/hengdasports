package com.international.wtw.sports.json;

/**
 * Created by XIAOYAN on 2017/11/3.
 */

public class AllOrderListBean {

    private int id;
    private String isNewRecord;
    private String lotterygamesId;
    private String total;
    private Double money;
    private String win;
    private String backwater;
    private int status;
    private int createdTime;
    private String createdDateTime;
    private String path;
    private String sum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(String isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getLotterygamesId() {
        return lotterygamesId;
    }

    public void setLotterygamesId(String lotterygamesId) {
        this.lotterygamesId = lotterygamesId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getBackwater() {
        return backwater;
    }

    public void setBackwater(String backwater) {
        this.backwater = backwater;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public AllOrderListBean() {
        super();
    }

    public AllOrderListBean(int id, String isNewRecord, String lotterygamesId, String total, Double money, String win, String backwater, int status, int createdTime, String createdDateTime, String path, String sum) {
        this.id = id;
        this.isNewRecord = isNewRecord;
        this.lotterygamesId = lotterygamesId;
        this.total = total;
        this.money = money;
        this.win = win;
        this.backwater = backwater;
        this.status = status;
        this.createdTime = createdTime;
        this.createdDateTime = createdDateTime;
        this.path = path;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "AllOrderListBean{" +
                "id=" + id +
                ", isNewRecord='" + isNewRecord + '\'' +
                ", lotterygamesId='" + lotterygamesId + '\'' +
                ", total='" + total + '\'' +
                ", money=" + money +
                ", win='" + win + '\'' +
                ", backwater='" + backwater + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", path='" + path + '\'' +
                ", sum='" + sum + '\'' +
                '}';
    }
}
