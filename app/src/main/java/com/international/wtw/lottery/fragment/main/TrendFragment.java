package com.international.wtw.lottery.fragment.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.LotteryHistoryActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.api.NetCallback;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.MenuPopupWindow;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.model.OpenModel;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.TimeFormatter;
import com.international.wtw.lottery.widget.LotteryNumberView;
import com.international.wtw.lottery.widget.RecyclerViewDivider;
import com.international.wtw.lottery.widget.TitleBar;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by XiaoXin on 2017/10/4.
 * 描述：开奖趋势
 * 主要逻辑: 每15秒轮询一次服务器获取数据, 因为上一期的开奖数据不会在这一期开始时马上刷出来,
 * 为了及时更新开奖数据, 暂时的想法是每15秒轮询1次服务器数据
 * 倒计时功能, 新建一个Runnable进行倒计时, 通过调用Adapter的notifyItemChanged(int position, Object payload)局部刷新数据
 */

public class TrendFragment extends NewBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rl_website)
    RelativeLayout mRlWebsite;
    @BindView(R.id.tv_website)
    TextView mTvWebsite;

    //当网络请求出错时显示
    private View mErrorView;
    private TextView mTvError;
    private BaseQuickAdapter<OpenModel, BaseViewHolder> mAdapter;
    private MenuPopupWindow mMenuPopup;
    private Handler mHandler;
    private Runnable mCountDownAction;
    private Runnable mPollingRunnable;

    @Override
    public void onStart() {
        super.onStart();
        pollingServer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPollingServer();
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lottery_trend;
    }

    @Override
    protected void initData() {
        mHandler = new Handler();
        mTvWebsite.setText("pk10tv.com开奖网");
        mRlWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, "开奖网");
                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, Constants.LOTTERY_WEBSITE);
                intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                intent.putExtra(WebViewActivity.EXTRA_HIDE_TITLE, true);
                startActivity(intent);
            }
        });
        mTitleBar.setOnRightTextClickListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new BaseQuickAdapter<OpenModel, BaseViewHolder>(R.layout.item_game_lotteries) {
            @Override
            protected void convert(BaseViewHolder helper, OpenModel item) {
                helper.setText(R.id.tv_game_name, LotteryUtil.get().getGameName(item.getLotterygamesId()));
                helper.setText(R.id.tv_round_number, String.format(Locale.CHINESE, "%s期", item.getRound()));
                if (item.getIsClose() != null && item.getIsClose() == 0 &&
                        !TextUtils.isEmpty(item.getOpenTime()) && !TextUtils.isEmpty(item.getSysTime())) {
                    int openTime = (int) (Long.parseLong(item.getOpenTime()) - Long.parseLong(item.getSysTime()));
                    helper.setText(R.id.tv_lottery_time, TimeFormatter.seconds2Time(openTime));
                } else {
                    helper.setText(R.id.tv_lottery_time, "封盘");
                }

                LotteryNumberView numberView = helper.getView(R.id.lotteryNumberView);
                numberView.setNumbers(item.getLotterygamesId(), item.getNumber().split(","));
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
                if (!payloads.isEmpty()) {
                    int openTime = (int) payloads.get(0);
                    holder.setText(R.id.tv_lottery_time, TimeFormatter.seconds2Time(openTime));
                } else {
                    super.onBindViewHolder(holder, position, payloads);
                }
            }
        };
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OpenModel item = mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                Intent intent = new Intent(mActivity, LotteryHistoryActivity.class);
                intent.putExtra(LotteryId.GAME_CODE, item.getLotterygamesId());
                startActivity(intent);
            }
        });
        initEmptyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //每次进入页面开始轮询服务器, 离开页面则停止轮询
        if (!hidden) {
            pollingServer();
        } else {
            stopPollingServer();
        }
    }

    /**
     * 轮询服务器获取数据, 每15秒钟拉去数据
     */
    private void pollingServer() {
        requestGameCloseTime();
        if (mPollingRunnable != null)
            mHandler.removeCallbacks(mPollingRunnable);
        mPollingRunnable = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 15 * 1000);
                requestGameCloseTime();
            }
        };
        mHandler.postDelayed(mPollingRunnable, 15 * 1000);
    }

    private void stopPollingServer() {
        mHandler.removeCallbacks(mPollingRunnable);
    }

    private void requestGameCloseTime() {
        NetRepository.get().lotteryHall(this, 1, 20, new NetCallback<List<OpenModel>>() {
            @Override
            public void onSuccess(List<OpenModel> data, int status, String msg, int total) {
                if (getActivity() != null && isAdded()) {
                    mHandler.removeCallbacks(mCountDownAction);
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (data != null && !data.isEmpty()) {
                        Iterator<OpenModel> iterator = data.iterator();
                        while (iterator.hasNext()) {
                            OpenModel next = iterator.next();
                            if (next == null || next.getLotterygamesId() == Constants.LOTTERY_TYPE.PJ_FUNNY_8
                                    || next.getLotterygamesId() == Constants.LOTTERY_TYPE.GD_5_IN_11) {
                                iterator.remove();
                            }
                        }
                    }
                    mAdapter.setNewData(data);
                    if (data != null && data.size() > 0)
                        startCountDown(data);
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (getActivity() != null && isAdded()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (!TextUtils.isEmpty(errorMsg))
                        mTvError.setText(errorMsg);
                    mAdapter.setEmptyView(mErrorView);
                }
            }
        });
    }

    private void startCountDown(List<OpenModel> list) {
        int[] openTime = new int[list.size()];
        Integer[] isClose = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            OpenModel item = list.get(i);
            isClose[i] = item.getIsClose();
            if (isClose[i] != null && isClose[i] == 0 && !TextUtils.isEmpty(item.getOpenTime())
                    && !TextUtils.isEmpty(item.getSysTime())) {//未封盘
                openTime[i] = (int) (Long.parseLong(item.getOpenTime()) - Long.parseLong(item.getSysTime()));
            }
        }
        //倒计时Runnable
        mCountDownAction = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 1000);
                //是否马上刷新
                boolean refresh = false;
                for (int i = 0; i < openTime.length; i++) {
                    //开盘状态才进行倒计时
                    if (isClose[i] != null && isClose[i] == 0) {
                        openTime[i]--;
                        mAdapter.notifyItemChanged(i, openTime[i]);
                        if (openTime[i] == 0) {
                            //其中一个倒计时变成0, 则刷新数据
                            refresh = true;
                        }
                    }
                }
                //马上刷新
                if (refresh) {
                    requestGameCloseTime();
                }
            }
        };
        mHandler.postDelayed(mCountDownAction, 1000);
    }

    private void initEmptyView() {
        mErrorView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty_view, null);
        mTvError = (TextView) mErrorView.findViewById(R.id.tv_error);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeRefreshLayout.setRefreshing(true);
                pollingServer();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (mMenuPopup == null) {
            mMenuPopup = new MenuPopupWindow(getActivity())
                    .createPopup();
        }
        mMenuPopup.showAtAnchorView(mTitleBar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT);
    }

    @Override
    public void onRefresh() {
        pollingServer();
    }
}