package com.cnn.android.basemodel.common.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnn.android.basemodel.R;
import com.cnn.android.basemodel.utils.ActivityUtils;
import com.cnn.android.basemodel.utils.ViewUtils;


/**
 *Created by nmj on 16/8/8.
 */
public class DefaultTitle extends RelativeLayout {
  private TextView mTitleName;
  private TextView mRightText;
  private ImageView mLeftArrow;

  public DefaultTitle(Context context) {
    super(context);
  }

  public DefaultTitle(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    mLeftArrow = (ImageView) findViewById(R.id.title_default_left_arrow);
    mTitleName = (TextView) findViewById(R.id.title_default_title);
    mRightText = (TextView) findViewById(R.id.title_default_right);
    mLeftArrow.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityUtils.findActivity(v).finish();
      }
    });
  }

  public static DefaultTitle newInstance(ViewGroup parent) {
    return (DefaultTitle) ViewUtils.newInstance(parent, R.layout.card_title_default);
  }

  public static DefaultTitle newInstance(Context context) {
    return (DefaultTitle) ViewUtils.newInstance(context, R.layout.card_title_default);
  }

  public void setTitle(String title) {
    mTitleName.setText(title);
  }

  public void setTitle(int res) {
    mTitleName.setText(getContext().getString(res));
  }

  public void setRightText(String title) {
    mRightText.setText(title);
    mRightText.setVisibility(VISIBLE);
  }

  public void setLeftArrowVisible(boolean show) {
    if (show) {
      mLeftArrow.setVisibility(VISIBLE);
    } else {
      mLeftArrow.setVisibility(GONE);
    }
  }

  public ImageView getLeftArrow() {
    return mLeftArrow;
  }

  public TextView getRightText() {
    return mRightText;
  }

  public TextView getTitle() {
    return mTitleName;
  }
}
