package com.international.wtw.sports.json;


import java.io.Serializable;

public class OnlinePayChannel implements Serializable {

    /**
     * id : 4
     * isNewRecord : true
     * payTypeId : 102
     * businessCode : 10349
     * domain : http://new_pay.ubssp.com
     * notifyurl : http://182.16.115.114/onlinePay/notify
     * rechargeOffer : 0.00
     * remark : gaotong
     * paymentClass : 2
     * maximumAmount : 5000
     * minimumAmount : 1
     * name : 高通--微信
     */

    public String id;
    public String payTypeId;
    public String businessCode;
    public String domain;
    public String notifyurl;
    public String rechargeOffer;
    public String remark;
    public int paymentClass;
    public int maximumAmount;
    public int minimumAmount;
    public String name;
}
