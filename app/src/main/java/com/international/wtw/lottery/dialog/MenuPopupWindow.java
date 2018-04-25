package com.international.wtw.lottery.dialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.mine.BetOnRecordActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.event.UserChangedEvent;
import com.international.wtw.lottery.model.UserModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.SizeUtils;
import com.international.wtw.lottery.utils.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MenuPopupWindow extends BaseCustomPopup implements View.OnClickListener {

    private FragmentActivity mActivity;
    private TextView mTvBalance;

    public MenuPopupWindow(FragmentActivity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_main_menu, SizeUtils.dp2px(210), ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setAnimationStyle(R.style.AnimDown);
    }

    @Override
    protected void initViews(View view) {
        mTvBalance = getView(R.id.tv_balance);
        getView(R.id.tv_recharge).setOnClickListener(this);
        getView(R.id.tv_withdraw).setOnClickListener(this);
        getView(R.id.tv_bet_record).setOnClickListener(this);
        getView(R.id.tv_game_rule).setOnClickListener(this);
        getView(R.id.tv_personal_center).setOnClickListener(this);
        getView(R.id.tv_service).setOnClickListener(this);
        getView(R.id.tv_log_out).setOnClickListener(this);
    }

    @Override
    public void showAtAnchorView(@NonNull View anchor, @VerticalGravity int vertGravity, @HorizontalGravity int horizGravity) {
        EventBus.getDefault().register(this);
        setBalance();
        super.showAtAnchorView(anchor, vertGravity, horizGravity);
    }

    @Override
    public void onPopupWindowDismiss() {
        super.onPopupWindowDismiss();
        EventBus.getDefault().unregister(this);
    }

    private void setBalance() {
        UserModel user = UserHelper.get().getCurrUser();
        mTvBalance.setText(user == null ? "¥" + "0.00" : "¥" + user.getBalance());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserChangedEvent event) {
        if (getPopupWindow().isShowing()) {
            UserModel user = event.mUser;
            mTvBalance.setText(user == null ? "¥" + "0.00" : "¥" + user.getBalance());
        }
    }

    @Override
    public void onClick(View v) {
        UserModel user = UserHelper.get().getCurrUser();
        boolean aBoolean = user != null && !user.isVisitor();
        switch (v.getId()) {
            case R.id.tv_recharge:
                if (aBoolean) {
                    if (mActivity instanceof MainActivity) {
                        MainActivity activty = (MainActivity) mActivity;
                        activty.showMoneyFragment(0);
                    } else {
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_FRAGMENT_INDEX, 2);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_MONEY_INDEX, 0);
                        mActivity.startActivity(intent);
                    }
                } else {
                    ToastDialog.error(mActivity.getString(R.string.login_is_shiwan)).show(mActivity.getSupportFragmentManager());
                }
                break;
            case R.id.tv_withdraw:
                if (aBoolean) {
                    if (mActivity instanceof MainActivity) {
                        MainActivity activty = (MainActivity) mActivity;
                        activty.showMoneyFragment(1);
                    } else {
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_FRAGMENT_INDEX, 2);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_MONEY_INDEX, 1);
                        mActivity.startActivity(intent);
                    }
                } else {
                    ToastDialog.error(mActivity.getString(R.string.login_is_shiwan)).show(mActivity.getSupportFragmentManager());
                }
                break;
            case R.id.tv_bet_record:
                mActivity.startActivity(new Intent(mActivity, BetOnRecordActivity.class));
                break;
            case R.id.tv_game_rule:
                Intent intent1 = new Intent(mActivity, WebViewActivity.class);
                intent1.putExtra(WebViewActivity.EXTRA_WEB_TITLE, "游戏规则");
                //暂时上测试站的链接
                intent1.putExtra(WebViewActivity.EXTRA_WEB_URL, "https://wap.alcp88.com/#/todayRule");
                mActivity.startActivity(intent1);
                break;
            case R.id.tv_personal_center:
                if (mActivity instanceof MainActivity) {
                    MainActivity activty = (MainActivity) mActivity;
                    activty.changeShowFragment(1);
                } else {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    intent.putExtra("to_mine_frag", true);
                    mActivity.startActivity(intent);
                }
                break;
            case R.id.tv_service:
                String serviceUrl = SharePreferencesUtil.getString(mActivity, LotteryId.SERVICE_URL, "");
                if (!TextUtils.isEmpty(serviceUrl)) {
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, mActivity.getString(R.string.service_online));
                    intent.putExtra(WebViewActivity.EXTRA_WEB_URL, serviceUrl);
                    intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                    mActivity.startActivity(intent);
                }
                break;
            case R.id.tv_log_out:
                createDialog();
                break;
        }
        dismiss();
    }

    private void createDialog() {
        if (null == mActivity)
            return;
        View view = LayoutInflater.from(mActivity).inflate(R.layout.out_login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setView(view);
        builder.setCancelable(false);
        builder.create();
        final AlertDialog dialog = builder.show();

        Button btn_out_login_cancel = (Button) view.findViewById(R.id.btn_out_login_cancel);
        Button btn_out_login_ok = (Button) view.findViewById(R.id.btn_out_login_ok);

        btn_out_login_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_out_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                dialog.dismiss();
            }
        });
    }

    private void logout() {
        NetRepository.get().logout();
        UserHelper.get().userSignOut();
    }
}
