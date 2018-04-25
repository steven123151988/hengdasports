package com.international.wtw.lottery.fragment.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.first.InfoCenterActivity;
import com.international.wtw.lottery.activity.manager.BankcardActivity;
import com.international.wtw.lottery.activity.mine.AboutUsActivity;
import com.international.wtw.lottery.activity.mine.BetOnRecordActivity;
import com.international.wtw.lottery.activity.mine.MyPasswordActivity;
import com.international.wtw.lottery.activity.mine.PersonalCenterActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.adapter.MineAdapter;
import com.international.wtw.lottery.api.LocalRepository;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.UserChangedEvent;
import com.international.wtw.lottery.fragment.BaseFragment;
import com.international.wtw.lottery.model.BankModel;
import com.international.wtw.lottery.model.MineItem;
import com.international.wtw.lottery.model.UserModel;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private TextView mine_tv_name, mine_tv_balance;
    private ImageView mine_img_balance;
    private GridView mine_gv;
    private List<MineItem> list;
    private TextView mine_out_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        EventBus.getDefault().register(this);
        InitView();

        list = LocalRepository.get().getMineItems();
        MineAdapter adapter = new MineAdapter(getActivity(), list);
        mine_gv.setAdapter(adapter);

        mine_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null == getActivity())
                    return;
                UserModel user = UserHelper.get().getCurrUser();
                boolean aBoolean = user != null && !user.isVisitor();
                switch (position) {
                    case 0:
                        if (aBoolean) {
                            startActivity(new Intent(getActivity(), PersonalCenterActivity.class));
                        } else {
                            ToastDialog.warning(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                        }
                        break;
                    case 1:
                        if (aBoolean) {
                            startActivity(new Intent(getActivity(), MyPasswordActivity.class));
                        } else {
                            ToastDialog.warning(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                        }
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), InfoCenterActivity.class));
                        break;
                    case 3:
                        if (aBoolean) {
                            ((MainActivity) getActivity()).changeShowFragment(2);
                        } else {
                            ToastDialog.warning(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                        }
                        break;
                    case 4:
                        if (aBoolean) {
                            NetRepository.get().getBankInfo(this, new NetCallback2<BankModel>() {
                                @Override
                                public void onSuccess(BankModel data, int status, String msg) {
                                    Intent intent = new Intent(getActivity(), BankcardActivity.class);
                                    intent.putExtra(BankcardActivity.BANK_INFO, data);
                                    intent.putExtra(BankcardActivity.TITLE, getResources().getString(
                                            data != null ? R.string.modify_bank_info : R.string.add_bank_info));
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(int status, String errorMsg) {
                                    if (status == 511) {
                                        Intent intent = new Intent(getActivity(), BankcardActivity.class);
                                        intent.putExtra(BankcardActivity.TITLE, getResources().getString(R.string.add_bank_info));
                                        startActivity(intent);
                                    } else {
                                        ToastDialog.warning(errorMsg).show(getFragmentManager());
                                    }
                                }
                            });
                        } else {
                            ToastDialog.warning(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                        }
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), BetOnRecordActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), InfoCenterActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), AboutUsActivity.class));
                        break;
                    case 8:
                        String serviceUrl = SharePreferencesUtil.getString(getActivity(), LotteryId.SERVICE_URL, "");
                        if (!TextUtils.isEmpty(serviceUrl)) {
                            Intent intent = new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, getString(R.string.service_online));
                            intent.putExtra(WebViewActivity.EXTRA_WEB_URL, serviceUrl);
                            intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                            getActivity().startActivity(intent);
                        }
                        break;
                }
            }
        });

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserChangedEvent event) {
        if (event.mUser != null) {
            mine_tv_name.setText(event.mUser.getUsername());
            mine_tv_balance.setText(event.mUser.getBalance());
        } else {
            mine_tv_name.setText("");
            mine_tv_balance.setText("0.00");
        }
    }

    public String gettime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        int time = Integer.parseInt(str);
        LogUtil.e("time---" + time);

        String hello = null;
        if (time >= 0 && time <= 5) {
            hello = "晚上好！";
        }
        if (time > 5 && time <= 8) {
            hello = "早上好！";
        }
        if (time > 8 && time <= 10) {
            hello = "上午好!";
        }
        if (time > 10 && time <= 12) {
            hello = "中午好！";
        }
        if (time > 12 && time <= 16) {
            hello = "下午好！";
        }
        if (time > 16 && time <= 18) {
            hello = "傍晚好！";
        }
        if (time > 18) {
            hello = "晚上好！";
        }
        return hello;
    }

    private void InitView() {
        mine_tv_name = (TextView) view.findViewById(R.id.mine_tv_name);
        mine_tv_balance = (TextView) view.findViewById(R.id.mine_tv_balance);
        mine_img_balance = (ImageView) view.findViewById(R.id.mine_img_balance);
        mine_gv = (GridView) view.findViewById(R.id.mine_gv);
        mine_out_login = (TextView) view.findViewById(R.id.mine_out_login);

        mine_out_login.setOnClickListener(this);
        mine_img_balance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_img_balance:

                break;
            case R.id.mine_out_login:
                CreateDialog();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != getActivity()) {
            UserModel user = UserHelper.get().getCurrUser();
            if (user == null) {
                mine_out_login.setVisibility(View.GONE);
            } else {
                String hello = gettime();
                mine_tv_name.setText(hello + " " + user.getUsername());
                mine_tv_balance.setText(user.getBalance());
            }
        }
    }

    public void CreateDialog() {
        if (null == getActivity())
            return;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.out_login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);
        builder.create();
        final AlertDialog dialog = builder.show();

        Button btn_out_login_cancel = (Button) view.findViewById(R.id.btn_out_login_cancel);
        Button btn_out_login_ok = (Button) view.findViewById(R.id.btn_out_login_ok);

        btn_out_login_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_out_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用退出登录接口
                NetRepository.get().logout();
                UserHelper.get().userSignOut();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
