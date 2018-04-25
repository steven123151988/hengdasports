package com.international.wtw.sports.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.adapter.HasAdapter;
import com.international.wtw.sports.adapter.OutStandAdapter;
import com.international.wtw.sports.api.NetCallback;
import com.international.wtw.sports.api.NetRepository;
import com.international.wtw.sports.base.app.BaseActivity;
import com.international.wtw.sports.base.app.ViewHolder;
import com.international.wtw.sports.base.view.CustomRefreshListView;
import com.international.wtw.sports.json.OutHaveListBean;
import com.international.wtw.sports.json.SummaryDetailsResBean;
import com.international.wtw.sports.utils.LogUtil;
import com.international.wtw.sports.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/28.
 */

public class BetRecordNewActivity extends BaseActivity implements CustomRefreshListView.OnRefreshListener {

    private CustomRefreshListView lv_bet_record;
    private ImageView iv_back;
    private TextView tv_rq;

    private HasAdapter hasAdapter;
    private OutStandAdapter outStandAdapter;

    private int type;
    private int createdTime;

    private List<SummaryDetailsResBean> summaryDetailsResBeanList;

    private FrameLayout fl_no_deposit;

    private int page_bet = 1;
    private List<OutHaveListBean> mData;
    private int mtotal;

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_betrecord_new;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        lv_bet_record = (CustomRefreshListView) findViewById(R.id.lv_bet_record);
        lv_bet_record.setOnRefreshListener(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_rq = (TextView) findViewById(R.id.tv_rq);
        fl_no_deposit = (FrameLayout) findViewById(R.id.fl_no_deposit);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        createdTime = intent.getIntExtra("createdTime", 0);
        String datetime = TimeUtil.getDateStringByTimeSTamp((long) createdTime, "yyyy-MM-dd HH:mm:ss");
        tv_rq.setText(datetime);

        if (type == 0) {
            SetDataBet1(page_bet, 20, createdTime);
        } else {
            SetDataBet2(page_bet, 20, createdTime);
        }
    }

    private void SetDataBet1(int page, int num, int time) {
        NetRepository.get().lotteryBillDef(this, page, num, time, new NetCallback<List<OutHaveListBean>>() {
            @Override
            public void onSuccess(List<OutHaveListBean> data, int status, String msg, int total) {
                LogUtil.e("当天未结成功---onSuccess---" + "-status-" + status + "-msg-" + msg);
                lv_bet_record.completeRefresh();
                mtotal = total;
                if (data != null) {
                    mData = data;
                    if (type == 0) {
                        if (page_bet == 1) {
                            outStandAdapter = new OutStandAdapter(BetRecordNewActivity.this, mData);
                            lv_bet_record.setAdapter(outStandAdapter);
                        } else {
                            outStandAdapter.addData(data);
//                            mData.addAll(data);
                            outStandAdapter.notifyDataSetChanged();
                        }
                    } else if (type == 1) {
                        if (page_bet == 1) {
                            hasAdapter = new HasAdapter(BetRecordNewActivity.this, mData);
                            lv_bet_record.setAdapter(hasAdapter);
                        } else {
                            hasAdapter.addData(data);
//                            mData.addAll(data);
                            hasAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (page_bet == 1) {
                        LogUtil.e("当天未结成功---onSuccess---" + "-data-" + data);
                        lv_bet_record.setVisibility(View.GONE);
                        fl_no_deposit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e("当天未结失败---onSuccess---" + "-status-" + status + "-errorMsg-" + errorMsg);
                lv_bet_record.setVisibility(View.GONE);
                fl_no_deposit.setVisibility(View.VISIBLE);
            }
        });
    }

    private void SetDataBet2(int page, int num, int time) {
        NetRepository.get().todayendDef(this, page, num, time, new NetCallback<List<OutHaveListBean>>() {
            @Override
            public void onSuccess(List<OutHaveListBean> data, int status, String msg, int total) {
                LogUtil.e("当天已结成功---onSuccess---" + "-status-" + status + "-msg-" + msg);
                lv_bet_record.completeRefresh();
                mtotal = total;
                if (data != null) {
                    mData = data;
                    if (type == 0) {
                        if (page_bet == 1) {
                            outStandAdapter = new OutStandAdapter(BetRecordNewActivity.this, mData);
                            lv_bet_record.setAdapter(outStandAdapter);
                        } else {
                            outStandAdapter.addData(data);
//                            mData.addAll(data);
                            outStandAdapter.notifyDataSetChanged();
                        }
                    } else if (type == 1) {
                        if (page_bet == 1) {
                            hasAdapter = new HasAdapter(BetRecordNewActivity.this, mData);
                            lv_bet_record.setAdapter(hasAdapter);
                        } else {
                            hasAdapter.addData(data);
//                            mData.addAll(data);
                            hasAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (page_bet == 1) {
                        LogUtil.e("当天已结成功---onSuccess---" + "-data-" + data);
                        lv_bet_record.setVisibility(View.GONE);
                        fl_no_deposit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e("当天已结失败---onSuccess---" + "-status-" + status + "-errorMsg-" + errorMsg);
                lv_bet_record.setVisibility(View.GONE);
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
        page_bet = 1;
        if (mData.size() < mtotal) {
            if (type == 0) {
                SetDataBet1(page_bet, 20, createdTime);
            } else {
                SetDataBet2(page_bet, 20, createdTime);
            }
        }
        lv_bet_record.completeRefresh();
    }

    @Override
    public void onLoadingMore() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        if (mData.size() < mtotal) {
            if (type == 0) {
                SetDataBet1(page_bet += 1, 20, createdTime);
            } else {
                SetDataBet2(page_bet += 1, 20, createdTime);
            }
        }
        lv_bet_record.completeRefresh();
    }
}
