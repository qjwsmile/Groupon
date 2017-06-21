package com.qijianwei.tarena.groupon.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.entity.City;
import com.qijianwei.tarena.groupon.entity.Group;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpRetryException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarena on 2017/6/19.
 */

public class VolleyClient {

    private static VolleyClient INSTANCE;

    //2)声明一个公有的静态的获取1)属性的方法
    public static VolleyClient getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (VolleyClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VolleyClient(context);
                }
            }
        }
        return INSTANCE;
    }

    RequestQueue queue;

    ImageLoader imageLoader;
    public VolleyClient(Context context) {
        queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            //least recently use
            LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getHeight()*value.getRowBytes();
                }
            };
            @Override
            public Bitmap getBitmap(String s) {
                return cache.get(s);
            }
            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                cache.put(s,bitmap);
            }
        });
    }

    public void test() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("city", "北京");
        params.put("category", "美食");

        String url = HttpUtil.getURL("http://api.dianping.com/v1/business/find_businesses", params);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("TAG", "利用Volley获取的服务器响应内容： " + s);
            }
        }, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }
    /**
     * 利用Volly去获取城市今日新增的团购信息
     *
     * @param city 获取该城市名称的团购信息
     * @param listener
     */
    public void getDailyDeals(String city, final Response.Listener<String> listener){
        Log.d("TAG", "onResponse:getDailyDeals");

        //1)获取新增团购的ID列表

        final Map<String,String> params = new HashMap<String,String>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        String url = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_daily_new_id_list",params);
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.d("TAG", "onResponse: ids:"+s);

                //利用Jsonlib(JSONObject)提取团购ID
                try {
                    JSONObject jsonObject= new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");

                    int size = jsonArray.length();
                    if(size>40){
                        size = 40;
                    }

                    StringBuilder sb = new StringBuilder();
                    for(int i=0;i<size;i++){
                        String id = jsonArray.getString(i);
                        sb.append(id).append(",");
                    }

                    //"1-33946,1-4531,1-4571.."
                    if(sb.length()>0) {
                        String idlist = sb.substring(0, sb.length() - 1);

                        //2）获取团购详情
                        Map<String, String> params2 = new HashMap<String, String>();
                        params2.put("deal_ids", idlist);
                        String url2 = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_batch_deals_by_id", params2);
                        StringRequest req2 = new StringRequest(url2, listener, null);
                        queue.add(req2);
                    }else {
                        //该城市今日无新增团购
                        listener.onResponse(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },null);
        queue.add(req);
    }
    /**
     * 显示网络中的一幅图片
     * @param url 图片在网络中的地址
     * @param iv  显示图片的控件
     */
    public void loadImage(String url,ImageView iv){

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.bucket_no_picture, R.drawable.bucket_no_picture);
        imageLoader.get(url,listener);
    }
    public class GroupRequest extends Request<Group>{

        Response.Listener<Group> listener;
        public GroupRequest(String url,Response.Listener<Group> listener ) {
            super(Method.GET, url, null);
            this.listener=listener;
        }

        @Override
        protected Response<Group> parseNetworkResponse(NetworkResponse networkResponse) {
            String re=new String(networkResponse.data);
            Gson gson=new Gson();
            Group group= gson.fromJson(re,Group.class);
            Response<Group> response=Response.success(group, HttpHeaderParser.parseCacheHeaders(networkResponse));
            return response;
        }

        @Override
        protected void deliverResponse(Group group) {
            listener.onResponse(group);
        }

    }

    public void getCities(Response.Listener<String> listener){

        Map<String,String> params = new HashMap<String,String>();
        String url = HttpUtil.getURL("http://api.dianping.com/v1/metadata/get_cities_with_businesses",params);
        StringRequest req = new StringRequest(url,listener,null);
        queue.add(req);

    }
 }
