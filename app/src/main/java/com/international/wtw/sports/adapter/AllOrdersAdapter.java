package com.international.wtw.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.json.AllOrderListBean;
import com.international.wtw.sports.utils.TimeUtil;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/11/3.
 */

public class AllOrdersAdapter extends BaseAdapter {

    private Context context;
    private List<AllOrderListBean> list;

    public AllOrdersAdapter(Context context, List<AllOrderListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.lv_bet_allorders_item, null);
            viewHolder.tv_rq = (TextView) view.findViewById(R.id.tv_rq);
            viewHolder.tv_tzds = (TextView) view.findViewById(R.id.tv_tzds);
            viewHolder.tv_xzje = (TextView) view.findViewById(R.id.tv_xzje);
            viewHolder.tv_ksje = (TextView) view.findViewById(R.id.tv_ksje);
            viewHolder.tv_yjts = (TextView) view.findViewById(R.id.tv_yjts);
            viewHolder.tv_yj = (TextView) view.findViewById(R.id.tv_yj);
            viewHolder.tv_is_win = (TextView) view.findViewById(R.id.tv_is_win);
            viewHolder.rl_bg_all = (RelativeLayout) view.findViewById(R.id.rl_bg_all);
            viewHolder.tv_ts = (TextView) view.findViewById(R.id.tv_ts);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String datetime = TimeUtil.getDateStringByTimeSTamp((long) list.get(position).getCreatedTime(), "yyyy-MM-dd HH:mm:ss");
        viewHolder.tv_rq.setText(datetime);

        viewHolder.tv_tzds.setText(list.get(position).getId()+"");

        String Money = getNumTwo(list.get(position).getMoney() + "");
        viewHolder.tv_xzje.setText(Money);

        String Win = getNumTwo(list.get(position).getWin() + "");
        viewHolder.tv_ksje.setText(Win);

        String Backwater = getNumTwo(list.get(position).getBackwater() + "");
        viewHolder.tv_yjts.setText(Backwater);

        int status = list.get(position).getStatus();
        if (status == 0) {
            viewHolder.tv_yj.setText("未结");
            viewHolder.rl_bg_all.setBackgroundResource(R.color.money_color);
            viewHolder.tv_is_win.setText("可赢金额  ");
            viewHolder.tv_ts.setText("预计退水  ");
        } else if (status == 1) {
            viewHolder.tv_yj.setText("已结");
            viewHolder.rl_bg_all.setBackgroundResource(R.color.pickerview_bgColor_overlay);
            viewHolder.tv_is_win.setText("输赢金额  ");
            viewHolder.tv_ts.setText("退水  ");
        }

        return view;
    }

    class ViewHolder {
        TextView tv_rq, tv_tzds, tv_xzje, tv_ksje, tv_yjts, tv_yj,tv_is_win,tv_ts;
        RelativeLayout rl_bg_all;
    }

    private String getNumTwo(String num) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        String format = df.format(Double.valueOf(num));
        return format;
    }

}
