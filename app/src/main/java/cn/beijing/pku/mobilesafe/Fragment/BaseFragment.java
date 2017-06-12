package cn.beijing.pku.mobilesafe.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017.6.11.
 */

public abstract  class BaseFragment extends Fragment {
    public Activity mActivity;
//    Fragment创建时，回调
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity(); //获取当前fregment
    }

    //初始化frag，ment时调用
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        return view;
    }

    //fragment所依赖的activity的onCreate方法执行结束
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
