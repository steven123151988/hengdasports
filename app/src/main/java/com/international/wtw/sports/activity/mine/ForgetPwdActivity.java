package com.international.wtw.sports.activity.mine;

import android.view.View;

import com.international.wtw.sports.R;
import com.international.wtw.sports.base.app.BaseActivity;
import com.international.wtw.sports.base.app.ViewHolder;

/**
 * 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity  implements  View.OnClickListener{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {


        holder.setOnClickListener(this,R.id.btn_recharge);
        holder.setOnClickListener(this,R.id.imageView_toLeftArrow);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn_recharge:
                finish();
                break;

            case R.id.imageView_toLeftArrow:
                finish();
                break;
        }
    }
}
