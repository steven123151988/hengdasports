package com.international.wtw.sports.base.app;

/**
 * Created by wuya on 2017/5/2.
 */


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.international.wtw.sports.json.MyObjectBox;
import com.international.wtw.sports.utils.ActivityManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import io.objectbox.BoxStore;

public class BaseApplication extends Application {

    private static BaseApplication sApp;
    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        //日志打印
        Logger.addLogAdapter(new AndroidLogAdapter());
        //ObjectBox数据库
        boxStore = MyObjectBox.builder().androidContext(this).build();
        registerActivityLifecycleCallbacks();
        //设置友盟统计场景
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        //友盟日志加密6.0.0版本及以后
        MobclickAgent.enableEncrypt(true);
    }

    public static BaseApplication getAppContext() {
        return sApp;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    /**
     * 获取当前activity的接口
     */
    private void registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

}

