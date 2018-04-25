package com.international.wtw.lottery.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.api.ApiClient;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.UploadApkDialog;
import com.international.wtw.lottery.json.LotteryVersion;
import com.international.wtw.lottery.model.UserModel;
import com.international.wtw.lottery.utils.UserHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 启动页 检查是否更新
 */
public class StartActivity extends BaseActivity {

    private UploadApkDialog uploadApkDialog;
    private SweetAlertDialog mSweetAlertDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        ImageView view = (ImageView) findViewById(R.id.img_start);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(1000);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                getVersion();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }


    private void getVersion() {
        Call<LotteryVersion> call = ApiClient.getInstance().getApiStore().getVersion();
        call.enqueue(new Callback<LotteryVersion>() {
            @Override
            public void onResponse(Call<LotteryVersion> call, retrofit2.Response<LotteryVersion> response) {
                if (call.isCanceled()) {
                    return;
                }
                if (response.isSuccessful()) {
                    dealUpdate(response.body());
                } else {
                    goNext();
                }
            }

            @Override
            public void onFailure(Call<LotteryVersion> call, Throwable t) {
                t.printStackTrace();
                goNext();
            }
        });
    }

    private void dealUpdate(LotteryVersion lotteryVersion) {
        String versionName = getAppVersionName(getApplicationContext());
        if (null != lotteryVersion && null != lotteryVersion.getResponse() &&
                null != lotteryVersion.getResponse().get(0).getForcedupdate() &&
                Float.parseFloat(lotteryVersion.getResponse().get(0).getVersionnum()) > Float.parseFloat(versionName)) {
            if (isFinishing()) {
                return;
            }
            if (lotteryVersion.getResponse().get(0).getForcedupdate().equals("0")) {
                mSweetAlertDialog = new SweetAlertDialog(StartActivity.this, cn.pedant.SweetAlert.SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                mSweetAlertDialog.setTitleText(getString(R.string.app_update) + lotteryVersion.getResponse().get(0).getVersionnum())
                        .setCustomImage(R.mipmap.update)
                        .setContentText(getString(R.string.center_text))
                        .setConfirmText(getString(R.string.confirm_text))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                showUpdateDialog(lotteryVersion);
                            }
                        })
                        .show();
            } else {
                mSweetAlertDialog = new SweetAlertDialog(StartActivity.this, cn.pedant.SweetAlert.SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                mSweetAlertDialog.setTitleText(getString(R.string.app_update) + lotteryVersion.getResponse().get(0).getVersionnum())
                        .setCustomImage(R.mipmap.update)
                        .setContentText(getString(R.string.center_text))
                        .setCancelText(getString(R.string.cancle_text))
                        .setConfirmText(getString(R.string.confirm_text))
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                goNext();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                showUpdateDialog(lotteryVersion);
                            }
                        })
                        .show();
            }
        } else {
            goNext();
        }
    }

    /**
     * 自动更新失败或者没有新的版本更新  走下一步
     */
    private void goNext() {
        //String Login_oid = SharePreferencesUtil.getString(StartActivity.this, LotteryId.Login_oid, null);
        UserModel user = UserHelper.get().getCurrUser();
        if (null == user) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
        finish();
    }


    /**
     * 获取当前版本号
     */
    private String getAppVersionName(Context context) {
        String versionName = "1.0";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "1.0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 展示加载升级对话框
     */
    private void showUpdateDialog(LotteryVersion lotteryVersion) {
        if (!isFinishing()) {
            if (null == uploadApkDialog) {
                uploadApkDialog = new UploadApkDialog(StartActivity.this,
                        lotteryVersion.getResponse().get(0).getVersionurl());
            }
            uploadApkDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != uploadApkDialog) {
            uploadApkDialog.dismiss();
        }
        if (null != mSweetAlertDialog) {
            mSweetAlertDialog.dismiss();
        }
    }
}
