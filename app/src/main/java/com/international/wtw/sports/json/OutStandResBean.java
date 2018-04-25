package com.international.wtw.sports.json;

import java.io.Serializable;

/**
 * Created by XIAOYAN on 2017/9/20.
 */

public class OutStandResBean implements Serializable {

    private double win_money;
    private String no;
    private String detail;
    private String game_name;
    private int money;
    private String count;
    private String game_code;
    private String round;
    private String rate;
    private String time;

    public double getWin_money() {
        return win_money;
    }

    public void setWin_money(double win_money) {
        this.win_money = win_money;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getGame_code() {
        return game_code;
    }

    public void setGame_code(String game_code) {
        this.game_code = game_code;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OutStandResBean() {
        super();
    }

    public OutStandResBean(double win_money, String no, String detail, String game_name, int money, String count, String game_code, String round, String rate, String time) {
        this.win_money = win_money;
        this.no = no;
        this.detail = detail;
        this.game_name = game_name;
        this.money = money;
        this.count = count;
        this.game_code = game_code;
        this.round = round;
        this.rate = rate;
        this.time = time;
    }

    @Override
    public String toString() {
        return "OutStandResBean{" +
                "win_money=" + win_money +
                ", no='" + no + '\'' +
                ", detail='" + detail + '\'' +
                ", game_name='" + game_name + '\'' +
                ", money=" + money +
                ", count='" + count + '\'' +
                ", game_code='" + game_code + '\'' +
                ", round='" + round + '\'' +
                ", rate='" + rate + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
