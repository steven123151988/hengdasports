package com.international.wtw.lottery.fragment.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.manager.OfflinePaymentActivity;
import com.international.wtw.lottery.activity.manager.OnlinePaymentActivity;
import com.international.wtw.lottery.adapter.PayWaysAdapter;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.json.OfflinePayModel;
import com.international.wtw.lottery.json.OnlinePayModel;
import com.international.wtw.lottery.json.PayInTitle;
import com.international.wtw.lottery.model.PayWaysModel;
import com.international.wtw.lottery.widget.RecyclerViewDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 入款面页
 */
public class RechargeFragment extends NewBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String GAME_NAME = "game_name";

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private PayWaysAdapter mAdapter;
    private List<OfflinePayModel> mOfflinePayModels;

    private boolean isPrepare;
    private String mGameName;

    public static RechargeFragment newInstance(String gameName) {
        RechargeFragment fragment = new RechargeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GAME_NAME, gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void initData() {
        isPrepare = true;
        if (getArguments() != null) {
            mGameName = getArguments().getString(GAME_NAME);
        }
        initRecycler();
        mAdapter.setEmptyView(R.layout.listview_loading, (ViewGroup) mRecyclerView.getParent());
        requestPayInWays();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            onRefresh();
        }
    }

    private void initRecycler() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mActivity,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mAdapter = new PayWaysAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity item = mAdapter.getData().get(position);
                switch (item.getItemType()) {
                    case PayWaysAdapter.TYPE_ONLINE_PAY:
                        //在线支付
                        OnlinePayModel onlinePayItem = (OnlinePayModel) item;
                        Intent intent = new Intent(mActivity, OnlinePaymentActivity.class);
                        intent.putExtra(OnlinePaymentActivity.PAY_IN_DATA, onlinePayItem);
                        startActivity(intent);
                        break;
                    case PayWaysAdapter.TYPE_OFFLINE_PAY:
                        //线下支付
                        OfflinePayModel offlinePayModel = (OfflinePayModel) item;
                        Intent intent1 = new Intent(mActivity, OfflinePaymentActivity.class);
                        intent1.putExtra(OfflinePaymentActivity.SELECTED_ITEM, offlinePayModel);
                        intent1.putExtra(OfflinePaymentActivity.OFFLINE_PAYEE_INFO, (Serializable) mOfflinePayModels);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        requestPayInWays();
    }

    private void requestPayInWays() {
        NetRepository.get().getPayWays(this, new NetCallback2<PayWaysModel>() {
            @Override
            public void onSuccess(PayWaysModel data, int status, String msg) {
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                dealPayInData(data);
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty_view, null);
                TextView tvNotice = (TextView) emptyView.findViewById(R.id.tv_error);
                tvNotice.setText(errorMsg);
                emptyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.setEmptyView(R.layout.listview_loading, (ViewGroup) mRecyclerView.getParent());
                        requestPayInWays();
                    }
                });
                mAdapter.setEmptyView(emptyView);
            }
        });
    }

    /**
     * 接口返回的是些什么鬼数据, 还需要我们自己拼装.
     */
    private void dealPayInData(PayWaysModel data) {
        List<MultiItemEntity> payWays = new ArrayList<>();

        List<OnlinePayModel> onlinePay = data.getOnlinePay();
        if (onlinePay != null && !onlinePay.isEmpty()) {
            payWays.add(0, new PayInTitle("在线支付", "Online Payment"));
            for (OnlinePayModel model : onlinePay) {
                if (!TextUtils.isEmpty(mGameName)) {
                    model.gameName = mGameName;
                }
            }
            payWays.addAll(onlinePay);
        }

        List<PayWaysModel.OfflinePayBean> offlinePay = data.getOfflinePay();
        if (offlinePay != null && !offlinePay.isEmpty()) {
            mOfflinePayModels = new ArrayList<>();
            payWays.add(new PayInTitle("线下支付", "Offline Payment"));
            for (int i = 0; i < offlinePay.size(); i++) {
                OfflinePayModel model = offlinePay.get(i).getChannel().get(0);
                model.paymentClass = offlinePay.get(i).getPaymentClass();
                if (!TextUtils.isEmpty(mGameName)) {
                    model.gameName = mGameName;
                }
                mOfflinePayModels.add(model);
            }
            payWays.addAll(mOfflinePayModels);
        }

        mAdapter.setNewData(payWays);
    }
}
