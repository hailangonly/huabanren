package com.bilibili.boxing_impl.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing_impl.R;
import com.cnn.android.basemodel.utils.AnimationUtils;

/**
 * Created by nmj on 2017/5/6.
 */

public class BoxingSelectDialog extends DialogFragment {
    private View mRootView;
    // private OnClickListener mListener;
    private RecyclerView mRecyclerView;
    private String[] arrys;
    private Fragment mContext;
    private static final int REQUEST_CODE = 1024;
    private static final int COMPRESS_REQUEST_CODE = 2048;
    public BoxingSelectDialog(Fragment mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.boxing_select_dialog, container, false);
        AnimationUtils.slideToUp(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.boxing_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boxing.of().withIntent(mContext.getContext(), BoxingActivity.class).start(mContext,REQUEST_CODE);
                dismiss();
            }
        });

        view.findViewById(R.id.boxing_vedio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO);
                Boxing.of(videoConfig).withIntent(mContext.getContext(), BoxingActivity.class).start(mContext, REQUEST_CODE);
                dismiss();
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
