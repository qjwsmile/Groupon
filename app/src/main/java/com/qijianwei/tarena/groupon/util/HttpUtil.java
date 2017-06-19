package com.qijianwei.tarena.groupon.util;

/**
 * Created by tarena on 2017/6/19.
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.qijianwei.tarena.groupon.config.Config;
import com.qijianwei.tarena.groupon.entity.Group;
import com.qijianwei.tarena.groupon.entity.Group2;
import com.qijianwei.tarena.groupon.util.VolleyClient;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络访问工具类
 * <p>
 * 符合大众点评服务器要求的地址：
 * http://网址部分?参数1=值1&参数2=值2....
 * <p>
 * http://api.dianping.com/v1/business/find_businesses?appkey=49814079&sign=生成的签名&city=%xx%xx%xx%xx%xx%xx&category=%xx%xx%xx%xx%xx%xx
 * <p>
 * 请求地址中签名的生成：
 * 利用Appkey,AppSecret,以及其他访问参数(例如city,category)
 * 首先将Appkey,AppSecret以及其他访问参数拼接成一个字符串
 * 例:49814079category美食city上海90e3438a41d646848033b6b9d461ed54
 * 将拼接好的字符串进行转码(转码算法为SHA1算法)
 * 转码后就得到了签名
 * <p>
 * Created by pjy on 2017/6/19.
 */

public class HttpUtil {
    public static final String APPKEY = "49814079";
    public static final String APPSECRET = "90e3438a41d646848033b6b9d461ed54";


    /**
     * 获得满足大众点评服务器要求的请求路径
     */
    public static String getURL(String url, Map<String, String> params) {

        String result = "";

        String sign = getSign(APPKEY, APPSECRET, params);

        String query = getQuery(APPKEY, sign, params);

        result = url + "?" + query;

        return result;

    }
    /**
     * 获取请求地址中的签名
     *
     * @param appkey
     * @param appsecret
     * @param params
     * @return
     */
    public static String getSign(String appkey, String appsecret, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();

// 对参数名进行字典排序
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
// 拼接有序的参数名-值串
        stringBuilder.append(appkey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(params.get(key));
        }
        String codes = stringBuilder.append(appsecret).toString();
        //纯JAVA环境中，利用Codec对字符串进行SHA1转码采用如下方式
        //String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
        //安卓环境中，利用Codec对字符串进行SHA1转码采用如下方式
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();
        return sign;
    }
    /**
     * 获取请求地址中的参数部分
     *
     * @param appkey
     * @param sign
     * @param params
     * @return
     */
    private static String getQuery(String appkey, String sign, Map<String, String> params) {
        try {
            // 添加签名
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("appkey=").append(appkey).append("&sign=").append(sign);
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                stringBuilder.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(),"utf8"));
            }
            String queryString = stringBuilder.toString();

            return queryString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //扔异常
            throw new RuntimeException("使用了不正确的字符集名称");
        }
    }
    public static void testHttpURLConnection(){

        //获取符合大众点评要求的请求地址
        Map<String,String> params = new HashMap<String,String>();
        params.put("city","北京");
        params.put("category","美食");
        final String url = getURL("http://api.dianping.com/v1/business/find_businesses",params);
        Log.d("TAG", "生成的网络请求地址是："+url);
        new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);//该方法可写可不写，因为默认就是true
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine())!=null){
                        sb.append(line);
                    }
                    reader.close();
                    String response = sb.toString();
                    Log.d("TAG", "HttpURLConnection获得的服务器响应内容："+response);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static void testVolley(Context context){

        VolleyClient.getInstance(context).test();


    }
    public static void testRetrogit(){
        RetrogitClient.getInstance().test();
    }
    public static void  httpGroup2Load(final Context context, String cityName, final GroupListener2 listener2 ){
        try {

            final Map<String,String> params=new HashMap<>();
            params.put("city",cityName);
            cityName=URLEncoder.encode(cityName,"utf8");
            String url= Config.TUANGOUURL+"city="+cityName+"&appkey="+APPKEY+"&sign="+getSign(APPKEY,APPSECRET,params);
            RequestQueue queue= Volley.newRequestQueue(context);
            StringRequest request=new StringRequest(url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson=new Gson();
                   Group2 group2 = gson.fromJson(s,Group2.class);

                    listener2.onGroupLoadEnd(group2);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public interface GroupListener2{
        public void onGroupLoadEnd(Group2 group);
    }
    public interface GroupListener{
        public void onGroupLoadEnd(Group group);
    }


    public static void  httpGroupLoad(Context context, String cityName, Group2 group2, final GroupListener listener){
            String batch="";
    for (int i=0;i<40;i++){
        batch=batch+group2.getId_list().get(i)+",";
    }
    batch=batch.substring(0,batch.length()-1);
        final Map<String,String> params=new HashMap<>();
            params.put("city",cityName);

        String  batchurl=Config.BATCH+batch+"&appkey="+APPKEY+"&sign="+getSign(APPKEY,APPSECRET,params);
        Log.i("TAG","BATCH-----------"+batchurl);
    RequestQueue que=Volley.newRequestQueue(context);
    StringRequest reque=new StringRequest(batchurl, new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            Gson gs=new Gson();
            Group group=gs.fromJson(s,Group.class);

            listener.onGroupLoadEnd(group);
        }
    }, new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    });
    que.add(reque);
    }
}