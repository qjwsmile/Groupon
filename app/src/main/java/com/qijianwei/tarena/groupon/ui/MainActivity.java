package com.qijianwei.tarena.groupon.ui;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.fragment.FaXianFragment;
import com.qijianwei.tarena.groupon.fragment.MyFragment;
import com.qijianwei.tarena.groupon.fragment.ShouYeFragment;
import com.qijianwei.tarena.groupon.fragment.YouHuiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListView();

    }

    private void initListView() {
        RadioGroup radiogroup= (RadioGroup) findViewById(R.id.radiofroup_id);
        radiogroup.setOnCheckedChangeListener(this);
        RadioButton radiobutton= (RadioButton) findViewById(R.id.radiobutton_shouye);
        radiobutton.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        switch (checkedId){
            case R.id.radiobutton_shouye:
                transaction.replace(R.id.containerid,new ShouYeFragment(),"Fragment_shouye");
                break;
            case R.id.radiobutton_youhui:
                transaction.replace(R.id.containerid,new YouHuiFragment(),"Fragment_youhui");
                break;
            case R.id.radiobutton_faxian:
                transaction.replace(R.id.containerid,new FaXianFragment(),"Fragment_faxian");
                break;
            case R.id.radiobutton_wode:
                transaction.replace(R.id.containerid,new MyFragment(),"Fragment_My");
                break;
        }
        transaction.commit();
    }
}
