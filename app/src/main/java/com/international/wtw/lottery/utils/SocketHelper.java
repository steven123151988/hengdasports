package com.international.wtw.lottery.utils;


import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketConsumer;
import com.international.wtw.lottery.api.TrustAllCerts;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.event.UserChangedEvent;
import com.international.wtw.lottery.model.SocketBase;
import com.international.wtw.lottery.model.UserModel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okio.ByteString;

public class SocketHelper {


    private OkHttpClient mClient;
    private Disposable mDisposable;

    private SocketHelper() {
        mClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }

    static class Holder {
        static SocketHelper Instance = new SocketHelper();
    }

    public static SocketHelper get() {
        return Holder.Instance;
    }

    public void connectWebSocket() {
        UserModel currUser = UserHelper.get().getCurrUser();
        if (currUser == null) {
            return;
        }
        String url = String.format(Constants.WEB_SOCKET_URL, UserHelper.get().getSessionId(), UserHelper.get().getUserId());
        Logger.e(url);
        RxWebSocketUtil.getInstance().setClient(mClient);
        RxWebSocketUtil.getInstance().setShowLog(true);
        RxWebSocketUtil.getInstance().getWebSocket(url);
        mDisposable = RxWebSocketUtil.getInstance().getWebSocketInfo(url)
                .subscribe(new WebSocketConsumer() {

                    @Override
                    public void onOpen(WebSocket webSocket) {
                        Logger.e("RxWebSocketUtil WebSocket connected to " + url);
                    }

                    @Override
                    public void onMessage(String text) {
                        LogUtil.e("RxWebSocketUtil WebSocket is onMessage: " + text);
                        try {
                            SocketBase socketBase = JsonUtil.fromJson(text, SocketBase.class);
                            if (socketBase == null) {
                                return;
                            }
                            if (socketBase.status == 200) {
                                if ("001".equals(socketBase.informationCode)) {
                                    // 表示余额信息
                                    currUser.setBalance(new BigDecimal((String) socketBase.payload));
                                    EventBus.getDefault().post(new UserChangedEvent(currUser));
                                } else if ("002".equals(socketBase.informationCode)) {
                                    // 002表示赔率信息
                                }
                            } else if (socketBase.status == 4001) {
                                UserHelper.get().userSignOut();
                            } else if (socketBase.status == 4002) {
                                Logger.e("当前用户已连接WebSocket，不允许重复连接。");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMessage(ByteString bytes) {
                        Logger.e("RxWebSocketUtil WebSocket is onMessageByte: " + bytes.toString());
                    }
                });
    }

    public void disconnectWebSocket() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ssfFactory;
    }
}
