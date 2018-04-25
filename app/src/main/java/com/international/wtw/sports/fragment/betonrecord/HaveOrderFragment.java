package com.international.wtw.sports.fragment.betonrecord;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.international.wtw.sports.R;
import com.international.wtw.sports.adapter.HasAdapter;
import com.international.wtw.sports.api.NetCallback;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.base.view.CustomRefreshListView;
import com.international.wtw.sports.json.OutHaveListBean;
import com.international.wtw.sports.utils.LogUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HaveOrderFragment extends Fragment implements CustomRefreshListView.OnRefreshListener {

    private View view;
    private CustomRefreshListView lv_have;
    private HasAdapter hasAdapter;

    private FrameLayout fl_no_deposit;

    private int page_have = 1;
    private List<OutHaveListBean> mData;
    private int mtotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_haveorder, container, false);

        lv_have = (CustomRefreshListView) view.findViewById(R.id.lv_have);
        lv_have.setOnRefreshListener(this);
        fl_no_deposit = (FrameLayout) view.findViewById(R.id.fl_no_deposit);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        LogUtil.e("HaveOrderFragment---date -*-*-*-" + date + "-*-*-*-" + time);

        SetDataHave(page_have, 20);

        return view;
    }

    private void SetDataHave(int page, int num) {
        NetRepository.get().todayend(this, page, num, new NetCallback<List<OutHaveListBean>>() {
            @Override
            public void onSuccess(List<OutHaveListBean> data, int status, String msg, int total) {
                LogUtil.e("已结成功---onSuccess---" + "-status-" + status + "-msg-" + msg+"-data-"+data);
                lv_have.completeRefresh();
                mtotal = total;
                if (data != null) {
                    mData = data;
                    if (page_have == 1) {
                        hasAdapter = new HasAdapter(getActivity(), mData);
                        lv_have.setAdapter(hasAdapter);
                    } else {
//                        mData.addAll(data);
                        hasAdapter.addData(data);
                        hasAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (page_have == 1) {
                        LogUtil.e("已结成功-data-" + data);
                        lv_have.setVisibility(View.GONE);
                        fl_no_deposit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e("已结失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
                lv_have.setVisibility(View.GONE);
                fl_no_deposit.setVisibility(View.VISIBLE);
            }
        });
    }

    public String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime() / 1000;
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    @Override
    public void onPullRefresh() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        page_have = 1;
        //        SetData(time, page, 1);
        SetDataHave(page_have, 20);
        lv_have.completeRefresh();
    }

    @Override
    public void onLoadingMore() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        //        SetData(time, page += 1, 0);
        if (mData.size() < mtotal) {
            SetDataHave(page_have += 1, 20);
        }
        lv_have.completeRefresh();
    }
}
