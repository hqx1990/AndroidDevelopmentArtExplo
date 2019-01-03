package com.example.beaselibrary.network.okHttp;

import android.content.pm.PackageManager;

import com.example.beaselibrary.base.BaseApplication;
import com.example.beaselibrary.base.BaseUrl;
import com.example.beaselibrary.network.okHttp.builder.GetBuilder;
import com.example.beaselibrary.network.okHttp.builder.PostStringBuilder;
import com.example.beaselibrary.network.okHttp.builder.PutStringBuilder;
import com.example.beaselibrary.network.okHttp.callback.DaoStringCallBack;
import com.example.beaselibrary.network.okHttp.utils.JsonUtils;
import com.example.beaselibrary.network.okHttp.utils.OkHttpUtils;
import com.example.beaselibrary.network.token.TokenHelper;
import com.example.beaselibrary.sp.SpHelper;
import com.example.beaselibrary.sp.SpKey;
import com.example.beaselibrary.util.CheckUtil;
import com.example.beaselibrary.util.Logger;

import java.util.Map;

/**
 * 项目名称：HFAndroid
 * 项目作者：hqx
 * 创建日期：2017/6/3 14:44.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public class HttpDAO {
    //不需要token接口添加到此处
    public static String helpid = "";
    //不需要重发的接口添加到此处
    public static final String NO_RETRY = "";

    private DaoStringCallBack callback;
    private Object tag;

    private String appVersion;
    private String token;

    public HttpDAO(NetResultCallBack netResultCallBack) {
        this.tag = netResultCallBack;
        token = SpHelper.getInstance().readMsgFromSp(SpKey.LOGIN, SpKey.LOGIN_ACCESSTOKEN);
        callback = new DaoStringCallBack(netResultCallBack);
        if (!CheckUtil.getInstance().isEmpty(token)) {
            Logger.e("-----", "token:" + token);
        }
        try {
            appVersion = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Exception e) {
            appVersion = "";
        }
    }

    /**
     * 可调用方法，requestBean是请求参数的封装类
     */
    public <E> void put(String url, E requestParam, String flag) {
        PutStringBuilder builder = OkHttpUtils.putString()
                .tag(tag)
                .url(BaseUrl.baseUrl + url)
                .content(JsonUtils.class2Json(requestParam))
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion)
                .addHeader("SysVersion", "AND-P-" + android.os.Build.VERSION.RELEASE);

        if (!CheckUtil.getInstance().isEmpty(token) && !helpid.contains(url)) {
            builder.addHeader("Authorization", "Bearer" + token)
                    .addHeader("access_token", token);
        }
        builder.build().execute(callback, flag);

        TokenHelper.getInstance().saveRequestParams(url, requestParam, flag, "PUT", callback);
    }


    public void get(String url, Map<String, String> requestParam, String flag) {
        get(url,requestParam,flag,true);
    }
    public void get(String url, Map<String, String> requestParam, String flag,boolean isAddToken) {
        GetBuilder builder = OkHttpUtils
                .get()
                .tag(tag)
                .url(BaseUrl.baseUrl + url)
                .params(requestParam)
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion);
        if(isAddToken) {
            if (!CheckUtil.getInstance().isEmpty(token) && !helpid.contains(url)) {
                builder.addHeader("Authorization", "Bearer" + token)
                        .addHeader("access_token", token);
            }
        }
        builder.build().execute(callback, flag);

        TokenHelper.getInstance().saveRequestParams(url, requestParam, flag, "GET", callback);
    }

    public <E> void post(String url, E requestParam, String flag) {
       post(url,requestParam,flag,true);
    }
    public <E> void post(String url, E requestParam, String flag,boolean isAddToken) {
        PostStringBuilder builder = OkHttpUtils
                .postString()
                .tag(tag)
                .url(BaseUrl.baseUrl + url)
                .content(JsonUtils.class2Json(requestParam))
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion);
        if(isAddToken) {
            if (!CheckUtil.getInstance().isEmpty(token) && !helpid.contains(url)) {
                builder.addHeader("Authorization", "Bearer" + token)
                        .addHeader("access_token", token);
            }
        }

        if (NO_RETRY.contains(url)) {
            builder.build(false).execute(callback, flag);
        } else {
            builder.build().execute(callback, flag);
        }
        TokenHelper.getInstance().saveRequestParams(url, requestParam, flag, "POST", callback);
    }
}
