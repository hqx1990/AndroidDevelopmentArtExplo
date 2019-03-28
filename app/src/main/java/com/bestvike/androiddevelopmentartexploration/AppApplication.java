package com.bestvike.androiddevelopmentartexploration;

import com.example.beaselibrary.base.BaseApplication;
import com.raizlabs.android.dbflow.config.FlowManager;

public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(getApplicationContext());//初始化数据库
        baseUrl = BuildConfig.API_HOST;
    }
}
