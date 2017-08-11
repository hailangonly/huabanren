package cn.hugeterry.coordinatortablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;

/**
 * @author hugeterry(http://hugeterry.cn)
 */

public class CoordinatorTabLayout extends CoordinatorLayout {

    private int[] mImageArray, mColorArray;

    private Context mContext;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private SlidingTabLayout mTabLayout;
    private View mHeaderView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LoadHeaderImagesListener mLoadHeaderImagesListener;

    private TextView topTitle;
    private ImageButton leftBtn,rightBtn;

    public CoordinatorTabLayout(Context context) {
        super(context);
        mContext = context;
    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (!isInEditMode()) {
            initView(context);
            initWidget(context, attrs);
        }
    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (!isInEditMode()) {
            initView(context);
            initWidget(context, attrs);
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_coordinatortablayout, this, true);
        initToolbar();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarlayout);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout);
        mHeaderView =  findViewById(R.id.coordinator_header_view);

        topTitle= (TextView) findViewById(R.id.top_text_view_text);
        leftBtn= (ImageButton) findViewById(R.id.left_btn);
        rightBtn= (ImageButton) findViewById(R.id.right_btn_btn);
    }

    private void initWidget(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs
                , R.styleable.CoordinatorTabLayout);

        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int contentScrimColor = typedArray.getColor(
                R.styleable.CoordinatorTabLayout_contentScrim, typedValue.data);
        mCollapsingToolbarLayout.setContentScrimColor(contentScrimColor);

     /*   int tabIndicatorColor = typedArray.getColor(R.styleable.CoordinatorTabLayout_tabIndicatorColor, Color.WHITE);
        mTabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);

        int tabTextColor = typedArray.getColor(R.styleable.CoordinatorTabLayout_tabTextColor, Color.WHITE);
        mTabLayout.setTabTextColors(ColorStateList.valueOf(tabTextColor));*/
        typedArray.recycle();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);
        mActionbar = ((AppCompatActivity) mContext).getSupportActionBar();
    }

    /**
     * 设置Toolbar标题
     *
     * @param title 标题
     * @return
     */
    public CoordinatorTabLayout setTitle(String title) {
        if (mActionbar != null) {
            mActionbar.setTitle(title);
        }
        return this;
    }

    /**
     * 设置Toolbar显示返回按钮及标题
     *
     * @param canBack 是否返回
     * @return
     */
    public CoordinatorTabLayout setBackEnable(Boolean canBack,int res) {
        if (canBack && mActionbar != null) {
            mActionbar.setDisplayHomeAsUpEnabled(true);
            mActionbar.setHomeAsUpIndicator(res);
        }
        return this;
    }

    /**
     * 设置每个tab对应的头部图片
     *
     * @param imageArray 图片数组
     * @return
     */
    public CoordinatorTabLayout setImageArray(@NonNull int[] imageArray) {
        mImageArray = imageArray;
        setupTabLayout();
        return this;
    }

    /**
     * 设置每个tab对应的头部照片和ContentScrimColor
     *
     * @param imageArray 图片数组
     * @param colorArray ContentScrimColor数组
     * @return
     */
    public CoordinatorTabLayout setImageArray(@NonNull int[] imageArray, @NonNull int[] colorArray) {
        mImageArray = imageArray;
        mColorArray = colorArray;
        setupTabLayout();
        return this;
    }

    /**
     * 设置每个tab对应的ContentScrimColor
     *
     * @param colorArray 图片数组
     * @return
     */
    public CoordinatorTabLayout setContentScrimColorArray(@NonNull int[] colorArray) {
        mColorArray = colorArray;
        return this;
    }

    private void setupTabLayout() {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mHeaderView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_dismiss));
                if (mLoadHeaderImagesListener == null) {

                } else {
                    //mLoadHeaderImagesListener.loadHeaderImages(mImageView, tab);
                }
                if (mColorArray != null) {
                  /*  mCollapsingToolbarLayout.setContentScrimColor(
                            ContextCompat.getColor(
                                    mContext, mColorArray[tab.getPosition()]));*/
                }
                mHeaderView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_show));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 设置与该组件搭配的ViewPager
     *
     * @param viewPager 与TabLayout结合的ViewPager
     * @return
     */
    public CoordinatorTabLayout setupWithViewPager(ViewPager viewPager) {
        mTabLayout.setViewPager(viewPager);
        return this;
    }

    /**
     * 获取该组件中的ActionBar
     */
    public ActionBar getActionBar() {
        return mActionbar;
    }

    /**
     * 获取该组件中的TabLayout
     */
    public SlidingTabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * 获取该组件中的ImageView
     */
    public View getHeaderView() {
        return mHeaderView;
    }


    public void setHeaderView(View headerView) {
        if(mHeaderView!=null)
            ((LinearLayout) mHeaderView).addView(headerView);
    }
    /**
     * 设置LoadHeaderImagesListener
     *
     * @param loadHeaderImagesListener 设置LoadHeaderImagesListener
     * @return
     */
    public CoordinatorTabLayout setLoadHeaderImagesListener(LoadHeaderImagesListener loadHeaderImagesListener) {
        mLoadHeaderImagesListener = loadHeaderImagesListener;
        setupTabLayout();
        return this;
    }


    public CoordinatorTabLayout setTopTitle(String title){
        if(topTitle!=null){
            topTitle.setText(title);
        }
        return this;
    }


    public CoordinatorTabLayout setLeftOnClickListener(OnClickListener mOnClickListener){
        if(leftBtn!=null){
            leftBtn.setOnClickListener(mOnClickListener);
        }
        return this;
    }

    public CoordinatorTabLayout setRightOnClickListener(OnClickListener mOnClickListener){
        if(rightBtn!=null){
            rightBtn.setOnClickListener(mOnClickListener);
        }
        return this;
    }

}