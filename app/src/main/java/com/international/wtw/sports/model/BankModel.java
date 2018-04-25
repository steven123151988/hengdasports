package com.international.wtw.sports.model;

import java.io.Serializable;

public class BankModel implements Serializable {

    /**
     * id : 20
     * isNewRecord : true
     * bankname : 工商银行
     * bankaccount : 622023100010011548
     * balance : 0.0
     */

    private String id;
    private boolean isNewRecord;
    private String bankname;
    private String bankaccount;
    private double balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
