package com.cnn.android.basemodel.fragment;

import android.os.Bundle;

/**
 * Created by nmj on 16/8/8.
 *
 *
 */
public abstract class BaseLoadFragment extends BaseTitleFragment {
  /**
   * tag for whether allow to load
   */
  private boolean mAllowLoad = true;

  @Override
  public final void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (!needToLoad()) {
      return;
    }
    mContainer.post(new Runnable() {
      @Override
      public void run() {
        if (isAdded() && needToLoad()) {
          onStartLoad();
        }
      }
    });
  }

  /**
   * do actual request load here
   */
  protected abstract void onStartLoad();


  /**
   * subclass override it for some condition before loading data
   */
  protected boolean needToLoad() {
    return mIsInflated && mAllowLoad;
  }

  /**
   * request load when refresh data manually
   */
  protected final void requestLoad() {
    if (!needToLoad() || !isAdded()) {
      return;
    }
    if (needToLoad()) {
      onStartLoad();
    }
  }

  /**
   * notify fragment whether to load
   */
  public void setAllowLoad(boolean allowLoad) {
    mAllowLoad = allowLoad;
    if (mAllowLoad) {
      requestLoad();
    }
  }


}
