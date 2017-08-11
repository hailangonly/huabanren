package com.cnn.android.basemodel.view.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnn.android.basemodel.R;


/**
 * Created by nimengjing on 16/7/14.
 */
public class BottomView extends RelativeLayout {

  private TextView title;
  private ImageView dotImg;
  private ImageView mImageView;

  private int mDrawable;
  private String mTitle;
  private int mPaddingBetween;

  public BottomView(Context context) {
    this(context, null);
  }

  public BottomView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater.from(context).inflate(R.layout.bottom_view, this);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomView);
    mDrawable = a.getResourceId(R.styleable.BottomView_bottom_src, 0);
    mTitle = a.getString(R.styleable.BottomView_bottom_text);
    int defaultPadding =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0F, context.getResources()
            .getDisplayMetrics());
    mPaddingBetween =
        a.getDimensionPixelOffset(R.styleable.BottomView_bottom_paddingBetween, defaultPadding);

    a.recycle();

  }



  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    title = (TextView) findViewById(R.id.bottom_text);
    dotImg = (ImageView) findViewById(R.id.bottom_img);
    mImageView = (ImageView) findViewById(R.id.top_img);

   mImageView.setImageResource(mDrawable);
    title.setText(mTitle);

  }

  @Override
  public void setPressed(boolean pressed) {
    super.setPressed(pressed);
    title.setPressed(pressed);
    mImageView.setPressed(pressed);
  }

  @Override
  public void setSelected(boolean selected) {
    super.setSelected(selected);
    title.setSelected(selected);
    mImageView.setSelected(selected);
  }

  public TextView getTitle() {
    return title;
  }

  public ImageView getImageView() {
    return mImageView;
  }

  public ImageView getDotImg() {
    return dotImg;
  }

}
