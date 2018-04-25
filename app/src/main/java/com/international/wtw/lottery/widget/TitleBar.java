package com.international.wtw.lottery.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;

public class TitleBar extends LinearLayout {
    private static final int DEFAULT_TEXT_COLOR = 0XFFFFFFFF;
    private static final int DEFAULT_TEXT_BG_COLOR = 0X00000000;
    private static final int DEFAULT_TEXT_SIZE = 16;

    private static final int DEFAULT_SUB_TEXT_SIZE = 12;
    /**
     * 左边容器
     */
    private LinearLayout mLeftLayout;

    /**
     * 右边容器
     */
    private LinearLayout mCenterLayout;
    /**
     * 左边容器
     */
    private LinearLayout mRightLayout;
    private TextView mLeftTv;
    /**
     * 主标题
     */
    private TextView mTitleTv;
    /**
     * 副标题
     */
    private TextView mSubTitleText;
    /**
     * 下方分割线
     */
    private View mDividerView;
    private TextView mRightTv;
    /**
     * 状态栏高度
     */
    private int mStatusBarHeight;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    private int mPaddingValue;
    private int mDividerHeight;
    private int mActionPadding;

    private int mLeftTextSize;
    private int mLeftTextColor;
    private int mLeftTextBackgroundColor;
    private int mLeftDrawable;
    private int mLeftDrawablePadding;
    private int mLeftTextBackgroundResource;

    private int mTitleMainTextSize;
    private int mTitleMainTextColor;
    private int mTitleMainTextBackgroundColor;
    private int mTitleMainTextBackgroundResource;

    private int mTitleSubTextSize;
    private int mTitleSubTextColor;
    private int mTitleSubTextBackgroundColor;
    private int mTitleSubTextBackgroundResource;

    private int mRightTextSize;
    private int mRightTextColor;
    private int mRightTextBackgroundColor;
    private int mRightDrawable;
    private int mRightDrawablePadding;
    private int mRightTextBackgroundResource;

    private int mActionTextSize;
    private int mActionTextColor;
    private int mActionTextBackgroundColor;
    private int mActionTextBackgroundResource;

    /**
     * 支持沉浸式状态栏
     */
    private boolean immersible = false;

    private boolean isTitleFakeBold;


    private String mTitleMainText;
    private String mTitleSubText;
    private String mLeftText;
    private String mRightText;


    public TitleBar(Context context) {
        this(context, null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        init(context);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mPaddingValue = ta.getDimensionPixelSize(R.styleable.TitleBar_title_outPadding, dip2px(context, 16));
        mDividerHeight = ta.getDimensionPixelSize(R.styleable.TitleBar_title_dividerHeight, 0);
        mActionPadding = ta.getDimensionPixelSize(R.styleable.TitleBar_title_actionPadding, dip2px(context, 2));
        immersible = ta.getBoolean(R.styleable.TitleBar_title_immersible, false);
        isTitleFakeBold = ta.getBoolean(R.styleable.TitleBar_title_titleMainTextFakeBold, false);

        mLeftText = ta.getString(R.styleable.TitleBar_title_leftText);
        mLeftTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_title_leftTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
        mLeftTextColor = ta.getColor(R.styleable.TitleBar_title_leftTextColor, DEFAULT_TEXT_COLOR);
        mLeftTextBackgroundResource = ta.getResourceId(R.styleable.TitleBar_title_leftTextBackgroundResource, -1);
        mLeftTextBackgroundColor = ta.getColor(R.styleable.TitleBar_title_leftTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mLeftDrawable = ta.getResourceId(R.styleable.TitleBar_title_leftTextDrawable, -1);
        mLeftDrawablePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_title_leftTextDrawablePadding, 0);

        mTitleMainText = ta.getString(R.styleable.TitleBar_title_titleMainText);
        mTitleMainTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_title_titleMainTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
        mTitleMainTextColor = ta.getColor(R.styleable.TitleBar_title_titleMainTextColor, DEFAULT_TEXT_COLOR);
        mTitleMainTextBackgroundColor = ta.getColor(R.styleable.TitleBar_title_titleMainTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mTitleMainTextBackgroundResource = ta.getResourceId(R.styleable.TitleBar_title_titleMainTextBackgroundResource, -1);

        mTitleSubText = ta.getString(R.styleable.TitleBar_title_titleSubText);
        mTitleSubTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_title_titleSubTextSize, sp2px(context, DEFAULT_SUB_TEXT_SIZE));
        mTitleSubTextColor = ta.getColor(R.styleable.TitleBar_title_titleSubTextColor, DEFAULT_TEXT_COLOR);
        mTitleSubTextBackgroundColor = ta.getColor(R.styleable.TitleBar_title_titleSubTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mTitleSubTextBackgroundResource = ta.getResourceId(R.styleable.TitleBar_title_titleSubTextBackgroundResource, -1);

        mRightText = ta.getString(R.styleable.TitleBar_title_rightText);
        mRightTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_title_rightTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
        mRightTextColor = ta.getColor(R.styleable.TitleBar_title_rightTextColor, DEFAULT_TEXT_COLOR);
        mRightTextBackgroundResource = ta.getResourceId(R.styleable.TitleBar_title_rightTextBackgroundResource, -1);
        mRightTextBackgroundColor = ta.getColor(R.styleable.TitleBar_title_rightTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mRightDrawable = ta.getResourceId(R.styleable.TitleBar_title_rightTextDrawable, -1);
        mRightDrawablePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_title_rightTextDrawablePadding, 0);

        mActionTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_title_actionTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
        mActionTextColor = ta.getColor(R.styleable.TitleBar_title_actionTextColor, DEFAULT_TEXT_COLOR);
        mActionTextBackgroundColor = ta.getColor(R.styleable.TitleBar_title_actionTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mActionTextBackgroundResource = ta.getResourceId(R.styleable.TitleBar_title_actionTextBackgroundResource, -1);

        ta.recycle();
    }

    private void init(Context context) {
        if (context instanceof Activity) {
            setImmersible((Activity) context, immersible);
        }
        mScreenWidth = getScreenWidth();
        initView(context);

    }


    private void initView(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        LayoutParams dividerParams = new LayoutParams(LayoutParams.MATCH_PARENT, mDividerHeight);

        mLeftLayout = new LinearLayout(context);
        mCenterLayout = new LinearLayout(context);
        mRightLayout = new LinearLayout(context);
        mDividerView = new View(context);

        mLeftLayout.setGravity(Gravity.CENTER_VERTICAL);
        mCenterLayout.setGravity(Gravity.CENTER);
        mCenterLayout.setOrientation(LinearLayout.VERTICAL);

        mRightLayout.setGravity(Gravity.CENTER_VERTICAL);
        mLeftLayout.setPadding(mPaddingValue, 0, 0, 0);
        mRightLayout.setPadding(0, 0, mPaddingValue, 0);

        mLeftTv = new TextView(context);
        mLeftTv.setGravity(Gravity.CENTER);
        setLeftText(mLeftText);
        mLeftTv.setSingleLine();
        mLeftTv.setTextSize(px2sp(context, mLeftTextSize));
        mLeftTv.setTextColor(mLeftTextColor);
        mLeftTv.setBackgroundColor(mLeftTextBackgroundColor);
        if (mLeftTextBackgroundResource != -1) {
            mLeftTv.setBackgroundResource(mLeftTextBackgroundResource);
        }
        if (mLeftDrawable != -1) {
            setLeftTextDrawable(mLeftDrawable, mLeftDrawablePadding);
        }
        mLeftLayout.addView(mLeftTv, params);

        mTitleTv = new TextView(context);
        mTitleTv.setGravity(Gravity.CENTER);
        setTitleMainText(mTitleMainText);
        mTitleTv.getPaint().setFakeBoldText(isTitleFakeBold);
        mTitleTv.setTextSize(px2sp(context, mTitleMainTextSize));
        mTitleTv.setTextColor(mTitleMainTextColor);
        mTitleTv.setBackgroundColor(mTitleMainTextBackgroundColor);
        if (mTitleMainTextBackgroundResource != -1) {
            mTitleTv.setBackgroundResource(mTitleMainTextBackgroundResource);
        }
        mCenterLayout.addView(mTitleTv);

        mSubTitleText = new TextView(context);
        mSubTitleText.setTextSize(px2sp(context, mTitleSubTextSize));
        mSubTitleText.setSingleLine();
        setTitleSubText(mTitleSubText);
        mSubTitleText.setGravity(Gravity.CENTER);
        mSubTitleText.setTextColor(mTitleSubTextColor);
        mSubTitleText.setBackgroundColor(mTitleSubTextBackgroundColor);
        mSubTitleText.setEllipsize(TextUtils.TruncateAt.END);
        if (mTitleSubTextBackgroundResource != -1) {
            mSubTitleText.setBackgroundResource(mTitleSubTextBackgroundResource);
        }
        mCenterLayout.addView(mSubTitleText);

        mRightTv = new TextView(context);
        mRightTv.setGravity(Gravity.CENTER);
        setRightText(mRightText);
        mRightTv.setSingleLine();
        mRightTv.setTextSize(px2sp(context, mRightTextSize));
        mRightTv.setTextColor(mRightTextColor);
        mRightTv.setBackgroundColor(mRightTextBackgroundColor);
        if (mRightTextBackgroundResource != -1) {
            mRightTv.setBackgroundResource(mRightTextBackgroundResource);
        }
        if (mRightDrawable != -1) {
            setRightTextDrawable(mRightDrawable, mRightDrawablePadding);
        }
        mRightLayout.addView(mRightTv, params);


        addView(mLeftLayout, params);//添加左边容器
        addView(mCenterLayout, params);//添加中间容器
        addView(mRightLayout, params);//添加右边容器
        addView(mDividerView, dividerParams);
    }

    public void setImmersible(Activity activity, boolean immersible) {
        setImmersible(activity, immersible, true);
    }

    /**
     * 设置沉浸式状态栏，4.4以上系统支持
     *
     * @param immersible         是否为侵入式
     * @param isPlusStatusHeight 是否增加statusBar 高度
     */
    public void setImmersible(Activity activity, boolean immersible, boolean isPlusStatusHeight) {
        if (this.immersible == immersible) {
            return;
        }
        this.immersible = immersible;
        if (immersible && isPlusStatusHeight && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarHeight = getStatusBarHeight();
        } else {
            mStatusBarHeight = 0;
        }
        if (activity == null) {
            return;
        }
        //透明状态栏
        Window window = activity.getWindow();
        if (immersible) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 透明状态栏
                window.addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 透明状态栏
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
            }
        }
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLeftLayout.layout(0, mStatusBarHeight, mLeftLayout.getMeasuredWidth(),
                mLeftLayout.getMeasuredHeight() + mStatusBarHeight);
        mRightLayout.layout(mScreenWidth - mRightLayout.getMeasuredWidth(),
                mStatusBarHeight, mScreenWidth,
                mRightLayout.getMeasuredHeight() + mStatusBarHeight);
        if (mLeftLayout.getMeasuredWidth() > mRightLayout.getMeasuredWidth()) {
            mCenterLayout.layout(mLeftLayout.getMeasuredWidth(),
                    mStatusBarHeight,
                    mScreenWidth - mLeftLayout.getMeasuredWidth(),
                    getMeasuredHeight());
        } else {
            mCenterLayout.layout(mRightLayout.getMeasuredWidth(),
                    mStatusBarHeight,
                    mScreenWidth - mRightLayout.getMeasuredWidth(),
                    getMeasuredHeight());
        }
        mDividerView.layout(0,
                getMeasuredHeight() - mDividerView.getMeasuredHeight(),
                getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChild(mLeftLayout, widthMeasureSpec, heightMeasureSpec);
        measureChild(mRightLayout, widthMeasureSpec, heightMeasureSpec);
        if (mLeftLayout.getMeasuredWidth() > mRightLayout.getMeasuredWidth()) {
            mCenterLayout.measure(MeasureSpec.makeMeasureSpec(mScreenWidth - 2
                            * mLeftLayout.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    heightMeasureSpec);
        } else {
            mCenterLayout.measure(MeasureSpec.makeMeasureSpec(mScreenWidth - 2
                            * mRightLayout.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    heightMeasureSpec);
        }
        measureChild(mDividerView, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec) + mStatusBarHeight);
    }

    public void setLeftText(CharSequence title) {
        mLeftTv.setText(title);
    }

    public void setLeftText(int id) {
        mLeftTv.setText(id);
    }

    public void setLeftTextSize(int unit, float size) {
        mLeftTv.setTextSize(unit, size);
    }

    public void setLeftTextSize(float size) {
        mLeftTv.setTextSize(size);
    }

    public void setLeftTextColor(int id) {
        mLeftTv.setTextColor(id);
    }

    public void setLeftTextColor(ColorStateList color) {
        try {
            mLeftTv.setTextColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLeftTextBackgroundColor(int color) {
        mLeftTv.setBackgroundColor(color);
    }

    public void setLeftTextBackgroundResource(int id) {
        mLeftTv.setBackgroundResource(id);
    }

    /**
     * 左边文本添加图片
     *
     * @param id 资源id
     */
    public void setLeftTextDrawable(int id, int drawablePadding) {
        mLeftTv.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
        setLeftTextDrawablePadding(drawablePadding);
    }

    public void setLeftTextDrawablePadding(int drawablePadding) {
        this.mLeftDrawablePadding = drawablePadding;
        mLeftTv.setCompoundDrawablePadding(dip2px(getContext(), mLeftDrawablePadding));
    }

    public void setLeftTextDrawable(int id) {
        setLeftTextDrawable(id, mLeftDrawablePadding);
    }

    public void setLeftTextPadding(int left, int top, int right, int bottom) {
        mLeftTv.setPadding(left, top, right, bottom);
    }

    public void setOnLeftTextClickListener(OnClickListener l) {
        mLeftTv.setOnClickListener(l);
    }

    public void setLeftVisible(boolean visible) {
        mLeftTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void addLeftAction(Action action, int position) {
        View view = inflateAction(action);
        mLeftLayout.addView(view, position);
    }

    public View addLeftAction(Action action) {
        View view = inflateAction(action);
        mLeftLayout.addView(view);
        return view;
    }

    /**
     * 自定义中间部分布局
     */
    public void addCenterAction(Action action, int position) {
        View view = inflateAction(action);
        mCenterLayout.addView(view, position);
    }

    /**
     * 自定义中间部分布局
     */
    public void addCenterAction(Action action) {
        View view = inflateAction(action);
        if (view != null) {
            mCenterLayout.addView(view);
        }
    }

    public void setOnCenterClickListener(OnClickListener l) {
        mCenterLayout.setOnClickListener(l);
    }

    public void setTitleMainText(int id) {
        mTitleTv.setText(id);
    }


    public void setTitleMainText(CharSequence charSequence) {
        mTitleTv.setText(charSequence);
    }

    public void setTitleMainTextSize(float titleMainTextSpValue) {
        mTitleTv.setTextSize(titleMainTextSpValue);
    }

    public void setTitleMainTextSize(int unit, float titleMainTextSpValue) {
        mTitleTv.setTextSize(unit, titleMainTextSpValue);
    }

    public void setTitleMainTextColor(int id) {
        mTitleTv.setTextColor(id);
    }

    public void setTitleMainTextBackgroundColor(int color) {
        mTitleTv.setBackgroundColor(color);
    }

    public void setTitleMainTextBackgroundResource(int id) {
        mTitleTv.setBackgroundResource(id);
    }

    /**
     * @param isFakeBold 是否设置粗体标题
     */
    public void setTitleMainTextFakeBold(boolean isFakeBold) {
        this.isTitleFakeBold = isFakeBold;
    }

    public void setTitleMainTextPadding(int left, int top, int right, int bottom) {
        mTitleTv.setPadding(left, top, right, bottom);
    }

    public void setTitleSubText(int id) {
        mSubTitleText.setText(id);
    }

    public void setTitleSubText(CharSequence charSequence) {
        if (charSequence == null || charSequence.toString().isEmpty()) {
            mSubTitleText.setVisibility(GONE);
        } else {
            mSubTitleText.setVisibility(VISIBLE);
        }
        mSubTitleText.setText(charSequence);
    }

    public void setTitleSubTextSize(float titleMainTextSpValue) {
        mSubTitleText.setTextSize(titleMainTextSpValue);
    }

    public void setTitleSubTextColor(int id) {
        mSubTitleText.setTextColor(id);
    }

    public void setTitleSubTextBackgroundColor(int color) {
        mSubTitleText.setBackgroundColor(color);
    }

    public void setTitleSubTextBackgroundResource(int id) {
        mSubTitleText.setBackgroundResource(id);
    }


    public void setRightText(CharSequence title) {
        mRightTv.setText(title);
    }

    public void setRightText(int id) {
        mRightTv.setText(id);
    }

    public void setRightTextSize(int unit, float size) {
        mRightTv.setTextSize(unit, size);
    }

    public void setRightTextSize(float size) {
        mRightTv.setTextSize(size);
    }

    public void setRightTextColor(int id) {
        mRightTv.setTextColor(id);
    }

    public void setRightTextColor(ColorStateList color) {
        try {
            mRightTv.setTextColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRightTextBackgroundColor(int color) {
        mRightTv.setBackgroundColor(color);
    }

    public void setRightTextBackgroundResource(int id) {
        mRightTv.setBackgroundResource(id);
    }

    /**
     * 右边文本添加图片
     *
     * @param id 资源id
     */
    public void setRightTextDrawable(int id, int drawablePadding) {
        mRightTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0);
        setRightTextDrawablePadding(drawablePadding);
    }

    public void setRightTextDrawablePadding(int drawablePadding) {
        this.mRightDrawablePadding = drawablePadding;
        mRightTv.setCompoundDrawablePadding(dip2px(getContext(), mRightDrawablePadding));
    }

    public void setRightTextDrawable(int id) {
        setRightTextDrawable(id, mRightDrawablePadding);
    }

    public void setRightTextPadding(int left, int top, int right, int bottom) {
        mRightTv.setPadding(left, top, right, bottom);
    }

    public void setOnRightTextClickListener(OnClickListener l) {
        mRightTv.setOnClickListener(l);
    }

    public void setRightVisible(boolean visible) {
        mRightTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 在标题栏右边添加action
     *
     * @param action   添加action
     * @param position 添加的位置
     */
    public View addRightAction(Action action, int position) {
        View view = inflateAction(action);
        mRightLayout.addView(view, position);
        return view;
    }

    public View addRightAction(Action action) {
        View view = inflateAction(action);
        mRightLayout.addView(view);
        return view;
    }

    /**
     * 设置下方分割线
     *
     * @param drawable 分割线drawable
     */
    @SuppressWarnings("deprecation")
    public void setDivider(Drawable drawable) {
        mDividerView.setBackgroundDrawable(drawable);
    }

    public void setDividerBackgroundColor(int color) {
        mDividerView.setBackgroundColor(color);
    }

    public void setDividerBackgroundResource(int resourceId) {
        mDividerView.setBackgroundResource(resourceId);
    }

    public void setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
        mDividerView.getLayoutParams().height = dividerHeight;
    }

    public void setOutPadding(int paddingValue) {
        mPaddingValue = paddingValue;
        mLeftLayout.setPadding(mPaddingValue, 0, 0, 0);
        mRightLayout.setPadding(0, 0, mPaddingValue, 0);
    }

    /**
     * 通过action加载一个View
     */
    private View inflateAction(Action action) {
        View view = null;
        Object obj = action.getData();
        if (obj == null)
            return null;
        if (obj instanceof View) {
            view = (View) obj;
        } else if (obj instanceof String) {
            TextView text = new TextView(getContext());
            text.setGravity(Gravity.CENTER);
            text.setText((String) obj);
            text.setTextSize(px2sp(getContext(), mActionTextSize));
            text.setTextColor(mActionTextColor);
            text.setBackgroundColor(mActionTextBackgroundColor);
            if (mActionTextBackgroundResource != -1) {
                text.setBackgroundResource(mActionTextBackgroundResource);
            }
            view = text;
        } else if (obj instanceof Integer) {
            ImageView img = new ImageView(getContext());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageResource((Integer) obj);
            view = img;
        }
        assert view != null;
        view.setPadding(mActionPadding, 0, mActionPadding, 0);
        view.setTag(action);
        view.setOnClickListener(action.getOnClickListener());
        return view;
    }


    /**
     * 添加View以及相应的动作接口
     */
    @SuppressWarnings("WeakerAccess")
    public interface Action<T> {
        T getData();

        OnClickListener getOnClickListener();
    }

    public class ImageAction implements Action<Integer> {

        private int mDrawable;
        private OnClickListener onClickListener;

        public ImageAction(int mDrawable, OnClickListener onClickListener) {
            this.mDrawable = mDrawable;
            this.onClickListener = onClickListener;
        }

        public ImageAction(int mDrawable) {
            this.mDrawable = mDrawable;
        }

        @Override
        public Integer getData() {
            return mDrawable;
        }

        @Override
        public OnClickListener getOnClickListener() {
            return onClickListener;
        }

    }

    public class TextAction implements Action<String> {

        private String mText;
        private OnClickListener onClickListener;

        public TextAction(String mText, OnClickListener onClickListener) {
            this.mText = mText;
            this.onClickListener = onClickListener;
        }

        public TextAction(int mText, OnClickListener onClickListener) {
            this.mText = getResources().getString(mText);
            this.onClickListener = onClickListener;
        }

        @Override
        public String getData() {
            return mText;
        }

        @Override
        public OnClickListener getOnClickListener() {
            return onClickListener;
        }

    }

    public class ViewAction implements Action<View> {

        private View mView;
        private OnClickListener onClickListener;

        public ViewAction(View mView, OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public View getData() {
            return mView;
        }

        @Override
        public OnClickListener getOnClickListener() {
            return onClickListener;
        }

    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 将dip或dp值转换为px值
     *
     * @param context  上下文
     * @param dipValue dp值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
