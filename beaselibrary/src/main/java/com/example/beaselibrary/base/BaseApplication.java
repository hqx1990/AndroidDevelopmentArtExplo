package com.example.beaselibrary.base;

import android.app.Application;
import android.content.Context;

import com.example.beaselibrary.util.ActManger;

/**
 * Created by Administrator on 2018/3/29.
 */

public class BaseApplication extends Application {

    public static Context CONTEXT;
    private static ActManger actManger = null; // activity管理类
    public static String baseUrl ;


    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
        actManger = getActManger(); // 获得实例

    }

    public static BaseApplication getInstance(){
        if(null == CONTEXT){
            CONTEXT = new BaseApplication();
        }
        return (BaseApplication) CONTEXT;
    }

    public static ActManger getActManger() {
        if (null != actManger) {
            return actManger;
        } else {
            return actManger = new ActManger();
        }
    }
}
