package cn.sharesdk.share;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/**
 * Created by nmj on 2017/3/23.
 */

public class ShareUtils {

  public static void showShare(Context mContext, ShareInfo info, FragmentManager fm) {
    ShareDialog mDialog = new ShareDialog(mContext);
    Bundle bundle = new Bundle();
    bundle.putParcelable("ShareInfo", info);
    mDialog.setArguments(bundle);
    mDialog.show(fm, "dialog");
  }
}
