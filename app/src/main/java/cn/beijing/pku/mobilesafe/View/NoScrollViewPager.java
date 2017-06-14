package cn.beijing.pku.mobilesafe.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017.6.12.
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /**
         * 因为新闻中心页面往左边滑动时，侧边栏也被滑动出来，所以需要中断父控件touch监听事件
         * 不拦截子控件的事件
         */
        return false;
    }
}
