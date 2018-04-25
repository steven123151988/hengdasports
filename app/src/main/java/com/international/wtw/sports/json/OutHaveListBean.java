package com.international.wtw.sports.json;

/**
 * Created by XIAOYAN on 2017/11/6.
 */

public class OutHaveListBean {

    private String id;
    private boolean isNewRecord;
    private String lotterygamesId;
    private String round;
    private int total;
    private Double money;
    private Double trueWin;
    private Double win;
    private String rate;
    private int createdTime;
    private String games;
    private String gamesPlay1;
    private String gamesPlay2;
    private String gamesPlay3;
    private String gamesPlay4;
    private String createdDateTime;
    private String path;
    private Double userCut;
    private Double moneyAfter;
    private String smallBall;
    private String countPay;
    private String totalMonty;

    public String getTotalMonty() {
        return totalMonty;
    }

    public void setTotalMonty(String totalMonty) {
        this.totalMonty = totalMonty;
    }

    public String getCountPay() {
        return countPay;
    }

    public void setCountPay(String countPay) {
        this.countPay = countPay;
    }

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

    public String getLotterygamesId() {
        return lotterygamesId;
    }

    public void setLotterygamesId(String lotterygamesId) {
        this.lotterygamesId = lotterygamesId;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getTrueWin() {
        return trueWin;
    }

    public void setTrueWin(Double trueWin) {
        this.trueWin = trueWin;
    }

    public Double getWin() {
        return win;
    }

    public void setWin(Double win) {
        this.win = win;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }

    public String getGamesPlay1() {
        return gamesPlay1;
    }

    public void setGamesPlay1(String gamesPlay1) {
        this.gamesPlay1 = gamesPlay1;
    }

    public String getGamesPlay2() {
        return gamesPlay2;
    }

    public void setGamesPlay2(String gamesPlay2) {
        this.gamesPlay2 = gamesPlay2;
    }

    public String getGamesPlay3() {
        return gamesPlay3;
    }

    public void setGamesPlay3(String gamesPlay3) {
        this.gamesPlay3 = gamesPlay3;
    }

    public String getGamesPlay4() {
        return gamesPlay4;
    }

    public void setGamesPlay4(String gamesPlay4) {
        this.gamesPlay4 = gamesPlay4;
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

    public Double getUserCut() {
        return userCut;
    }

    public void setUserCut(Double userCut) {
        this.userCut = userCut;
    }

    public Double getMoneyAfter() {
        return moneyAfter;
    }

    public void setMoneyAfter(Double moneyAfter) {
        this.moneyAfter = moneyAfter;
    }

    public String getSmallBall() {
        return smallBall;
    }

    public void setSmallBall(String smallBall) {
        this.smallBall = smallBall;
    }

    public OutHaveListBean() {
        super();
    }

    public OutHaveListBean(String id, boolean isNewRecord, String lotterygamesId, String round, int total, Double money, Double trueWin, Double win, String rate, int createdTime, String games, String gamesPlay1, String gamesPlay2, String gamesPlay3, String gamesPlay4, String createdDateTime, String path, Double userCut, Double moneyAfter, String smallBall, String countPay, String totalMonty) {
        this.id = id;
        this.isNewRecord = isNewRecord;
        this.lotterygamesId = lotterygamesId;
        this.round = round;
        this.total = total;
        this.money = money;
        this.trueWin = trueWin;
        this.win = win;
        this.rate = rate;
        this.createdTime = createdTime;
        this.games = games;
        this.gamesPlay1 = gamesPlay1;
        this.gamesPlay2 = gamesPlay2;
        this.gamesPlay3 = gamesPlay3;
        this.gamesPlay4 = gamesPlay4;
        this.createdDateTime = createdDateTime;
        this.path = path;
        this.userCut = userCut;
        this.moneyAfter = moneyAfter;
        this.smallBall = smallBall;
        this.countPay = countPay;
        this.totalMonty = totalMonty;
    }

    @Override
    public String toString() {
        return "OutHaveListBean{" +
                "id='" + id + '\'' +
                ", isNewRecord=" + isNewRecord +
                ", lotterygamesId='" + lotterygamesId + '\'' +
                ", round='" + round + '\'' +
                ", total=" + total +
                ", money=" + money +
                ", trueWin=" + trueWin +
                ", win=" + win +
                ", rate=" + rate +
                ", createdTime=" + createdTime +
                ", games='" + games + '\'' +
                ", gamesPlay1='" + gamesPlay1 + '\'' +
                ", gamesPlay2='" + gamesPlay2 + '\'' +
                ", gamesPlay3='" + gamesPlay3 + '\'' +
                ", gamesPlay4='" + gamesPlay4 + '\'' +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", path='" + path + '\'' +
                ", userCut=" + userCut +
                ", moneyAfter=" + moneyAfter +
                ", smallBall='" + smallBall + '\'' +
                ", countPay='" + countPay + '\'' +
                ", totalMonty='" + totalMonty + '\'' +
                '}';
    }
}
