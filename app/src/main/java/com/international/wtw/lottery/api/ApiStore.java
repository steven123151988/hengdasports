package com.international.wtw.lottery.api;


import com.international.wtw.lottery.json.AllOrderListBean;
import com.international.wtw.lottery.json.InfoCenterBean;
import com.international.wtw.lottery.json.LotteryVersion;
import com.international.wtw.lottery.json.LunbotuBean;
import com.international.wtw.lottery.json.OddsModel;
import com.international.wtw.lottery.json.OutHaveListBean;
import com.international.wtw.lottery.json.PreferentialProBean;
import com.international.wtw.lottery.model.BankModel;
import com.international.wtw.lottery.model.BetRequest;
import com.international.wtw.lottery.model.DisposableToken;
import com.international.wtw.lottery.model.FundingRecord;
import com.international.wtw.lottery.model.OnlinePayUrl;
import com.international.wtw.lottery.model.OpenModel;
import com.international.wtw.lottery.model.PayWaysModel;
import com.international.wtw.lottery.model.Result;
import com.international.wtw.lottery.model.TimeInfo;
import com.international.wtw.lottery.model.UserModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiStore {

    /**
     * 注册
     */
    @POST("user/register")
    Call<Result<List<UserModel>>> register(@Body RequestBody body);

    /**
     * 登录
     */
    @POST("user/login")
    Call<Result<List<UserModel>>> login(@Body RequestBody body);

    /**
     * 试玩登录
     */
    @POST("user/touristslogin")
    Call<Result<List<UserModel>>> loginDemo();

    /**
     * 登出
     */
    @POST("user/esc")
    Call<ResponseBody> logout(@Body RequestBody body);

    /**
     * 下注记录  汇总
     */
    @POST("lotterybill/summary")
    Call<Result<List<AllOrderListBean>>> betRecord(@Body RequestBody body);

    /**
     * 未结
     */
    @POST("lotterybill/findlotteyBill")
    Call<Result<List<OutHaveListBean>>> lotteryBill(@Body RequestBody body);

    /**
     * 已结
     */
    @POST("lotterybill/todayend")
    Call<Result<List<OutHaveListBean>>> todayend(@Body RequestBody body);

    /**
     * 修改取款密码
     */
    @POST("user/updateplypwd")
    Call<Result<String>> updatePayPassword(@Body RequestBody body);

    /**
     * 修改密码
     */
    @POST("user/updatepwd")
    Call<Result<String>> updatePassword(@Body RequestBody body);

    /**
     * 轮播图
     */
    @POST("imageupload/carousel")
    Call<LunbotuBean> getBannerImg(@Body RequestBody body);

    /**
     * 用户提现初始化 得到用户名/余额和银行卡信息
     */
    @POST("userdeposit/userBank")
    Call<Result<List<BankModel>>> getBankInfo(@Body RequestBody body);

    /**
     * 检查用户名是否可用
     */
    @POST("user/checkName")
    Call<Result<Object>> checkUsername(@Body RequestBody body);

    /**
     * 存取款记录
     */
    @POST("userdeposit/withdrawalsAndSaveDetail")
    Call<Result<List<FundingRecord>>> getFundingRecord(@Body RequestBody body);

    /**
     * 用户提现
     */
    @POST("userdeposit/withdrawAmount")
    Call<Result<Object>> withdraw(@Body RequestBody body);

    /**
     * 添加/修改银行卡
     */
    @POST("userdeposit/bindBank")
    Call<Result<Object>> bindBankcard(@Body RequestBody body);

    /**
     * 获取最新
     */
    @POST("announcement/newmessage")
    Call<Result<Object>> getAnnouncement(@Body RequestBody body);

    /**
     * 获取所有广播
     */
    @POST("announcement/findall")
    Call<Result<List<InfoCenterBean.ResponseBean>>> getAllAnnouncements(@Body RequestBody body);

    /**
     * 获取广播详情
     */
    @POST("announcement/details")
    Call<Result<Object>> getAnnouncementDetail(@Body RequestBody body);

    /**
     * 获取赔率
     */
    @POST("betting/bettingQuery")
    Call<Result<List<OddsModel>>> getGameOdds(@Body RequestBody body);

    /**
     * 开奖历史
     */
    @POST("lotterOpen/openselet")
    Call<Result<List<OpenModel>>> lotteryHall(@Body RequestBody body);

    /**
     * 开奖历史
     */
    @POST("lotterOpen/historyOpenLottery")
    Call<Result<List<OpenModel>>> lotteryHistory(@Body RequestBody body);

    /**
     * 下注
     */
    @POST("betting/betting")
    Call<Result<List<DisposableToken>>> betting(@Header("disposableToken") String token,@Body BetRequest body);

    /**
     * 期数查询
     */
    @POST("lotterOpen/getinfo/periods")
    Call<Result<List<TimeInfo>>> lotteryInfo(@Body RequestBody body);

    /**
     * 优惠活动
     */
    @POST("activityUrl/activityQuery")
    Call<Result<List<PreferentialProBean>>> Preferential(@Body RequestBody body);

    /**
     * 获取支持的支付方式
     */
    @POST("onlinePay/payType")
    Call<Result<List<PayWaysModel>>> getPayInWays(@Body HashMap<String, Object> params);


    @POST("onlinePay/pay")
    Call<Result<List<OnlinePayUrl>>> onlinePayment(@Body RequestBody body);

    @POST("offlinePay/TransactionRecord")
    Call<Result<Object>> offlinePayment(@Body RequestBody body);

    @POST("android/version")
    Call<LotteryVersion> getVersion(/*@Body RequestBody body*/);

    /**
     * 获取下注一次性token
     */
    @POST("auth/getDisposableToken")
    Call<DisposableToken> getDisposableToken(@Body RequestBody body);
}
