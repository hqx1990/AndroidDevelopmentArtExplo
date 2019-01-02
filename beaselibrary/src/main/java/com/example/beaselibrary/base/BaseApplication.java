package com.example.beaselibrary.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/3/29.
 */

public class BaseApplication extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
    }

    public static BaseApplication getInstance(){
        if(null == CONTEXT){
            CONTEXT = new BaseApplication();
        }
        return (BaseApplication) CONTEXT;
    }
}
