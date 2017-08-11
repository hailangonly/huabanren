package com.cnn.android.basemodel.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * @author afree on 4/7/16.
 */
public class ActivityUtils {
  public static Activity findActivity(View view) {
    if (view == null) {
      return null;
    }
    View targetView = view;
    while (true) {
      if (targetView.getContext() instanceof Activity) {
        return (Activity) targetView.getContext();
      }
      if (!(targetView.getParent() instanceof View)) {
        return null;
      }
      targetView = (View) targetView.getParent();
    }
  }

  public static boolean isActivityContext(Context context) {
    return context instanceof Activity;
  }

}
