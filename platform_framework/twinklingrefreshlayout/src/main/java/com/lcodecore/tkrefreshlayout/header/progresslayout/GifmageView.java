package com.lcodecore.tkrefreshlayout.header.progresslayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.R;

/**
 * Private class created to work around issues with AnimationListeners being
 * called before the animation is actually complete and support shadows on older
 * platforms.
 *
 */
public class GifmageView extends ImageView {


    public GifmageView(Context context) {
        super(context);
    }

    public GifmageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GifmageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.drawable.refresh_recycler_header);
    }
}