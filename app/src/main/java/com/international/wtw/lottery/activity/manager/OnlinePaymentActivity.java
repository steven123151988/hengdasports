package com.international.wtw.lottery.activity.manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.adapter.AmountGridAdapter;
import com.international.wtw.lottery.adapter.ChannelGridAdapter;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.dialog.SelectBankDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.event.PaymentCompletedEvent;
import com.international.wtw.lottery.json.OnlinePayChannel;
import com.international.wtw.lottery.json.OnlinePayModel;
import com.international.wtw.lottery.model.OnlinePayUrl;
import com.international.wtw.lottery.utils.KeyBoardUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 线上入款面页
 */
public class OnlinePaymentActivity extends NewBaseActivity {

    public static final String PAY_IN_DATA = "pay_in_data";

    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.iv_pay_type)
    ImageView mIvPayType;
    @BindView(R.id.ll_select_bank)
    LinearLayout mLlSelectBank;
    @BindView(R.id.tv_select_bankcard)
    TextView mTvSelectBankcard;
    @BindView(R.id.et_recharge_amount)
    EditText mEtRechargeAmount;
    @BindView(R.id.gv_channel)
    GridView mGvChannel;
    @BindView(R.id.tv_channel_notice)
    TextView mChannelNotice;
    @BindView(R.id.gv_recharge_amount)
    GridView mGvRechargeAmount;
    @BindView(R.id.tv_money_limit)
    TextView mTvMoneyLimit;
    private AmountGridAdapter mAmountGridAdapter;
    //是否需要选择银行
    private boolean isShowBank;
    //存款金额
    private String mAmount;
    //银行名称
    private String mBankName = "银联支付";
    private BaseNiceDialog mBankDialog;
    private OnlinePayModel mModel;
    private OnlinePayChannel mPayChannel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_payment;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        //获取上一个页面传过来的参数
        mModel = (OnlinePayModel) getIntent().getSerializableExtra(PAY_IN_DATA);
        mTvPayType.setText(TextUtils.isEmpty(mModel.getPayName()) ? getString(R.string.online_pay) : mModel.getPayName());
        mPayChannel = mModel.channel.get(0);
        mLlSelectBank.setVisibility(isShowBank ? View.VISIBLE : View.GONE);
        mIvPayType.setImageResource(mModel.getLargeLogoRes());
        initMoneyLimit();
        if (mModel.channel.size() > 1) {
            mGvChannel.setVisibility(View.VISIBLE);
            mChannelNotice.setVisibility(View.VISIBLE);
            initChannelGridView();
        } else {
            mGvChannel.setVisibility(View.GONE);
            mChannelNotice.setVisibility(View.GONE);
        }
        initAmountGridView();
    }

    private void initMoneyLimit() {
        String str = String.format(this.getString(R.string.amount_limit_tip2), mPayChannel.minimumAmount, mPayChannel.maximumAmount);
        int start1 = str.indexOf("下限");
        int start2 = str.indexOf("上限");
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE), start1 + 2, str.indexOf("，"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE), start2 + 2, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvMoneyLimit.setText(builder);
        mEtRechargeAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!text.equals("")) {
                    if (text.length() == 1 && text.equals("0")) {
                        s.clear();
                    }
                    int aFloat = Integer.parseInt(text);
                    if (aFloat > mPayChannel.maximumAmount) {
                        String s2 = String.valueOf(mPayChannel.maximumAmount);
                        mEtRechargeAmount.setText(s2);
                        mEtRechargeAmount.setSelection(s2.length());
                    }
                }
            }
        });
    }

    private void initChannelGridView() {
        ChannelGridAdapter adapter = new ChannelGridAdapter(this, mModel.channel);
        mGvChannel.setAdapter(adapter);
        mGvChannel.setItemChecked(0, true);
        mGvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPayChannel = mModel.channel.get(position);
                initMoneyLimit();
                //isShowBank = mModel.type == 0 && mPayChannel.payUrl.contains("isShowBank=1");
            }
        });
    }

    private void initAmountGridView() {
        String[] amounts = {"50", "100", "300", "500", "800", "1000", "2000", "3000"};
        mAmountGridAdapter = new AmountGridAdapter(this, amounts);
        mGvRechargeAmount.setAdapter(mAmountGridAdapter);
        mGvRechargeAmount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String amount = (String) parent.getAdapter().getItem(position);
                mEtRechargeAmount.setText(amount);
                //光标放最后
                mEtRechargeAmount.setSelection(mEtRechargeAmount.getText().length());
            }
        });
    }

    @OnClick({R.id.img_back, R.id.ll_select_bank, R.id.btn_confirm, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.ll_select_bank:
                showSelectBankDialog();
                break;
            case R.id.btn_confirm:
                commitRecharge();
                break;
            case R.id.btn_reset:
                resetAmount();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new PaymentCompletedEvent());
        super.onBackPressed();
    }

    private void showSelectBankDialog() {
        if (mBankDialog == null) {
            mBankDialog = SelectBankDialog.newInstance()
                    .setListener(new SelectBankDialog.BankSelectListener() {
                        @Override
                        public void onBankSelect(String bank) {
                            mBankName = bank;
                            mTvSelectBankcard.setText(bank);
                        }
                    })
                    .setAnimStyle(R.style.window_bottom_in_bottom_out)
                    .setShowBottom(true);
        }
        mBankDialog.showDialog(getSupportFragmentManager());
    }

    private void commitRecharge() {
        KeyBoardUtils.closeKeyboard(this, mEtRechargeAmount);
        mAmount = mEtRechargeAmount.getText().toString().trim();
        //如果最后一位是'.', 去掉该小数点
        if (mAmount.indexOf(".") == mAmount.length() - 1) {
            mAmount = mAmount.replace(".", "");
        }
        if (isShowBank && TextUtils.isEmpty(mTvSelectBankcard.getText().toString().trim())) {
            //如果需要显示银行卡
            ToastDialog.error(getString(R.string.no_select_bank)).show(getSupportFragmentManager());
        } else if (TextUtils.isEmpty(mAmount)) {
            //如果金额为空 提示并返回
            ToastDialog.error(getString(R.string.input_amount)).show(getSupportFragmentManager());
        } else {
            float amount = Float.parseFloat(mAmount);
            if (amount > mPayChannel.maximumAmount) {
                ToastDialog.error(String.format(getString(R.string.amount_max_out_of2), mPayChannel.maximumAmount)).show(getSupportFragmentManager());
            } else if (amount < mPayChannel.minimumAmount) {
                ToastDialog.error(String.format(getString(R.string.amount_min_out_of2), mPayChannel.minimumAmount)).show(getSupportFragmentManager());
            } else {
                requestOnlinePayment();
            }
        }
    }

    private void resetAmount() {
        mGvRechargeAmount.clearChoices();
        mAmountGridAdapter.notifyDataSetChanged();
        mEtRechargeAmount.setText("");
    }

    private void requestOnlinePayment() {
        showProgressDialog(getString(R.string.loading));
        NetRepository.get().onlinePayment(this, mAmount, mPayChannel, new NetCallback2<OnlinePayUrl>() {
                    @Override
                    public void onSuccess(OnlinePayUrl data, int status, String msg) {
                        dismissProgressDialog();
                        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                        intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, mTvPayType.getText().toString());
                        intent.putExtra(WebViewActivity.EXTRA_WEB_URL, data.getUrl());
                        intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int status, String errorMsg) {
                        dismissProgressDialog();
                        ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                    }
                });
    }
}