package com.international.wtw.lottery.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.first.InfoCenterActivity;
import com.international.wtw.lottery.activity.first.PreferentialActivity;
import com.international.wtw.lottery.activity.login.LoginActivity;
import com.international.wtw.lottery.activity.login.RegisterActivity;
import com.international.wtw.lottery.activity.lottery.BetActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.adapter.base.NewRecyclerViewBaseAdapter;
import com.international.wtw.lottery.api.LocalRepository;
import com.international.wtw.lottery.api.NetCallback;
import com.international.wtw.lottery.api.NetCallback2;
import com.international.wtw.lottery.api.NetRepository;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.view.CustomGridView;
import com.international.wtw.lottery.dialog.MenuPopupWindow;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.easypopup.EasyPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.event.UserChangedEvent;
import com.international.wtw.lottery.fragment.BaseFragment;
import com.international.wtw.lottery.json.InfoCenterBean;
import com.international.wtw.lottery.json.LunbotuBean;
import com.international.wtw.lottery.json.PreferentialProBean;
import com.international.wtw.lottery.model.GameModel;
import com.international.wtw.lottery.model.UserModel;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.NetWorkUtils;
import com.international.wtw.lottery.utils.RoundedCornersTransformation;
import com.international.wtw.lottery.utils.ScreenUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.SizeUtils;
import com.international.wtw.lottery.utils.UserHelper;
import com.international.wtw.lottery.widget.MarqueeView;
import com.international.wtw.lottery.widget.banner.BannerBaseView;
import com.international.wtw.lottery.widget.banner.MainBannerView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by 18Steven on 2017/6/24. 首页
 */

public class FirstFragment extends BaseFragment implements View.OnClickListener {
    private CustomGridView recycle_menu;
    private RelativeLayout rl_home_logo;
    private View view;
    private ImageView img_default;
    private TextView tv_user_name;
    private NewRecyclerViewBaseAdapter menuAdapter;
    private MarqueeView noteTextView;
    private ImageView img_menu;

    private RecyclerView bottom_recycler;
    private LunbotuBean lunbotu;
    private ImageView img_user_default;
    private Button btn_shiwan_reg;
    private LinearLayout ll_user;
    private ImageView img_more;
    private EasyPopup mMenuPopup;
    private RelativeLayout topTitle;
    private List<PreferentialProBean> promotions;
    private BaseQuickAdapter<PreferentialProBean, BaseViewHolder> mBottomAdapter;
    private String serviceUrl;
    private List<InfoCenterBean.ResponseBean> mInfoCenterBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firstview_un, null);
        view.findViewById(R.id.img_shiwan).setOnClickListener(this);
        view.findViewById(R.id.img_login_reg).setOnClickListener(this);
        topTitle = (RelativeLayout) view.findViewById(R.id.topTitle);
        noteTextView = (MarqueeView) view.findViewById(R.id.marquee);
        view.findViewById(R.id.marquee).setOnClickListener(this);
        bottom_recycler = (RecyclerView) view.findViewById(R.id.bottom_recycler);
        img_more = (ImageView) view.findViewById(R.id.img_more);
        img_more.setOnClickListener(this);

        rl_home_logo = (RelativeLayout) view.findViewById(R.id.rl_home_logo);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        img_menu = (ImageView) view.findViewById(R.id.img_menu);
        img_user_default = (ImageView) view.findViewById(R.id.img_user_default);
        btn_shiwan_reg = (Button) view.findViewById(R.id.btn_shiwan_reg);
        ll_user = (LinearLayout) view.findViewById(R.id.ll_user);
        ll_user.setOnClickListener(this);
        btn_shiwan_reg.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        recycle_menu = (CustomGridView) view.findViewById(R.id.recycle_menu);
        view.findViewById(R.id.iv_laba).setOnClickListener(this);
        view.findViewById(R.id.img_jiantou).setOnClickListener(this);
        //轮播图占位
        setDefaultImag();
        //请求轮播图数据设置轮播图
        if (null != getActivity()) {
            if (NetWorkUtils.isNetworkAvailable(getActivity())) {
                getBannerData();
            }
        }

        //获取消息中心数据
        getInformation();

        List<GameModel> homeGames = LocalRepository.get().getHomeGames();
        initLotteryData(homeGames);

        SetData();
        initBottomRecycler();
        
        //个人信息
        EventBus.getDefault().register(this);
        initUser();

        noteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        noteTextView.pause();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                        noteTextView.start();
                        break;
                }
                return true;
            }
        });

        return view;
    }

    public void SetData() {
        NetRepository.get().Preferential(getActivity(), 1, 3, new NetCallback<List<PreferentialProBean>>() {
            @Override
            public void onSuccess(List<PreferentialProBean> data, int status, String msg, int total) {
                promotions = data;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBottomAdapter.setNewData(promotions);
                    }
                });
            }

            @Override
            public void onFailure(int status, String errorMsg) {
            }
        });
    }

    private void initBottomRecycler() {
        mBottomAdapter = new BaseQuickAdapter<PreferentialProBean, BaseViewHolder>(R.layout.item_home_bottom) {
            @Override
            protected void convert(BaseViewHolder helper, PreferentialProBean item) {
                ImageView view = helper.getView(R.id.iv_promotion_img);
                if (!TextUtils.isEmpty(item.getImageurl())) {
                    Picasso.with(getContext())
                            .load(item.getImageurl())
                            .error(R.drawable.shape_gray)
                            .transform(new RoundedCornersTransformation(SizeUtils.dp2px(5), 0, RoundedCornersTransformation.CornerType.TOP))
                            .into(view);
                }
                helper.setText(R.id.tv_promotion_desc, item.getTitle());
            }
        };
        bottom_recycler.setHasFixedSize(true);
        bottom_recycler.setNestedScrollingEnabled(true);
        bottom_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        bottom_recycler.setAdapter(mBottomAdapter);
        mBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, promotions.get(position).getTitle());
                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, promotions.get(position).getWeburl());
                startActivity(intent);
            }
        });
    }

    /**
     * 彩票信息
     */
    private void initLotteryData(List<GameModel> msgBeen) {
        if (null == getActivity())
            return;

        menuAdapter = new NewRecyclerViewBaseAdapter(getActivity(), msgBeen);
        recycle_menu.setAdapter(menuAdapter);
        recycle_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GameModel item = (GameModel) menuAdapter.getItem(position);
                navToTargetActivity(item.gameCode);
            }
        });
    }

    private void initUser() {
        UserModel user = UserHelper.get().getCurrUser();
        if (user == null) {
            //没登录
            img_user_default.setVisibility(View.GONE);
            tv_user_name.setVisibility(View.GONE);
            rl_home_logo.setVisibility(View.VISIBLE);
            btn_shiwan_reg.setVisibility(View.GONE);
            ll_user.setVisibility(View.VISIBLE);
            img_menu.setVisibility(View.GONE);
        } else if (user.isVisitor()) {
            //游客登录
            rl_home_logo.setVisibility(View.GONE);
            img_user_default.setVisibility(View.VISIBLE);
            tv_user_name.setVisibility(View.VISIBLE);
            ll_user.setVisibility(View.VISIBLE);
            btn_shiwan_reg.setVisibility(View.VISIBLE);
            img_menu.setVisibility(View.VISIBLE);
            tv_user_name.setText(user.getUsername());
        } else {
            //正式账号登录
            img_user_default.setVisibility(View.VISIBLE);
            tv_user_name.setVisibility(View.VISIBLE);
            rl_home_logo.setVisibility(View.GONE);
            btn_shiwan_reg.setVisibility(View.GONE);
            ll_user.setVisibility(View.VISIBLE);
            img_menu.setVisibility(View.VISIBLE);
            tv_user_name.setText(user.getUsername());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserChangedEvent event) {
        initUser();
    }

    /**
     * 跳转到对应的Activity
     *
     * @param lotteryType
     */
    private void navToTargetActivity(int lotteryType) {
        if (null == getActivity())
            return;
        switch (lotteryType) {
            case Constants.LOTTERY_TYPE.ONLINE_SERVICE:
                if (!TextUtils.isEmpty(serviceUrl)) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, getString(R.string.service_online));
                    intent.putExtra(WebViewActivity.EXTRA_WEB_URL, serviceUrl);
                    intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                    getActivity().startActivity(intent);
                }
                break;
            case Constants.LOTTERY_TYPE.REAL_VIDEO:
                //getActivity().startActivity(new Intent(getActivity(), NewAgGameActivity.class));
                ToastDialog.warning("即将上线").show(getFragmentManager());
                break;
            default:
                Intent intent = new Intent(getActivity(), BetActivity.class);
                intent.putExtra(BetActivity.GAME_CODE, lotteryType);
                startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (null == getActivity())
            return;
        switch (v.getId()) {
            case R.id.ll_user:
                ((MainActivity) getActivity()).changeShowFragment(3);
                break;
            case R.id.btn_shiwan_reg:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.img_shiwan:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.img_login_reg:
                NetRepository.get().loginDemo(this, new NetCallback2<UserModel>() {
                    @Override
                    public void onSuccess(UserModel data, int status, String msg) {
                        LogUtil.e("登录试玩成功---onSuccess---" + "-status-" + status + "-msg-" + msg);
                        ToastDialog.success(msg).show(getFragmentManager());
                        if (data != null) {
                            UserHelper.get().setCurrUser(data);
                        }
                    }

                    @Override
                    public void onFailure(int status, String errorMsg) {
                        LogUtil.e("登录试玩失败---onFailure---" + "-status-" + status + "-errorMsg-" + errorMsg);
                        ToastDialog.error(errorMsg).show(getFragmentManager());
                    }
                });

                break;
            case R.id.img_menu:
                SetMenu();
                break;
            case R.id.iv_laba:
            case R.id.img_jiantou:
                if (null != LotteryId.sInfoCenterBean) {
                    Intent intetn = new Intent(getActivity(), InfoCenterActivity.class);
                    intetn.putExtra("title", getString(R.string.info_center));
                    startActivity(intetn);
                }
                break;
            case R.id.img_more:
                startActivity(new Intent(getActivity(), PreferentialActivity.class));
                break;
        }
    }

    /**
     * 获取轮播图和消息
     */
    public void getBannerData() {
        NetRepository.get().getBannerImg(this, new Callback<LunbotuBean>() {
            @Override
            public void onResponse(Call<LunbotuBean> call, retrofit2.Response<LunbotuBean> response) {
                lunbotu = response.body();
                if (null != lunbotu && lunbotu.getHttpCode() == 200) {
                    if (null != lunbotu.getResponse() && lunbotu.getResponse().size() > 0) {
                        setLunBo(lunbotu.getResponse());
                    }
                    if (lunbotu != null || lunbotu.getParameter() != null) {
                        serviceUrl = lunbotu.getParameter();
                        if (!TextUtils.isEmpty(serviceUrl)) {
                            SharePreferencesUtil.addString(getContext(), LotteryId.SERVICE_URL, serviceUrl);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LunbotuBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * 获取消息中心数据
     */
    private void getInformation() {
        NetRepository.get().getAllAnnouncements(this, new NetCallback<List<InfoCenterBean.ResponseBean>>() {
            @Override
            public void onSuccess(List<InfoCenterBean.ResponseBean> data, int status, String msg, int total) {
                if (data == null) {
                    return;
                }
                LotteryId.sInfoCenterBean = data;
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < data.size(); i++) {
                    buffer.append(data.get(i).getContent()).append("    ");
                }
                noteTextView.setText(buffer.toString());
                noteTextView.start();
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                Logger.e(errorMsg);
            }
        });
    }


    public void SetMenu() {
        if (mMenuPopup == null) {
            mMenuPopup = new MenuPopupWindow(getActivity())
                    .createPopup();
        }
        mMenuPopup.showAtAnchorView(topTitle, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT);
    }

    /**
     * 设置默认的图片
     */
    private void setDefaultImag() {
        img_default = (ImageView) view.findViewById(R.id.img_default);
        int bannerHeight = (int) ((ScreenUtils.getScreenWidth(getActivity()) * 0.4f));
        ViewGroup.LayoutParams params = img_default.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = bannerHeight;
        img_default.setLayoutParams(params);
        img_default.setVisibility(View.VISIBLE);
    }

    /**
     * 设置轮播图
     */
    private void setLunBo(List<LunbotuBean.ResponseBean> responseBean) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < responseBean.size(); i++) {
            list.add(responseBean.get(i).getImageUrl());
        }

        if (null != getActivity()) {
            RelativeLayout bannerContent = (RelativeLayout) view.findViewById(R.id.banner_cont);
            BannerBaseView banner = new MainBannerView(getActivity());
            bannerContent.addView(banner);
            banner.update(list);
            img_default.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
