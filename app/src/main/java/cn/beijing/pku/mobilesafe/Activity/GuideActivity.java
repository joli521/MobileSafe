package cn.beijing.pku.mobilesafe.Activity;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.xutils.ex.HttpException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.beijing.pku.mobilesafe.Adapter.GuideAdapter;
import cn.beijing.pku.mobilesafe.R;
import cn.beijing.pku.mobilesafe.Utils.BaseActivity;
import cn.beijing.pku.mobilesafe.Utils.ConstValueUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;
import cn.beijing.pku.mobilesafe.Utils.PrefUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private Button mStartButton;
    private LinearLayout mLinearLayout;
    private ImageView mRedImgView;
    private int[] mImgIds = new int[]{R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    private ArrayList<ImageView> mImgViewList = null;
    // 小红点移动距离
    private int mPointDis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        1212121212
        super.onCreate(savedInstanceState);
        System.out.println("test");
//        getSupportActionBar().hide();  //去掉标题栏,只能去掉单个Acitvity
        setContentView(R.layout.activity_guide);
        initUI();
        initData();
        mViewPager.setAdapter( new GuideAdapter(mImgViewList));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //主要获得图片移动的距离
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.e(ConstValueUtil.TAG, "移动的百分比="+positionOffset);
                // 更新小红点距离**********不懂************************************
//                int leftMargin = (int) (mPointDis * positionOffset) + position* mPointDis;// 计算小红点当前的左边距
                int leftMargin = (int) (mPointDis * positionOffset) + position* mPointDis;// 计算小红点当前的左边距
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRedImgView.getLayoutParams();
                layoutParams.leftMargin = leftMargin;
                mRedImgView.setLayoutParams(layoutParams);
            }

            //主要用来监听在最后一个页面显示 开始体验 按钮
            @Override
            public void onPageSelected(int position) {
                LogUtil.e(ConstValueUtil.TAG, "mViewPager的位置==="+position);
                if (position == mImgViewList.size() - 1){
                    mStartButton.setVisibility(View.VISIBLE);
                }else{
                    mStartButton.setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 计算两个圆点的距离
        // 移动距离=第二个圆点left值 - 第一个圆点left值
        // measure->layout(确定位置)->draw(activity的onCreate方法执行结束之后才会走此流程)
        // mPointDis = llContainer.getChildAt(1).getLeft()
        // - llContainer.getChildAt(0).getLeft();
        // System.out.println("圆点距离:" + mPointDis);

        // 监听layout方法结束的事件,位置确定好之后再获取圆点间距
        // 视图树
        mRedImgView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //需要注意的是OnGlobalLayoutListener可能会被多次触发，因此在得到了高度之后，要将OnGlobalLayoutListener注销掉。
                mRedImgView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointDis = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                LogUtil.e(ConstValueUtil.TAG, "小红点移动的距离==="+mPointDis);
            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                第一次安装后，将状态表示为false：表示不是第一次使用
                PrefUtils.setBoolean(getApplicationContext(), "isnotfirst", false);
//                Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
//                startActivity(intent);
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }

    /**
     * 1.向Viewpage中添加图片 2.添加三个空白点
     */
    private void initData() {
        mImgViewList = new ArrayList<>();
        for (int i = 0; i < mImgIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImgIds[i]);
            mImgViewList.add(imageView);

            //每个IamgeView中添加一个空白圆点,
            ImageView pointIV = new ImageView(this);
            pointIV.setBackgroundResource(R.drawable.iv_point_dark);
            //设置空白点之间的距离：1.获取LinearLayout, 2.设置参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            if (i > 0) {
                layoutParams.leftMargin = 10;
            }
            //参数设置完，必须作用于线性布局里的控件
            pointIV.setLayoutParams(layoutParams);
            mLinearLayout.addView(pointIV);
        }


    }


    /**
     * 初始化UI主要是获得控件
     */
    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        mStartButton = (Button) findViewById(R.id.btn_start);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_container);
        mRedImgView = (ImageView) findViewById(R.id.iv_red_point);
    }

}
