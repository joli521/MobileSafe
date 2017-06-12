package cn.beijing.pku.mobilesafe.Pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import cn.beijing.pku.mobilesafe.Utils.ConstValueUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;

/**
 * Created by Administrator on 2017.6.12.
 */
//首页
public class SmartServicePager extends BasePager {
    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        LogUtil.v(ConstValueUtil.TAG, "加载第3个页面");
//        填充布局
        TextView view = new TextView(mActivity);
        view.setText("智慧服务");
        view.setTextColor(Color.RED);
        flContent.addView(view);
        tvTitle.setText("智慧服务");
        btnMenu.setVisibility(View.VISIBLE);
    }
}
