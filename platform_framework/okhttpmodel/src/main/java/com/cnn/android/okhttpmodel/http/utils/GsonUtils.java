package com.cnn.android.okhttpmodel.http.utils;


import com.google.gson.Gson;

/**
 * 全局使用的 Gson ，避免重复创建。
 * 
 * Created by nmj on 16/8/8
 */
public class GsonUtils {

  private static Gson sGson = new Gson();

  public static Gson getGson() {
    return sGson;
  }

}
