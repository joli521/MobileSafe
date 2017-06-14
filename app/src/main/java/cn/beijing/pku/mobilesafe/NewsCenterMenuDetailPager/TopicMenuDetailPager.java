package cn.beijing.pku.mobilesafe.NewsCenterMenuDetailPager;


import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.beijing.pku.mobilesafe.BasePager.BaseNewsCenterMenuDetailPager;


/**
 * 菜单详情页-专题
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class TopicMenuDetailPager extends BaseNewsCenterMenuDetailPager {

	public TopicMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView view = new TextView(mActivity);
		view.setText("菜单详情页-专题");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		return view;
	}

}
