package cn.beijing.pku.mobilesafe.BasePager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import cn.beijing.pku.mobilesafe.Domain.NewsMenuBean;
import cn.beijing.pku.mobilesafe.Domain.NewsTabBean;
import cn.beijing.pku.mobilesafe.NewsCenterTabDetaiPager.TopNewsViewPager;
import cn.beijing.pku.mobilesafe.R;
import cn.beijing.pku.mobilesafe.Utils.CacheUtil;
import cn.beijing.pku.mobilesafe.Utils.ConstantUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;

/**
 * Created by Administrator on 2017.6.13.
 */
public  class BaseNewsCenterTabDetailPager extends BaseNewsCenterMenuDetailPager {
    private NewsMenuBean.NewsTabData mTabData; //单个页签的网络数据
    private TopNewsViewPager mViewPager;
    private String mUrl;
    private NewsTabBean mNewsTabBeanData;
    public ArrayList<NewsTabBean.TopNews> mTopnews;

    private TextView tvTitle;
    private CirclePageIndicator mCirclePageIndicator;
    private ListView mListView;
    private ArrayList<NewsTabBean.NewsData> mNewsList;
    private NewsListAdapter mNewsListAdapeter;


    public BaseNewsCenterTabDetailPager(Activity activity, NewsMenuBean.NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
        mUrl = ConstantUtil.SERVER_URL + mTabData.url;
    }

    @Override
    public View initView() {
//        //        填充布局
        View view = View.inflate(mActivity, R.layout.pager_newscenter_tab_detail, null);
        mViewPager = (TopNewsViewPager) view.findViewById(R.id.vp_news_tab);
        tvTitle = (TextView) view.findViewById(R.id.tv_news_tab);
        mCirclePageIndicator = (CirclePageIndicator) view.findViewById(R.id.cpIndicator_news_tab);
        mListView = (ListView) view.findViewById(R.id.lv_news_tab);
        return view;
    }


    @Override
    public void initData() {
//        view.setText(mTabData.title); //放到此处
        /*检测是否有缓存*/
        String cache = CacheUtil.getCache(mUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            LogUtil.v(ConstantUtil.TAG, "有缓存！！！！");
            processData(cache);
        }
        getDataFromServer();
    }

    /**
     * @func:获得新闻中心页中上部页签的数据
     */
    private void getDataFromServer() {
        RequestParams requestParams = new RequestParams(mUrl);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String json = result.toString();
                LogUtil.v(ConstantUtil.TAG, "TabDetailData======" + json);
                processData(json);
//                写缓存
                CacheUtil.setCache(mUrl, json, mActivity);
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
     * @func: 使用GSon 解析数据，添加左边四个Menu item
     */
    private void processData(String json) {
        Gson gson = new Gson();
        mNewsTabBeanData = gson.fromJson(json, NewsTabBean.class);
        mTopnews = mNewsTabBeanData.data.topnews;
        LogUtil.v(ConstantUtil.TAG, "mTopnews" + mTopnews.size());
        if (mTopnews != null) {
            LogUtil.v(ConstantUtil.TAG, "加载成功" + mTopnews.size());
            mViewPager.setAdapter(new TopNewsAdapter());

            mCirclePageIndicator.setViewPager(mViewPager);
            mCirclePageIndicator.setSnap(true);

            //页面切换事件设置个mCirclePageIndicator
            mCirclePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    NewsTabBean.TopNews topNews = mTopnews.get(position);
                    tvTitle.setText(topNews.title);
                }
                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            tvTitle.setText(mTopnews.get(0).title);
            mCirclePageIndicator.onPageSelected(0);// 默认让第一个选中(解决页面销毁后重新初始化时,Indicator仍然保留上次圆点位置的bug)
        }
//        列表新闻内容
        mNewsList = mNewsTabBeanData.data.news;
        if (mNewsList != null) {
            mNewsListAdapeter = new NewsListAdapter();
            mListView.setAdapter(mNewsListAdapeter);
        }
    }


    /**
     * 新闻中心页适配器
     */
    class TopNewsAdapter extends PagerAdapter {

        //每个页签都不同，需要根据网络数据进行动态获取
        @Override
        public int getCount() {
            return mTopnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setImageResource(R.drawable.topnews_item_default);
            view.setScaleType(ImageView.ScaleType.FIT_XY);  // 设置图片缩放方式, 宽高填充父控件
            String imageUrl = mTopnews.get(position).topimage;
//            LogUtil.v(ConstantUtil.TAG, "URL==" + imageUrl);
            //通过ImageOptions.Builder().set方法设置图片的属性
            ImageOptions imageOptions = new ImageOptions.Builder().setFadeIn(true).build(); //淡入效果
            x.image().bind(view, imageUrl, imageOptions, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
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
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * @func:新闻列表适配器
     */
    class NewsListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}

