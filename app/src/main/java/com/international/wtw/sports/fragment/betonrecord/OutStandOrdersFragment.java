package com.international.wtw.sports.fragment.betonrecord;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.international.wtw.sports.R;
import com.international.wtw.sports.adapter.OutStandAdapter;
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

/**
 * 未结注单
 *
 */
public class OutStandOrdersFragment extends Fragment implements CustomRefreshListView.OnRefreshListener {

    private View view;
    private CustomRefreshListView lv_outstand;
    private OutStandAdapter outStandAdapter;

    private FrameLayout fl_no_deposit;
    private int page_out = 1;
    private List<OutHaveListBean> mData;
    private int mtotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outstandorder, container, false);

        lv_outstand = (CustomRefreshListView) view.findViewById(R.id.lv_outstand);
        lv_outstand.setOnRefreshListener(this);
        fl_no_deposit = (FrameLayout) view.findViewById(R.id.fl_no_deposit);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        LogUtil.e("OutStandOrdersFragment---date -*-*-*-" + date + "-*-*-*-" + time);

        SetDataOut(page_out, 20);

        return view;
    }

    private void SetDataOut(int page, int num) {
        NetRepository.get().lotteryBill(this, page, num, new NetCallback<List<OutHaveListBean>>() {
            @Override
            public void onSuccess(List<OutHaveListBean> data, int status, String msg, int total) {
                LogUtil.e("未结成功---onSuccess---" + "-status-" + status + "-msg-" + msg+"-data-"+data);
                lv_outstand.completeRefresh();
                mtotal = total;
                if (data != null) {
                    mData = data;
                    if (page_out == 1) {
                        outStandAdapter = new OutStandAdapter(getActivity(), mData);
                        lv_outstand.setAdapter(outStandAdapter);
                    } else {
//                        mData.addAll(data);
                        outStandAdapter.addData(data);
                        outStandAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (page_out == 1) {
                        LogUtil.e("未结成功-data-" + data);
                        lv_outstand.setVisibility(View.GONE);
                        fl_no_deposit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e("未结失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
                lv_outstand.setVisibility(View.GONE);
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
        page_out = 1;
        //        SetData(time, page_out, 1);
        SetDataOut(page_out, 20);
        lv_outstand.completeRefresh();
    }

    @Override
    public void onLoadingMore() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        //        SetData(time, page_out += 1, 0);
        if (mData.size() < mtotal) {
            SetDataOut(page_out += 1, 20);
        }
        lv_outstand.completeRefresh();
    }
}
