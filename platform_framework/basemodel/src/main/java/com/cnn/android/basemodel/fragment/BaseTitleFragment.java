package com.cnn.android.basemodel.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnn.android.basemodel.R;

/**
 * Created by nmj on 2017/3/19.
 */

public abstract class BaseTitleFragment extends BaseFragment {
  public TextView topTitleView;
  public TextView  rightBtn;
  public ImageButton leftBtn;
  public ImageView topTitleIcon;

  public void initTopTile() {
    // topView = (LinearLayout) findViewById(R.id.top_title_view);
    topTitleView = (TextView) findViewById(R.id.top_text_view_text);
    leftBtn = (ImageButton) findViewById(R.id.left_btn);
    rightBtn = (TextView) findViewById(R.id.right_btn);
    topTitleIcon = (ImageView) findViewById(R.id.top_title_Icon);

    if (leftBtn != null) {
      leftBtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          getActivity().finish();
        }
      });
    }

    if (rightBtn != null) {
      rightBtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          onRightBtnClick();
        }
      });
    }
  }

  protected void setTopTitleText(String title) {
    if (topTitleView != null) {
      topTitleView.setText(title);
    }
  }

  protected void setTopTitleText(int title) {
    if (topTitleView != null) {
      topTitleView.setText(title);
    }
  }

  protected void setTopTitleIcon(int iconRes) {
    if (topTitleView != null) {
      topTitleIcon.setImageResource(iconRes);
    }
  }

  public abstract void onRightBtnClick();
}
