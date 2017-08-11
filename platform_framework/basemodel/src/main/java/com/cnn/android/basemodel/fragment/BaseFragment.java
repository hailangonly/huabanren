package com.cnn.android.basemodel.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cnn.android.basemodel.R;

import java.lang.reflect.Field;

/**
 * Created by nmj on 16/8/8.
 */
public abstract class BaseFragment extends Fragment {
  protected View mContainer;
  protected boolean mIsInflated;

  private ProgressDialog mProgressDialog;

  protected Context mContext;

  protected abstract int getLayoutResId();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mContainer = inflater.inflate(getLayoutResId(), container, false);
    return mContainer;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (mContainer != null) {
      mContext = getContext();
      onInflated(mContainer, savedInstanceState);
      mIsInflated = true;
    }
  }

  protected abstract void onInflated(View container, Bundle savedInstanceState);

  @Override
  public void onDetach() {
    super.onDetach();
    try { // 解决fragment嵌套子fragment状态问题
      Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
      childFragmentManager.setAccessible(true);
      childFragmentManager.set(this, null);
    } catch (Exception e) {
      // do nothing
    }
  }

  protected View findViewById(int id) {
    if (mContainer != null) {
      return mContainer.findViewById(id);
    }
    return null;
  }

  public void showToast(int res) {
    String msg = getString(res);
    showToast(msg);
  }

  public void showToast(String msg) {
    if (getActivity() != null && isAdded()) {
      Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
  }

  protected void showLoadingView() {
    showLoadingView(null);
  }

  protected void showLoadingView(String msg) {
    if (mProgressDialog == null) {
      createProgressDialog(msg);
    }
    if (!TextUtils.isEmpty(msg))
      mProgressDialog.setMessage(msg);
    mProgressDialog.show();
  }


  protected void hideLoadingView() {
    if (mProgressDialog != null) {
      mProgressDialog.dismiss();
    }
  }


  private void createProgressDialog(
      String msg) {
    mProgressDialog = new ProgressDialog(getActivity(),
        R.style.CustomProgressLoadingDialog);
    if (TextUtils.isEmpty(msg)) {
      mProgressDialog.setMessage(getString(R.string.base_load_loading));
    } else {
      mProgressDialog.setMessage(msg);
    }
    mProgressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
    mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
  }

  @Override
  public void onDestroyView() {
    if (mProgressDialog != null) {
      if (mProgressDialog.isShowing()) {
        mProgressDialog.dismiss();
      }
    }
    mProgressDialog = null;
    super.onDestroyView();
  }

  public boolean onBackPressed (){
    return false;
  }
}
