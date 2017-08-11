package com.cnn.android.okhttpmodel.http;

/**
 * api creator
 * Created by afree on 4/2/16.
 */
public class ApiCreator {

  public static <T> T createApi(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }
    return MjOkHttpWrapper.getInstance().getRetrofit().create(clazz);
  }

}
