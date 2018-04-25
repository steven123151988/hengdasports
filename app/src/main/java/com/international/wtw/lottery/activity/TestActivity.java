package com.international.wtw.lottery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.model.UserModel;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class TestActivity extends AppCompatActivity {

    private String userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void onLogin(View view) {
        NetRepository.get().login(this, "yuci233", "123456789", new NetCallback2<UserModel>() {
            @Override
            public void onSuccess(UserModel data, int status, String msg) {
                //UserModel user = data;
                sessionId = data.getSessionId();
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.d("status = " + status + ": " + errorMsg);
            }
        });
    }

    public void onSocket(View view) {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("ws://182.16.62.242:8092/websoket")
                .build();
        client.newWebSocket(request, new WebSocketListener() {

            WebSocket webSocket = null;

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                //保存引用，用于后续操作
                this.webSocket = webSocket;
                //打印一些内容
                System.out.println("client onOpen");
                System.out.println("client request header:" + response.request().headers());
                System.out.println("client response header:" + response.headers());
                System.out.println("client response:" + response);
                //注意下面都是write线程回写给客户端
                //建立连接成功后，发生command 1给服务器端
                webSocket.send(String.format("%s,%s", userId, sessionId));
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                t.printStackTrace();
            }
        });
    }
}
