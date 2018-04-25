package com.international.wtw.sports.model;

public class FundingRecord {

    /**
     * id : 34
     * billcode : 1509505242786
     * orderStatus : 0
     * money : 10
     * remark : 会员提款
     * createdTime : 1445488920000
     * type : 1
     * status : 0
     */

    private int id;
    private String billcode;
    private int orderStatus;
    private Double money;
    private String remark;
    private long createdTime;
    private int type;//存取款类型 提款(1) 存款(0)
    //private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillcode() {
        return billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
/*
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }*/
}
