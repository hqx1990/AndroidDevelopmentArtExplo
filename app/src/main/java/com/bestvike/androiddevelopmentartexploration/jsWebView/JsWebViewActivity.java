package com.bestvike.androiddevelopmentartexploration.jsWebView;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.WebviewJsBaseActivity;
import com.example.beaselibrary.util.CheckUtil;
import com.qingmei2.rximagepicker_extension.utils.UIUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bk.com.jsbridge.BKWebChromeClient;
import bk.com.jsbridge.BKWebViewClient;
import bk.com.jsbridge.IWebViewCallback;
import bk.com.jsbridge.JSCallBack;

public class JsWebViewActivity extends WebviewJsBaseActivity implements IWebViewCallback {

    public String url;//链接
    private String methodId;//网页点击返回的业务类型

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView.clearCache(true);//清除网页缓存

        loaDatas();//加载网页

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                javaToJs();//java调用js中的方法
            }
        });
    }

    private void loaDatas(){
                    //TODO H5是否分享测试地址
//                    url = "http://10.164.194.123/hf/#!/activity/20170904/index.html?flag=share";
//                    //TODO 本地JS测试地址
                    url = "file:///android_asset/androidindex.html";
//                    商品分享
//                    url = "http://testpm.haiercash.com:9002/hf/#!/share/goods.html?goodsCode=7845";
//                    额度激活（底部带按钮的）
//                    url = "http://testpm.haiercash.com:9002/hf/#!/edjh.html";
//                    大额专项（底部无按钮的）
//                    url = "http://testpm.haiercash.com:9002/hf/#!/dezx/cjzxfq.html";
//                    活动页
//                    url = "http://testpm.haiercash.com:9002/hf/#!/activity/20180115/";
//                    url = "http://testpm.haiercash.com:9002/hf/#!/activity/20171228/";

        Map<String, String> params = new HashMap<String, String>();
//        if (!CheckUtil.getInstance().isEmpty(SpHelper.getInstance().readMsgFromSp(SpKey.LOGIN, SpKey.LOGIN_USERID))) {
//            params.put("userId", SpHelper.getInstance().readMsgFromSp(SpKey.LOGIN, SpKey.LOGIN_USERID));
//        }
//        if (!CheckUtil.getInstance().isEmpty(SpHelper.getInstance().readMsgFromSp(SpKey.USER, SpKey.USER_CUSTNO))) {
//            params.put("custNo", SpHelper.getInstance().readMsgFromSp(SpKey.USER, SpKey.USER_CUSTNO));
//        }
        if (0 == params.size()) {
            CallUrl(url);
        } else {
            CallUrl(url, params);
        }



        mWebView.setWebViewClient(new BKWebViewClient(this) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                slowlyProgressBar.onProgressStart();
//                if (!CheckUtil.isEmpty(loanName)) {
//                    setTitle(loanName);
//                } else {
//                    if (!CheckUtil.isEmpty(title)) {
//                        if (!title.contains("http://")
//                                && !title.contains("https://")) {
//                            setTitle(title);
//                        }
//                    } else {
//                        setTitle("");
//                    }
//                }
            }

            @Override
            public void onPageFinished(final WebView view, String url)//解决webview嵌套于scrollvew中压缩网页的问题
            {
//                if (!CheckUtil.isEmpty(loanName)) {
//                    setTitle(loanName);
//                } else {
//                    if (!CheckUtil.isEmpty(title)) {
//                        if (!title.contains("http://")
//                                && !title.contains("https://")) {
//                            setTitle(title);
//                        }
//                    } else {
//                        setTitle("");
//                    }
//                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
            }
        });
        mWebView.setWebChromeClient(new BKWebChromeClient() {
            /**
             * 设置头部标题栏设置获取到的网页title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!CheckUtil.getInstance().isEmpty(title)) {
//                    if (!title.contains("http://")
//                            && !title.contains("https://")) {
//                        JsWebviewActivity.this.title = title;
//                    }
                }
            }

            /**
             * webview顶部加载条
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                slowlyProgressBar.onProgressChange(newProgress);
//                if (!CheckUtil.isEmpty(loanName)) {
//                    setTitle(loanName);
//                } else {
//                    if (!CheckUtil.isEmpty(title)) {
//                        if (!title.contains("http://")
//                                && !title.contains("https://")) {
//                            setTitle(title);
//                        }
//                    } else {
//                        setTitle("");
//                    }
//                }
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
    }

    /**
     * 交互
     * @param param 传参数
     * @param callback 回调，可以没有回调为null
     */
    @Override
    public void commonMethod(JSONObject param, JSCallBack callback) {
        methodId = optString(param, "method");

        showToast(methodId);
        if(!CheckUtil.getInstance().isEmpty(methodId)){
            Log.e("----","交互："+methodId);

        }
    }

    /**
     * 判断value是否为空
     *
     * @param json
     * @param key
     * @return
     */
    public static String optString(JSONObject json, String key) {
        if (null == json) {
            return null;
        } else if (json.isNull(key)) {
            return null;
        } else {
            return json.optString(key, null);
        }
    }
}
