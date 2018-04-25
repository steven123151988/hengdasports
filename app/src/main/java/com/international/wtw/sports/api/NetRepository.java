package com.international.wtw.sports.api;

import android.support.v4.util.ArrayMap;

import com.international.wtw.sports.base.LotteryId;
import com.international.wtw.sports.base.app.BaseApplication;
import com.international.wtw.sports.json.AllOrderListBean;
import com.international.wtw.sports.json.InfoCenterBean;
import com.international.wtw.sports.json.LunbotuBean;
import com.international.wtw.sports.json.OddsModel;
import com.international.wtw.sports.json.OfflinePayModel;
import com.international.wtw.sports.json.OnlinePayChannel;
import com.international.wtw.sports.json.OutHaveListBean;
import com.international.wtw.sports.json.PreferentialProBean;
import com.international.wtw.sports.model.BankModel;
import com.international.wtw.sports.model.BetRequest;
import com.international.wtw.sports.model.DisposableToken;
import com.international.wtw.sports.model.FundingRecord;
import com.international.wtw.sports.model.OnlinePayUrl;
import com.international.wtw.sports.model.OpenModel;
import com.international.wtw.sports.model.PayWaysModel;
import com.international.wtw.sports.model.Result;
import com.international.wtw.sports.model.TimeInfo;
import com.international.wtw.sports.model.UserModel;
import com.international.wtw.sports.utils.JsonUtil;
import com.international.wtw.sports.utils.SharePreferencesUtil;
import com.international.wtw.sports.utils.UserHelper;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetRepository {

    private ApiStore mService = ApiClient.getInstance().getApiStore();
    private static ArrayMap<Object, List<Call>> mCallMap = new ArrayMap<>();

    private NetRepository() {
    }

    private static class SingletonHolder {
        private static NetRepository instance = new NetRepository();
    }

    public static NetRepository get() {
        return SingletonHolder.instance;
    }

    private synchronized void putCall(Object tag, Call call) {
        if (tag == null) {
            return;
        }
        List<Call> callList = mCallMap.get(tag);
        if (callList == null) {
            callList = Collections.synchronizedList(new ArrayList<>());
        }
        callList.add(call);
        mCallMap.put(tag, callList);
    }

    public synchronized void cancel(Object tag) {
        if (tag == null) {
            return;
        }
        List<Call> callList = mCallMap.get(tag);
        if (callList == null || callList.size() == 0) {
            return;
        }
        for (Call call : callList) {
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
        mCallMap.remove(tag);
    }

    private String getUserId() {
        return UserHelper.get().getUserId();
    }

    private String getSessionId() {
        return UserHelper.get().getSessionId();
    }

    private class RequestBodyBuilder {

        Map<String, Object> params;

        private RequestBodyBuilder() {
            params = new HashMap<>();
        }

        private RequestBodyBuilder addParam(String key, Object value) {
            params.put(key, value);
            return this;
        }

        private RequestBody build() {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(params));
        }
    }

    /**
     * 注册
     *
     * @param userName    用户名
     * @param password    密码
     * @param realName    真实姓名
     * @param payPassword 支付密码
     */
    public void register(Object tag, String userName, String password, String realName,
                         String payPassword, NetCallback2<UserModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("username", userName)
                .addParam("password", password)
                .addParam("realName", realName)
                .addParam("payPassword", payPassword)
                .build();
        Call<Result<List<UserModel>>> call = mService.register(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     */
    public void login(Object tag, String userName, String password,
                      NetCallback2<UserModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("username", userName)
                .addParam("password", password)
                .build();
        Call<Result<List<UserModel>>> call = mService.login(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 试玩登录
     */
    public void loginDemo(Object tag, NetCallback2<UserModel> callback) {
        Call<Result<List<UserModel>>> call = mService.loginDemo();
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 退出登录
     * 退出登录时我们调用一下这个接口, 通知一下服务器,
     * 其实我们并不太关心接口是否调用成功, 在调用此接口后清空用户数据, 让用户重新登录.
     */
    public void logout() {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .build();
        Call<ResponseBody> call = mService.logout(body);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getBankInfo(Object tag, NetCallback2<BankModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .build();
        Call<Result<List<BankModel>>> call = mService.getBankInfo(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void getFundingRecord(Object tag, int pageNo, int pageSize, NetCallback<List<FundingRecord>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("pageNo", pageNo)
                .addParam("pageSize", pageSize)
                .build();
        Call<Result<List<FundingRecord>>> call = mService.getFundingRecord(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void bindBankcard(Object tag, String bankId, String bankcardNo, String bankName, String bankAddress, NetCallback<Object> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("bankId", bankId)
                .addParam("bankCardNumbers", bankcardNo)
                .addParam("bankName", bankName)
                .addParam("bankAddress", bankAddress)
                .build();
        Call<Result<Object>> call = mService.bindBankcard(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 下注记录
     */
    public void betRecord(Object tag, int page, int num, NetCallback<List<AllOrderListBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("pageNo", page)
                .addParam("pageSize", num)
                .build();
        Call<Result<List<AllOrderListBean>>> call = mService.betRecord(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 未结
     */
    public void lotteryBill(Object tag, int page, int num, NetCallback<List<OutHaveListBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("pageNo", page)
                .addParam("pageSize", num)
                .build();
        Call<Result<List<OutHaveListBean>>> call = mService.lotteryBill(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 已结
     */
    public void todayend(Object tag, int page, int num, NetCallback<List<OutHaveListBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("pageNo", page)
                .addParam("pageSize", num)
                .build();
        Call<Result<List<OutHaveListBean>>> call = mService.todayend(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 当天未结
     */
    public void lotteryBillDef(Object tag, int page, int num, int time, NetCallback<List<OutHaveListBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("pageNo", page)
                .addParam("pageSize", num)
                .addParam("createdTime", time)
                .build();
        Call<Result<List<OutHaveListBean>>> call = mService.lotteryBill(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 当天已结
     */
    public void todayendDef(Object tag, int page, int num, int time, NetCallback<List<OutHaveListBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("pageNo", page)
                .addParam("pageSize", num)
                .addParam("createdTime", time)
                .build();
        Call<Result<List<OutHaveListBean>>> call = mService.todayend(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 修改支付密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     */
    public void updatePayPassword(Object tag, String oldPwd, String newPwd, NetCallback<String> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("oldPayPassWord", oldPwd)
                .addParam("payPassWord", newPwd)
                .build();
        Call<Result<String>> call = mService.updatePayPassword(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     */
    public void updatePassword(Object tag, String oldPwd, String newPwd, NetCallback<String> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("oldpassword", oldPwd)
                .addParam("password", newPwd)
                .build();
        Call<Result<String>> call = mService.updatePassword(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 首页轮播图
     */
    public void getBannerImg(Object tag, Callback<LunbotuBean> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("type", 0)
                .build();
        Call<LunbotuBean> call = mService.getBannerImg(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void getAllAnnouncements(Object tag, NetCallback<List<InfoCenterBean.ResponseBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("pageNo", 1)
                .addParam("pageSize", -1)
                .build();
        Call<Result<List<InfoCenterBean.ResponseBean>>> call = mService.getAllAnnouncements(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 检查用户名是否可用
     */
    public void checkUsername(Object tag, String username, NetCallback<Object> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("username", username)
                .build();
        Call<Result<Object>> call = mService.checkUsername(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 提现
     *
     * @param bankcardNo 银行卡号
     * @param amount     提现金额
     * @param payPwd     支付密码
     */
    public void withdraw(Object tag, String bankcardNo, String amount, String payPwd, NetCallback<Object> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("bankCardNumbers", bankcardNo)
                .addParam("withdrawalAmount", amount)
                .addParam("payPassword", payPwd)
                .build();
        Call<Result<Object>> call = mService.withdraw(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 最新公告
     *
     * @param type 0-滚动公告,1-弹窗公告,2充值公告
     */
    public void getAnnouncement(Object tag, String type, NetCallback<Object> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("type", type)
                .build();
        Call<Result<Object>> call = mService.getAnnouncement(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void lotteryInfo(Object tag, String gameCode, NetCallback2<TimeInfo> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("gameCode", gameCode)
                .build();
        Call<Result<List<TimeInfo>>> call = mService.lotteryInfo(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void lotteryHall(Object tag, int pageNo, int pageSize, NetCallback<List<OpenModel>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("pageNo", pageNo)
                .addParam("pageSize", pageSize)
                .build();
        Call<Result<List<OpenModel>>> call = mService.lotteryHall(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void lotteryHistory(Object tag, Integer gameCode, int pageNo, int pageSize, NetCallback<List<OpenModel>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("lotteryGamesId", gameCode)
                .addParam("pageNo", pageNo)
                .addParam("pageSize", pageSize)
                .build();
        Call<Result<List<OpenModel>>> call = mService.lotteryHistory(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void getGameOdds(Object tag, String play, NetCallback<List<OddsModel>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("play", play)
                .build();
        Call<Result<List<OddsModel>>> call = mService.getGameOdds(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void betting(Object tag, BetRequest body, NetCallback2<DisposableToken> callback) {
        body.setUsersId(getUserId());
        body.setSessionId(getSessionId());
        String token = SharePreferencesUtil.getString(BaseApplication.getAppContext(), LotteryId.DISPOSABLE_TOKEN, null);
        Call<Result<List<DisposableToken>>> call = mService.betting(token, body);
        putCall(tag, call);
        call.enqueue(new Callback<Result<List<DisposableToken>>>() {
            @Override
            public void onResponse(Call<Result<List<DisposableToken>>> call, Response<Result<List<DisposableToken>>> response) {
                if (response.isSuccessful()) {
                    Result<List<DisposableToken>> token = response.body();
                    if (token != null) {
                        String disposableToken = token.getResponse().get(0).disposableToken;
                        SharePreferencesUtil.addString(BaseApplication.getAppContext(), LotteryId.DISPOSABLE_TOKEN, disposableToken);
                    }
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Result<List<DisposableToken>>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void Preferential(Object tag, int pageNo, int pageSize, NetCallback<List<PreferentialProBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("pageNo", pageNo)
                .addParam("pageSize", pageSize)
                .build();
        Call<Result<List<PreferentialProBean>>> call = mService.Preferential(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void getPayWays(Object tag, NetCallback2<PayWaysModel> callback) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("usersId", getUserId());
        map.put("sessionId", getSessionId());
        Call<Result<List<PayWaysModel>>> call = mService.getPayInWays(map);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void onlinePayment(Object tag, String amount, OnlinePayChannel channel, NetCallback2<OnlinePayUrl> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("amount", amount)
                .addParam("id", channel.id)
                .addParam("businessCode", channel.businessCode)
                .addParam("domain", channel.domain)
                .addParam("notifyurl", channel.notifyurl)
                .addParam("remark", channel.remark)
                .addParam("paymentClass", channel.paymentClass)
                .addParam("rechargeOffer", channel.rechargeOffer)
                .addParam("payTypeId", channel.payTypeId)
                .addParam("name", channel.name)
                .build();
        Call<Result<List<OnlinePayUrl>>> call = mService.onlinePayment(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void offlinePayment(Object tag, String businessCode, String amount, String user,
                               String createdTime, OfflinePayModel payModel, NetCallback<Object> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .addParam("amount", amount)
                .addParam("businessCode", businessCode)
                .addParam("createdTime", createdTime)
                .addParam("user", user)
                .addParam("id", payModel.id)
                .addParam("offlinePayId", payModel.payTypeId)
                .addParam("address", payModel.address)
                .addParam("cardCode", payModel.code)
                .addParam("rechargeOffer", payModel.rechargeOffer)
                .build();
        Call<Result<Object>> call = mService.offlinePayment(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void getDisposableToken() {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", getUserId())
                .addParam("sessionId", getSessionId())
                .build();
        mService.getDisposableToken(body).enqueue(new Callback<DisposableToken>() {
            @Override
            public void onResponse(Call<DisposableToken> call, Response<DisposableToken> response) {
                if (response.isSuccessful()) {
                    DisposableToken token = response.body();
                    if (token != null) {
                        String disposableToken = token.disposableToken;
                        SharePreferencesUtil.addString(BaseApplication.getAppContext(), LotteryId.DISPOSABLE_TOKEN, disposableToken);
                    }
                }
            }

            @Override
            public void onFailure(Call<DisposableToken> call, Throwable t) {
                Logger.e("Get disposable token failed!");
            }
        });
    }

}
