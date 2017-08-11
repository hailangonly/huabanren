package com.bilibili.boxing_impl.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bilibili.boxing.model.BoxingManager;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.R;
import com.bilibili.boxing_impl.adapter.BoxingTabFragmentAdapter;
import com.bilibili.boxing_impl.impl.BoxingFrescoLoader;
import com.bilibili.boxing_impl.impl.BoxingUcrop;
import com.cnn.android.basemodel.event.AddFeedEvent;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by nmj on 2017/4/27.
 */

public class BoxingActivityNew extends FragmentActivity implements Boxing.OnBoxingFinishListener{
  private SlidingTabLayout mTabLayout;
  private ViewPager mViewPager;
  BoxingConfig config;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_boxing_new);

    initTopView();



    mTabLayout = (SlidingTabLayout) findViewById(R.id.boxing_tab);

    mViewPager = (ViewPager) findViewById(R.id.boxing_viewpager);
    mViewPager.setOffscreenPageLimit(1);
    config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
    BoxingManager.getInstance().setBoxingConfig(config);

    BoxingTabFragmentAdapter mAdapter =
        new BoxingTabFragmentAdapter(getSupportFragmentManager(),this);
    mViewPager.setAdapter(mAdapter);
    mTabLayout.setViewPager(mViewPager);

    mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelect(int position) {
       if(position==0){
         config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
         BoxingManager.getInstance().setBoxingConfig(config);
       }else{
         config = new BoxingConfig(BoxingConfig.Mode.VIDEO);
         BoxingManager.getInstance().setBoxingConfig(config);
       }
      }

      @Override
      public void onTabReselect(int position) {

      }
    });
  }

  private ImageButton leftBtn;
  private TextView titleTextView,okBtn;

  private void initTopView() {
    leftBtn= (ImageButton) findViewById(R.id.left_btn);
    titleTextView= (TextView) findViewById(R.id.top_text_view_text);
    okBtn= (TextView) findViewById(R.id.right_btn);
    leftBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    titleTextView.setText("选择图片");
    okBtn.setVisibility(View.VISIBLE);
    okBtn.setText("继续");
    okBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
  }

  public static void launch(Activity context,int requestCode) {
    final Intent intent = new Intent(context, BoxingActivityNew.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    /*
     * Bundle bundle=new Bundle();
     * bundle.putParcelable("FeedModel",model);
     * intent.putExtras(bundle);
     */
    context.startActivityForResult(intent,requestCode);
  }


  @Override
  public void onBoxingFinish(Intent intent, @Nullable List<BaseMedia> medias) {
    setResult(Activity.RESULT_OK, intent);
    AddFeedEvent event=new AddFeedEvent();
    event.setSelectedImgae(true);
    event.selectImgae(intent);
    EventBus.getDefault().post(event);
    finish();
  }
}
