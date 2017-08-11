package com.cnn.android.basemodel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by nmj on 16/8/8.
 */
public class ResponseModel implements Parcelable {
  public String message;//": "调用成功",
    public String code;//": "00000"

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.message);
    dest.writeString(this.code);
  }

  public ResponseModel() {
  }

  protected ResponseModel(Parcel in) {
    this.message = in.readString();
    this.code = in.readString();
  }

  public static final Parcelable.Creator<ResponseModel> CREATOR = new Parcelable.Creator<ResponseModel>() {
    @Override
    public ResponseModel createFromParcel(Parcel source) {
      return new ResponseModel(source);
    }

    @Override
    public ResponseModel[] newArray(int size) {
      return new ResponseModel[size];
    }
  };
}
