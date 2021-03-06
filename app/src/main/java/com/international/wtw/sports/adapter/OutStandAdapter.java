package com.international.wtw.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.json.OutHaveListBean;
import com.international.wtw.sports.utils.TimeUtil;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/20.
 */

public class OutStandAdapter extends BaseAdapter {

    private Context context;
    private List<OutHaveListBean> list;

    public OutStandAdapter(Context context, List<OutHaveListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<OutHaveListBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
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
            view = LayoutInflater.from(context).inflate(R.layout.outstand_item, null);
            viewHolder.tv_zdh = (TextView) view.findViewById(R.id.tv_zdh);
            viewHolder.tv_yx_zl = (TextView) view.findViewById(R.id.tv_yx_zl);
            viewHolder.tv_yx_wf = (TextView) view.findViewById(R.id.tv_yx_wf);
            viewHolder.tv_yxwf = (TextView) view.findViewById(R.id.tv_yxwf);
            viewHolder.tv_xzsj = (TextView) view.findViewById(R.id.tv_xzsj);
            viewHolder.tv_xzje = (TextView) view.findViewById(R.id.tv_xzje);
            viewHolder.tv_kyje = (TextView) view.findViewById(R.id.tv_kyje);
            viewHolder.tv_yxwf2 = (TextView) view.findViewById(R.id.tv_yxwf2);
            viewHolder.ll_fushi = (LinearLayout) view.findViewById(R.id.ll_fushi);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_zdh.setText(list.get(position).getId());
        viewHolder.tv_yx_zl.setText(list.get(position).getGames());
        viewHolder.tv_yx_wf.setText("第" + list.get(position).getRound() + "期");
//        String play1 = "", play2 = "", play3 = "", play4 = "";
//        if (list.get(position).getGamesPlay1() != null) {
//            play1 = list.get(position).getGamesPlay1();
//        }
//        if (list.get(position).getGamesPlay2() != null) {
//            play2 = list.get(position).getGamesPlay2();
//        }
//        if (list.get(position).getGamesPlay3() != null) {
//            play3 = list.get(position).getGamesPlay3();
//        }
//        if (list.get(position).getGamesPlay4() != null) {
//            play4 = list.get(position).getGamesPlay4();
//        }
        viewHolder.tv_yxwf.setText(list.get(position).getSmallBall() + " @" + list.get(position).getRate());
        String datetime = TimeUtil.getDateStringByTimeSTamp((long) list.get(position).getCreatedTime(), "yyyy-MM-dd HH:mm:ss");
        viewHolder.tv_xzsj.setText(datetime);

        String countPay = list.get(position).getCountPay();
        if ("1".equals(countPay)) {
            String TotalMonty = getNumTwo(list.get(position).getTotalMonty());
            viewHolder.tv_xzje.setText(TotalMonty);
        } else {
            String Money = getNumTwo(list.get(position).getMoney() + "");
            String TotalMonty = getNumTwo(list.get(position).getTotalMonty());
            viewHolder.tv_xzje.setText(Money + "*" + list.get(position).getCountPay() + "=" + TotalMonty);
        }

        String Win = getNumTwo(list.get(position).getWin() + "");
        viewHolder.tv_kyje.setText(Win);
        if (Integer.valueOf(list.get(position).getCountPay()) > 1) {
            viewHolder.ll_fushi.setVisibility(View.VISIBLE);
            viewHolder.tv_yxwf2.setText("『" + list.get(position).getCountPay() + "组』");
        }


        return view;
    }

    class ViewHolder {
        TextView tv_zdh, tv_yx_zl, tv_yx_wf, tv_yxwf, tv_xzsj, tv_xzje, tv_kyje, tv_yxwf2;
        LinearLayout ll_fushi;
    }

    private String getNumTwo(String num) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        String format = df.format(Double.valueOf(num));
        return format;
    }

}
