package com.international.wtw.lottery.activity.manager;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.NetCallback;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.SelectBankDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.fragment.money.WithdrawFragment;
import com.international.wtw.lottery.model.BankModel;

import butterknife.BindView;
import butterknife.OnClick;

public class BankcardActivity extends BaseActivity {

    public static final String TITLE = "title";
    public static final String BANK_INFO = "bank_info";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_real_name)
    LinearLayout mLlRealName;
    @BindView(R.id.tv_real_name)
    TextView mTvRealName;
    @BindView(R.id.tv_select_bankcard)
    TextView mTvSelectBankcard;
    @BindView(R.id.et_bankcard_number)
    EditText mEtBankNo;
    @BindView(R.id.et_bankcard_number_again)
    EditText mEtBankNoAgain;
    @BindView(R.id.et_open_address)
    EditText mEtOpenAddress;
    private BaseNiceDialog mBankDialog;
    private BankModel mBankInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_bankcard;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTvTitle.setText(getIntent().getStringExtra(TITLE));
        mBankInfo = (BankModel) getIntent().getSerializableExtra(BANK_INFO);
        if (mBankInfo != null) {
            setBankcardData(mBankInfo);
        }
    }

    private void setBankcardData(BankModel data) {
        /*if (TextUtils.isEmpty(data.getUser_name())) {
            mLlRealName.setVisibility(View.GONE);
        } else {
            mLlRealName.setVisibility(View.VISIBLE);
            mTvRealName.setText(data.getUser_name());
        }*/
        mTvSelectBankcard.setText(data.getBankname());
        mEtBankNo.setText(data.getBankaccount());
        //mEtOpenAddress.setText(data.getBank_address());
    }

    @OnClick({R.id.img_back, R.id.ll_select_bank, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_select_bank:
                showSelectBankDialog();
                break;
            case R.id.btn_commit:
                commitAddOrModify();
                break;
        }
    }

    private void showSelectBankDialog() {
        if (mBankDialog == null) {
            mBankDialog = SelectBankDialog.newInstance()
                    .setListener(new SelectBankDialog.BankSelectListener() {
                        @Override
                        public void onBankSelect(String bank) {
                            mTvSelectBankcard.setText(bank);
                        }
                    })
                    .setAnimStyle(R.style.window_bottom_in_bottom_out)
                    .setShowBottom(true);
        }
        mBankDialog.showDialog(getSupportFragmentManager());
    }

    private void commitAddOrModify() {
        String bankName = mTvSelectBankcard.getText().toString().trim();
        String bankNo = mEtBankNo.getText().toString().trim();
        //String bankNoAgain = mEtBankNoAgain.getText().toString().trim();
        String bankAddress = mEtOpenAddress.getText().toString().trim();
        //检查是否选择了银行
        if (TextUtils.isEmpty(bankName)) {
            ToastDialog.error(getString(R.string.no_select_bank)).show(getSupportFragmentManager());
            return;
        }
        //检查银行卡号格式是否正确
        if (TextUtils.isEmpty(bankNo) || bankNo.length() < 16 || bankNo.length() > 19) {
            ToastDialog.error(getString(R.string.correct_card_num)).show(getSupportFragmentManager());
            return;
        }
        /*if (TextUtils.isEmpty(bankNoAgain)) {
            ToastDialog.error(getString(R.string.input_card_num_again)).show(getSupportFragmentManager());
            return;
        }
        //检查两次输入的银行卡号是否相同
        if (!bankNo.equals(bankNoAgain)) {
            ToastDialog.error(getString(R.string.bankcard_number_different)).show(getSupportFragmentManager());
            return;
        }*/
        if (TextUtils.isEmpty(bankAddress)) {
            ToastDialog.error(getString(R.string.input_bank_address));
            return;
        }

        String bankId = mBankInfo != null ? mBankInfo.getId() : null;

        requestAddOrModifyBankcard(bankId, bankName, bankNo, bankAddress);
    }

    /**
     * 请求修改或添加银行卡接口
     */
    private void requestAddOrModifyBankcard(String bankId, String bankName, String bankNo, String bankAddress) {
        NetRepository.get().bindBankcard(this, bankId, bankNo, bankName, bankAddress, new NetCallback<Object>() {
            @Override
            public void onSuccess(Object data, int status, String msg, int total) {
                ToastDialog.success(getString(R.string.operation_success))
                        .setDismissListener(new ToastDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(ToastDialog dialog) {
                                Intent intent = new Intent();
                                intent.putExtra("bankcard_no", bankNo);
                                intent.putExtra("bank_name", bankName);
                                setResult(WithdrawFragment.REQUEST_CODE_BIND_CARD, intent);
                                finish();
                            }
                        }).show(getSupportFragmentManager());
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
            }
        });
    }
}
