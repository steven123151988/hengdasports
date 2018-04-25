package com.international.wtw.lottery.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PayWaysAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class OnlinePayModel implements MultiItemEntity, Serializable {

    /**
     * paymentClass : 5
     * channel : [{"isNewRecord":true,"payTypeId":5,"user":"123","address":"123","code":"123","imageUrl":"http://182.16.115.114:8093/image/lMIyrwA.jpg","rechargeOffer":10,"maximumAmount":5000,"minimumAmount":1},{"isNewRecord":true,"payTypeId":5,"user":"测试","address":"测试","code":"123312","imageUrl":"http://182.16.115.114:8093/image/lMIyrwA.jpg","rechargeOffer":10,"maximumAmount":5000,"minimumAmount":1}]
     */
    /**
     * 支付类型 1-在线，2-微信，3-支付宝，4-QQ
     */
    public int paymentClass;
    public List<OnlinePayChannel> channel;
    public String gameName;

    public String getPayName() {
        String payName = "在线充值";
        switch (paymentClass) {
            case 1:
                payName = "网银在线充值";
                break;
            case 2:
                payName = "微信在线充值";
                break;
            case 3:
                payName = "支付宝在线充值";
                break;
            case 4:
                payName = "财付通在线充值";
                break;
        }
        return payName;
    }

    public int getSmallLogoRes() {
        int smallLogoRes = R.drawable.img_logo_union_small;
        switch (paymentClass) {
            case 1://在线
                smallLogoRes = R.drawable.img_logo_union_small;
                break;
            case 2://微信
                smallLogoRes = R.drawable.img_logo_wecaht_small;
                break;
            case 3://支付宝
                smallLogoRes = R.drawable.img_logo_alipay_small;
                break;
            case 4://财付通
                smallLogoRes = R.drawable.img_logo_cft_small;
                break;
        }
        return smallLogoRes;
    }

    public int getLargeLogoRes() {
        int largeLogoRes = R.drawable.img_logo_union_large;
        switch (paymentClass) {
            case 1://在线
                largeLogoRes = R.drawable.img_logo_union_large;
                break;
            case 2://微信
                largeLogoRes = R.drawable.img_logo_wecaht_large;
                break;
            case 3://支付宝
                largeLogoRes = R.drawable.img_logo_alipay_large;
                break;
            case 4://财付通
                largeLogoRes = R.drawable.img_logo_cft_large;
                break;
        }
        return largeLogoRes;
    }

    @Override
    public int getItemType() {
        return PayWaysAdapter.TYPE_ONLINE_PAY;
    }
}
