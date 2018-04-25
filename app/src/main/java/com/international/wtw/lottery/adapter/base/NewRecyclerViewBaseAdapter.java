package com.international.wtw.lottery.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.model.GameModel;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/15.
 */

public class NewRecyclerViewBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<GameModel> mDatas;

    public NewRecyclerViewBaseAdapter(Context mContext, List<GameModel> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_mainfragment_gridview_un, null);
            viewHolder.img_type = (ImageView) view.findViewById(R.id.img_type);
            viewHolder.tv_type_name = (TextView) view.findViewById(R.id.tv_type_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_type_name.setText(mDatas.get(position).gameName);
        viewHolder.img_type.setImageResource(mDatas.get(position).logoResId);

        return view;
    }

    class ViewHolder {
        ImageView img_type;
        TextView tv_type_name;
    }

}
