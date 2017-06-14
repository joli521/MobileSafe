package cn.beijing.pku.mobilesafe.NewsCenterMenuDetailPager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import cn.beijing.pku.mobilesafe.Activity.HomeActivity;
import cn.beijing.pku.mobilesafe.BasePager.BaseNewsCenterMenuDetailPager;
import cn.beijing.pku.mobilesafe.BasePager.BaseNewsCenterTabDetailPager;
import cn.beijing.pku.mobilesafe.Domain.NewsMenuBean;
import cn.beijing.pku.mobilesafe.R;
import cn.beijing.pku.mobilesafe.Utils.ConstantUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;


/**
 * 菜单详情页-新闻
 * 將内部的頁面看作整体，好比contFragment中的五个页面一样，可以继承BasePager即可
 */
public class NewsMenuDetailPager extends BaseNewsCenterMenuDetailPager implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    private ArrayList<NewsMenuBean.NewsTabData> mTabData;
    private ArrayList<BaseNewsCenterTabDetailPager> mPagers; //页签页面
    private ImageButton mImageBtn;

    //tab页面处理
    private TabPageIndicator mIndicator;
    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenuBean.NewsTabData> tabDatas) {
        super(activity);
        mTabData = tabDatas;
    }

    @Override
    public View initView() {
        //获取控件
        View view = View.inflate(mActivity, R.layout.pager_newscenter_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_news_menu_detail);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.tbi_indicator);
        mImageBtn = (ImageButton) view.findViewById(R.id.btn_next);
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<BaseNewsCenterTabDetailPager>();   //导致出错，找了两个小时的错误。报空指针异常
//        LogUtil.v(ConstantUtil.TAG, "测试"+mTabData);
        //初始化页面
        for (int i = 0; i < mTabData.size(); i++) {
            LogUtil.v(ConstantUtil.TAG, "====="+mTabData);
            BaseNewsCenterTabDetailPager pager = new BaseNewsCenterTabDetailPager(mActivity, mTabData.get(i));
            mPagers.add(pager);
        }
        mViewPager.setAdapter(new NewsMenuDetaiAdpater());
        /**
         *  ViewPagerIndicator使用流程:
         *  1.引入库
         *  2.解决support-v4冲突(让两个版本一致)
         *  3.从例子程序中拷贝布局文件
         * 4.从例子程序中拷贝相关代码(指示器和viewpager绑定; 重写getPageTitle返回标题)
         * 5.在清单文件中增加样式
         * 6.背景修改为白色
         * 7.修改样式-背景样式&文字样式
         *
         */
        mIndicator.setViewPager(mViewPager);  //将指示器绑定到ViewPager
//        设置页面滑动监听:因为往左滑动时，拦截掉了事件，侧边栏会弹出来
//        mViewPager.setOnPageChangeListener(this);// 此处必须给指示器设置页面监听,不能设置给viewpager
        mIndicator.setOnPageChangeListener(this);
        mImageBtn.setOnClickListener(this);

    }

    /**
     * @func:按钮点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
          int id =  v.getId();
        switch (id) {
            case R.id.btn_next:
                //获取当前
                int currentItem = mViewPager.getCurrentItem();
                currentItem++;
                mViewPager.setCurrentItem(currentItem);
                break;
            default:
                break;
        }
    }


    class NewsMenuDetaiAdpater extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            NewsMenuBean.NewsTabData data = mTabData.get(position);
            return data.title;
        }

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseNewsCenterTabDetailPager pager = mPagers.get(position);
            View view = pager.mRootView;
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    /**
     * 监听页面滑动
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        System.out.println("当前位置:" + position);
        if (position == 0) {
            // 开启侧边栏
            setSlidingMenuEnable(true);
        } else {
            // 禁用侧边栏
            setSlidingMenuEnable(false);
        }

    }

    public void setSlidingMenuEnable(boolean enable) {
//        获取侧边栏对象
        HomeActivity mainUI = (HomeActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
