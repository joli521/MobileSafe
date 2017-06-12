package cn.beijing.pku.mobilesafe.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017.6.11.
 */

public class GuideAdapter extends PagerAdapter {
    private ArrayList<ImageView> mImgViewList;

    public GuideAdapter(ArrayList<ImageView> arrayList) {
        this.mImgViewList = arrayList;
    }
    //是获取当前窗体界面数

    @Override
    public int getCount() {
        return mImgViewList.size();
    }

    //用于判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    // 初始化item布局,return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象*放在当前的ViewPager中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImgViewList.get(position);
        container.addView(imageView);
        return imageView;

    }
    // 从ViewGroup中移出当前View
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}