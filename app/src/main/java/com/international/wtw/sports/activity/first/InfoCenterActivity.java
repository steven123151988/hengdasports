package com.international.wtw.sports.activity.first;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.adapter.infoCenterAdaper;
import com.international.wtw.sports.base.LotteryId;
import com.international.wtw.sports.base.app.BaseActivity;
import com.international.wtw.sports.base.app.ViewHolder;
import com.international.wtw.sports.json.InfoCenterBean;
import com.international.wtw.sports.model.Announcement;

import java.util.List;

/**
 * Created by 18Steven on 2017/9/22. 消息中心
 */

public class InfoCenterActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private infoCenterAdaper adaper;
    private List<InfoCenterBean.ResponseBean> mInfoCenterBean;
    private TextView tv_tltle_infor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_center;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        listView = (ListView) findViewById(R.id.lv_info);
        tv_tltle_infor = (TextView) findViewById(R.id.tv_tltle_infor);
        findViewById(R.id.iv_back).setOnClickListener(this);
        mInfoCenterBean = LotteryId.sInfoCenterBean;
        String title = getIntent().getStringExtra("title");
        tv_tltle_infor.setText(title);
        if (null != mInfoCenterBean&& mInfoCenterBean.size() > 0) {
            adaper = new infoCenterAdaper(InfoCenterActivity.this, mInfoCenterBean);
            listView.setAdapter(adaper);
            adaper.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
