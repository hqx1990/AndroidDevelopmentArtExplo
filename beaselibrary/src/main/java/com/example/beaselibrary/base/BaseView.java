package com.example.beaselibrary.base;


import com.example.beaselibrary.interfaces.DialogListener;
import com.example.beaselibrary.network.okHttp.NetResultCallBack;

/**
 * Created by Administrator on 2017/6/2.
 */
public interface BaseView extends NetResultCallBack
{
    void showProgress(boolean flag);

    void showProgress(boolean flag, String msg);

    void showDialog(String msg);

    void showDialog(String msg, DialogListener listener);
}
