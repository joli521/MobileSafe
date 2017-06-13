package cn.beijing.pku.mobilesafe.BottomPager;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.beijing.pku.mobilesafe.Activity.HomeActivity;
import cn.beijing.pku.mobilesafe.BasePager.BaseMenuDetailPager;
import cn.beijing.pku.mobilesafe.BasePager.BasePager;
import cn.beijing.pku.mobilesafe.Domain.NewsMenu;
import cn.beijing.pku.mobilesafe.Fragment.LeftMenuFragment;
import cn.beijing.pku.mobilesafe.Http.ServiceResponse;
import cn.beijing.pku.mobilesafe.NewsCenterChildPager.InteractMenuDetailPager;
import cn.beijing.pku.mobilesafe.NewsCenterChildPager.NewsMenuDetailPager;
import cn.beijing.pku.mobilesafe.NewsCenterChildPager.PhotosMenuDetailPager;
import cn.beijing.pku.mobilesafe.NewsCenterChildPager.TopicMenuDetailPager;
import cn.beijing.pku.mobilesafe.Utils.CacheUtil;
import cn.beijing.pku.mobilesafe.Utils.ConstantUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;

import static cn.beijing.pku.mobilesafe.Utils.ConstantUtil.CATEGORY_URL;

/**
 * Created by Administrator on 2017.6.12.
 */
//首页
public class NewsCenterPager extends BasePager {
    private NewsMenu mNewsData;// 分类信息网络数据
    private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;// 菜单详情页集合
    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        LogUtil.v(ConstantUtil.TAG, "加载第2个页面");
//        填充布局
        TextView view = new TextView(mActivity);
        view.setText("新闻中心");
        view.setTextColor(Color.RED);
        flContent.addView(view);
        tvTitle.setText("新闻中心");
        btnMenu.setVisibility(View.VISIBLE);

        /**
         * 因为需要拿到新闻标题，所以进行网络连接，进行json数据解析，
         * 调用leftFragment中的方法，动态设置菜单item
         * */
        String cache = CacheUtil.getCache(ConstantUtil.CATEGORY_URL, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            LogUtil.v(ConstantUtil.TAG, "发现缓存");
            processData(cache);
        }
//        }else {
//            getDataFromServer();
//        }
//       先加载本地缓存，然后再更新
            getDataFromServer();
    }

    /**
     * @func:使用XUtil3.5.0从网络获取数据，进行解析
     */
    private void getDataFromServer() {

        RequestParams requestParams = new RequestParams(CATEGORY_URL);
        Callback.Cancelable cancelable = x.http().get(requestParams, new Callback.CommonCallback<List<ServiceResponse>>(){

            @Override
            public void onSuccess(List<ServiceResponse> result) {
                String response = result.toString();
                response =  response.substring(1, response.length()-1); //去掉最外边的[]
                LogUtil.v(ConstantUtil.TAG,response);
                processData(response);
//                写缓存
                CacheUtil.setCache(ConstantUtil.CATEGORY_URL, response, mActivity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * @func:  使用GSon 解析数据，添加左边四个Menu item
     *
     */
    private void processData(String json) {
        Gson gson = new Gson();
        mNewsData = gson.fromJson(json, NewsMenu.class);
        System.out.println("解析结果:" + mNewsData);
        // 获取侧边栏对象
        HomeActivity mainUI = (HomeActivity) mActivity;
        LeftMenuFragment fragment = mainUI.getLeftMenuFragement();

        // 给侧边栏设置数据
        fragment.setMenuData(mNewsData.data);

        // 初始化4个菜单详情页
        mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
        mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));

        // 将新闻菜单详情页设置为默认页面
        setCurrentDetailPager(0);

    }


    /**
     * @func:    设置菜单详情页
     */
    public void setCurrentDetailPager(int position) {
        // 重新给frameLayout添加内容
        BaseMenuDetailPager pager = mMenuDetailPagers.get(position);// 获取当前应该显示的页面
        View view = pager.mRootView;// 当前页面的布局
        // 清除之前旧的布局
        flContent.removeAllViews();
        flContent.addView(view);// 给帧布局添加布局
        // 初始化页面数据
        pager.initData();

        // 更新标题
        tvTitle.setText(mNewsData.data.get(position).title);
    }
}
