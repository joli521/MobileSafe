package cn.beijing.pku.mobilesafe.Fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import cn.beijing.pku.mobilesafe.Activity.HomeActivity;
import cn.beijing.pku.mobilesafe.Domain.NewsMenu;
import cn.beijing.pku.mobilesafe.BottomPager.NewsCenterPager;
import cn.beijing.pku.mobilesafe.R;

/**
 * Created by Administrator on 2017.6.11.
 */

public class LeftMenuFragment extends BaseFragment {
    private ListView lvList;
    private ArrayList<NewsMenu.NewsMenuData> mNewsMenuData;// 侧边栏网络数据对象
    private int mCurrPosition;
    private LeftMenuAdapter mLeftAdpater;
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        lvList = (ListView) view.findViewById(R.id.lv_list);
        return view;
    }

    @Override
    public void initData() {

    }

    /**
     * @func:设置侧边菜单的数据：从NewsCenterPager中获取数据，填充到适配器中
     * @param data
     */
    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data){
        mCurrPosition = 0;
        mNewsMenuData = data;
        mLeftAdpater = new LeftMenuAdapter();
        lvList.setAdapter(mLeftAdpater);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrPosition = position;
                mLeftAdpater.notifyDataSetChanged();
                toggle();

                // 侧边栏点击之后, 要修改新闻中心的FrameLayout中的内容
                setCurrentDetailPager(position);
            }
        });
    }
    /**
     * @func  设置当前菜单详情页
     * @param position
     */
    protected void setCurrentDetailPager(int position) {
        // 获取新闻中心的对象
        HomeActivity mainUI = (HomeActivity) mActivity;
        // 获取ContentFragment
        ContentFragment fragment = mainUI.getContentFragement();
        // 获取NewsCenterPager
        NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();
        // 修改新闻中心的FrameLayout的布局
        newsCenterPager.setCurrentDetailPager(position);
    }
    /**
     * @func 打开或者关闭侧边栏:如何拿到侧边栏？通过Acvitvity
     */
    protected void toggle() {
        HomeActivity mainUI = (HomeActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();// 如果当前状态是开, 调用后就关; 反之亦然
    }


    class LeftMenuAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mNewsMenuData.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
            TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu);
            NewsMenu.NewsMenuData item = (NewsMenu.NewsMenuData) getItem(position);
            tvMenu.setText(item.title);
            if (position == mCurrPosition){
                tvMenu.setEnabled(true);
            }else {
                tvMenu.setEnabled(false);
            }
            return view;
        }
    }

}
