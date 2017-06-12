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
public class NewsCenterPager extends BasePager {
    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        LogUtil.v(ConstValueUtil.TAG, "加载第2个页面");
//        填充布局
        TextView view = new TextView(mActivity);
        view.setText("新闻中心");
        view.setTextColor(Color.RED);
        flContent.addView(view);
        tvTitle.setText("新闻中心");
        btnMenu.setVisibility(View.VISIBLE);
    }
}
