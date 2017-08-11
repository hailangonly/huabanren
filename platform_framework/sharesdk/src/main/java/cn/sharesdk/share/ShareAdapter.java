package cn.sharesdk.share;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnn.android.basemodel.utils.ToolUtil;
import com.cnn.android.basemodel.utils.ViewUtils;

import cn.sharesdk.R;

/**
 * Created by nmj on 2017/3/27.
 */

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareViewHolder> {
  private String[] arrys;
  private Context mContext;

  private OnShareOnClick mOnShareOnClick;

  public void setOnShareOnClick(OnShareOnClick mOnShareOnClick) {
    this.mOnShareOnClick = mOnShareOnClick;
  }

  public ShareAdapter(Context mContext, String[] arrys) {
    this.arrys = arrys;
    this.mContext = mContext;
  }

  @Override
  public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(mContext).inflate(R.layout.share_item, null);
    return new ShareViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ShareViewHolder holder, int position) {

    holder.itemTextView.setText(arrys[position]);
    holder.itemTextView.setCompoundDrawablesWithIntrinsicBounds(0, getIcon(position), 0, 0);
    holder.itemTextView.setTag(position);
  }

  @Override
  public int getItemCount() {
    return arrys.length;
  }

  class ShareViewHolder extends RecyclerView.ViewHolder {
    TextView itemTextView;

    public ShareViewHolder(View itemView) {
      super(itemView);
      int lpw = ToolUtil.getScreenWidth(mContext) - ToolUtil.dp2px(mContext, 50);
      itemTextView = (TextView) itemView.findViewById(R.id.share_item_text);

      ViewUtils.setViewSize(itemTextView,lpw/4,lpw/4);
      itemTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         int type= (int) v.getTag();
          if(mOnShareOnClick!=null){
            mOnShareOnClick.onClick(type);
          }
        }
      });

    }
  }

  private int getIcon(int position) {
    int res;
    switch (position) {
      case 0:
        res = R.drawable.ssdk_oks_classic_wechat;
        break;
      case 1:
        res = R.drawable.ssdk_oks_classic_wechatmoments;
        break;
      case 2:
        res = R.drawable.ssdk_oks_classic_wechatfavorite;
        break;
      case 3:
        res = R.drawable.ssdk_oks_classic_qq;
        break;
      case 4:
        res = R.drawable.ssdk_oks_classic_qzone;
        break;
      default:
        res = R.drawable.ssdk_oks_classic_wechat;
        break;
    }

    return res;
  }

  public interface OnShareOnClick{
     void onClick(int type);
  }

}
