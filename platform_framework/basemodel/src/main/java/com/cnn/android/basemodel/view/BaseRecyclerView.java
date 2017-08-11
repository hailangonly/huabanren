package com.cnn.android.basemodel.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

/**
 * Created by nmj on 2017/5/4.
 */

public class BaseRecyclerView extends RecyclerView{
    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrollStateChanged(int newState) {
        super.onScrollStateChanged(newState);

        switch (newState) {
            case 0:
                Glide.with(getContext()).resumeRequests();
                break;

            case 1:
                Glide.with(getContext()).resumeRequests();
                break;

            case 2:
              Glide.with(getContext()).pauseRequests();
                break;
        }
    }
}
