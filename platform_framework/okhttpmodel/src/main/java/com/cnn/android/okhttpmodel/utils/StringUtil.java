package com.cnn.android.okhttpmodel.utils;

import com.cnn.android.okhttpmodel.config.GlobalConfig;

/**
 * Created by nmj on 16/9/17.
 */
public class StringUtil {
    public static String getString(int res){
        if(GlobalConfig.getAppContext()!=null) {
            return GlobalConfig.getAppContext().getString( res );
        }else{
            return "";
        }
    }
}
