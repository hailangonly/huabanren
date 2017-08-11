package cn.sharesdk.share;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cnn.android.basemodel.decor.GridItemDecoration;
import com.cnn.android.basemodel.utils.ToolUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

import cn.sharesdk.R;
import cn.sharesdk.ThirdKey;
import com.cnn.android.basemodel.utils.AnimationUtils;

/**
 * Created by nmj on 2017/3/24.
 */

public class ShareDialog extends DialogFragment implements ThirdKey {
    private View mRootView;
    // private OnClickListener mListener;
    private ShareInfo info;
    private RecyclerView mRecyclerView;
    private String[] arrys;
    private Context mContext;

    private IWXAPI apiWX;
    private Tencent mTencent;

    public ShareDialog(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });

        apiWX = WXAPIFactory.createWXAPI(getContext(), WEIXIN_KEY);
        mTencent = Tencent.createInstance(QQ_KEY, getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initData();
        mRootView = inflater.inflate(R.layout.share_dialog, container, false);
        AnimationUtils.slideToUp(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        ShareAdapter adapter = new ShareAdapter(getContext(), arrys);
        mRecyclerView
                .setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView
                .addItemDecoration(new GridItemDecoration(4, ToolUtil.dp2px(getActivity(), 10), false));
        mRecyclerView.setAdapter(adapter);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter.setOnShareOnClick(new ShareAdapter.OnShareOnClick() {
            @Override
            public void onClick(int type) {
                shareTo(type);
            }
        });
    }

    private void initData() {
        info = getArguments().getParcelable("ShareInfo");
        arrys = getResources().getStringArray(R.array.share_item);
    }

    private void shareTo(int type) {
        switch (type) {
            case 0:
                shareToWechat();
                break;
            case 1:
                shareToWechatMomentst();
                break;
            case 2:
                wechatFavorite();
                break;
            case 3:
                shareToQQ();
                break;
            case 4:
                shareToQzone();
            default:
                shareToWechat();
                break;

        }
    }

    private void shareToWechat() {
        if (apiWX.isWXAppInstalled()) {
            new ShareWXToFriends().execute(0);


        } else {
            Toast.makeText(getContext(), R.string.wechat_client_inavailable, Toast.LENGTH_LONG).show();
        }
    }

    private void shareToWechatMomentst() {
        if (apiWX.isWXAppInstalled()) {
            new ShareWXToFriends().execute(1);
        } else {
            Toast.makeText(getContext(), R.string.wechat_client_inavailable, Toast.LENGTH_LONG).show();
        }
    }

    private void wechatFavorite() {
        if (apiWX.isWXAppInstalled()) {
            new ShareWXToFriends().execute(2);
        } else {
            Toast.makeText(getContext(), R.string.wechat_client_inavailable, Toast.LENGTH_LONG).show();
        }
    }

    private void shareToQQ() {
        // if(mTencent.is)
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, info.mTitle);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, info.mText);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, info.mUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, info.mImageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "街去");
        // params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        mTencent.shareToQQ(getActivity(), params, new BaseUiListener());
    }

    private void shareToQzone() {
        // 分享类型
        final Bundle params = new Bundle();
        params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_KEY_TYPE);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, info.mTitle);// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, info.mText);// 选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, info.mUrl);// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, info.mImageUrl);
        mTencent.shareToQzone(getActivity(), params, new BaseUiListener());
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }


    private byte[] getBitmap() {
        Bitmap mBitmap = null;
        try {
            mBitmap = Glide.with(getContext()).load(info.mImageUrl)
                    .asBitmap().centerCrop().into(150, 150).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        byte[] b = null;
        try {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 85, localByteArrayOutputStream);
            b = localByteArrayOutputStream.toByteArray();
            localByteArrayOutputStream.close();
            mBitmap.recycle();
            mBitmap = null;
        } catch (Exception e) {

        }
        return b;
    }

    /**
     * 微信分享图片不能超过64kb，特别坑...
     *
     * @param targetBitmap bitmap
     * @return Bitmap
     */
    protected Bitmap getWxShareBitmap(Bitmap targetBitmap) {
        if (targetBitmap == null) {
            return null;
        }
        float scale =
                Math.min((float) 150 / targetBitmap.getWidth(), (float) 150 / targetBitmap.getHeight());
        Bitmap fixedBmp = Bitmap.createScaledBitmap(targetBitmap,
                (int) (scale * targetBitmap.getWidth()), (int) (scale * targetBitmap.getHeight()), false);
        return fixedBmp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    class ShareWXToFriends extends AsyncTask<Object, Void, String> {
        int buttonIndex = 0;
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
      /*
       * if (mProgressDialog == null) mProgressDialog =
       * ToolUtil.createProgressDialog(mContext, "正在为您努力分享...");
       * mProgressDialog.show();
       */
        }

        @Override
        protected String doInBackground(Object... params) {
            buttonIndex = (Integer) params[0];
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = info.mUrl;

            //
            String share_desc = info.mText;
            WXMediaMessage msg = new WXMediaMessage();
            msg.title = info.mTitle;
            if (buttonIndex == 0)
                msg.description = share_desc + "：更多精彩，点此进入";
            else
                msg.description = share_desc;
            Bitmap bitmap = null;
            // 图片处理
            if (!ToolUtil.isEmpty(info.mImageUrl)) {
                byte[] b = getBitmap();

                if (b != null) {
                    if (b.length > 32 * 1024) {
                        msg.thumbData = ToolUtil.imageZoom32(b);
                    } else {
                        msg.thumbData = b;
                    }
                } else {
                    bitmap = BitmapFactory.decodeResource(
                            mContext.getResources(), R.drawable.share_icon);
                    msg.thumbData = ToolUtil.imageZoom32(bitmap);
                }
            } else {
                bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.share_icon);
                msg.thumbData = ToolUtil.imageZoom32(bitmap);
            }

            msg.mediaObject = webpage;

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.message = msg;
            req.transaction = buildTransaction("text");
            if (buttonIndex == 0)
                req.scene = SendMessageToWX.Req.WXSceneSession;
            else if (buttonIndex == 1)
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
            else
                req.scene = SendMessageToWX.Req.WXSceneFavorite;

            if (apiWX == null)
                return "分享失败，可能没有安装微信";

            boolean result = apiWX.sendReq(req);
            if (result) {
                return "0";
            }
            return "-1";
        }

        @Override
        protected void onPostExecute(String result) {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            if (!"0".equals(result))
                Toast.makeText(mContext, "分享失败，可能没有安装微信", Toast.LENGTH_LONG)
                        .show();
            // doAction();
            super.onPostExecute(result);

        }
    }

    private String buildTransaction(final String type) {
        return (type == null)
                ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

}
