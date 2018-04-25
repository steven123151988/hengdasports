package com.international.wtw.sports.api;

import android.support.v4.app.FragmentActivity;

import com.google.gson.JsonParseException;
import com.international.wtw.sports.dialog.ToastDialog;
import com.international.wtw.sports.model.Result;
import com.international.wtw.sports.utils.ActivityManager;
import com.international.wtw.sports.utils.UserHelper;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class NetCallback2<T> implements Callback<Result<List<T>>> {

    @Override
    public void onResponse(Call<Result<List<T>>> call, Response<Result<List<T>>> response) {
        if (call.isCanceled()) {
            return;
        }
        if (response.isSuccessful()) {
            Result<List<T>> tResult = response.body();
            if (tResult == null) {
                return;
            }
            switch (tResult.getStatus()) {
                case 200://请求成功
                case 201://更新成功
                case 505://新增成功
                case 507://删除成功
                case 509://登出成功
                case 515://登陆成功
                case 526://投注成功
                case 528://添加成功
                case 530://提现成功
                case 532://注册成功
                case 534://账户可用
                    List<T> resp = tResult.getResponse();
                    T data = resp == null || resp.size() == 0 ? null : resp.get(0);
                    onSuccess(data, tResult.getStatus(), tResult.getMessage());
                    break;
                case 535://sessionId不能为空
                case 536://usersId不能为空
                case 4001:
                    FragmentActivity currActivity = (FragmentActivity) ActivityManager.getInstance().getCurrentActivity();
                    //提示用户
                    ToastDialog.error(tResult.getMessage()).show(currActivity.getSupportFragmentManager());
                    UserHelper.get().userSignOut();
                    break;
                default:
                    onFailure(tResult.getStatus(), tResult.getMessage());
            }
        } else {
            onFailure(call, new HttpException(response));
        }
    }

    @Override
    public void onFailure(Call<Result<List<T>>> call, Throwable t) {
        if (call.isCanceled()) {
            return;
        }
        if (t instanceof HttpException) {
            int code = ((HttpException) t).code();
            switch (code) {
                case 401:
                    onFailure(code, "访问被服务器拒绝啦~");
                    break;
                case 403:
                    onFailure(code, "服务器资源不可用");
                    break;
                case 404:
                    onFailure(code, "我们好像迷路了，找不到服务器");
                    break;
                case 408:
                    onFailure(code, "糟糕，请求超时了，请检查网络连接后重试");
                    break;
                case 500:
                    onFailure(code, "服务器正在开小差，请稍后重试");
                    break;
                case 502:
                    onFailure(code, "服务器正在开小差，请稍后重试");
                    break;
                case 503:
                    onFailure(code, "服务器正在开小差，请稍后重试");
                    break;
                case 504:
                    onFailure(code, "糟糕，请求超时了，请检查网络连接后重试");
                    break;
                default:
                    onFailure(code, "出现了未知的错误，请稍后重试");
            }
        } else if (t instanceof JSONException || t instanceof JsonParseException || t instanceof ParseException) {
            onFailure(0x10011, "数据解析错误");
        } else if (t instanceof ConnectException) {
            onFailure(0x10012, "连接失败，网络连接可能存在异常，请检查网络后重试");
        } else if (t instanceof SSLHandshakeException) {
            onFailure(0x10013, "证书验证失败");
        } else if (t instanceof UnknownHostException) {
            onFailure(0x10014, "无法连接到服务器，请检查你的网络或稍后重试");
        } else if (t instanceof SocketTimeoutException) {
            onFailure(0x10015, "请求超时，请检查网络后重试");
        } else {
            onFailure(0x10016, "出现了未知的错误，请稍后重试");
        }
    }

    public abstract void onSuccess(T data, int status, String msg);

    public abstract void onFailure(int status, String errorMsg);
}
