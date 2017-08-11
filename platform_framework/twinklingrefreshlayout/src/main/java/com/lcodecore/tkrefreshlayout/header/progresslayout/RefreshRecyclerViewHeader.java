package com.lcodecore.tkrefreshlayout.header.progresslayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lcodecore.tkrefreshlayout.R;
import com.lcodecore.tkrefreshlayout.utils.ViewUtils;

/**
 * Created by nmj on 2017/3/16.
 */

public class RefreshRecyclerViewHeader extends FrameLayout implements IHeaderView {


  private static final int CIRCLE_DIAMETER = 40;
  private static final int CIRCLE_DIAMETER_LARGE = 56;
  // Maps to ProgressBar.Large style
  public static final int LARGE = MaterialProgressDrawable.LARGE;
  // Maps to ProgressBar default style
  public static final int DEFAULT = MaterialProgressDrawable.DEFAULT;
  // Default background for the progress spinner
  private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
  // Default offset in dips from the top of the view to where the progress spinner should stop
  private static final int DEFAULT_CIRCLE_TARGET = 64;
  private static final float MAX_PROGRESS_ANGLE = .8f;

  private static final int MAX_ALPHA = 255;
  private static final int STARTING_PROGRESS_ALPHA = (int) (.3f * MAX_ALPHA);

  private ImageView mCircleView;
  private AnimationDrawable mAnimationDrawable;

  public RefreshRecyclerViewHeader(Context context) {
    this(context, null);
  }

  public RefreshRecyclerViewHeader(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RefreshRecyclerViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    // the absolute offset has to take into account that the circle starts at an offset
  }

  public static RefreshRecyclerViewHeader newInstance(Context context) {
    return (RefreshRecyclerViewHeader) ViewUtils.newInstance(context, R.layout.refresh_recycler_header_view);
  }

  public static RefreshRecyclerViewHeader newInstance(ViewGroup parent) {
    return (RefreshRecyclerViewHeader) ViewUtils.newInstance(parent, R.layout.refresh_recycler_header_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    createProgressView();
  }

  private void createProgressView() {
    mCircleView = (ImageView) findViewById(R.id.ptr_rotate_arrow);
    mCircleView.setVisibility(View.GONE);
    mAnimationDrawable= (AnimationDrawable) mCircleView.getDrawable();
    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    mCircleView.setLayoutParams(params);
  }

  /**
   * Set the background color of the progress spinner disc.
   *
   * @param colorRes Resource id of the color.
   */
  public void setProgressBackgroundColorSchemeResource(@ColorRes int colorRes) {
    setProgressBackgroundColorSchemeColor(getResources().getColor(colorRes));
  }

  /**
   * Set the background color of the progress spinner disc.
   *
   * @param color
   */
  public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
    mCircleView.setBackgroundColor(color);
  }

  /**
   * Set the color resources used in the progress animation from color resources.
   * The first color will also be the color of the bar that grows in response
   * to a user swipe gesture.
   *
   * @param colorResIds
   */
  public void setColorSchemeResources(@ColorRes int... colorResIds) {
    final Resources res = getResources();
    int[] colorRes = new int[colorResIds.length];
    for (int i = 0; i < colorResIds.length; i++) {
      colorRes[i] = res.getColor(colorResIds[i]);
    }
    setColorSchemeColors(colorRes);
  }

  /**
   * Set the colors used in the progress animation. The first
   * color will also be the color of the bar that grows in response to a user
   * swipe gesture.
   *
   * @param colors
   */
  public void setColorSchemeColors(int... colors) {}



  @Override
  public void reset() {
    mCircleView.clearAnimation();
    mCircleView.setVisibility(View.GONE);
    ViewCompat.setScaleX(mCircleView, 0);
    ViewCompat.setScaleY(mCircleView, 0);
    ViewCompat.setAlpha(mCircleView, 1);
  }

  @Override
  public View getView() {
    return this;
  }

  private boolean mIsBeingDragged = false;

  @Override
  public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

    if (mCircleView.getVisibility() != View.VISIBLE) {
      mCircleView.setVisibility(View.VISIBLE);
    }

    if (fraction >= 1f) {
      ViewCompat.setScaleX(mCircleView, 1f);
      ViewCompat.setScaleY(mCircleView, 1f);
    } else {
      ViewCompat.setScaleX(mCircleView, fraction);
      ViewCompat.setScaleY(mCircleView, fraction);
    }
  }

  @Override
  public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
    mIsBeingDragged = false;
    if (fraction >= 1f) {
      ViewCompat.setScaleX(mCircleView, 1f);
      ViewCompat.setScaleY(mCircleView, 1f);
    } else {
      ViewCompat.setScaleX(mCircleView, fraction);
      ViewCompat.setScaleY(mCircleView, fraction);
    }
  }

  @Override
  public void startAnim(float maxHeadHeight, float headHeight) {
    mCircleView.setVisibility(View.VISIBLE);
    mAnimationDrawable.start();
    ViewCompat.setScaleX(mCircleView, 1f);
    ViewCompat.setScaleY(mCircleView, 1f);
  }

  @Override
  public void onFinish(final OnAnimEndListener animEndListener) {
    mCircleView.animate().scaleX(0).scaleY(0).alpha(0).setListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        reset();
        animEndListener.onAnimEnd();
      }
    }).start();
    mAnimationDrawable.stop();
  }
}
