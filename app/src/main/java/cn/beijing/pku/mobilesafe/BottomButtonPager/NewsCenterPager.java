package cn.beijing.pku.mobilesafe.BottomButtonPager;

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

import cn.beijing.pku.mobilesafe.Activity.HomeActivity;
import cn.beijing.pku.mobilesafe.BasePager.BaseNewsCenterMenuDetailPager;
import cn.beijing.pku.mobilesafe.BasePager.BaseBottmButtonPager;
import cn.beijing.pku.mobilesafe.Domain.NewsMenuBean;
import cn.beijing.pku.mobilesafe.Fragment.LeftMenuFragment;
import cn.beijing.pku.mobilesafe.NewsCenterMenuDetailPager.InteractMenuDetailPager;
import cn.beijing.pku.mobilesafe.NewsCenterMenuDetailPager.NewsMenuDetailPager;
import cn.beijing.pku.mobilesafe.NewsCenterMenuDetailPager.PhotosMenuDetailPager;
import cn.beijing.pku.mobilesafe.NewsCenterMenuDetailPager.TopicMenuDetailPager;
import cn.beijing.pku.mobilesafe.Utils.CacheUtil;
import cn.beijing.pku.mobilesafe.Utils.ConstantUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;

import static cn.beijing.pku.mobilesafe.Utils.ConstantUtil.CATEGORY_URL;

/**
 * Created by Administrator on 2017.6.12.
 */
//新闻中心
public class NewsCenterPager extends BaseBottmButtonPager {
    private NewsMenuBean mNewsData;// 分类信息网络数据
    private ArrayList<BaseNewsCenterMenuDetailPager> mMenuDetailPagers;// 菜单详情页集合
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
//            LogUtil.v(ConstantUtil.TAG, "发现缓存");
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
        RequestParams params = new RequestParams(CATEGORY_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                String json = result.toString();
//                LogUtil.v(ConstantUtil.TAG,"============"+json);
                processData(json);
////                写缓存
                CacheUtil.setCache(ConstantUtil.CATEGORY_URL, json, mActivity);
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            //主动调用取消请求的回调方法
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
        mNewsData = gson.fromJson(json, NewsMenuBean.class);
//        // 获取侧边栏对象
        HomeActivity mainUI = (HomeActivity) mActivity;
        LeftMenuFragment fragment = mainUI.getLeftMenuFragement();

//        // 给侧边栏设置数据
        fragment.setMenuData(mNewsData.data);

        /**
         *   初始化4个菜单详情页
         *   在拿到数据后，如何将数据传递给内部的页签TabDetaiPager哪？
         *   通过构造方法进行传递的，修改此处的参数：传递mNewsData中的data中的children即可
         *
         */
        mMenuDetailPagers = new ArrayList<BaseNewsCenterMenuDetailPager>();
        mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity, mNewsData.data.get(0).children));
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
        BaseNewsCenterMenuDetailPager pager = mMenuDetailPagers.get(position);// 获取当前应该显示的页面
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
