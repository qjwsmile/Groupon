package com.qijianwei.tarena.groupon.util;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.qijianwei.tarena.groupon.config.Config;

/**
 * 对偏好设置文件的操作
 * 1) Context的getSharedPreferences(文件名,模式);
 * 2) Activity的getPreference(模式);获取以preference_activiy名的偏好设置文件
 * 3) PreferenceManager的getDefaultSharedPreferences(Context);
 *    获取preference_包名 偏好设置文件
 *    模式 Context_MODE_PRIVATE
 * Created by hahaha on 2017/6/15.
 */


public class SharedPreferences {

     android.content.SharedPreferences sp;

    //通过构造方法，来重载偏好设置
         public SharedPreferences(Context context,String name){
             sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
         }
         public SharedPreferences(Context context){
        sp= PreferenceManager.getDefaultSharedPreferences(context);
        }

        public  boolean isFirst(){
            return sp.getBoolean(Config.FIRST,true);
        }
        public void setFirst(boolean flag){
        android.content.SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Config.FIRST,flag);
        editor.commit();
    }

}
