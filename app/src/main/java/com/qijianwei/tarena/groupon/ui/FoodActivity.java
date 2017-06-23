package com.qijianwei.tarena.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.adapter.FoodAdapter;
import com.qijianwei.tarena.groupon.entity.Food;
import com.qijianwei.tarena.groupon.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends Activity {
    @BindView(R.id.pulltorefresh_listview_food)
    PullToRefreshListView pullToRefreshListView;
    private String city;
    ListView listview;
    TextView food;
    List<Food.BusinessesBean> datas;
    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        setlistview();
    }

    private void setlistview() {
        listview=pullToRefreshListView.getRefreshableView();
        datas=new ArrayList<>();
        adapter = new FoodAdapter(this,datas);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();

    }
    private void refresh() {
        HttpUtil.getFoodByRetrofit(city, new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                Food food = response.body();
                List<Food.BusinessesBean> businesses = food.getBusinesses();
                adapter.addAll(businesses,true);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable throwable) {

            }
        });
    }
}
