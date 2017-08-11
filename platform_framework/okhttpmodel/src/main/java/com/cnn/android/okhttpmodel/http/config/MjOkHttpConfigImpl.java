package com.cnn.android.okhttpmodel.http.config;





import com.cnn.android.okhttpmodel.config.AppConstants;
import com.cnn.android.okhttpmodel.config.GlobalConfig;
import com.cnn.android.okhttpmodel.http.interceptor.MjExtraParamsInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by afree on 4/4/16.
 */
public class MjOkHttpConfigImpl implements IOkHttpConfig {
  private List<Interceptor> mInterceptors;
  private Cache mCache;
  private List<Converter.Factory> mConverterFactories;
  private static final String CACHE_PATH = "/api/okhttp";
  private static final int CACHE_SIZE = 5 * 1024 * 1024;

  public MjOkHttpConfigImpl() {
    initInterceptors();
    initCache();
    initConverters();
  }

  private void initInterceptors() {
    mInterceptors = new ArrayList<>();

    mInterceptors.add(new MjExtraParamsInterceptor());
    // mInterceptors.add(new CacheInterceptor());
    // mInterceptors.add(new CookieInterceptor());
    // mInterceptors.add(new MjSignInterceptor()); // 此interceptor需要放最后！！！
  }

  private void initCache() {
    String parentPath = getCacheParentPath();
    if (parentPath != null) {
      File f = new File(parentPath.concat(CACHE_PATH));
      if (f.exists() || f.mkdirs()) {
        mCache = new Cache(f, CACHE_SIZE);
      }
    }
  }

  private void initConverters() {
    mConverterFactories = new ArrayList<>();
    mConverterFactories.add(GsonConverterFactory.create());
  }

  @Override
  public List<Interceptor> getInterceptors() {
    return mInterceptors;
  }

  @Override
  public Cache getCache() {
    return mCache;
  }

  @Override
  public String getBaseUrl() {

    return AppConstants.SERVER_NET_ADDRESS;
  }

  @Override
  public List<Converter.Factory> getConverter() {
    return mConverterFactories;
  }

  @Override
  public SSLSocketFactory getSSlSocketFactory() {
    return null; // TODO: 4/5/16
  }

  @Override
  public HostnameVerifier getHostnameVerifier() {
    return null; // TODO: 4/5/16
  }


  private String getCacheParentPath() {
    File file = GlobalConfig.getAppContext().getExternalFilesDir(null);
    if (file == null || !file.exists()) {
      file = GlobalConfig.getAppContext().getCacheDir();
    }
    String path = null;
    if (file != null) {
      path = file.getAbsolutePath();
    }
    return path;
  }
}
