package com.international.wtw.lottery.widget;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.SizeUtils;

import java.util.List;

/**
 * Created by XiaoXin on 2017/9/28.
 * 描述：
 */

public class LastLotteryView extends Fragment {

    public static final String GAME_CODE = "game_code";

    private ViewHolder mHolder = null;

    private int gameCode;

    public static LastLotteryView newInstance(int gameCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(GAME_CODE, gameCode);
        LastLotteryView view = new LastLotteryView();
        view.setArguments(bundle);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        gameCode = arguments.getInt(GAME_CODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        switch (gameCode) {
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京pk10
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                mHolder = new ViewHolder(inflater, container, R.layout.lottery_numers_pk10);
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://幸运农场
                mHolder = new ViewHolder(inflater, container, R.layout.lottery_numers_lucky_farm);
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                mHolder = new ViewHolder(inflater, container, R.layout.lottery_numers_mark_six);
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY://重庆时时彩
                mHolder = new ViewHolder(inflater, container, R.layout.lottery_numers_cj_lottery);
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                mHolder = new ViewHolder(inflater, container, R.layout.lottery_numers_lucky_28);
                break;
        }
        return mHolder == null ? null : mHolder.getRootView();
    }

    public void setLotteryData(List<String> lotteryNumbers, List<String> lotteryProperties) {
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京pk10
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                addLotteryNumber(lotteryNumbers, 25, false);
                int sum = sum(lotteryNumbers.get(0), lotteryNumbers.get(1));
                mHolder.setText(R.id.tv_sum, String.valueOf(sum));
                mHolder.setText(R.id.tv_dx, sum > 11 ? "大" : "小");
                mHolder.setText(R.id.tv_ds, sum % 2 == 0 ? "双" : "单");
                mHolder.setText(R.id.tv_lh1, lotteryNumbers.get(0).compareTo(lotteryNumbers.get(9)) > 0 ? "龙" : "虎");
                mHolder.setText(R.id.tv_lh2, lotteryNumbers.get(1).compareTo(lotteryNumbers.get(8)) > 0 ? "龙" : "虎");
                mHolder.setText(R.id.tv_lh3, lotteryNumbers.get(2).compareTo(lotteryNumbers.get(7)) > 0 ? "龙" : "虎");
                mHolder.setText(R.id.tv_lh4, lotteryNumbers.get(3).compareTo(lotteryNumbers.get(6)) > 0 ? "龙" : "虎");
                mHolder.setText(R.id.tv_lh5, lotteryNumbers.get(4).compareTo(lotteryNumbers.get(5)) > 0 ? "龙" : "虎");
                mHolder.get(R.id.ll_lottery_properties).setVisibility(View.VISIBLE);
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://幸运农场
                if (gameCode == Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY) {
                    addLotteryNumber(lotteryNumbers, 25, true);
                } else {
                    addLotteryNumber(lotteryNumbers, 25, false);
                }
                int sum2 = sum(lotteryNumbers);
                mHolder.setText(R.id.tv_sum, String.valueOf(sum2));
                mHolder.setText(R.id.tv_dx, sum2 > 85 ? "大" : "小");
                mHolder.setText(R.id.tv_ds, sum2 % 2 == 0 ? "双" : "单");
                mHolder.setText(R.id.tv_wei_dx, Integer.valueOf(lotteryNumbers.get(7)) > 11 ? "大" : "小");
                mHolder.setText(R.id.tv_lh1, lotteryNumbers.get(0).compareTo(lotteryNumbers.get(7)) > 0 ? "龙" : "虎");
                mHolder.setText(R.id.tv_lh2, lotteryNumbers.get(1).compareTo(lotteryNumbers.get(6)) > 0 ? "龙" : "虎");
                mHolder.setText(R.id.tv_lh3, lotteryNumbers.get(2).compareTo(lotteryNumbers.get(5)) > 0 ? "龙" : "虎");
                mHolder.setText(R.id.tv_lh4, lotteryNumbers.get(3).compareTo(lotteryNumbers.get(4)) > 0 ? "龙" : "虎");
                mHolder.get(R.id.ll_lottery_properties).setVisibility(View.VISIBLE);
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                for (int i = 0; i < lotteryNumbers.size(); i++) {
                    mHolder.setText(getResIdByIdentifier("tv_number" + i), lotteryNumbers.get(i));
                    mHolder.setText(getResIdByIdentifier("tv_property" + i), lotteryProperties.get(i));
                    mHolder.setBackgroundRes(getResIdByIdentifier("tv_number" + i),
                            LotteryUtil.get().getBackgroundRes(gameCode, lotteryNumbers.get(i)));
                }
                mHolder.get(R.id.gl_root).setVisibility(View.VISIBLE);
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY:
                addLotteryNumber(lotteryNumbers, 25, true);
                int sum3 = sum(lotteryNumbers);
                mHolder.setText(R.id.tv_sum, String.valueOf(sum3));
                mHolder.setText(R.id.tv_dx, sum3 > 23 ? "大" : "小");
                mHolder.setText(R.id.tv_ds, sum3 % 2 == 0 ? "双" : "单");
                String lh = lotteryNumbers.get(0).compareTo(lotteryNumbers.get(4)) > 0 ? "龙"
                        : (lotteryNumbers.get(0).compareTo(lotteryNumbers.get(4)) < 0 ? "虎" : "和");
                mHolder.setText(R.id.tv_lh, lh);
                mHolder.get(R.id.ll_lottery_properties).setVisibility(View.VISIBLE);
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                addLotteryNumber(lotteryNumbers, 25, true);
                int sum4 = sum(lotteryNumbers);
                mHolder.setText(R.id.tv_sum, String.valueOf(sum4));
                mHolder.setText(R.id.tv_dx, sum4 > 13 ? "大" : "小");
                mHolder.setText(R.id.tv_ds, sum4 % 2 == 0 ? "双" : "单");
                mHolder.get(R.id.ll_lottery_properties).setVisibility(View.VISIBLE);
                break;
        }
    }

    private void addLotteryNumber(List<String> lotteryNumbers, int size, boolean setText) {
        LinearLayout llLotteryNumbers = mHolder.get(R.id.ll_lottery_numbers);
        llLotteryNumbers.removeAllViews();
        for (int i = 0; i < lotteryNumbers.size(); i++) {
            TextView oneNumber = (TextView) getActivity().getLayoutInflater().inflate(R.layout.open_number_layout, null);
            if (setText) {
                oneNumber.setText(lotteryNumbers.get(i));
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(size), SizeUtils.dp2px(size));
            layoutParams.setMargins(0, 0, SizeUtils.dp2px(3), 0);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            oneNumber.setBackgroundResource(LotteryUtil.get().getBackgroundRes(gameCode, lotteryNumbers.get(i)));
            llLotteryNumbers.addView(oneNumber, layoutParams);
        }
    }

    private int getResIdByIdentifier(String name) {
        Application context = BaseApplication.getAppContext();
        return context.getResources().getIdentifier(name, "id", context.getPackageName());
    }

    private int sum(String... nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += Integer.valueOf(nums[i]);
        }
        return sum;
    }

    private int sum(List<String> nums) {
        int sum = 0;
        for (int i = 0; i < nums.size(); i++) {
            sum += Integer.valueOf(nums.get(i));
        }
        return sum;
    }
}
