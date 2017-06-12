package cn.beijing.pku.mobilesafe.Pager;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

import cn.beijing.pku.mobilesafe.Utils.ConstValueUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;

/**
 * Created by Administrator on 2017.6.12.
 */
//首页
public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        LogUtil.v(ConstValueUtil.TAG, "加载第一个页面");
//        填充布局
        TextView view = new TextView(mActivity);
        view.setText("设置");
        view.setTextColor(Color.RED);
        flContent.addView(view);
        tvTitle.setText("设置");
    }
}
