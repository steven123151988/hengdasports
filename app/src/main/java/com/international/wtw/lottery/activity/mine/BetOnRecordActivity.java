package com.international.wtw.lottery.activity.mine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.NetCallback;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.BetPopupWindow;
import com.international.wtw.lottery.dialog.easypopup.EasyPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.fragment.betonrecord.AllOrdersFragment;
import com.international.wtw.lottery.fragment.betonrecord.HaveOrderFragment;
import com.international.wtw.lottery.fragment.betonrecord.OutStandOrdersFragment;
import com.international.wtw.lottery.json.OutHaveListBean;
import com.international.wtw.lottery.utils.LogUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/13.  下注记录
 */

public class BetOnRecordActivity extends BaseActivity implements View.OnClickListener {

    private AllOrdersFragment allOrdersFragment;
    private OutStandOrdersFragment outStandOrdersFragment;
    private HaveOrderFragment haveOrderFragment;

    private FragmentManager manager;

    private LinearLayout ll_all_order, ll_have_order;
    private RelativeLayout ll_outstand_order;

    private TextView tv_all_order;
    private TextView tv_outstand_order;
    private TextView tv_have_order;

    private ImageView iv_back;

    private View view_all_order, view_outstand_order, view_have_order;

    private TextView tv_out_num;

    private RelativeLayout rl_iswin;
    private ImageView img_iswin;
    private EasyPopup mMenuPopup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_betonrecord;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        manager = getFragmentManager();

        InitFragments();

        InitView();
        setTabSelection(0);
    }

    private void InitFragments() {
        FragmentTransaction ft = manager.beginTransaction();

        allOrdersFragment = new AllOrdersFragment();
        ft.add(R.id.frame_content, allOrdersFragment);

        outStandOrdersFragment = new OutStandOrdersFragment();
        ft.add(R.id.frame_content, outStandOrdersFragment);

        haveOrderFragment = new HaveOrderFragment();
        ft.add(R.id.frame_content, haveOrderFragment);

        ft.commitAllowingStateLoss();
    }

    private void InitView() {
        ll_all_order = (LinearLayout) findViewById(R.id.ll_all_order);
        ll_outstand_order = (RelativeLayout) findViewById(R.id.ll_outstand_order);
        ll_have_order = (LinearLayout) findViewById(R.id.ll_have_order);

        tv_all_order = (TextView) findViewById(R.id.tv_all_order);
        tv_outstand_order = (TextView) findViewById(R.id.tv_outstand_order);
        tv_have_order = (TextView) findViewById(R.id.tv_have_order);

        view_all_order = findViewById(R.id.view_all_order);
        view_outstand_order = findViewById(R.id.view_outstand_order);
        view_have_order = findViewById(R.id.view_have_order);

        ll_all_order.setOnClickListener(this);
        ll_outstand_order.setOnClickListener(this);
        ll_have_order.setOnClickListener(this);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_out_num = (TextView) findViewById(R.id.tv_out_num);

        rl_iswin = (RelativeLayout) findViewById(R.id.rl_iswin);
        rl_iswin.setOnClickListener(this);

        img_iswin = (ImageView) findViewById(R.id.img_iswin);
        img_iswin.setOnClickListener(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);

        //        SetData(time);

        SetDataOut(1, 20);
    }

    private void SetDataOut(int page, int num) {
        NetRepository.get().lotteryBill(this, page, num, new NetCallback<List<OutHaveListBean>>() {
            @Override
            public void onSuccess(List<OutHaveListBean> data, int status, String msg, int total) {
                LogUtil.e("未结成功---onSuccess---" + "-status-" + status + "-msg-" + msg);
                if (data != null) {
                    if (total != 0) {
                        tv_out_num.setText(total + "");
                        tv_out_num.setVisibility(View.VISIBLE);
                    }
                } else {
                    LogUtil.e("未结成功-data-" + data);
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e("未结失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all_order:
                setTabSelection(0);
                break;
            case R.id.ll_outstand_order:
                setTabSelection(1);
                break;
            case R.id.ll_have_order:
                setTabSelection(2);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.img_iswin:
                SetMenu();
                break;
            case R.id.rl_iswin:
                SetMenu();
                break;
        }
    }

    public void SetMenu() {
        if (mMenuPopup == null) {
            mMenuPopup = new BetPopupWindow(this)
                    .createPopup();
        }
        mMenuPopup.showAtAnchorView(view_have_order, VerticalGravity.BELOW, HorizontalGravity.CENTER);
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                view_all_order.setBackgroundResource(R.color.color_primary);
                if (allOrdersFragment == null) {
                    allOrdersFragment = new AllOrdersFragment();
                    transaction.add(R.id.frame_content, allOrdersFragment);
                } else {
                    transaction.show(allOrdersFragment);
                }
                break;
            case 1:
                view_outstand_order.setBackgroundResource(R.color.color_primary);
                if (outStandOrdersFragment == null) {
                    outStandOrdersFragment = new OutStandOrdersFragment();
                    transaction.add(R.id.frame_content, outStandOrdersFragment);
                } else {
                    transaction.show(outStandOrdersFragment);
                }
                break;
            case 2:
                view_have_order.setBackgroundResource(R.color.color_primary);
                if (haveOrderFragment == null) {
                    haveOrderFragment = new HaveOrderFragment();
                    transaction.add(R.id.frame_content, haveOrderFragment);
                } else {
                    transaction.show(haveOrderFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void clearSelection() {
        ll_all_order.setBackgroundDrawable(null);
        ll_outstand_order.setBackgroundDrawable(null);
        ll_have_order.setBackgroundDrawable(null);

        view_all_order.setBackgroundDrawable(null);
        view_outstand_order.setBackgroundDrawable(null);
        view_have_order.setBackgroundDrawable(null);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (allOrdersFragment != null) {
            transaction.hide(allOrdersFragment);
        }
        if (outStandOrdersFragment != null) {
            transaction.hide(outStandOrdersFragment);
        }
        if (haveOrderFragment != null) {
            transaction.hide(haveOrderFragment);
        }
    }

    public String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime() / 1000;
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

}
