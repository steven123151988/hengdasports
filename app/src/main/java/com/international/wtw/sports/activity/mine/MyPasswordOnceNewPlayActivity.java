package com.international.wtw.sports.activity.mine;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.activity.MainActivity;
import com.international.wtw.sports.api.NetCallback;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.base.LotteryId;
import com.international.wtw.sports.base.app.BaseActivity;
import com.international.wtw.sports.base.app.ViewHolder;
import com.international.wtw.sports.dialog.ToastDialog;
import com.international.wtw.sports.utils.LogUtil;
import com.international.wtw.sports.utils.SharePreferencesUtil;
import com.international.wtw.sports.widget.PasswordInputView;

/**
 * 修改支付密码   确定新
 */
public class MyPasswordOnceNewPlayActivity extends BaseActivity {

    private PasswordInputView edittext_withdrawl_oncenewpwd;
    private ImageView imageView_toLeftArrow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mypass_oncenewplay;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        edittext_withdrawl_oncenewpwd = (PasswordInputView) findViewById(R.id.edittext_withdrawl_oncenewpwd);

        String Pay_OldPwd = SharePreferencesUtil.getString(MyPasswordOnceNewPlayActivity.this, LotteryId.Pay_OldPwd, null);
        String Pay_NewPwd = SharePreferencesUtil.getString(MyPasswordOnceNewPlayActivity.this, LotteryId.Pay_NewPwd, null);

        edittext_withdrawl_oncenewpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //这里的count树枝上是和onTextChanged（）里的before一样的,after树枝上是和onTextChanged（）里的count一样的
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本每次改变就会跑这个方法
                if (s.length() == 4) {
                    Log.e("TAG", "Pay_OldPwd-" + Pay_OldPwd + "-Pay_NewPwd-" + Pay_NewPwd);
                    String Pay_OnceNewPwd = s.toString();

                    if (!Pay_NewPwd.equals(Pay_OnceNewPwd)) {
                        ToastDialog.error("两次输入密码不一致").setDismissListener(new ToastDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(ToastDialog dialog) {
                                startActivity(new Intent(MyPasswordOnceNewPlayActivity.this, MainActivity.class));
                                finish();
                            }
                        }).show(getSupportFragmentManager());
                    } else {
                        if (Pay_OldPwd.equals(Pay_NewPwd)) {
                            ToastDialog.error("新密码不能与旧密码相同").setDismissListener(new ToastDialog.OnDismissListener() {
                                @Override
                                public void onDismiss(ToastDialog dialog) {
                                    startActivity(new Intent(MyPasswordOnceNewPlayActivity.this, MainActivity.class));
                                    finish();
                                }
                            }).show(getSupportFragmentManager());
                        } else {

                            NetRepository.get().updatePayPassword(this, Pay_OldPwd, Pay_NewPwd, new NetCallback<String>() {
                                @Override
                                public void onSuccess(String data, int status, String msg, int total) {
                                    LogUtil.e("修改支付密码成功---onSuccess---" + "-status-" + status + "-msg-" + msg);
                                    ToastDialog.error("修改成功").setDismissListener(new ToastDialog.OnDismissListener() {
                                        @Override
                                        public void onDismiss(ToastDialog dialog) {
                                            startActivity(new Intent(MyPasswordOnceNewPlayActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }).show(getSupportFragmentManager());
                                }

                                @Override
                                public void onFailure(int status, String errorMsg) {
                                    LogUtil.e("修改支付密码失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
                                    ToastDialog.error(errorMsg).setDismissListener(new ToastDialog.OnDismissListener() {
                                        @Override
                                        public void onDismiss(ToastDialog dialog) {
                                            startActivity(new Intent(MyPasswordOnceNewPlayActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }).show(getSupportFragmentManager());
                                }
                            });

                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //这里显示出输入的字符串
            }
        });

        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        imageView_toLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);*/
    }
}
