package com.cnn.android.okhttpmodel.http;



import com.cnn.android.okhttpmodel.R;
import com.cnn.android.okhttpmodel.utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * okHttp callback wrapper
 * 
 * @author afree on 4/25/16.
 */
public abstract class MjCallback<T> implements Callback<T> {
  /**
   * 错误状态－Cookie过期
   */
  private static final int STATUS_COOKIE_EXPIRE = 3001;
  /**
   * 错误状态－登陆身份失效
   */
  private static final int STATUS_NOT_LOGIN = 401;

  /**
   * 错误状态－验证身份失败（扫码收款统一错误码）
   */
  private static final int STATUS_VERIFY_K2_ERROR = 4002;

  @Override
  public void onResponse(Call<T> call, Response<T> response) {
    if (response == null) {
      onFailure(StringUtil.getString(R.string.okhttp_response_error));
      return;
    }

    if (response.body() != null) {
      onResponse(response.body());
      onFinish();
    } else {
      onFailure(StringUtil.getString(R.string.okhttp_response_error));
    }
  }

  @Override
  public void onFailure(Call<T> call, Throwable t) {
    call.toString();
    if (t != null) {
      onFailure(t.getMessage());
    } else {
      onFailure(StringUtil.getString(R.string.okhttp_network_error));
    }

    onFinish();
  }

  /**
   * @param t model wouldn't be null
   */
  public abstract void onResponse(T t);

  public abstract void onFailure(String message);

  public  void onFinish(){};

}
