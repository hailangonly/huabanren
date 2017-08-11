package com.cnn.android.okhttpmodel.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 签名类，一定要确保它是最后一个interceptor
 * 
 * @author afree on 5/18/16.
 */
public class MjSignInterceptor implements Interceptor {
  private static final String KEY_SIGN = "sign";

  @Override
  public Response intercept(Chain chain) throws IOException {
    return null;
  }
/*

  @Override
  public Response intercept(Chain chain) throws IOException {*/
/*

    Request request = chain.request();
    String k2 = SecureManager.getK2();
    if (TextUtils.isEmpty(k2)) {
      return chain.proceed(request);
    }

    HttpUrl url = request.url();
    String query = url.query();

    Map<String, String> map = getParamsMap(request, query);

    String sortStr = MapUtils.mapToString(map);
    if (TextUtils.isEmpty(sortStr)) {
      return chain.proceed(request);
    }

    String sign = MD5Utils.get(sortStr.concat(k2));
    HttpUrl.Builder urlBuilder = request.url().newBuilder();
    urlBuilder.addQueryParameter(KEY_SIGN, sign);
    HttpUrl httpUrl = urlBuilder.build();

    //WdLog.d(httpUrl.toString());

    request = request.newBuilder().url(httpUrl).build();
    return chain.proceed(request);
  *//*
}
*/
/*
  private Map<String, String> getParamsMap(Request request, String query) {
    Map<String, String> map = MapUtils.stringToMap(query);
    if (request.body() instanceof FormBody) {
      FormBody body = (FormBody) request.body();
      for (int i = 0; i < body.size(); i++) {
        if (body.encodedName(i) != null && body.encodedValue(i) != null) {
          map.put(decode(body.encodedName(i)), decode(body.encodedValue(i)));
        }
      }
    }
    map = MapUtils.sortMapByKey(map);
    return map;
  }

  private String decode(String s) {
    try {
      return URLDecoder.decode(s, "UTF-8");
    } catch (Exception e) {
      return "";
    }
  }*/

}
