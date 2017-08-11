package com.cnn.android.basemodel.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.cnn.android.basemodel.R;
import com.cnn.android.basemodel.utils.ViewUtils;
import com.demievil.library.RefreshLayout;

/**
 * Created by nmj on 16/9/6.
 */
public abstract class BaseListLoadFragment extends BaseLoadFragment {

  protected ListView mContentListView;
  protected int page = 1;
  protected int size = 10;
  protected int mCurrentCounter = 0;
  protected int total;
  protected boolean loadingNextPage = false;
  protected int cumSize = 0;
  protected boolean isErr;
  protected RefreshLayout mSwipeRefreshLayout;
  protected View notLoadingView;;


  @Override
  protected int getLayoutResId() {
    return R.layout.base_async_load_list_fragment;
  }

  @Override
  protected void onInflated(View container, Bundle savedInstanceState) {
    mContentListView = (ListView) findViewById(R.id.common_fragment_recycler);
    mSwipeRefreshLayout = (RefreshLayout) findViewById(R.id.swipeLayout);

    mSwipeRefreshLayout.setColorSchemeResources(R.color.tab_s,
        R.color.tab_s,
        R.color.tab_s,
        R.color.tab_s);

    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        page = 1;
        onStartLoad();
      }
    });
/*
 * mContentListView.setOnScrollListener(new AbsListView.OnScrollListener() {
 * @Override
 * public void onScrollStateChanged(AbsListView view, int scrollState) {
 * if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
 * int count = view.getCount();
 * count = count > 5 ? count - 5 : count - 1;// 提前5个item开始判断是否加载
 * // 停止滚动，判断是否加载下一页数据
 * if (view.getLastVisiblePosition() > count && cumSize < total
 * ) {
 * if (!loadingNextPage) {
 * // showFooterView(FooterView.LOADING);
 * ++page;
 * loadingMore(true);
 * onStartLoad();
 * }
 * }
 * }
 * }
 * @Override
 * public void onScroll(AbsListView view, int firstVisibleItem,
 * int visibleItemCount, int totalItemCount) {
 */ /*
     * mRecyclerView.onScroll(view, firstVisibleItem, visibleItemCount,
     * totalItemCount);
     *//*
        * }
        * });
        */
  }

  protected abstract void initAdapter();

  protected void onRefreshing() {
    if (mSwipeRefreshLayout == null)
      return;
    mSwipeRefreshLayout.post(new Runnable() {
      @Override
      public void run() {
        mSwipeRefreshLayout.setRefreshing(true);
        onStartLoad();

      }
    });
  }

  protected void refreshing() {
    if (mSwipeRefreshLayout == null)
      return;
    mSwipeRefreshLayout.post(new Runnable() {
      @Override
      public void run() {
        mSwipeRefreshLayout.setRefreshing(true);


      }
    });
  }



  protected abstract void setCumSize();

  public void onRefresh() {
    page = 1;
    onStartLoad();
  }

  protected View getNotLoadingView() {
    if (getActivity() != null) {
      return ViewUtils.newInstance(getActivity(), R.layout.not_loading);
    }
    return null;
  }
}
