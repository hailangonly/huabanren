package com.cnn.android.basemodel.view.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by nmj on 16/9/2.
 */
public class RecyclableImageView extends ImageView{
    public RecyclableImageView(Context context) {
        super(context);
    }

    public RecyclableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}
