package com.cnn.android.basemodel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by nmj on 16/9/6.
 */
public abstract class BaseFragmentStatePagerAdapter extends FragmentPagerAdapter {
  protected SparseArray<Fragment> mArray = new SparseArray<>();

  public BaseFragmentStatePagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    Fragment fragment = (Fragment) super.instantiateItem(container, position);
    mArray.put(position, fragment);
    return fragment;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    super.destroyItem(container, position, object);
    mArray.remove(position);
  }

  public Fragment getFragment(int position) {
    return mArray.get(position);
  }

  @Override
  public int getItemPosition(Object object) {
    return PagerAdapter.POSITION_NONE;
  }
}
