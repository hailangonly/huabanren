package com.cnn.android.basemodel.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author afree on 4/13/16.
 */
public class BaseHttpModel implements Parcelable {
  private final static String RESPONSE_CODE_SUCCESS = "00000";
  public ResponseModel result;

  public String getStatus() {
    return result.code;
  }

  public String getMessage() {
    if (!TextUtils.isEmpty(result.message)) {
      return result.message;
    }
    /*if (!TextUtils.isEmpty(msg)) {
      return msg;
    }*/
    return null;
  }

  /**
   * @return 服务端返回码是否匹配
   */
  public boolean isResponseCodeMatch() {
    return   RESPONSE_CODE_SUCCESS.equals(result.code);
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.result, flags);
  }

  public BaseHttpModel() {
  }

  protected BaseHttpModel(Parcel in) {
    this.result = in.readParcelable(ResponseModel.class.getClassLoader());
  }

}
