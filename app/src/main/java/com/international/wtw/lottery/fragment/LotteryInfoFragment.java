package com.international.wtw.lottery.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.event.UserChangedEvent;
import com.international.wtw.lottery.model.TimeInfo;
import com.international.wtw.lottery.model.UserModel;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.TimeFormatter;
import com.international.wtw.lottery.utils.UserHelper;
import com.international.wtw.lottery.widget.LotteryNumberView;
import com.international.wtw.lottery.widget.LotteryPropertyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by XiaoXin on 2017/9/29.
 * 描述：开奖信息的部分单独在一个Fragment中处理
 */

public class LotteryInfoFragment extends NewBaseFragment {

    public static final String GAME_CODE = "game_code";

    @BindView(R.id.tv_next_round_no)
    TextView mTvNextRoundNo;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.tv_lottery_time)
    TextView mTvLotteryTime;
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.tv_time_minute)
    TextView mTvTimeMinute;
    @BindView(R.id.tv_time_seconds)
    TextView mTvTimeSeconds;
    @BindView(R.id.ll_time2)
    LinearLayout mLlTime2;
    @BindView(R.id.tv_time_hour)
    TextView mTvTimeHour;
    @BindView(R.id.tv_time_minute2)
    TextView mTvTimeMinute2;
    @BindView(R.id.tv_time_seconds2)
    TextView mTvTimeSeconds2;
    @BindView(R.id.tv_last_round)
    TextView mTvLastRound;
    @BindView(R.id.lotteryNumberView)
    LotteryNumberView mLotteryNumberView;
    @BindView(R.id.lotteryPropertyView)
    LotteryPropertyView mLotteryPropertyView;

    private int gameCode;
    //游戏期数(当前期)
    private String mNextRound = "";
    //离开奖时间秒数
    private int endSeconds;
    //离封盘时间秒数
    private int closeSeconds;
    private Handler mHandler;

    public String getNextRound() {
        return mNextRound;
    }


    public static LotteryInfoFragment newInstance(int gameCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(GAME_CODE, gameCode);
        LotteryInfoFragment fragment = new LotteryInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lottery_info;
    }

    @Override
    protected void initData() {
        gameCode = getArguments().getInt(GAME_CODE);
        mHandler = new Handler();

        if (Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY == gameCode) {
            mLlTime.setVisibility(View.GONE);
            mLlTime2.setVisibility(View.VISIBLE);
            mTvLotteryTime.setText("00:00:00");
        } else {
            mLlTime.setVisibility(View.VISIBLE);
            mLlTime2.setVisibility(View.GONE);
            mTvLotteryTime.setText("00:00");
        }

        UserModel user = UserHelper.get().getCurrUser();
        mTvBalance.setText(user == null ? "0.00" : user.getBalance());

        requestDisposableToken();
    }

    private void requestDisposableToken() {
        NetRepository.get().getDisposableToken();
    }

    @Override
    public void onResume() {
        super.onResume();
        //请求开奖信息
        requestTimeInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserChangedEvent event) {
        if (event.mUser != null) {
            mTvBalance.setText(event.mUser.getBalance());
        } else {
            mTvBalance.setText("0.00");
        }
    }

    private void requestTimeInfo() {
        NetRepository.get().lotteryInfo(this, String.format(Locale.CHINA, "%02d", gameCode), new NetCallback2<TimeInfo>() {
            @Override
            public void onSuccess(TimeInfo data, int status, String msg) {
                if (data != null)
                    setLotteryInfo(data);
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestTimeInfo();
                    }
                }, 10 * 1000);
            }
        });
    }

    /**
     * @param data 设置本期和上期的开奖信息
     */
    private void setLotteryInfo(TimeInfo data) {
        TimeInfo.NextBean nextBean = data.getNext();
        TimeInfo.LastBean lastBean = data.getLast();

        //下期开奖数据
        if (nextBean != null) {
            String nextRound = nextBean.getRound();
            if (nextRound != null && !nextRound.equals(this.mNextRound)) {
                mNextRound = nextRound;
                SharePreferencesUtil.addString(mActivity, LotteryId.ROUND, nextRound);
                mTvNextRoundNo.setText(String.format(Locale.getDefault(), "%s期", nextRound));
                if (nextBean.getEndTime() == null || nextBean.getCloseTime() == null || nextBean.getTimestamp() == null) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            requestTimeInfo();
                        }
                    }, 10 * 1000);
                    return;
                }

                endSeconds = (int) (nextBean.getEndTime() - nextBean.getTimestamp());
                closeSeconds = (int) (nextBean.getCloseTime() - nextBean.getTimestamp());

                //是否封盘
                //如果返回了isClose参数(只有晚上封盘时返回), 则判断isClose参数
                //否则还是以返回的封盘时间作判断
                boolean isClose = nextBean.getIsClose() != null && nextBean.getIsClose() == 1 || closeSeconds <= 0;
                EventBus.getDefault().post(new BetClosedEvent(gameCode, isClose));
                //显示开奖/封盘时间, 并倒计时
                refreshTime(isClose);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (endSeconds > 0) {
                            endSeconds--;
                            closeSeconds--;
                            if (closeSeconds == 0) {
                                LogUtil.e("" + closeSeconds);
                                EventBus.getDefault().post(new BetClosedEvent(gameCode, true));
                            }
                            refreshTime(isClose);
                            mHandler.postDelayed(this, 1000);
                        }
                        if (endSeconds == 0) {
                            mHandler.removeCallbacks(this);
                            requestTimeInfo();
                        }
                    }
                }, 1000);
            }
        }

        if (lastBean != null) {
            //上一期开奖数据
            String lastRound = lastBean.getRound();
            showLastLotteryNumbers(lastRound, lastBean.getNumber());

            if (TextUtils.isEmpty(lastRound) || TextUtils.isEmpty(mNextRound)) {
                return;
            }

            long nextS = Long.parseLong(mNextRound.replace("-", ""));
            long lastS = Long.parseLong(lastRound.replace("-", ""));
            //如果上一期开奖还没刷新出来, 则每过10秒请求一次数据, 直到刷出开奖结果为止
            if (Math.abs(nextS - lastS) != 1) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestTimeInfo();
                    }
                }, 10 * 1000);
            }
        }
    }

    private void refreshTime(boolean isClose) {
        String timeEnd = TimeFormatter.seconds2Time(endSeconds);
        mTvLotteryTime.setText(timeEnd);
        if (closeSeconds > 0) {
            String hour = TimeFormatter.getHour(closeSeconds);
            String minute = TimeFormatter.getMinuteOfHour(closeSeconds);
            String second = TimeFormatter.getSecondOfMinute(closeSeconds);
            if (!"00".equals(hour)) {
                mLlTime.setVisibility(View.GONE);
                mLlTime2.setVisibility(View.VISIBLE);
                mTvTimeHour.setText(isClose ? "--" : hour);
                mTvTimeMinute2.setText(isClose ? "--" : minute);
                mTvTimeSeconds2.setText(isClose ? "--" : second);
            } else {
                mLlTime.setVisibility(View.VISIBLE);
                mLlTime2.setVisibility(View.GONE);
                mTvTimeMinute.setText(isClose ? "--" : minute);
                mTvTimeSeconds.setText(isClose ? "--" : second);
            }
        } else {
            mTvTimeHour.setText("--");
            mTvTimeMinute.setText("--");
            mTvTimeSeconds.setText("--");
            mTvTimeMinute2.setText("--");
            mTvTimeSeconds2.setText("--");
        }
    }

    /**
     * 显示上一期开奖
     */
    private void showLastLotteryNumbers(String round, List<String> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return;
        }
        mTvLastRound.setText(String.format("%s期：", round));
        mLotteryNumberView.setNumbers(gameCode, numbers.toArray(new String[numbers.size()]));
        if (mLotteryPropertyView.getVisibility() != View.VISIBLE) {
            mLotteryPropertyView.setVisibility(View.VISIBLE);
        }
        mLotteryPropertyView.setNumbers(gameCode, numbers.toArray(new String[numbers.size()]));
    }
}
