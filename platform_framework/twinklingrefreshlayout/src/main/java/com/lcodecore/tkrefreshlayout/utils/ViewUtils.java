package com.lcodecore.tkrefreshlayout.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mj 16/5/7.
 */
public class ViewUtils {

    public static View newVew(Context mContext, int id, ViewGroup parent){
        return LayoutInflater.from(mContext).inflate(id,parent);
    }

    public static void setViewSize(View view, int width, int height) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public static void setViewSize(View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width=ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
    }


    public static View newInstance(Context context,int res){
        return LayoutInflater.from(context).inflate(res,null);
    }
    public static View newInstance(ViewGroup parent,int res){
        return LayoutInflater.from(parent.getContext()).inflate(res,parent);
    }

}
