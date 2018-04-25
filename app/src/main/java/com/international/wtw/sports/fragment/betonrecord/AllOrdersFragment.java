package com.international.wtw.sports.fragment.betonrecord;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.activity.mine.BetRecordNewActivity;
import com.international.wtw.sports.adapter.AllOrdersAdapter;
import com.international.wtw.sports.api.NetCallback;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.base.view.CustomListView;
import com.international.wtw.sports.json.AllOrderListBean;
import com.international.wtw.sports.utils.LogUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

public class AllOrdersFragment extends Fragment {

    private View view;
    private CustomListView lv_bet_outstand, lv_bet_has;
    private ScrollView slv_no_deposit;
    private FrameLayout fl_no_deposit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allorder, container, false);

        lv_bet_outstand = (CustomListView) view.findViewById(R.id.lv_bet_outstand);
        lv_bet_has = (CustomListView) view.findViewById(R.id.lv_bet_has);

        slv_no_deposit = (ScrollView) view.findViewById(R.id.slv_no_deposit);
        fl_no_deposit = (FrameLayout) view.findViewById(R.id.fl_no_deposit);

        SetData();

        return view;
    }

    private void SetData() {

        NetRepository.get().betRecord(this, 1, 20, new NetCallback<List<AllOrderListBean>>() {
            @Override
            public void onSuccess(List<AllOrderListBean> data, int status, String msg, int total) {
                LogUtil.e("汇总成功---onSuccess---" + "-status-" + status + "-msg-" + msg+"-data-"+data);
                if (data != null) {
                    AllOrdersAdapter adapter = new AllOrdersAdapter(getActivity(), data);
                    lv_bet_outstand.setAdapter(adapter);

                    lv_bet_outstand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent_outstand = new Intent(getActivity(), BetRecordNewActivity.class);
                            intent_outstand.putExtra("type", data.get(position).getStatus());
                            intent_outstand.putExtra("createdTime", data.get(position).getCreatedTime());
                            startActivity(intent_outstand);
                        }
                    });
                } else {
                    LogUtil.e("汇总成功---onSuccess---" + "-data-" + data);
                    slv_no_deposit.setVisibility(View.GONE);
                    fl_no_deposit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e("汇总失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
                slv_no_deposit.setVisibility(View.GONE);
                fl_no_deposit.setVisibility(View.VISIBLE);
            }
        });

    }

}
