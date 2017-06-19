package com.qijianwei.tarena.groupon.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qijianwei.tarena.groupon.entity.Group;
import com.qijianwei.tarena.groupon.entity.Group2;
import com.qijianwei.tarena.groupon.util.HttpUtil;
import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.ui.CityActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouYeFragment extends Fragment {
    //头部
    @BindView(R.id.ll_header_left_container)
    LinearLayout cityContainer;
    @BindView(R.id.tv_header_main_city)
    TextView tvcity;//显示城市名称
    @BindView(R.id.include_menu)
    View mennuLayout;
    //右上角+号
    @BindView(R.id.iamgeview_shouye_add)
    ImageView imageView_add;


    @BindView(R.id.pulltorefresh_listview_shouye)
    PullToRefreshListView  pullToRefreshListView;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> datas;
    private View vHead1;

    public ShouYeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_shou_ye, container, false);
        ButterKnife.bind(this,view);
        setlistview();
        return view;
    }
    //点击北京跳转
    @OnClick(R.id.ll_header_left_container)
    public void jumpToCity(View view){
        startActivity(new Intent(getActivity(), CityActivity.class));
    }
    //点击+号弹出menu
    @OnClick(R.id.iamgeview_shouye_add)
    public void toggleMenu(View view){
        if (mennuLayout.getVisibility()==View.VISIBLE){
            mennuLayout.setVisibility(View.INVISIBLE);
        }else {
            mennuLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setlistview() {
        listView=pullToRefreshListView.getRefreshableView();

        //为ListView添加若干个头部
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View listHeaderIcons = inflater.inflate(R.layout.header_list_icons, listView, false);
        View listHeaderSquares =inflater.inflate(R.layout.header_list_square,listView,false);
        View listHeaderAds = inflater.inflate(R.layout.header_list_ads, listView, false);
        View listHeaderCategories = inflater.inflate(R.layout.header_list_categories,listView,false);
        View listHeaderRecommend = inflater.inflate(R.layout.linearlayout_heared_tuijian,listView,false);
        listView.addHeaderView(listHeaderIcons);
        listView.addHeaderView(listHeaderSquares);
        listView.addHeaderView(listHeaderAds);
        listView.addHeaderView(listHeaderCategories);
        listView.addHeaderView(listHeaderRecommend);

        datas=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);
        ininListHeaderIcons(listHeaderIcons);
    }
    //实现Viewpage滑动
    private void ininListHeaderIcons(final View listHeaderIcons) {
        final ViewPager viewpager= (ViewPager) listHeaderIcons.findViewById(R.id.vp_header_list_icons);
        PagerAdapter adapter=new PagerAdapter() {
            int[] resIDs = new int[]{
                    R.layout.linearlayout_viewpager_item1,
                    R.layout.linearlayout_viewpager_item2,
                    R.layout.linearlayout_viewpager_item3
            };
            @Override
            public int getCount() {
                return 30000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int layoutId = resIDs[position%3];
                View view = LayoutInflater.from(getActivity()).inflate(layoutId,viewpager,false);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
               container.removeView((View) object);
            }
        };
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(15000);
        //实现viewpager滑动地下View跟着滑动
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                ImageView iv_header_list_icons_indicator1= (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator1);
                ImageView iv_header_list_icons_indicator2= (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator2);
                ImageView iv_header_list_icons_indicator3= (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator3);
                if (position%3==0){
                    iv_header_list_icons_indicator1.setImageResource(R.drawable.banner_dot_pressed);
                    iv_header_list_icons_indicator2.setImageResource(R.drawable.banner_dot);
                    iv_header_list_icons_indicator3.setImageResource(R.drawable.banner_dot);
                }else if (position%3==1){
                    iv_header_list_icons_indicator1.setImageResource(R.drawable.banner_dot);
                    iv_header_list_icons_indicator2.setImageResource(R.drawable.banner_dot_pressed);
                    iv_header_list_icons_indicator3.setImageResource(R.drawable.banner_dot);
                }else if (position%3==2){
                    iv_header_list_icons_indicator1.setImageResource(R.drawable.banner_dot);
                    iv_header_list_icons_indicator2.setImageResource(R.drawable.banner_dot);
                    iv_header_list_icons_indicator3.setImageResource(R.drawable.banner_dot_pressed);
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem==0){
                cityContainer.setVisibility(View.VISIBLE);
                imageView_add.setVisibility(View.VISIBLE);
            }else {
                cityContainer.setVisibility(View.GONE);
                imageView_add.setVisibility(View.GONE);
            }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        adapter.notifyDataSetChanged();
        //HttpUtil.testHttpURLConnection();
        //HttpUtil.testVolley(getActivity());
       // HttpUtil.testRetrogit();
        HttpUtil.httpGroup2Load(getActivity(), tvcity.getText().toString(), new HttpUtil.GroupListener2() {
            @Override
            public void onGroupLoadEnd(Group2 group2) {
                Log.i("TAG","group2-----------"+group2.getId_list());
                HttpUtil.httpGroupLoad(getActivity(), tvcity.getText().toString(), group2, new HttpUtil.GroupListener() {
                    @Override
                    public void onGroupLoadEnd(Group group) {
                        Log.i("TAG","group2-----------"+group.getDeals());
                    }
                });
            }
        });

    }
}
