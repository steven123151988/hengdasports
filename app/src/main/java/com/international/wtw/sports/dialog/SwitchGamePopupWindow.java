package com.international.wtw.sports.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.sports.R;
import com.international.wtw.sports.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.sports.utils.LotteryUtil;
import com.international.wtw.sports.utils.SizeUtils;

import java.util.Map;
import java.util.Set;

/**
 * Created by XiaoXin on 2017/10/6.
 * 描述：
 */

public class SwitchGamePopupWindow extends BaseCustomPopup implements View.OnClickListener {

    private Context context;
    private int gameCode;
    private OnItemClickListener onItemClickListener;

    public SwitchGamePopupWindow(Context context, int gameCode) {
        super(context);
        this.context = context;
        this.gameCode = gameCode;
    }

    public SwitchGamePopupWindow setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_layout_switch_game, SizeUtils.dp2px(150), ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true)
                .setAnimationStyle(R.style.AnimDown);
    }

    @Override
    protected void initViews(View view) {
        LinearLayout llContainer = getView(R.id.ll_container);
        Map<Integer, String> otherGames = LotteryUtil.get().getAllOtherGames(gameCode);
        llContainer.removeAllViews();
        Set<Map.Entry<Integer, String>> entries = otherGames.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            TextView textView = new TextView(context);
            textView.setTag(entry.getKey());
            textView.setText(entry.getValue());
            textView.setTextSize(16);
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(40)
            );
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(this);
            llContainer.addView(textView, params);
        }
    }

    @Override
    public void onClick(View v) {
        int gameCode = (int) v.getTag();
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(gameCode);
        }
        dismiss();
    }

    public interface OnItemClickListener {
        void onItemClick(int gameCode);
    }
}
