package com.qijianwei.tarena.groupon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.ListView;

import com.qijianwei.tarena.groupon.fragment.FragmentGuidanceFour;
import com.qijianwei.tarena.groupon.fragment.FragmentGuidanceOne;
import com.qijianwei.tarena.groupon.fragment.FragmentGuidanceThree;
import com.qijianwei.tarena.groupon.fragment.FragmentGuidanceTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarena on 2017/6/15.
 */

public class GuideViewPageAdapter extends FragmentPagerAdapter{
    List<Fragment> fragmentList;
    public GuideViewPageAdapter(FragmentManager fm) {
        super(fm);
        fragmentList=new ArrayList<>();
        fragmentList.add(new FragmentGuidanceOne());
        fragmentList.add(new FragmentGuidanceTwo());
        fragmentList.add(new FragmentGuidanceThree());
        fragmentList.add(new FragmentGuidanceFour());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
