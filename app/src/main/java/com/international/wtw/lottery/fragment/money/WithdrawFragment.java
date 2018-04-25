package com.international.wtw.lottery.fragment.money;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.manager.BankcardActivity;
import com.international.wtw.lottery.api.NetCallback;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.UserChangedEvent;
import com.international.wtw.lottery.json.AgAccountBalance;
import com.international.wtw.lottery.model.BankModel;
import com.international.wtw.lottery.model.UserModel;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.StringUtils;
import com.international.wtw.lottery.utils.UserHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 取款面页
 */
public class WithdrawFragment extends NewBaseFragment {

    public static final String GAME_NAME = "game_name";
    public static final int REQUEST_CODE_BIND_CARD = 0x01;

    @BindView(R.id.iv_bank_logo)
    ImageView mIvBankLogo;
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    @BindView(R.id.tv_bank_card_number)
    TextView mTvBankcardId;
    @BindView(R.id.rl_bankcard_info)
    RelativeLayout mRlBankInfo;
    @BindView(R.id.rl_add_bankcard)
    RelativeLayout mRlAddBank;
    @BindView(R.id.tv_account_balance)
    TextView mTvAccountBalance;
    @BindView(R.id.et_embodied_amount)
    EditText mEtAmount;
    @BindView(R.id.edt_tx_psd)
    EditText mEtPayPassword;
    //private boolean isDemo;//是否是试玩

    private String mBankName;
    private String mBankcardId;
    private String mAmount;
    private String mPayPassword;

    private String mGameName;
    private AgAccountBalance.BalanceBean agInfo;
    private BankModel mBankModel;
    private boolean isPrepare;
    private UserModel mUser;

    public static WithdrawFragment newInstance(String gameName) {
        WithdrawFragment fragment = new WithdrawFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GAME_NAME, gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_drawing;
    }

    @Override
    protected void initData() {
        isPrepare = true;
        if (getArguments() != null) {
            mGameName = getArguments().getString(GAME_NAME);
        }
        initMoneyInfo();
        //isDemo = SharePreferencesUtil.getBoolean(mActivity, LotteryId.IS_SHI_WAN, false);
        mEtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1 && "0".equals(s.toString())) {
                    s.clear();
                }
            }
        });
        if (getUserVisibleHint()) {
            getBankInfo();
        }

        //设置监听、4位数
        mEtPayPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    KeyBoardUtils.closeKeyboard(getActivity(), mEtPayPassword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initMoneyInfo() {
        mUser = UserHelper.get().getCurrUser();
        if (mUser != null) {
            mTvAccountBalance.setText(String.format(Locale.US, "(账户余额%s)", mUser.getBalance()));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isPrepare && isVisibleToUser) {
            getBankInfo();
        }
    }

    private void getBankInfo() {
        NetRepository.get().getBankInfo(this, new NetCallback2<BankModel>() {
            @Override
            public void onSuccess(BankModel data, int status, String msg) {
                //{"status":200,"response":[{"isNewRecord":true,"bankname":"工商银行","bankaccount":"622023100010011548","balance":0.00}]}
                if (data != null) {
                    setBankInfo(data);
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {

            }
        });
    }

    public void setBankInfo(BankModel data) {
        mBankModel = data;
        mBankName = data.getBankname();
        mBankcardId = data.getBankaccount();
        if (TextUtils.isEmpty(mBankName) || TextUtils.isEmpty(mBankcardId)) {
            mRlBankInfo.setVisibility(View.GONE);
            mRlAddBank.setVisibility(View.VISIBLE);
        } else {
            mRlBankInfo.setVisibility(View.VISIBLE);
            mRlAddBank.setVisibility(View.GONE);
            mTvBankName.setText(mBankName);
            mIvBankLogo.setBackgroundResource(getImageRes(mBankName));
            mTvBankcardId.setText(StringUtils.formatBankcardId(mBankcardId));
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEvent(AgAccountBalance.BalanceBean bean) {
        agInfo = bean;
        mTvAccountBalance.setText(String.format(Locale.US, "(%s余额%.2f)", mGameName, bean.getAgBalance()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserChangedEvent event) {
        mUser = event.mUser;
        mTvAccountBalance.setText(String.format(Locale.US, "(账户余额%s)", mUser != null ? mUser.getBalance() : "0.00"));
    }

    @OnClick({R.id.rl_bankcard_info, R.id.rl_add_bankcard, R.id.btn_confirm, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bankcard_info:
                toBankcardActivity(getString(R.string.modify_bank_info));
                break;
            case R.id.rl_add_bankcard:
                toBankcardActivity(getString(R.string.add_bank_info));
                break;
            case R.id.btn_confirm:
                withdraw();
                break;
            case R.id.btn_reset:
                resetInput();
                break;
        }
    }

    /**
     * 添加或许编辑(修改)银行卡
     */
    private void toBankcardActivity(String title) {
        Intent intent = new Intent(getActivity(), BankcardActivity.class);
        intent.putExtra(BankcardActivity.TITLE, title);
        intent.putExtra(BankcardActivity.BANK_INFO, mBankModel);
        startActivityForResult(intent, REQUEST_CODE_BIND_CARD);
    }

    /**
     * 取款
     */
    private void withdraw() {
        mAmount = mEtAmount.getText().toString().trim();
        mPayPassword = mEtPayPassword.getText().toString().trim();
        if (mUser == null || ("AG".equals(mGameName) && agInfo == null)) {
            ToastDialog.warning(getString(R.string.is_loading_user_info)).show(getFragmentManager());
        } else if (TextUtils.isEmpty(mBankName) || TextUtils.isEmpty(mBankcardId)) {
            ToastDialog.warning(getString(R.string.bind_bankcard_first)).show(getFragmentManager());
        } else if (TextUtils.isEmpty(mAmount)) {
            ToastDialog.warning(getString(R.string.input_tx_amount)).show(getFragmentManager());
        } else if (Double.valueOf(mAmount.replace(",", "")) < 100.00) {
            ToastDialog.warning(getString(R.string.money_beyond1)).show(getFragmentManager());
        } else {
            double balance = "AG".equals(mGameName) ? agInfo.getAgBalance() : Double.valueOf(mUser.getBalance());
            if (Double.valueOf(mAmount.replace(",", "")) > balance) {
                ToastDialog.warning(getString(R.string.money_beyond)).show(getFragmentManager());
            } else if (TextUtils.isEmpty(mPayPassword)) {
                ToastDialog.warning(getString(R.string.input_tx_pwd)).show(getFragmentManager());
            } else if (mPayPassword.length() != 4) {
                ToastDialog.warning(getString(R.string.input_tx_pwd1)).show(getFragmentManager());
            } else {
                requestWithdraw();
            }
        }
    }

    private void resetInput() {
        mEtAmount.setText("");
        mEtPayPassword.setText("");
    }

    /**
     * 请求提款接口进行提款
     */
    private void requestWithdraw() {
        showLoadingDialog();
        NetRepository.get().withdraw(this, mBankcardId, mAmount, mPayPassword, new NetCallback<Object>() {
            @Override
            public void onSuccess(Object data, int status, String msg, int total) {
                dismissLoadingDialog();
                ToastDialog.success(getString(R.string.operation_success)).show(getFragmentManager());
                resetInput();
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                dismissLoadingDialog();
                if ("AG".equals(mGameName)) {
                    //MoneyInfoManager.get().requestAgInfo();
                }
                ToastDialog.warning(errorMsg).show(getFragmentManager());
            }
        });
    }

    private int getImageRes(String bankName) {
        switch (bankName) {
            case "工商银行":
                return R.mipmap.icon_bank_logo_icbc;
            case "建设银行":
                return R.mipmap.icon_bank_logo_ccb;
            case "招商银行":
                return R.mipmap.icon_bank_logo_cmb;
            case "农业银行":
                return R.mipmap.icon_bank_logo_abc;
            case "中国银行":
                return R.mipmap.icon_bank_logo_bc;
            case "邮政储蓄银行":
                return R.mipmap.icon_bank_logo_psbc;
            case "民生银行":
                return R.mipmap.icon_bank_logo_cmbc;
            case "兴业银行":
                return R.mipmap.icon_bank_logo_ibc;
            case "中信银行":
                return R.mipmap.icon_bank_logo_cib;
            case "渤海银行":
                return R.mipmap.icon_bank_logo_cbhb;
            case "光大银行":
                return R.mipmap.icon_bank_logo_ceb;
            case "广发银行":
                return R.mipmap.icon_bank_logo_gdb;
            case "华夏银行":
                return R.mipmap.icon_bank_logo_hxb;
            case "平安银行":
                return R.mipmap.icon_bank_logo_pab;
            case "浦发银行":
                return R.mipmap.icon_bank_logo_spdb;
            case "北京农商银行":
                return R.mipmap.icon_bank_logo_brcb;
            case "上海银行":
                return R.mipmap.icon_bank_logo_bs;
            case "交通银行":
                return R.mipmap.icon_bank_logo_bcomm;
        }
        return R.mipmap.bank_car_icon;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BIND_CARD && data != null) {
            getBankInfo();
        }
    }
}
