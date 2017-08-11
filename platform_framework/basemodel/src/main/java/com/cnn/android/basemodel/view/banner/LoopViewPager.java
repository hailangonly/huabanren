package com.cnn.android.basemodel.view.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.cnn.android.basemodel.view.banner.adapter.LoopPagerAdapterWrapper;

/**
 * Created by nmj on 16/9/26.
 */
public class LoopViewPager extends CommonViewPager {
    private static final boolean DEFAULT_BOUNDARY_CASHING = false;
    private boolean mIsTouching = false;
    ViewPager.OnPageChangeListener mOuterPageChangeListener;
    private LoopPagerAdapterWrapper mAdapter;
    private boolean mBoundaryCaching = false;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        private float mPreviousOffset = -1.0F;
        private float mPreviousPosition = -1.0F;

        public void onPageSelected(int position) {
            if( mAdapter != null) {
                int realPosition =  mAdapter.toRealPosition(position);
                if(this.mPreviousPosition != (float)realPosition) {
                    this.mPreviousPosition = (float)realPosition;
                    if( mOuterPageChangeListener != null) {
                         mOuterPageChangeListener.onPageSelected(realPosition);
                    }
                }

            }
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if( mAdapter == null ||  mAdapter.getCount() > 1) {
                int realPosition = position;
                if( mAdapter != null) {
                    realPosition =  mAdapter.toRealPosition(position);
                    if(positionOffset == 0.0F && this.mPreviousOffset == 0.0F && (position == 0 || position ==  mAdapter.getCount() - 1)) {
                         setCurrentItem(realPosition, false);
                    }
                }

                this.mPreviousOffset = positionOffset;
                if( mOuterPageChangeListener != null) {
                    if( mAdapter != null && realPosition !=  mAdapter.getRealCount() - 1) {
                         mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                    } else if((double)positionOffset > 0.5D) {
                         mOuterPageChangeListener.onPageScrolled(0, 0.0F, 0);
                    } else {
                         mOuterPageChangeListener.onPageScrolled(realPosition, 0.0F, 0);
                    }
                }

                if(positionOffset != 0.0F || positionOffsetPixels != 0) {
                     mIsTouching = true;
                }

            }
        }

        public void onPageScrollStateChanged(int state) {
            if( mAdapter != null) {
                int position = LoopViewPager.super.getCurrentItem();
                int realPosition =  mAdapter.toRealPosition(position);
                if(state == 0 && (position == 0 || position ==  mAdapter.getCount() - 1)) {
                     setCurrentItem(realPosition, false);
                }
            }

            if( mOuterPageChangeListener != null) {
                 mOuterPageChangeListener.onPageScrollStateChanged(state);
            }

            if(state == 1) {
                 mIsTouching = true;
            } else {
                 mIsTouching = false;
            }

        }
    };

    public static int toRealPosition(int position, int count) {
        --position;
        if(position < 0) {
            position += count;
        } else {
            position %= count;
        }

        return position;
    }

    public void setBoundaryCaching(boolean flag) {
        this.mBoundaryCaching = flag;
        if(this.mAdapter != null) {
            this.mAdapter.setBoundaryCaching(flag);
        }

    }

    public void setAdapter(PagerAdapter adapter) {
        this.mAdapter = new LoopPagerAdapterWrapper(adapter);
        this.mAdapter.setBoundaryCaching(this.mBoundaryCaching);
        super.setAdapter(this.mAdapter);
        if(adapter != null) {
            if(adapter.getCount() > 0) {
                this.setCurrentItem(0, false);
                if(adapter.getCount() == 1) {
                    super.setOnPageChangeListener((ViewPager.OnPageChangeListener)null);
                }
            }

        }
    }

    public PagerAdapter getAdapter() {
        return (PagerAdapter)(this.mAdapter != null?this.mAdapter.getRealAdapter():this.mAdapter);
    }

    public void notifyDataSetChanged() {
        if(this.mAdapter != null) {
            if(this.mAdapter.getRealAdapter() != null) {
                this.mAdapter.getRealAdapter().notifyDataSetChanged();
            }

            this.mAdapter.notifyDataSetChanged();
            if(this.mAdapter.getRealAdapter() != null && this.mAdapter.getRealAdapter().getCount() == 1) {
                super.setOnPageChangeListener((ViewPager.OnPageChangeListener)null);
            } else {
                super.setOnPageChangeListener(this.onPageChangeListener);
            }
        }

    }

    public int getCurrentItem() {
        return this.mAdapter != null && this.mAdapter.getCount() > 0?this.mAdapter.toRealPosition(super.getCurrentItem()):0;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if(this.mAdapter != null) {
            int realItem = this.mAdapter.toInnerPosition(item);
            super.setCurrentItem(realItem, smoothScroll);
        }
    }

    public void setCurrentItem(int item) {
        if(this.getCurrentItem() != item) {
            this.setCurrentItem(item, true);
        }

    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mOuterPageChangeListener = listener;
    }

    public LoopViewPager(Context context) {
        super(context);
        this.init();
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        super.addOnPageChangeListener(this.onPageChangeListener);
    }

    public boolean isTouching() {
        return this.mIsTouching;
    }
}

