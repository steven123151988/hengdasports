package com.international.wtw.lottery.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by wuya on 2017/6/19.
 */

public class InterceptCustomGridView extends GridView {

    public InterceptCustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InterceptCustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptCustomGridView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;// true 拦截事件自己处理，禁止向下传递
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;// false 自己不处理此次事件以及后续的事件，那么向上传递给外层view
    }

}
