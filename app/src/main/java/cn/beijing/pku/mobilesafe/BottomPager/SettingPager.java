package cn.beijing.pku.mobilesafe.BottomPager;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import cn.beijing.pku.mobilesafe.BasePager.BasePager;
import cn.beijing.pku.mobilesafe.Utils.ConstantUtil;
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
        LogUtil.v(ConstantUtil.TAG, "加载第5个页面");

//        填充布局
        TextView view = new TextView(mActivity);
        view.setText("设置");
        view.setTextColor(Color.RED);
        flContent.addView(view);
        tvTitle.setText("设置");
        btnMenu.setVisibility(View.INVISIBLE);
    }
}
