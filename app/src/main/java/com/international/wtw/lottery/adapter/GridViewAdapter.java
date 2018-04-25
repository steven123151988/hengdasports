package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;

import java.util.List;

/**
 * Created by wuya on 2017/5/5.
 */

public class GridViewAdapter extends BaseAdapter{

    private List<ArrayMap<String, Object>> datas;
    private Context context;


    public  GridViewAdapter(Context context, List<ArrayMap<String,Object>> datas){
        this.context =context;
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return  null==datas?0:datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mainfragment_gridview,null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView)convertView.findViewById(R.id.gridItem_Icon);
            viewHolder.title = (TextView)convertView.findViewById(R.id.textTitle);
            convertView.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }
        ArrayMap<String, Object>  oneArrayMap = datas.get(position);
        viewHolder.title.setText((String)oneArrayMap.get(LotteryId.TitleNameKey));
        viewHolder.icon.setBackgroundResource((Integer)oneArrayMap.get(LotteryId.IconResIdKey));

        return convertView;
    }

    class ViewHolder{

        TextView title;
        ImageView icon;
    }
}
