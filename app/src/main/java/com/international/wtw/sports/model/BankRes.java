package com.international.wtw.sports.model;


import android.support.annotation.DrawableRes;

public class BankRes {

    public String bankName;
    public String shortName;
    @DrawableRes
    public int logoRes;

    public BankRes(String bankName, String shortName, int logoRes) {
        this.bankName = bankName;
        this.shortName = shortName;
        this.logoRes = logoRes;
    }
}
