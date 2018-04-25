package com.international.wtw.lottery.activity.login;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.mine.ForgetPwdActivity;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.model.UserModel;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.RandomCode;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.UserHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private EditText edittext_yzm;
    private ImageView iv_showCode, img_show_pwd;
    private String realCode;  //产生的验证码
    private boolean isShow;
    private View view1, view2, view3;
    private boolean isChecked = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(ViewHolder holder, View root) {
        username = holder.get(R.id.edittext_account);
        password = holder.get(R.id.edittext_pwd);

        holder.setOnClickListener(this, R.id.btn_login);
        holder.setOnClickListener(this, R.id.btn_regist);
        holder.setOnClickListener(this, R.id.textView_forgetPwd);
        holder.setOnClickListener(this, R.id.imageView_toLeftArrow);
        holder.setOnClickListener(this, R.id.bt_shi_wan);

        edittext_yzm = holder.get(R.id.edittext_yzm);

        iv_showCode = holder.get(R.id.iv_showCode);
        iv_showCode.setImageBitmap(RandomCode.getInstance().createBitmap());
        realCode = RandomCode.getInstance().getCode().toLowerCase();
        iv_showCode.setOnClickListener(this);

        img_show_pwd = holder.get(R.id.img_show_pwd);
        img_show_pwd.setOnClickListener(this);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        if (username.getText().length() > 0) {
            view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
        } else {
            view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
        }
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
                } else {
                    view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view2.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
                } else {
                    view2.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edittext_yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view3.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
                    KeyBoardUtils.openKeyboard(LoginActivity.this, edittext_yzm);
                } else {
                    view3.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
                    if (s.length() == 4) {
                        KeyBoardUtils.closeKeyboard(LoginActivity.this, edittext_yzm);
                    } else {
                        KeyBoardUtils.openKeyboard(LoginActivity.this, edittext_yzm);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*
         *  选择是否记住密码
         */
        isChecked = SharePreferencesUtil.getBoolean(getApplicationContext(), LotteryId.IF_REMEMBER_PASSWORD, false);
        CheckBox cbx = (CheckBox) findViewById(R.id.checkbox);
        cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                isChecked = checked;
                if (checked) {
                    SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IF_REMEMBER_PASSWORD, true);
                } else {
                    SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IF_REMEMBER_PASSWORD, false);
                }
            }
        });
        cbx.setChecked(isChecked);
        username.setText(SharePreferencesUtil.getString(getApplicationContext(), LotteryId.Login_username, ""));
        if (isChecked) {
            password.setText(SharePreferencesUtil.getString(getApplicationContext(), LotteryId.USER_PSW, ""));
        } else {
            password.setText("");
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                String name = username.getText().toString();
                String pwd = password.getText().toString();
                String yzm = edittext_yzm.getText().toString();
                if (name.isEmpty()) {
                    ToastDialog.error(getString(R.string.type_in_username)).show(getSupportFragmentManager());
                    return;
                }
                if (pwd.isEmpty()) {
                    ToastDialog.error(getString(R.string.pwd_empty_fail)).show(getSupportFragmentManager());
                    return;
                }
                if (pwd.length() < 6 || pwd.length() > 15) {
                    ToastDialog.error(getString(R.string.pwd_length_fail)).show(getSupportFragmentManager());
                    return;
                }

                if (TextUtils.isEmpty(edittext_yzm.getText().toString().trim())) {
                    ToastDialog.error(getString(R.string.yzm_empty)).show(getSupportFragmentManager());
                    return;
                }

                if (!yzm.equals(realCode)) {
                    ToastDialog.error(getString(R.string.yzm_fail)).show(getSupportFragmentManager());
                    return;
                }

                NetRepository.get().login(this, name, pwd, new NetCallback2<UserModel>() {
                    @Override
                    public void onSuccess(UserModel data, int status, String msg) {
                        LogUtil.e("登录成功---onSuccess---" + "-status-" + status + "-msg-" + msg);
                        UserHelper.get().setCurrUser(data);
                        dealRememberAccount(isChecked, name, pwd);
                        ToastDialog.success(msg).setDismissListener(new ToastDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(ToastDialog dialog) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }).show(getSupportFragmentManager());
                    }

                    @Override
                    public void onFailure(int status, String errorMsg) {
                        LogUtil.e("登录失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
                        ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                    }
                });
                break;

            case R.id.btn_regist:
                openActivity(RegisterActivity.class);
                break;

            case R.id.textView_forgetPwd:
                openActivity(ForgetPwdActivity.class);
                break;

            case R.id.imageView_toLeftArrow:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.iv_showCode:
                iv_showCode.setImageBitmap(RandomCode.getInstance().createBitmap());
                realCode = RandomCode.getInstance().getCode().toLowerCase();
                break;

            case R.id.img_show_pwd:
                if (isShow) {
                    img_show_pwd.setImageResource(R.mipmap.no_see_pwd);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShow = false;
                } else {
                    img_show_pwd.setImageResource(R.mipmap.see_pwd);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShow = true;
                }
                break;
            //试玩
            case R.id.bt_shi_wan:
                NetRepository.get().loginDemo(this, new NetCallback2<UserModel>() {
                    @Override
                    public void onSuccess(UserModel data, int status, String msg) {
                        LogUtil.e("登录试玩成功---onSuccess---" + "-status-" + status + "-msg-" + msg);
                        if (data != null) {
                            UserHelper.get().setCurrUser(data);
                            ToastDialog.success(msg).setDismissListener(new ToastDialog.OnDismissListener() {
                                @Override
                                public void onDismiss(ToastDialog dialog) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }).show(getSupportFragmentManager());
                        }
                    }

                    @Override
                    public void onFailure(int status, String errorMsg) {
                        LogUtil.e("登录试玩失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
                        ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                    }
                });
                break;
        }
    }


    private void dealRememberAccount(boolean isRemember, String username, String password) {
        SharePreferencesUtil.addString(BaseApplication.getAppContext(), LotteryId.Login_username, username);
        SharePreferencesUtil.addBoolean(BaseApplication.getAppContext(), LotteryId.IF_REMEMBER_PASSWORD, isRemember);
        if (isRemember) {
            SharePreferencesUtil.addString(BaseApplication.getAppContext(), LotteryId.USER_PSW, password);
        } else {
            SharePreferencesUtil.addString(BaseApplication.getAppContext(), LotteryId.USER_PSW, "");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtils.isShouldHideInput(v, ev)) {
                if (KeyBoardUtils.closeKeyboard(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
