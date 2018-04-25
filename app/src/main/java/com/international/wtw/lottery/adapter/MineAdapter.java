package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.model.MineItem;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/8/14.
 */

public class MineAdapter extends BaseAdapter {

    private Context context;
    private List<MineItem> list;

    public MineAdapter(Context context, List<MineItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.fragment_mine_item, null);
            viewHolder.mine_item_img_type = (ImageView) view.findViewById(R.id.mine_item_img_type);
            viewHolder.mine_item_tv_type = (TextView) view.findViewById(R.id.mine_item_tv_type);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        MineItem mineItem = list.get(position);
        viewHolder.mine_item_tv_type.setText(mineItem.name);
        viewHolder.mine_item_img_type.setImageResource(mineItem.resId);

        return view;
    }

    class ViewHolder {
        ImageView mine_item_img_type;
        TextView mine_item_tv_type;
    }
}
