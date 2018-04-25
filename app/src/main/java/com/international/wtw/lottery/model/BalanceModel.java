package com.international.wtw.lottery.model;


import java.math.BigDecimal;

public class BalanceModel {


    /**
     * status : 200
     * balance : 1981.0
     */
    public int status;
    public BigDecimal balance;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBalance() {
        return balance.stripTrailingZeros().toString();
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
