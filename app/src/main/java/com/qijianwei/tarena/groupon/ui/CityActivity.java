package com.qijianwei.tarena.groupon.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.adapter.CityAdapter;
import com.qijianwei.tarena.groupon.entity.City;
import com.qijianwei.tarena.groupon.entity.CitynameBean;
import com.qijianwei.tarena.groupon.util.HttpUtil;
import com.qijianwei.tarena.groupon.util.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends Activity {
    @BindView(R.id.rv_city_cities)
    RecyclerView recyclerView;
    //适配器
    CityAdapter adapter;
    //数据源
    List<CitynameBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        //初始化数据源，适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        datas = new ArrayList<CitynameBean>();
        adapter = new CityAdapter(this,datas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        HttpUtil.getCitiesByRetrofit(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City city=response.body();
                List<String> list=city.getCities();
                List<CitynameBean> citynameBeanList = new ArrayList<CitynameBean>();
                for (String name : list) {

                    if (!name.equals("全国") && !name.equals("其它城市")&&!name.equals("点评实验室")) {
                        CitynameBean citynameBean = new CitynameBean();
                        citynameBean.setCityName(name);
                        citynameBean.setPyName(PinYinUtil.getPinYin(name));
                        citynameBean.setLetter(PinYinUtil.getLetter(name));
                        Log.d("TAG", "创建的CitynameBean内容： "+citynameBean);

                        citynameBeanList.add(citynameBean);
                    }
                    Collections.sort(citynameBeanList, new Comparator<CitynameBean>() {
                        @Override
                        public int compare(CitynameBean t1, CitynameBean t2) {
                            return t1.getPyName().compareTo(t2.getPyName());
                        }
                    });

                }

                adapter.addAll(citynameBeanList,true);
            }

            @Override
            public void onFailure(Call<City> call, Throwable throwable) {

            }
        });
    }
}
