package cn.beijing.pku.mobilesafe.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StatFs;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import cn.beijing.pku.mobilesafe.Fragment.ContentFragment;
import cn.beijing.pku.mobilesafe.Fragment.LeftMenuFragment;
import cn.beijing.pku.mobilesafe.R;
import me.tangke.slidemenu.SlideMenu;

public class HomeActivity extends SlidingActivity {
    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
    private static final String TAG_CONT_MAIN = "TAG_CONT_MAIN";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlideMenu slideMenu = new SlideMenu(this);
        setContentView(R.layout.activity_home);

        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = new SlidingMenu(this);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 全屏触摸
        slidingMenu.setBehindOffset(200);// 屏幕预留200像素宽度
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_left_menu, new LeftMenuFragment(),TAG_LEFT_MENU);
        ft.replace(R.id.fl_main, new ContentFragment(),TAG_CONT_MAIN);
        ft.commit();
    }
    //获得左边的fragment
    public LeftMenuFragment getLeftFragement(){
        FragmentManager fm = getFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(TAG_LEFT_MENU);
        return fragment;
    }
    //获得右边的fragment
    public ContentFragment getContentFragement(){
//        888888888888888888888888888888888888888
        FragmentManager fm = getFragmentManager();
        ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(TAG_CONT_MAIN);
        return fragment;
    }


    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
}
