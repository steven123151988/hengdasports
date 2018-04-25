package com.international.wtw.sports.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.sports.R;
import com.international.wtw.sports.activity.lottery.BetActivity;
import com.international.wtw.sports.api.NetCallback2;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.dialog.nice.BaseNiceDialog;
import com.international.wtw.sports.dialog.nice.ViewHolder;
import com.international.wtw.sports.json.BetTypeItem;
import com.international.wtw.sports.json.MultiBetItem;
import com.international.wtw.sports.json.OddsItem;
import com.international.wtw.sports.model.BetRequest;
import com.international.wtw.sports.model.DisposableToken;
import com.international.wtw.sports.utils.LotteryUtil;
import com.international.wtw.sports.utils.SizeUtils;
import com.international.wtw.sports.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by XiaoXin on 2017/9/24.
 * 描述：下注明细列表
 */

public class BetDetailDialog extends BaseNiceDialog implements View.OnClickListener {

    private int gameCode;
    private int fragPosition;
    private int selectedId;
    private int betMoney;
    private String round;
    private static BetTypeItem sComboCode;
    private static List<MultiBetItem> betItems;
    private static OnBetResultListener mOnBetResultListener;
    private int betType;

    public static BetDetailDialog init(int gameCode, int position, int selectedId, int betMoney,
                                       String round, BetTypeItem comboCode,
                                       List<MultiBetItem> itemList, OnBetResultListener listener) {
        BetDetailDialog detailDialog = new BetDetailDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("game_code", gameCode);
        bundle.putInt("frag_position", position);
        bundle.putInt("selected_id", selectedId);
        bundle.putInt("bet_money", betMoney);
        bundle.putString("round_num", round);
        sComboCode = comboCode;
        betItems = itemList;
        mOnBetResultListener = listener;
        detailDialog.setArguments(bundle);
        return detailDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        gameCode = bundle.getInt("game_code");
        fragPosition = bundle.getInt("frag_position");
        selectedId = bundle.getInt("selected_id");
        betMoney = bundle.getInt("bet_money");
        round = bundle.getString("round_num");

        betType = LotteryUtil.get().getBetType(gameCode, fragPosition);
    }

    @Override
    public int intLayoutId() {
        return R.layout.layout_dialog_bet_detail;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        RecyclerView recyclerView = holder.getView(R.id.recycler_view);
        holder.setOnClickListener(R.id.iv_close, this);
        holder.setOnClickListener(R.id.tv_dialog_confirm, this);
        holder.setOnClickListener(R.id.tv_dialog_cancel, this);
        BaseQuickAdapter.OnItemChildClickListener clickListener = new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        betItems.remove(position);
                        if (betItems.isEmpty()) {
                            dismiss();
                        } else {
                            refreshRecycler(recyclerView, betType, holder);
                            adapter.notifyItemRemoved(position);
                        }
                        break;
                }
            }
        };

        switch (betType) {
            /*
             * 下注方式一:适用于 北京赛车，幸运飞艇，PC蛋蛋（幸运二八），重庆时时彩，广东快乐十分（除连码外），
             * 重庆幸运农场（除连码外），香港六合彩（特码，正码，正码特，正码1-6，半波，一肖、尾数，特码生肖）
             */
            case LotteryUtil.BET_TYPE_ONE:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),
                        RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
                BaseQuickAdapter<MultiBetItem, BaseViewHolder> adapter
                        = new BaseQuickAdapter<MultiBetItem, BaseViewHolder>(R.layout.item_bet_data, betItems) {
                    @Override
                    protected void convert(BaseViewHolder helper, MultiBetItem item) {
                        helper.setText(R.id.tv_bet_type, String.format("%s %s (%s)",
                                item.getTypeName(), item.getBetItem().getName(), item.getBetItem().getOdds()));
                        helper.setText(R.id.tv_bet_money, String.valueOf(betMoney));
                        helper.addOnClickListener(R.id.iv_delete);
                    }
                };
                adapter.setOnItemChildClickListener(clickListener);
                recyclerView.setAdapter(adapter);
                break;
            /*
             * 下注方式二:适用于 广东快乐十分（连码），重庆幸运农场（连码），香港六合彩（连码）
             */
            case LotteryUtil.BET_TYPE_TWO:
            /*
            * 下注方式三:适用于（过关 ，合肖，生肖连，尾数连，全不中）
            */
            case LotteryUtil.BET_TYPE_THREE:
                holder.getView(R.id.tv_bet_name).setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_bet_name, sComboCode.getTypeName());
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),
                        RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
                BaseQuickAdapter<MultiBetItem, BaseViewHolder> adapter2
                        = new BaseQuickAdapter<MultiBetItem, BaseViewHolder>(R.layout.item_bet_data2, betItems) {

                    @Override
                    protected void convert(BaseViewHolder helper, MultiBetItem item) {
                        if (betType == LotteryUtil.BET_TYPE_TWO) {
                            helper.setText(R.id.tv_bet_num, String.valueOf(item.getNumber()));
                        } else {
                            helper.setText(R.id.tv_bet_num, item.getBetItem().getName());
                        }
                    }
                };
                adapter2.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                        if (position == betItems.size() - 1 && betItems.size() % 2 == 1) {
                            return 2;
                        }
                        return 1;
                    }
                });
                adapter2.setOnItemChildClickListener(clickListener);
                recyclerView.setAdapter(adapter2);
                break;
        }

        refreshRecycler(recyclerView, betType, holder);
    }

    private void refreshRecycler(RecyclerView recyclerView, int betType, ViewHolder holder) {
        int total = LotteryUtil.get().getTotalBetNum(gameCode, fragPosition, selectedId, betItems.size());
        holder.setText(R.id.tv_total_count, String.valueOf(total));
        holder.setText(R.id.tv_total_money, String.valueOf(betMoney * total));

        int size = betType == LotteryUtil.BET_TYPE_ONE ? 5 : 10;
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        if (betItems.size() > size) {
            params.height = SizeUtils.dp2px(150);
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        recyclerView.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
            case R.id.tv_dialog_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_confirm:
                requestBet();
                break;
        }
    }

    private void requestBet() {
        BetRequest betRequest = new BetRequest();
        betRequest.setRound(round);
        betRequest.setGame(String.format(Locale.CHINA, "%02d", gameCode));
        List<BetRequest.BetItem> list = new ArrayList<>();
        switch (betType) {
            case LotteryUtil.BET_TYPE_ONE://普通下注
                for (MultiBetItem betItem : betItems) {
                    BetRequest.BetItem item = new BetRequest.BetItem();
                    item.setLotterygamesId(betItem.getBetItem().getKey());
                    item.setSingleMoney(String.valueOf(betMoney));
                    item.setOddId(betItem.getBetItem().getOddId());
                    list.add(item);
                }
                break;
            case LotteryUtil.BET_TYPE_TWO://连码下注
                BetRequest.BetItem item = new BetRequest.BetItem();
                StringBuilder sb = new StringBuilder(sComboCode.getOddsItem().getKey());
                for (MultiBetItem betItem : betItems) {
                    sb.append(",").append(betItem.getNumber());
                    item.setOddId(sComboCode.getOddsItem().getOddId());
                }
                item.setLotterygamesId(sb.toString());
                item.setSingleMoney(String.valueOf(betMoney));
                list.add(item);
                break;
            case LotteryUtil.BET_TYPE_THREE://复式下注
                BetRequest.BetItem item1 = new BetRequest.BetItem();
                StringBuilder sb1 = new StringBuilder(sComboCode.getDuplex());
                for (MultiBetItem betItem : betItems) {
                    OddsItem oddsItem = betItem.getBetItem();
                    sb1.append(",").append(oddsItem.getKey());
                    item1.setOddId(oddsItem.getOddId());
                }
                item1.setLotterygamesId(sb1.toString());
                item1.setSingleMoney(String.valueOf(betMoney));
                list.add(item1);
                break;
        }
        betRequest.setPlays(list);

        if (mOnBetResultListener != null) {
            mOnBetResultListener.onCommit();
        }
        NetRepository.get().betting(this, betRequest, new NetCallback2<DisposableToken>() {

            @Override
            public void onSuccess(DisposableToken data, int status, String msg) {
                dismiss();
                if (mOnBetResultListener != null) {
                    mOnBetResultListener.onBetResult(true, getString(R.string.congratulations_on_your_betting_success));
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (status == 400) {
                    //如果status为400，表示重复下注或者下注Token为空，需重新调用获取下注Token接口
                    NetRepository.get().getDisposableToken();
                }
                dismiss();
                if (mOnBetResultListener != null) {
                    mOnBetResultListener.onBetResult(false, errorMsg);
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //弹窗消失时清空之前的选择
        FragmentActivity activity = getActivity();
        if (activity instanceof BetActivity) {
            BetActivity betActivity = (BetActivity) activity;
            betActivity.clearSelection();
        }
    }

    public interface OnBetResultListener {
        //void onRequest();
        void onCommit();

        void onBetResult(boolean isSuccess, String msg);
    }
}
