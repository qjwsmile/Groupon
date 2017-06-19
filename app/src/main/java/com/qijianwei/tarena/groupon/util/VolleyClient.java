package com.qijianwei.tarena.groupon.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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


//    private VolleyClient(Context context) {
//        queue = Volley.newRequestQueue(MyApp.CONTEXT);
//    }

    private VolleyClient(Context context) {
        queue = Volley.newRequestQueue(context);
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
}
