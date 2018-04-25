package com.international.wtw.sports.model;

import java.util.List;

public class TimeInfo {


    /**
     * next : {"round":"20171005-006","endTime":1592668800000,"closeTime":1592668798200,"timestamp":1504141897}
     * last : {"round":"20171005-0061","endTime":1592668800000,"number":["4","3","2","1","5","7","8"]}
     */

    private NextBean next;
    private LastBean last;

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
    }

    public LastBean getLast() {
        return last;
    }

    public void setLast(LastBean last) {
        this.last = last;
    }

    public static class NextBean {
        /**
         * round : 20171005-006
         * endTime : 1592668800000
         * closeTime : 1592668798200
         * timestamp : 1504141897
         * isClose : 0
         */

        private String round;
        private Long endTime;
        private Long closeTime;
        private Long timestamp;
        private Integer isClose;

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Long getCloseTime() {
            return closeTime;
        }

        public void setCloseTime(Long closeTime) {
            this.closeTime = closeTime;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Integer getIsClose() {
            return isClose;
        }

        public void setIsClose(Integer isClose) {
            this.isClose = isClose;
        }
    }

    public static class LastBean {
        /**
         * round : 20171005-0061
         * endTime : 1592668800000
         * number : ["4","3","2","1","5","7","8"]
         */

        private String round;
        private List<String> number;

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public List<String> getNumber() {
            return number;
        }

        public void setNumber(List<String> number) {
            this.number = number;
        }
    }
}
