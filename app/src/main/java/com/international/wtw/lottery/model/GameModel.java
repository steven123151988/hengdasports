package com.international.wtw.lottery.model;

import android.support.annotation.DrawableRes;

public class GameModel {

    public int gameCode;

    public String gameName;

    @DrawableRes
    public int logoResId;

    public GameModel(int gameCode, String gameName, int logoResId) {
        this.gameCode = gameCode;
        this.gameName = gameName;
        this.logoResId = logoResId;
    }
}
