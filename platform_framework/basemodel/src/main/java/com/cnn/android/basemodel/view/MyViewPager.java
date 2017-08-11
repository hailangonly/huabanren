package com.cnn.android.basemodel.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @Title: MyViewPager.java 
 * @Package com.iremold.android.view 
 * @Description: TODO(解决viewpager 滑动的时候子view的冲突问题) 
 * @author MJ
 * @date 2014年7月18日 下午3:08:46 
 * @version V1.0   
 * @update name
 * @update_date 2014年7月18日 下午3:08:46
 */
public class MyViewPager extends ViewPager {

	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	@Override
	protected boolean canScroll(View paramView, boolean arg1, int arg2, int arg3,
			int arg4) {
		if(paramView instanceof ViewPager && (paramView != this))
			return true;
		return super.canScroll(paramView, arg1, arg2, arg3, arg4);
	}
	
	public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
	  {
	    return super.onInterceptTouchEvent(paramMotionEvent);
	  }
}
