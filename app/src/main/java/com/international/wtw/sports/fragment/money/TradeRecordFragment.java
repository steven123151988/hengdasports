package com.international.wtw.sports.fragment.money;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.international.wtw.sports.R;
import com.international.wtw.sports.adapter.TradeRecordAdapter;
import com.international.wtw.sports.api.NetCallback;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.base.app.NewBaseFragment;
import com.international.wtw.sports.dialog.RecordDetailDialog;
import com.international.wtw.sports.model.FundingRecord;

import java.util.List;

import butterknife.BindView;

/**
 * Created by XiaoXin on 2017/10/22.
 * 描述：
 */

public class TradeRecordFragment extends NewBaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String GAME_NAME = "game_name";
    public static final int PAGE_SIZE = 30;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int pageIndex = 1;
    private String mGameName;
    private TradeRecordAdapter mAdapter;
    private boolean isPrepare;

    public static TradeRecordFragment newInstance(String gameName) {
        TradeRecordFragment fragment = new TradeRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GAME_NAME, gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade_record;
    }

    @Override
    protected void initData() {
        isPrepare = true;
        if (getArguments() != null) {
            mGameName = getArguments().getString(GAME_NAME);
        }
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TradeRecordAdapter();
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecordDetailDialog detailDialog = new RecordDetailDialog(mActivity, mAdapter.getItem(position));
                detailDialog.show();
            }
        });
        if (getUserVisibleHint()) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        requestTransactionRecord();
    }

    @Override
    public void onLoadMoreRequested() {
        pageIndex++;
        requestTransactionRecord();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            onRefresh();
        }
    }

    /**
     * 获取存取款记录
     */
    private void requestTransactionRecord() {
        NetRepository.get().getFundingRecord(this, pageIndex, PAGE_SIZE, new NetCallback<List<FundingRecord>>() {
            @Override
            public void onSuccess(List<FundingRecord> data, int status, String msg, int total) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (data == null || data.size() < PAGE_SIZE) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
                if (pageIndex == 1) {
                    mAdapter.setNewData(data);
                    mAdapter.disableLoadMoreIfNotFullPage();
                    if (data == null) {
                        View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty_view, null);
                        mAdapter.setEmptyView(emptyView);
                    }
                } else {
                    if (data != null)
                        mAdapter.addData(data);
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (pageIndex == 1) {
                    View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty_view, null);
                    TextView tvNotice = (TextView) emptyView.findViewById(R.id.tv_error);
                    tvNotice.setText(errorMsg);
                    emptyView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRefresh();
                        }
                    });
                    mAdapter.setEmptyView(emptyView);
                } else {
                    mAdapter.loadMoreFail();
                }
            }
        });
    }
}
