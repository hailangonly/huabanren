package com.cnn.android.basemodel.view.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nmj on 16/9/26.
 */
public class CommonViewPager extends ViewPager  {
    private Context mContext;
    private boolean mIsScrollEnabled = true;
    private OnPageChangeListener mOnPageChangeListener;

    public CommonViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public CommonViewPager(Context context) {
        super(context);
        this.initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        super.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(CommonViewPager.this.mOnPageChangeListener != null) {
                    CommonViewPager.this.mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

            }

            public void onPageSelected(int position) {
                if(CommonViewPager.this.mOnPageChangeListener != null) {
                    CommonViewPager.this.mOnPageChangeListener.onPageSelected(position);
                }

            }

            public void onPageScrollStateChanged(int state) {
                if(CommonViewPager.this.mOnPageChangeListener != null) {
                    CommonViewPager.this.mOnPageChangeListener.onPageScrollStateChanged(state);
                }

            }
        });
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public boolean isScrollEnabled() {
        return this.mIsScrollEnabled;
    }

    public void setScrollEnabled(boolean isEnabled) {
        this.mIsScrollEnabled = isEnabled;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !this.mIsScrollEnabled?false:super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return !this.mIsScrollEnabled?false:super.onTouchEvent(ev);
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return v != this && v instanceof ViewPager?true:super.canScroll(v, checkV, dx, x, y);
    }
}
