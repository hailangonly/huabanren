package com.cnn.android.basemodel.decor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cnn.android.basemodel.R;

/**
 * Created by nmj on 2017/5/5.
 */

public class FeedRecycleViewDivider extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint dividerPaint;
    private int margin;

    public FeedRecycleViewDivider(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(ContextCompat.getColor(context,R.color.recycleview_divider));
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
        margin=context.getResources().getDimensionPixelSize(R.dimen.view_spacing_10);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = margin;
        int right = parent.getWidth() - margin;

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}

