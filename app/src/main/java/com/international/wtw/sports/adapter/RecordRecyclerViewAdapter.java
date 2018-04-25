package com.international.wtw.sports.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.json.TransactionRecord;
import com.international.wtw.sports.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a bin
 * on 2015/7/6.
 */
public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<TransactionRecord.ResBean> dataList = new ArrayList<>();
    protected OnItemClickListener mOnItemClickListener;

    public void addAllData(List<TransactionRecord.ResBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public RecordRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_time, tv_amount, tv_record_status;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_record_status = (TextView) itemView.findViewById(R.id.tv_record_status);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TransactionRecord.ResBean resBean = dataList.get(position);
        holder.tv_time.setText(DateUtil.convertTime(resBean.getAddtime()));
        if ("0".equals(resBean.getType_code())) {
            holder.tv_amount.setText(mContext.getResources().getString(R.string.deposit) + resBean.getMoney());
        } else if ("1".equals(resBean.getType_code())) {
            holder.tv_amount.setText(mContext.getResources().getString(R.string.take_out) + resBean.getMoney());
        }

        if ("0".equals(resBean.getStatus())) {
            holder.tv_record_status.setText(mContext.getResources().getString(R.string.untreated));
            holder.tv_record_status.setTextColor(mContext.getResources().getColor(R.color.bet_color_blue));
        } else if ("1".equals(resBean.getStatus())) {
            if ("0".equals(resBean.getIs_clear())) {
                holder.tv_record_status.setText(mContext.getResources().getString(R.string.fail));
                holder.tv_record_status.setTextColor(mContext.getResources().getColor(R.color.red));
            } else if ("1".equals(resBean.getIs_clear())) {
                holder.tv_record_status.setText(mContext.getResources().getString(R.string.success));
                holder.tv_record_status.setTextColor(mContext.getResources().getColor(R.color.gray));
            }
        }
        setListener(holder, resBean, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected void setListener(ViewHolder viewHolder, TransactionRecord.ResBean resBean, int position) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    //mOnItemClickListener.onItemClick(v, dataList.get(position), position);
                    mOnItemClickListener.onItemClick(v, resBean, position);
                }
            }
        });
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, TransactionRecord.ResBean bean, int position);
    }

}