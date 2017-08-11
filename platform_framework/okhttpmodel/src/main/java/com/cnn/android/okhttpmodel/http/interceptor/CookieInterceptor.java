package com.cnn.android.okhttpmodel.http.interceptor;



import com.cnn.android.okhttpmodel.http.cookie.CookieClient;
import com.cnn.android.okhttpmodel.http.cookie.ICookieClient;

import java.io.IOException;
import java.net.URI;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http请求的cookie获取和植入
 * Created by afree on 4/2/16.
 */
public class CookieInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    URI uri = request.url().uri(); // save 和 get对应uri一致
    String cookieValue = CookieClient.getInstance().getCookieValue(uri);
    if (cookieValue != null) {
      request = request
              .newBuilder()
              .addHeader(ICookieClient.KEY_REQUEST_COOKIE, cookieValue)
              .build();
    }

    Response response = chain.proceed(request);
    CookieClient.getInstance().saveCookie(uri, response.headers().toMultimap());
    return response;
  }
}
