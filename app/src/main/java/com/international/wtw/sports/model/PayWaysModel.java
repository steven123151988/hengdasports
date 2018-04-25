package com.international.wtw.sports.model;


import com.google.gson.annotations.SerializedName;
import com.international.wtw.sports.json.OfflinePayModel;
import com.international.wtw.sports.json.OnlinePayChannel;
import com.international.wtw.sports.json.OnlinePayModel;

import java.util.List;

public class PayWaysModel {

    @SerializedName("offlinepay")
    private List<OfflinePayBean> offlinePay;
    @SerializedName("onlinepay")
    private List<OnlinePayModel> onlinePay;

    public List<OfflinePayBean> getOfflinePay() {
        return offlinePay;
    }

    public void setOfflinePay(List<OfflinePayBean> offlinePay) {
        this.offlinePay = offlinePay;
    }

    public List<OnlinePayModel> getOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(List<OnlinePayModel> onlinePay) {
        this.onlinePay = onlinePay;
    }

    public static class OfflinePayBean {

        /**
         * paymentClass : 5
         * channel : [{"isNewRecord":true,"payTypeId":5,"user":"123","address":"123","code":"123","imageUrl":"http://182.16.115.114:8093/image/lMIyrwA.jpg","rechargeOffer":10,"maximumAmount":5000,"minimumAmount":1},{"isNewRecord":true,"payTypeId":5,"user":"测试","address":"测试","code":"123312","imageUrl":"http://182.16.115.114:8093/image/lMIyrwA.jpg","rechargeOffer":10,"maximumAmount":5000,"minimumAmount":1}]
         */

        private int paymentClass;
        private List<OfflinePayModel> channel;

        public int getPaymentClass() {
            return paymentClass;
        }

        public void setPaymentClass(int paymentClass) {
            this.paymentClass = paymentClass;
        }

        public List<OfflinePayModel> getChannel() {
            return channel;
        }

        public void setChannel(List<OfflinePayModel> channel) {
            this.channel = channel;
        }
    }

    public static class OnlinePayBean {

        /**
         * paymentClass : 5
         * channel : [{"isNewRecord":true,"payTypeId":5,"user":"123","address":"123","code":"123","imageUrl":"http://182.16.115.114:8093/image/lMIyrwA.jpg","rechargeOffer":10,"maximumAmount":5000,"minimumAmount":1},{"isNewRecord":true,"payTypeId":5,"user":"测试","address":"测试","code":"123312","imageUrl":"http://182.16.115.114:8093/image/lMIyrwA.jpg","rechargeOffer":10,"maximumAmount":5000,"minimumAmount":1}]
         */

        private int paymentClass;
        private List<OnlinePayChannel> channel;

        public int getPaymentClass() {
            return paymentClass;
        }

        public void setPaymentClass(int paymentClass) {
            this.paymentClass = paymentClass;
        }

        public List<OnlinePayChannel> getChannel() {
            return channel;
        }

        public void setChannel(List<OnlinePayChannel> channel) {
            this.channel = channel;
        }
    }
}
