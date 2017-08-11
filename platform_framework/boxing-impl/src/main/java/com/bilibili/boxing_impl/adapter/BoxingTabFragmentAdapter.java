package com.bilibili.boxing_impl.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bilibili.boxing.AbsBoxingViewFragment;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.BoxingManager;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.presenter.PickerPresenter;
import com.bilibili.boxing_impl.ui.BoxingActivityNew;
import com.bilibili.boxing_impl.ui.BoxingViewFragment;
import com.cnn.android.basemodel.adapter.BaseFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmj on 2017/3/14.
 */

public class BoxingTabFragmentAdapter extends BaseFragmentStatePagerAdapter
    implements
      Boxing.OnBoxingFinishListener {
  private static final String[] list = {"图片", "视频"};
  private ArrayList<BaseMedia> selectedMedias = new ArrayList<>();

  private Context mContext;

  public BoxingTabFragmentAdapter(FragmentManager fm, Context mContext) {
    super(fm);
    this.mContext = mContext;
  }

  @Override
  public Fragment getItem(int position) {
    return instantiateItem(position);
  }

  public Fragment instantiateItem(int position) {
    AbsBoxingViewFragment fragment = null;
    if (position == 0) {
      fragment = BoxingViewFragment.newInstance().setSelectedBundle(selectedMedias);
      BoxingConfig pickerConfig = BoxingManager.getInstance().getBoxingConfig();
      pickerConfig.needCamera();
      fragment.setPresenter(new PickerPresenter(fragment));
      fragment.setPickerConfig(pickerConfig);
      Boxing.get().setupFragment(fragment, this);
    } else {
      fragment = BoxingViewFragment.newInstance().setSelectedBundle(selectedMedias);

      BoxingConfig pickerConfig = BoxingManager.getInstance().getBoxingConfig();
      pickerConfig.needCamera();
      fragment.setPresenter(new PickerPresenter(fragment));
      fragment.setPickerConfig(pickerConfig);
      Boxing.get().setupFragment(fragment, this);
    }
    return fragment;
  }

  @Override
  public int getCount() {
    return list.length;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return list[position];
  }

  @Override
  public void onBoxingFinish(Intent intent, @Nullable List<BaseMedia> medias) {
    ((BoxingActivityNew) mContext).onBoxingFinish(intent, medias);
  }
}
