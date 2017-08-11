package com.cnn.android.basemodel.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.cnn.android.basemodel.R;
import com.cnn.android.basemodel.common.title.DefaultTitle;


/**
 * Fixme flexible title
 * 
 * @author afree on 4/7/16.
 */
public class BaseTitleActivity extends BaseActivity {
  protected DefaultTitle mDefaultTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mDefaultTitle = (DefaultTitle) findViewById(R.id.title_base_activity);
  }


  public void setTitle(String title) {
    if (!TextUtils.isEmpty(title)) {
      mDefaultTitle.setTitle(title);
    }
  }

  protected void setTitleRes(int res) {
    setTitle(getString(res));
  }

  protected void setRightText(String t) {
    if (!TextUtils.isEmpty(t)) {
      mDefaultTitle.setRightText(t);
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.base_title_activity;
  }
}
