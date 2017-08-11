package com.cnn.android.basemodel.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.cnn.android.basemodel.R;
import com.cnn.android.basemodel.view.progresslayout.MyProgressLayout;
import com.cnn.android.basemodel.view.swiperefresh.RefreshRecyclerViewHeader;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * Created by nmj on 2017/3/15.
 */

public abstract class BaseListLoadingFragment extends BaseLoadFragment {
  protected TwinklingRefreshLayout mRefreshLayout;
  protected int page = 1;
  protected int size = 10;
  protected int total = 0;
  protected boolean loadingNextPage = false;

  @Override
  protected void onInflated(View container, Bundle savedInstanceState) {
    mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
    // ProgressLayout headerView = new ProgressLayout(getContext());
    if (mRefreshLayout != null) {
      mRefreshLayout.setHeaderView(getRefreshHeaderView());
      // mRefreshLayout.setMaxHeadHeight(140);
      mRefreshLayout.setOverScrollRefreshShow(false);
      initRefreshListener();
    }
  }

  protected void initRefreshListener() {
    if (mRefreshLayout == null)
      return;
    mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
      @Override
      public void onRefresh(TwinklingRefreshLayout refreshLayout) {
        if (loadingNextPage)
          return;
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            page = 1;
            onStartLoad();
          }
        }, 2000);

      }

      @Override
      public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
        if (loadingNextPage)
          return;
        ++page;
        onStartLoad();
      }
    });
  }

  protected void onRefreshFinish(int size) {
    loadingNextPage = false;

    if (mRefreshLayout == null)
      return;
    if (page == 1)
      mRefreshLayout.finishRefreshing();
    else
      mRefreshLayout.finishLoadmore();

    if (!(total < size)) {
      mRefreshLayout.setEnableLoadmore(true);
    }

  }

  private IHeaderView getRefreshHeaderView() {
   // return RefreshRecyclerViewHeader.newInstance(getContext());
    return  new MyProgressLayout(getContext());
  }

}

