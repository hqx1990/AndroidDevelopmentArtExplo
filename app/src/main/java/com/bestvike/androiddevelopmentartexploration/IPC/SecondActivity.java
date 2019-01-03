package com.bestvike.androiddevelopmentartexploration.IPC;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.beaselibrary.base.BaseActivity;

/**
 * Created by Administrator on 2018/5/4.
 * 多线程使用，主要是在Manifest中设置android:process的值
 */

public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("0-----","userId:"+UserManager.sUserId);
    }

    @Override
    protected void destroyPresenter() {

    }
}
