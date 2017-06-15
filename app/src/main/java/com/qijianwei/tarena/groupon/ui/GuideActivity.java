package com.qijianwei.tarena.groupon.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.adapter.GuideViewPageAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends FragmentActivity {


    @BindView(R.id.indicator)
    CirclePageIndicator circlePageIndicator;
    @BindView(R.id.viewpager_guide_id)
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        setAdapter();
    }
    private void setAdapter() {
        GuideViewPageAdapter adapter=new GuideViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        circlePageIndicator.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;
        circlePageIndicator.setBackgroundColor(0x00FFFFFF);
        //圈的大小
        circlePageIndicator.setRadius(10 * density);
        circlePageIndicator.setPageColor(0xFFFFFFFF);
        circlePageIndicator.setFillColor(0xFFFF6633);
        circlePageIndicator.setStrokeColor(0xFFFF6633);
        //圈的粗细
        circlePageIndicator.setStrokeWidth(2 * density);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            if (position==3){
            circlePageIndicator.setVisibility(View.INVISIBLE);
            }else {
            circlePageIndicator.setVisibility(View.VISIBLE);
            }
         }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
