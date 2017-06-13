package cn.beijing.pku.mobilesafe.BasePager;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2017.6.12.
 */

/**
 * @func:新闻中心页面会添加四个子页面，可以抽取成基类，因为需要互动，所以添加Activity和mRootview
 */
public abstract class BaseMenuDetailPager {
    public Activity mActivity;
    public View mRootView;// 菜单详情页根布局

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    // 初始化布局,必须子类实现
    public abstract View initView();

    // 初始化数据
    public void initData() {

    }
}
