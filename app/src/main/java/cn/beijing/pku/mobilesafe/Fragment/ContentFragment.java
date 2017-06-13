package cn.beijing.pku.mobilesafe.Fragment;

import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import cn.beijing.pku.mobilesafe.Activity.HomeActivity;
import cn.beijing.pku.mobilesafe.BasePager.BasePager;
import cn.beijing.pku.mobilesafe.BottomPager.GovAffairsPager;
import cn.beijing.pku.mobilesafe.BottomPager.HomePager;
import cn.beijing.pku.mobilesafe.BottomPager.NewsCenterPager;
import cn.beijing.pku.mobilesafe.BottomPager.SettingPager;
import cn.beijing.pku.mobilesafe.BottomPager.SmartServicePager;
import cn.beijing.pku.mobilesafe.R;
import cn.beijing.pku.mobilesafe.View.NoScrollViewPager;


/**
 * Created by Administrator on 2017.6.11.
 */
public class ContentFragment extends BaseFragment {
    private NoScrollViewPager  mViewPager;
    private ArrayList<BasePager> mPagers;
    private RadioGroup mRadioGroup;
    @Override
    public View initView() {
        View view = View.inflate(mActivity,R.layout.fragment_content,null);
        mViewPager = (NoScrollViewPager ) view.findViewById(R.id.vp_content);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new NewsCenterPager(mActivity));
        mPagers.add(new SmartServicePager(mActivity));
        mPagers.add(new GovAffairsPager(mActivity));
        mPagers.add(new SettingPager(mActivity));

        mViewPager.setAdapter(new ContentAdapter());
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
//                        去掉平滑动画
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4, false);
                        break;
                    default:
                        break;
                }
            }
        });

//        只有当前页面被选中时，才调用
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                BasePager pager = mPagers.get(position);
                pager.initData();
            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mPagers.get(position);
                pager.initData();
                if (position == 0 || position == mPagers.size() - 1) {
                    setSlidingMenuEnable(false);
                }else{
                    setSlidingMenuEnable(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        添加默认第一页数据
        mPagers.get(0).initData();
        setSlidingMenuEnable(false);
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
    // 获取新闻中心页面
    public NewsCenterPager getNewsCenterPager() {
        NewsCenterPager pager = (NewsCenterPager) mPagers.get(1);
        return pager;
    }

    class  ContentAdapter extends PagerAdapter{

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

            BasePager pager = mPagers.get(position);
            View view = pager.mRootView; // 获取当前页面对象的布局
            // pager.initData();// 初始化数据, viewpager会默认加载下一个页面,
            // 为了节省流量和性能,不要在此处调用初始化数据的方法
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
