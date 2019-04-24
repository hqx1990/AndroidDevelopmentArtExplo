package com.example.beaselibrary.base;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.beaselibrary.util.ActManger;

/**
 * Created by Administrator on 2018/3/29.
 */

public class BaseApplication extends Application {

    public static BaseApplication CONTEXT;
    private static ActManger actManger = null; // activity管理类
    public static String baseUrl ;
    public FragmentActivity currentActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = (BaseApplication) getApplicationContext();
        actManger = getActManger(); // 获得实例

    }

    public static BaseApplication getInstance(){
        if(null == CONTEXT){
            CONTEXT = new BaseApplication();
        }
        return  CONTEXT;
    }

    public static ActManger getActManger() {
        if (null != actManger) {
            return actManger;
        } else {
            return actManger = new ActManger();
        }
    }
}
