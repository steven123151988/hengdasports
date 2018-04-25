package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.listener.MyOnclickListener;
import com.international.wtw.lottery.base.Constants;

import java.util.List;

/**
 * Created by wuya on 2017/6/17.
 */

public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.MyViewHolder> {
    private Context mContext;
    private List<ArrayMap<String,Object>> list;
    private MyOnclickListener myItemClickListener;


    public PopupAdapter(Context mContext,List<ArrayMap<String,Object>> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setOnItemClickListener(MyOnclickListener listener) {
        this.myItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_item, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (position==list.size()-1){
            holder.view_diver.setVisibility(View.GONE);
        }
        ArrayMap<String,Object> oneData = list.get(position);
        holder.choice_text.setText((String)oneData.get(LotteryId.LOTTERY_NAME_KEY));
        holder.choice_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onItemClick(v, position,(Class<?> )oneData.get(LotteryId.LOTTERY_CLASS_KEY));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView choice_text;
        View view_diver;

        public MyViewHolder(final View itemView) {
            super(itemView);
            choice_text = (TextView) itemView.findViewById(R.id.choice_text);
            view_diver = itemView.findViewById(R.id.view_diver);
        }
    }
}