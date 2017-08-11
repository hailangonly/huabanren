package cn.sharesdk.share;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nmj on 2017/3/23.
 */

public class ShareInfo implements Parcelable {
    public String mTitle;

    public String mTitleUrl;
    public String mText;
    public String mImageUrl;
    public String mUrl;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mTitleUrl);
        dest.writeString(this.mText);
        dest.writeString(this.mImageUrl);
        dest.writeString(this.mUrl);
    }

    public ShareInfo() {
    }

    protected ShareInfo(Parcel in) {
        this.mTitle = in.readString();
        this.mTitleUrl = in.readString();
        this.mText = in.readString();
        this.mImageUrl = in.readString();
        this.mUrl = in.readString();
    }

    public static final Parcelable.Creator<ShareInfo> CREATOR = new Parcelable.Creator<ShareInfo>() {
        @Override
        public ShareInfo createFromParcel(Parcel source) {
            return new ShareInfo(source);
        }

        @Override
        public ShareInfo[] newArray(int size) {
            return new ShareInfo[size];
        }
    };
}
