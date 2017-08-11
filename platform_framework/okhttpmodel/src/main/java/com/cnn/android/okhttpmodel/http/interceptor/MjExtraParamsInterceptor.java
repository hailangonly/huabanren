package com.cnn.android.okhttpmodel.http.interceptor;




import android.text.TextUtils;
import android.util.Log;

import com.cnn.android.okhttpmodel.config.GlobalConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by afree on 4/2/16.
 */
public class MjExtraParamsInterceptor implements Interceptor {

  private final static String KEY_APP_TYPE = "appType";
  private final static String KEY_CLIENT_TYPE = "clientType";
  private final static String KEY_VERSION = "version";
  private final static String KEY_UID = "uid";
  private final static String KEY_USER_ID = "userId";
  private final static String KEY_AG_ID = "agid";
  private final static String KEY_LOGIN_TOKEN = "token";
  private final static String VALUE_APP_TYPE = "bpMobile";
  private final static String VALUE_CLIENT_TYPE = "Android";
  private final static String DEVICE_ID = "deviceId";
  private final static String KEY_UNI_SOURCE = "_uni_source";
  private final static String VALUE_UNI_SOURCE = "2.1";


  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    HttpUrl.Builder urlBuilder = request.url().newBuilder();

    HttpUrl httpUrl = urlBuilder.build();


    Log.d("httpUrl",httpUrl.toString());
    Request.Builder builder =request.newBuilder();
    if(!TextUtils.isEmpty(GlobalConfig.getToken())){
       builder.addHeader(KEY_LOGIN_TOKEN,GlobalConfig.getToken());
    }

    request = builder.url(httpUrl).build();
    return chain.proceed(request);
  }
}
