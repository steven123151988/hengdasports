package com.international.wtw.sports.activity.first;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.activity.mine.WebViewActivity;
import com.international.wtw.sports.adapter.PreferentialAdapter;
import com.international.wtw.sports.api.NetCallback;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.base.app.BaseActivity;
import com.international.wtw.sports.base.app.ViewHolder;
import com.international.wtw.sports.json.PreferentialProBean;
import com.international.wtw.sports.utils.LogUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/10/4.
 */

public class PreferentialActivity extends BaseActivity {

    private ImageView img_back;
    private GridView gv_preferential;
    private PreferentialAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preferential;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gv_preferential = (GridView) findViewById(R.id.gv_preferential);

        SetData();
    }

    public void SetData() {
        NetRepository.get().Preferential(this, 1, -1, new NetCallback<List<PreferentialProBean>>() {
            @Override
            public void onSuccess(List<PreferentialProBean> data, int status, String msg, int total) {
                LogUtil.e("优惠活动--onSuccess-" + "-status-" + status + "-msg-" + msg);
                adapter = new PreferentialAdapter(PreferentialActivity.this, data);
                gv_preferential.setAdapter(adapter);

                gv_preferential.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 = new Intent(PreferentialActivity.this, WebViewActivity.class);
                        intent1.putExtra(WebViewActivity.EXTRA_WEB_TITLE, data.get(position).getTitle());
                        intent1.putExtra(WebViewActivity.EXTRA_WEB_URL, data.get(position).getWeburl());
                        startActivity(intent1);
                    }
                });
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e("优惠活动--onFailure-" + "-status-" + status + "-errorMsg-" + errorMsg);
            }
        });
    }

}
