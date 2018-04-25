package com.international.wtw.lottery.json;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class OddsItem {
    /**
     * name : 大
     * odds : 101.01
     * backwater : 1.988
     * key : ip_3001_3011
     * singleMoney : 1
     * betmin : 3
     * betmax : 10
     * roundmax : 100
     */
    @Id
    private Long id;
    private String oddId;
    private String name;
    private String odds;
    private String backwater;
    private String key;
    private String singleMoney;//单注下注金额
    private String betmin;//单注最低金额
    private String betmax;//单注最高金额
    private String roundmax;//单轮最高金额

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOddId() {
        return oddId;
    }

    public void setOddId(String oddId) {
        this.oddId = oddId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOdds() {
        return this.odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBackwater() {
        return this.backwater;
    }

    public void setBackwater(String backwater) {
        this.backwater = backwater;
    }

    public String getSingleMoney() {
        return singleMoney;
    }

    public void setSingleMoney(String singleMoney) {
        this.singleMoney = singleMoney;
    }

    public String getBetmin() {
        return betmin;
    }

    public void setBetmin(String betmin) {
        this.betmin = betmin;
    }

    public String getBetmax() {
        return betmax;
    }

    public void setBetmax(String betmax) {
        this.betmax = betmax;
    }

    public String getRoundmax() {
        return roundmax;
    }

    public void setRoundmax(String roundmax) {
        this.roundmax = roundmax;
    }
}
