package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.international.wtw.lottery.R;

import java.util.List;

/**
 * Created by Abin on 2017/9/13.
 * 描述：离线支付方式的Adapter
 */

public class OfflinePayTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> payTypes;
    private int selectedPosition;

    public OfflinePayTypeAdapter(Context context, List<Integer> payTypes) {
        mContext = context;
        this.payTypes = payTypes;
    }

    @Override
    public int getCount() {
        return payTypes == null ? 0 : payTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return payTypes == null ? null : payTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_offline_paytype, null);
            viewHolder = new ViewHolder();
            viewHolder.ibPayType = (ImageView) convertView.findViewById(R.id.ib_pay_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (payTypes.get(position)) {
            case 1:
                viewHolder.ibPayType.setImageResource(R.drawable.selector_offline_unionpay);
                break;
            case 2:
                viewHolder.ibPayType.setImageResource(R.drawable.selector_offline_alipay);
                break;
            case 3:
                viewHolder.ibPayType.setImageResource(R.drawable.selector_offline_wechatpay);
                break;
            case 4:
                viewHolder.ibPayType.setImageResource(R.drawable.selector_offline_cftpay);
                break;
        }

        viewHolder.ibPayType.setSelected(position == selectedPosition);
        return convertView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView ibPayType;
    }
}
