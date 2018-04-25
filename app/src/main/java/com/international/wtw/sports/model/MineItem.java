package com.international.wtw.sports.model;

import android.support.annotation.DrawableRes;

public class MineItem {

    public String name;

    @DrawableRes
    public int resId;

    public MineItem(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }
}
