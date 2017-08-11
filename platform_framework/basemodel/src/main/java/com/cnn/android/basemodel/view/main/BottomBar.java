package com.cnn.android.basemodel.view.main;

import android.content.Context;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cnn.android.basemodel.R;
import com.cnn.android.basemodel.utils.ViewUtils;


/**
 * @author nimengjing 2016-04-09
 */
public class BottomBar extends RelativeLayout {
  private LinearLayout mBottomBar;
  private BottomView newCMS;

  private OnTabChangeListener mOnTabChangeListener;


  public BottomBar(Context context) {
    this(context, null);
  }

  public BottomBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater.from(context).inflate(R.layout.classic_activity_bottom_bar_internal, this);
    mBottomBar = (LinearLayout) findViewById(R.id.ll_bottom_bar);
    newCMS = (BottomView) findViewById(R.id.smartlife_icon);

  }



  public static BottomBar newInstance(Context context) {
    return (BottomBar) ViewUtils.newInstance(context, R.layout.classic_activity_bottom_bar);
  }

  public static BottomBar newInstance(ViewGroup parent) {
    return (BottomBar) ViewUtils.newInstance(parent, R.layout.classic_activity_bottom_bar);
  }

  public void setSmartLifeIconGone() {
    newCMS.getDotImg().setVisibility(GONE);
  }

  public void setSmartLifeIconVisible() {
    newCMS.getDotImg().setVisibility(VISIBLE);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    for (int i = 0; i < mBottomBar.getChildCount(); ++i) {
      final View child = mBottomBar.getChildAt(i);
      final int position = i;
      /*
       * switch (i) {
       * case 0:
       * child.setTag(EventUtils.EVENT_TAG, EventConstants.BOTTOM_SHOPPING);
       * break;
       * case 1:
       * child.setTag(EventUtils.EVENT_TAG, EventConstants.BOTTOM_LIVE);
       * break;
       * case 2:
       * child.setTag(EventUtils.EVENT_TAG, EventConstants.BOTTOM_SHAKE);
       * break;
       * case 3:
       * child.setTag(EventUtils.EVENT_TAG, EventConstants.BOTTOM_FFAN_CARD);
       * break;
       * case 4:
       * child.setTag(EventUtils.EVENT_TAG, EventConstants.BOTTOM__MINE);
       * break;
       * }
       */
      child.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          selectTab(v);
          if (mOnTabChangeListener != null) {
            mOnTabChangeListener.onTabSelected(position);
          }

          /*
           * if (v.getTag(EventUtils.EVENT_TAG) instanceof String) {
           * EventUtils.recordOnClick(String.valueOf(v.getTag()));
           * }
           */
        }
      });
    }

    /*
     * mCenterTab.setOnClickListener(new OnClickListener() {
     * @Override
     * public void onClick(View v) {
     * if (mOnTabChangeListener != null) {
     * mOnTabChangeListener.onTabSelected(2);
     * }
     * }
     * });
     */
  }

  private void selectTab(View view) {
    for (int i = 0; i < mBottomBar.getChildCount(); ++i) {
      View child = mBottomBar.getChildAt(i);
      if (child != view) {
        child.setSelected(false);
      } else {
        child.setSelected(true);
      }
    }
  }

  public void selectTab(int position) {
    View child = mBottomBar.getChildAt(position);
    selectTab(child);
  }

  public void addOnTabChangeListener(OnTabChangeListener l) {
    mOnTabChangeListener = l;
  }

  public interface OnTabChangeListener {
    void onTabSelected(int position);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    if (visibility == VISIBLE) {
      // AppEventManager.getInstance().checkSingleAppModuleEvent(mAppModuleEventHandler);
    }
  }
}
