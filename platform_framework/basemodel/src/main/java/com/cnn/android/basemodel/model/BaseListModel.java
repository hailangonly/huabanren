package com.cnn.android.basemodel.model;

import android.os.Parcel;

/**
 * Created by nmj on 16/9/8.
 */
public  class BaseListModel extends BaseHttpModel{

    /**
     * total : 0
     * page : 0
     * size : 0
     */

    public int total;
    public int page;
    public int size;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.total);
        dest.writeInt(this.page);
        dest.writeInt(this.size);
    }

    public BaseListModel() {
    }

    protected BaseListModel(Parcel in) {
        super(in);
        this.total = in.readInt();
        this.page = in.readInt();
        this.size = in.readInt();
    }

    public static final Creator<BaseListModel> CREATOR = new Creator<BaseListModel>() {
        @Override
        public BaseListModel createFromParcel(Parcel source) {
            return new BaseListModel(source);
        }

        @Override
        public BaseListModel[] newArray(int size) {
            return new BaseListModel[size];
        }
    };
}
