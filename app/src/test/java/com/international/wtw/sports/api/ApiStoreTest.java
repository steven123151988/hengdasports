package com.international.wtw.sports.api;

import com.international.wtw.sports.json.AllOrderListBean;
import com.international.wtw.sports.json.InfoCenterBean;
import com.international.wtw.sports.json.OddsModel;
import com.international.wtw.sports.json.OutHaveListBean;
import com.international.wtw.sports.model.BankModel;
import com.international.wtw.sports.model.FundingRecord;
import com.international.wtw.sports.model.OpenModel;
import com.international.wtw.sports.model.Result;
import com.international.wtw.sports.model.TimeInfo;
import com.international.wtw.sports.model.UserModel;
import com.international.wtw.sports.utils.JsonUtil;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ApiStoreTest extends TestCase {

    public static final String SESSION_ID = "SESSION_ID";
    private ApiStore mService;
    private String sessionId;

    public void setUp() throws Exception {
        super.setUp();
        mService = ApiClient.getInstance().getApiStore();
        //如果接口返回4001, 则重新运行testLogin, 将得到的oid复制到此.
        sessionId = "27f100262f4f424aba008b2e6c4c05de";
    }

    public void testRegister() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("username", "yuci233")
                .addParam("password", "123456789")
                .addParam("realName", "鱼刺")
                .addParam("payPassword", "0000")
                .build();
        Call<Result<List<UserModel>>> call = mService.register(body);
        call.execute();
    }

    public void testLogin() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("username", "xiao233")
                .addParam("password", "123456789")
                .build();
        Call<Result<List<UserModel>>> call = mService.login(body);
        call.execute();
    }

    public void testLoginDemo() throws Exception {
        Call<Result<List<UserModel>>> call = mService.loginDemo();
        call.execute();
    }

    public void testLogout() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                .build();
        Call<ResponseBody> call = mService.logout(body);
        call.execute();
    }

    public void testBetRecord() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                .build();
        Call<Result<List<AllOrderListBean>>> call = mService.betRecord(body);
        call.execute();
    }

    public void testLotteryBill() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                .addParam("status", 0)
                .addParam("pageNo", 1)
                .addParam("pageSize", 20)
                .build();
        Call<Result<List<OutHaveListBean>>> call = mService.lotteryBill(body);
        call.execute();
    }


    public void testUpdatePayPassword() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                .addParam("oldpaypasswd", "0000")
                .addParam("paypassword", "1234")
                .build();
        Call<Result<String>> call = mService.updatePayPassword(body);
        call.execute();
    }

    public void testUpdatePassword() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                .addParam("oldpassword", "123456789")
                .addParam("password", "123123123")
                .build();
        Call<Result<String>> call = mService.updatePassword(body);
        call.execute();
    }

    public void testGetBank() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "2")
                .addParam("sessionId", sessionId)
                .build();
        Call<Result<List<BankModel>>> call = mService.getBankInfo(body);
        call.execute();
    }

    public void testGetFundingRecord() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                .build();
        Call<Result<List<FundingRecord>>> call = mService.getFundingRecord(body);
        call.execute();
    }

    public void testBindBankcard() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "150")
                .addParam("sessionId", sessionId)
                .addParam("bankCardNumbers", "622023100010011548")
                .addParam("bankName", "工商银行")
                .addParam("bankAddress", "深圳市南山区西丽支行")
                .build();
        Call<Result<Object>> call = mService.bindBankcard(body);
        call.execute();
    }

    public void testCheckUsername() throws Exception {
        RequestBody body = new RequestBodyBuilder()
                .addParam("username", "yuci")
                .build();
        Call<Result<Object>> call = mService.checkUsername(body);
        call.execute();
    }

    public void testWithdraw() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "150")
                .addParam("sessionId", sessionId)
                .addParam("bankCardNumbers", "622023100010011548")
                .addParam("withdrawalAmount", "100")
                .addParam("payPassword", "0000")
                .build();
        Call<Result<Object>> call = mService.withdraw(body);
        call.execute();
    }

    public void testGetAnnouncement() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .addParam("type", 0)
                .build();
        Call<Result<Object>> call = mService.getAnnouncement(body);
        call.execute();
    }

    public void testGetAllAnnouncement() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .build();
        Call<Result<List<InfoCenterBean.ResponseBean>>> call = mService.getAllAnnouncements(body);
        call.execute();
    }

    public void testGetAnnouncementDetail() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .addParam("id", 0)
                .build();
        Call<Result<Object>> call = mService.getAnnouncementDetail(body);
        call.execute();
    }

    public void testLotteryInfo() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .addParam("gameCode", 3)
                .build();
        Call<Result<List<TimeInfo>>> call = mService.lotteryInfo(body);
        call.execute();
    }

    public void testLotteryHistory() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                //.addParam("lotterygamesId", 1)
                .build();
        Call<Result<List<OpenModel>>> call = mService.lotteryHistory(body);
        call.execute();
    }

    public void testGetGameOdds() throws IOException {
        RequestBody body = new RequestBodyBuilder()
                .addParam("usersId", "156")
                .addParam("sessionId", sessionId)
                .addParam("play", 20)
                .build();
        Call<Result<List<OddsModel>>> call = mService.getGameOdds(body);
        call.execute();
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

}