package com.international.wtw.sports.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.sports.R;
import com.international.wtw.sports.adapter.LotteryHistoryAdapter;
import com.international.wtw.sports.api.NetCallback;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.base.Constants;
import com.international.wtw.sports.base.LotteryId;
import com.international.wtw.sports.base.NewBaseActivity;
import com.international.wtw.sports.dialog.SwitchGamePopupWindow;
import com.international.wtw.sports.dialog.easypopup.HorizontalGravity;
import com.international.wtw.sports.dialog.easypopup.VerticalGravity;
import com.international.wtw.sports.model.OpenModel;
import com.international.wtw.sports.utils.LotteryUtil;
import com.international.wtw.sports.widget.RecyclerViewDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LotteryHistoryActivity extends NewBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int PAGE_SIZE = 20;

    @BindView(R.id.tv_bet_title)
    TextView mTvBetTitle;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.tv_open_num)
    TextView mTvOpenNum;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    //当网络请求出错时显示
    private View mErrorView;
    private TextView mTvError;

    private int mGameCode;
    private int pageIndex = 1;
    private BaseQuickAdapter<OpenModel, BaseViewHolder> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lottery_history;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mGameCode = getIntent().getIntExtra(LotteryId.GAME_CODE, Constants.LOTTERY_TYPE.PJ_PK_10);
        initTitle();
        initEmptyView();
        initRecycler();
        //获取历史开奖数据
        refresh();
    }

    private void initTitle() {
        mTvBetTitle.setText(LotteryUtil.get().getGameName(mGameCode));
    }

    private void initRecycler() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.color_primary));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mAdapter = new LotteryHistoryAdapter(mGameCode);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initEmptyView() {
        mErrorView = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null);
        mTvError = (TextView) mErrorView.findViewById(R.id.tv_error);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.ll_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_title:
                showSwitchGamePopup();
                break;
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        pageIndex = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        requestLotteryHistory();
    }

    @Override
    public void onLoadMoreRequested() {
        pageIndex++;
        requestLotteryHistory();
    }

    private void requestLotteryHistory() {

        NetRepository.get().lotteryHistory(this, mGameCode, pageIndex, PAGE_SIZE, new NetCallback<List<OpenModel>>() {
            @Override
            public void onSuccess(List<OpenModel> data, int status, String msg, int total) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (pageIndex == 1) {
                    mAdapter.setNewData(data);
                    mAdapter.disableLoadMoreIfNotFullPage();
                } else {
                    mAdapter.addData(data);
                }
                if (data == null || data.size() < PAGE_SIZE) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (pageIndex == 1) {
                    mTvError.setText(errorMsg);
                    mAdapter.setEmptyView(mErrorView);
                } else {
                    mAdapter.loadMoreFail();
                }
            }
        });
    }

    private void showSwitchGamePopup() {
        new SwitchGamePopupWindow(this, mGameCode)
                .setOnItemClickListener(new SwitchGamePopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int gameCode) {
                        getIntent().putExtra(LotteryId.GAME_CODE, gameCode);
                        recreate();
                    }
                })
                .createPopup()
                .showAtAnchorView(mLlTitle, VerticalGravity.BELOW, HorizontalGravity.CENTER);
    }
}
