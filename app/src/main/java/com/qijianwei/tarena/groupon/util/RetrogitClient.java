package com.qijianwei.tarena.groupon.util;

import android.util.Log;

import com.qijianwei.tarena.groupon.config.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by tarena on 2017/6/19.
 */

public class RetrogitClient {
    private static RetrogitClient INSTANCE;
    public static RetrogitClient getInstance(){
        if (INSTANCE==null){
        synchronized (RetrogitClient.class){
            if (INSTANCE==null){
                INSTANCE=new RetrogitClient();
            }
        }

        }
        return INSTANCE;
    }

    private Retrofit retrofit;
    private NetService netService;

    public RetrogitClient(){
        retrofit=new Retrofit.Builder().baseUrl(Config.BASEURL).addConverterFactory(ScalarsConverterFactory.create()).build();
        netService=retrofit.create(NetService.class);
    }
    public void test(){

        Map<String,String> params=new HashMap<>();
        params.put("city","北京");
        params.put("category","美食");
        final String sign = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params);
        Call<String> call = netService.test(HttpUtil.APPKEY, sign, params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body=response.body();
                Log.i("TAG", "onResponse: "+body);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

}
