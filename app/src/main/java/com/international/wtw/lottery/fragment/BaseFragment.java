package com.international.wtw.lottery.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.dialog.SimpleProgressDialog;

/**
 * fragment父类
 * Created by 18Steven on 2017/6/24.fragment父类
 */

public class BaseFragment extends Fragment {
    protected Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 展示loading。。。。
     */
    public void showLoadingDialog() {
        if (null != getActivity()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null == dialog) {
                        dialog = new SimpleProgressDialog(getActivity(), "请稍等...");
                    }
                    dialog.show();
                }
            });
        }

    }


    public void dismissDialog() {
        if (null != dialog && null != getActivity()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        dismissDialog();
        NetRepository.get().cancel(this);
        super.onDestroyView();
    }
}
