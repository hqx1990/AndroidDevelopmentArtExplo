package com.example.beaselibrary.network.token;


import com.example.beaselibrary.network.okHttp.callback.DaoStringCallBack;

/**
 * 项目名称：token 失效重新请求
 * 项目作者：hqx
 * 创建日期：2017/5/8 10:01.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：用于储存原请求的参数的类
 * ----------------------------------------------------------------------------------------------------
 */
public class RequestParamsForToken {
    public String url;
    public String flag;
    public Object params;
    public String method;//GET POST PUT DELETE
    public Object tag;
    public DaoStringCallBack callBack;

    public void release(){
        url = null;
        flag = null;
        params = null;
        method = null;
        callBack = null;
        tag = null;
        System.gc();
    }
}
