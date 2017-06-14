package cn.beijing.pku.mobilesafe.NewsCenterTabDetaiPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017.6.13.
 * 新闻头条自定义pager
 */

public class TopNewsViewPager extends ViewPager {
    private  int startX;
    private  int startY;
    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1. 上下滑动需要拦截
     * 2. 向右滑动并且当前是第一个页面,需要拦截
     * 3. 向左滑动并且当前是最后一个页面,需要拦截
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);  //刚开始不要拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                 startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int dx = endX - startX;
                int dy = endY - startY;
                int currentItem = getCurrentItem();
                if (Math.abs(dy) < Math.abs(dx)) { //左右滑动
                    if (dx > 0) {
//                        向右滑动,对第一个页面进行拦截
                        if (currentItem == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
//                    向左滑动
                        int count = getAdapter().getCount();
                        if (currentItem == count - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
