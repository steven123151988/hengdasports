package com.international.wtw.lottery.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PayWaysAdapter;

import java.io.Serializable;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class OfflinePayModel implements MultiItemEntity, Serializable {

    public String gameName;
    /**
     * id : 4
     * isNewRecord : true
     * payTypeId : 5
     * user : 123
     * address : 123
     * code : 123
     * imageUrl : http://182.16.115.114:8093/image/lMIyrwA.jpg
     * rechargeOffer : 10
     * maximumAmount : 5000
     * minimumAmount : 1
     */

    public int paymentClass;

    public String id;
    public int payTypeId;
    public String user;
    public String address;
    public String code;
    public String imageUrl;
    public String rechargeOffer;
    public int maximumAmount;
    public int minimumAmount;

    public String getPayName() {
        String payName = "银行转账";
        switch (paymentClass) {
            case 5:
                payName = "银行转账";
                break;
            case 4:
                payName = "微信转账";
                break;
            case 6:
                payName = "支付宝转账";
                break;
        }
        return payName;
    }

    public int getSmallLogoRes() {
        int smallLogoRes = R.drawable.img_logo_union_small;
        switch (paymentClass) {
            case 5://在线
                smallLogoRes = R.drawable.img_logo_union_small;
                break;
            case 4://微信
                smallLogoRes = R.drawable.img_logo_wecaht_small;
                break;
            case 6://支付宝
                smallLogoRes = R.drawable.img_logo_alipay_small;
                break;
        }
        return smallLogoRes;
    }

    public int getLargeLogoRes() {
        int largeLogoRes = R.drawable.selector_offline_unionpay;
        switch (paymentClass) {
            case 5://在线
                largeLogoRes = R.drawable.selector_offline_unionpay;
                break;
            case 4://微信
                largeLogoRes = R.drawable.selector_offline_wechatpay;
                break;
            case 6://支付宝
                largeLogoRes = R.drawable.selector_offline_alipay;
                break;
        }
        return largeLogoRes;
    }

    @Override
    public int getItemType() {
        return PayWaysAdapter.TYPE_OFFLINE_PAY;
    }
}
