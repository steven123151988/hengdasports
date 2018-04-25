package com.international.wtw.sports.adapter;


import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.sports.R;
import com.international.wtw.sports.model.FundingRecord;
import com.international.wtw.sports.utils.DateUtil;

import java.util.Locale;

public class TradeRecordAdapter extends BaseQuickAdapter<FundingRecord, BaseViewHolder> {

    public TradeRecordAdapter() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(BaseViewHolder holder, FundingRecord item) {
        /*holder.setText(R.id.tv_time, DateUtil.convertTime(item.getAddtime()));
        if ("0".equals(item.getType_code())) {
            if ("AG".equals(item.getBank_name())) {
                holder.setText(R.id.tv_amount, item.getBank_account() + "：" + item.getMoney());
            } else {
                holder.setText(R.id.tv_amount, mContext.getResources().getString(R.string.deposit) + item.getMoney());
            }
        } else if ("1".equals(item.getType_code())) {
            if ("AG".equals(item.getBank_name())) {
                holder.setText(R.id.tv_amount, item.getBank_account() + "：" + item.getMoney());
            } else {
                holder.setText(R.id.tv_amount, mContext.getResources().getString(R.string.take_out) + item.getMoney());
            }
        }

        if ("0".equals(item.getStatus())) {
            holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.untreated));
            holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.bet_color_blue));
        } else if ("1".equals(item.getStatus())) {
            if ("0".equals(item.getIs_clear())) {
                holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.fail));
                holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.red));
            } else if ("1".equals(item.getIs_clear())) {
                holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.success));
                holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.gray));
            }
        }*/
        holder.setText(R.id.tv_time, DateUtil.convertTime(item.getCreatedTime() * 1000));
        String type = item.getType() == 0 ? mContext.getResources().getString(R.string.deposit) : mContext.getResources().getString(R.string.take_out);
        holder.setText(R.id.tv_amount, String.format(Locale.CHINA, "%s%.2f", type, item.getMoney()));
        switch (item.getOrderStatus()) {
            case 0://审核中
                holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.untreated));
                holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.bet_color_blue));
                break;
            case 1://成功
                holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.success));
                holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.gray));
                break;
            case 2://失败
                holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.fail));
                holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.red));
                break;
        }
    }
}
