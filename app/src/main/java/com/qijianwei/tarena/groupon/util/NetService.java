package com.qijianwei.tarena.groupon.util;


import com.qijianwei.tarena.groupon.entity.City;
import com.qijianwei.tarena.groupon.entity.Food;
import com.qijianwei.tarena.groupon.entity.Group;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by tarena on 2017/6/19.
 */

public interface NetService {
    @GET("business/find_businesses")
    public Call<String> test(@Query("appkey") String appkey, @Query("sign") String sign, @QueryMap Map<String,String> params);

    @GET("deal/get_daily_new_id_list")
    public Call<String> getDailyIds(@Query("appkey") String appkey, @Query("sign") String sign,@QueryMap Map<String,String> params);
    @GET("deal/get_batch_deals_by_id")
    public Call<String> getDailyDeals(@Query("appkey") String appkey, @Query("sign") String sign,@QueryMap Map<String,String> params);
    @GET("deal/get_batch_deals_by_id")
    public Call<Group> getDailyDeals2(@Query("appkey") String appkey, @Query("sign") String sign, @QueryMap Map<String,String> params);

    @GET("deal/get_daily_new_id_list")
    public Call<String> getDailyIds2(@QueryMap Map<String,String> params);

    @GET("deal/get_batch_deals_by_id")
    public Call<Group> getDailyDeals3(@QueryMap Map<String,String> params);
    @GET("metadata/get_cities_with_businesses")
    public Call<City> getCities();
    @GET("http://api.dianping.com/v1/business/find_businesses")
    public Call<Food> getFood(@QueryMap Map<String,String> params);
}
