package cn.beijing.pku.mobilesafe.BasePager;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.beijing.pku.mobilesafe.R;

/**
 * Created by Administrator on 2017.6.12.
 */

/**
 * @func 五个标签页的父类:1.标题、标题栏菜单、FrameLayout为五个标签页共有，所以设置成属性 2.标签页会与leftFragment交互，所以增加Activity属性
 *  3.因为需要拿到这五个标签页，所以增加view属性 4.因为需要数据交换、动态初始化UI等，增加两个方法
 */
public class BaseBottmButtonPager {
    public Activity mActivity;
    public View mRootView;
    public TextView tvTitle;
    public ImageView btnMenu;
    public FrameLayout flContent;

    public BaseBottmButtonPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }
    public View initView(){
        View view = View.inflate(mActivity, R.layout.base_bottm_button_pager, null);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        btnMenu = (ImageView) view.findViewById(R.id.btn_menu);
        flContent = (FrameLayout) view.findViewById(R.id.fl_content);
        return view;
    }

    public void initData(){

    }
}
