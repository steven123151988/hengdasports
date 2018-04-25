package com.international.wtw.lottery.model;

import java.util.List;

public class BetRequest {

    private String usersId;
    private String sessionId;
    private String round;
    private String game;
    private List<BetItem> plays;

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public List<BetItem> getPlays() {
        return plays;
    }

    public void setPlays(List<BetItem> plays) {
        this.plays = plays;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public static class BetItem {
        private String lotterygamesId;
        private String singleMoney;//单注下注金额
        private String oddId;

        public BetItem() {
        }

        public String getLotterygamesId() {
            return lotterygamesId;
        }

        public void setLotterygamesId(String lotterygamesId) {
            this.lotterygamesId = lotterygamesId;
        }

        public String getSingleMoney() {
            return singleMoney;
        }

        public void setSingleMoney(String singleMoney) {
            this.singleMoney = singleMoney;
        }

        public String getOddId() {
            return oddId;
        }

        public void setOddId(String oddId) {
            this.oddId = oddId;
        }
    }
}
