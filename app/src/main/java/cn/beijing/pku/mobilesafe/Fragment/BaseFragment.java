package cn.beijing.pku.mobilesafe.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.beijing.pku.mobilesafe.Domain.NewsMenu;

/**
 * Created by Administrator on 2017.6.11.
 * 1.因为左右两个Fragment都必须进行两项操作：1.初始化UI  2.填充数据（pager页面）所以可以抽取成基类；
 * 2.新闻中心Pager需要与leftFragment进行交互，所以需要Activitu充当中间者，无论是获取上下文、传递数据、获取控件等；
 * 3.基类Fragment主要覆写三种方法：（1）Fragment创建时，父类回调该子类的onCreate方法 （2）初始化Fragment回调的onCreateView方法
 *      （3）fragment所依赖的activity的onCreate方法执行结束时回调的方法
 * 4.因为在两个Fragment中都填充Pager，所以增添initView方法；各pager页、fragment之间需要传递数据，增添了initData函数
 */

public abstract  class BaseFragment extends Fragment {

    public Activity mActivity;
    /**
     * @func:    Fragment创建时，回调
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity(); //获取当前fregment
    }

    /**
     * @func:  初始化fragment时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        return view;
    }

    /**
     * @func:fragment所依赖的activity的onCreate方法执行结束
     * 注意：先创建Activity后，将Fragment进行绑定
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // 初始化数据
        initData();
    }
    // 初始化布局, 必须由子类实现
    public abstract View initView();

    // 初始化数据, 必须由子类实现
    public abstract void initData();

}
