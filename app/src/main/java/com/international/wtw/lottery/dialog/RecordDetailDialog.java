package com.international.wtw.lottery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.model.FundingRecord;
import com.international.wtw.lottery.utils.DateUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by A bin on 2017/6/25.
 */

public class RecordDetailDialog extends Dialog implements View.OnClickListener {
    private TextView tv_record_no, tv_record_time, tv_record_style, tv_record_status, tv_record_amount, tv_record_bz;
    private Button btn_confirm;
    private View mContentView;
    private ImageView img_close;

    public RecordDetailDialog(Context context, FundingRecord item) {
        super(context, R.style.DialogTheme);
        // LinearLayout.LayoutParams params = new
        mContentView = View.inflate(getContext(), R.layout.dialog_record_detail, null);
        setContentView(mContentView);
        tv_record_no = (TextView) mContentView.findViewById(R.id.tv_record_no);
        tv_record_time = (TextView) mContentView.findViewById(R.id.tv_record_time);
        tv_record_style = (TextView) mContentView.findViewById(R.id.tv_record_style);
        tv_record_status = (TextView) mContentView.findViewById(R.id.tv_record_status);
        tv_record_amount = (TextView) mContentView.findViewById(R.id.tv_record_amount);
        tv_record_bz = (TextView) mContentView.findViewById(R.id.tv_record_bz);
        btn_confirm = (Button) mContentView.findViewById(R.id.btn_recharge);
        img_close = (ImageView) mContentView.findViewById(R.id.img_close);
        btn_confirm.setOnClickListener(this);
        img_close.setOnClickListener(this);

        tv_record_no.setText(item.getBillcode());
        String time = DateUtil.convertTime(item.getCreatedTime() * 1000);
        Logger.d(time);
        tv_record_time.setText(time);
        switch (item.getType()) {
            case 0:
                tv_record_style.setText(context.getResources().getString(R.string.cun_ru));
                break;
            case 1:
                tv_record_style.setText(context.getResources().getString(R.string.qu_chu));
                break;
        }
        switch (item.getOrderStatus()) {
            case 0://审核中
                tv_record_status.setText(context.getResources().getString(R.string.untreated));
                tv_record_status.setTextColor(context.getResources().getColor(R.color.bet_color_blue));
                break;
            case 1://成功
                tv_record_status.setText(context.getResources().getString(R.string.success));
                tv_record_status.setTextColor(context.getResources().getColor(R.color.gray));
                break;
            case 2://失败
                tv_record_status.setText(context.getResources().getString(R.string.fail));
                tv_record_status.setTextColor(context.getResources().getColor(R.color.red));
                break;
        }

        tv_record_amount.setText(String.valueOf(item.getMoney()));
        if (!TextUtils.isEmpty(item.getRemark())) {
            tv_record_bz.setVisibility(View.VISIBLE);
            tv_record_bz.setText(item.getRemark());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge:
                dismiss();
                break;
            case R.id.img_close:
                dismiss();
                break;
        }
    }
}
