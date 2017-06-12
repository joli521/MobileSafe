package cn.beijing.pku.mobilesafe.Pager;

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
 * 五个标签页的父类
 */
public class BasePager {
    public Activity mActivity;
    public View mRootView;
    public TextView tvTitle;
    public ImageView btnMenu;
    public FrameLayout flContent;

    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }
    public View initView(){
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        btnMenu = (ImageView) view.findViewById(R.id.btn_menu);
        flContent = (FrameLayout) view.findViewById(R.id.fl_content);
        return view;
    }

    public void initData(){

    }
}
