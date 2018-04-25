package com.international.wtw.sports.fragment.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.adapter.FragmentAdapter;
import com.international.wtw.sports.base.app.NewBaseFragment;
import com.international.wtw.sports.dialog.ToastDialog;
import com.international.wtw.sports.event.UserChangedEvent;
import com.international.wtw.sports.fragment.money.RechargeFragment;
import com.international.wtw.sports.fragment.money.TradeRecordFragment;
import com.international.wtw.sports.fragment.money.WithdrawFragment;
import com.international.wtw.sports.model.UserModel;
import com.international.wtw.sports.utils.KeyBoardUtils;
import com.international.wtw.sports.utils.UserHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述：资金管理页面
 */

public class MoneyManageFragment extends NewBaseFragment {

    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_account_balance)
    TextView mTvAccountBalance;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private RechargeFragment mRechargeFragment;    //入款
    private WithdrawFragment mWithdrawFragment;   //出款
    private TradeRecordFragment mRecordFragment;  //交易记录
    private int position = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money_manage;
    }

    @Override
    protected void initView() {
        if (null == mRechargeFragment) {
            mRechargeFragment = new RechargeFragment();
        }
        if (null == mWithdrawFragment) {
            mWithdrawFragment = new WithdrawFragment();
        }
        if (null == mRecordFragment) {
            mRecordFragment = new TradeRecordFragment();
        }
        mFragments.clear();
        mFragments.add(mRechargeFragment);
        mFragments.add(mWithdrawFragment);
        mFragments.add(mRecordFragment);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setCurrentItem(position);
        mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
        //注册监听
        initListener();
    }

    @Override
    protected void initData() {
        UserModel user = UserHelper.get().getCurrUser();
        if (user == null) {
            ToastDialog.error("您的账号已失效，请重新登录").show(getFragmentManager());
            UserHelper.get().userSignOut();
        } else {
            mTvUserName.setText(user.getUsername());
            mTvAccountBalance.setText(user.getBalance());
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserChangedEvent event) {
        if (event.mUser != null) {
            mTvUserName.setText(event.mUser.getUsername());
            mTvAccountBalance.setText(event.mUser.getBalance());
        } else {
            mTvUserName.setText("");
            mTvAccountBalance.setText("0.00");
        }
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioGroup.check(R.id.rb_deposit);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.rb_withdraw);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.rb_record);
                        break;
                }
                KeyBoardUtils.hideInputForce(mActivity);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_deposit:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_withdraw:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_record:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                KeyBoardUtils.hideInputForce(mActivity);
            }
        });

    }

    public void setCurrentPosition(int moneyIndex) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(moneyIndex);
        } else {
            position = moneyIndex;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRechargeFragment != null) {
            mRechargeFragment.setUserVisibleHint(!hidden);
        }
    }
}
