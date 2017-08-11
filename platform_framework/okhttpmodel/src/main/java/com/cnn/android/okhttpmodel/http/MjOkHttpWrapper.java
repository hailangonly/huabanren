package com.cnn.android.okhttpmodel.http;

import android.text.TextUtils;

import com.cnn.android.okhttpmodel.http.config.MjOkHttpConfigImpl;
import com.cnn.android.okhttpmodel.http.utils.CollectionUtils;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * wrapper,a single instance, for OkHttp,retrofit,rxJava...
 * Created by afree on 4/4/16.
 */
public class MjOkHttpWrapper {

  private static final long DEFAULT_TIMEOUT=30;

  private static MjOkHttpWrapper sInstance;
  private MjOkHttpConfigImpl mConfig;
  private OkHttpClient mOkHttpClient;
  private Retrofit mRetrofit;
  private Retrofit.Builder mBuilder;

  private MjOkHttpWrapper() {
    init();
  }

  private void init() {
    initConfig();
    initOkHttpClient();
    initRetrofit();
  }

  private void initConfig() {
    mConfig = new MjOkHttpConfigImpl();
  }

  private void initOkHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (mConfig.getCache() != null) {
      builder.cache(mConfig.getCache());
    }
    builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    if (!CollectionUtils.isEmpty(mConfig.getInterceptors())) {
      for (Interceptor i : mConfig.getInterceptors()) {
        builder.addInterceptor(i);
      }
    }

    mOkHttpClient = builder.build();
  }

  /**
   * retrofit默认连接配置，连接超时15秒,读取超时20秒,没有写入超时
   */
  private void initRetrofit() {
    mBuilder = new Retrofit.Builder();
    mBuilder.callFactory(mOkHttpClient)
        .baseUrl(mConfig.getBaseUrl());
    if (!CollectionUtils.isEmpty(mConfig.getConverter())) {
      for (Converter.Factory c : mConfig.getConverter()) {
        mBuilder.addConverterFactory(c);
      }
    }
    mRetrofit = mBuilder.build();
  }

  public static MjOkHttpWrapper getInstance() {
    if (sInstance == null) {
      synchronized (MjOkHttpWrapper.class) {
        if (sInstance == null) {
          sInstance = new MjOkHttpWrapper();
        }
      }
    }
    return sInstance;
  }

  public OkHttpClient getOkHttpClient() {
    return mOkHttpClient;
  }

  public Retrofit getRetrofit() {
    return mRetrofit;
  }

  public boolean clearCache() {
    try {
      mConfig.getCache().delete();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public void updateBaseUrl(String url) {
    if (!TextUtils.isEmpty(url)) {
      mBuilder = mBuilder.baseUrl(url);
      mRetrofit = mBuilder.build();
    }
  }
}
