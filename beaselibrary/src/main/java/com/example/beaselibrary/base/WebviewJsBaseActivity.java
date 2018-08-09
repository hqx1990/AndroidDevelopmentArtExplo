package com.example.beaselibrary.base;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;


import com.example.beaselibrary.R;
import com.example.beaselibrary.util.CheckUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import bk.com.jsbridge.BKWebChromeClient;
import bk.com.jsbridge.BKWebView;
import bk.com.jsbridge.BKWebViewClient;
import bk.com.jsbridge.BridgeImpl;
import bk.com.jsbridge.CallJS;
import bk.com.jsbridge.IWebViewCallback;
import bk.com.jsbridge.JSBridge;
import bk.com.jsbridge.JSCallBack;
import bk.com.jsbridge.SlowlyProgressBar;


/**
 * Created by xuxiaoyan on 2017/8/19.
 * Webview JS交互基类
 */

public class WebviewJsBaseActivity extends BaseActivity implements IWebViewCallback
{
    public BKWebView mWebView;
    private String appVersion;
    public SlowlyProgressBar slowlyProgressBar;
    public String userAgent;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }
        setContentView(R.layout.activity_js_base_webview);
        mWebView = (BKWebView) findViewById(R.id.bkWebview);
        /**
         * webview顶部加载条
         */
        slowlyProgressBar = new SlowlyProgressBar
                (
                        (ProgressBar) findViewById(R.id.ProgressBar)
                );
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//设置能够解析Javascript
        settings.setDomStorageEnabled(true);//设置适应Html5的一些方法
        settings.setUseWideViewPort(true);
        //webview定位相关设置
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(getFilesDir().getPath());
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        //获取特殊字符串头，是一种向访问网站提供你所使用的浏览器类型及版本、操作系统及版本、浏览器内核、等信息的标识
        userAgent = settings.getUserAgentString();
        Log.e(getClass().getSimpleName() + "old", userAgent);
        userAgent = userAgent + "webview";
        settings.setUserAgentString(userAgent);
        Log.e(getClass().getSimpleName() + "old", userAgent);
        /**
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //如果用户设置了WebViewClient，则在点击新的链接以后就不会跳转到系统浏览器了，而是在本WebView中显示。
        // 注意：并不需要覆盖 shouldOverrideUrlLoading 方法，同样可以实现所有的链接都在 WebView 中打开
        mWebView.setWebViewClient(new BKWebViewClient(this)
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                slowlyProgressBar.onProgressStart();
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
            }
        });
        mWebView.setWebChromeClient(new BKWebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                slowlyProgressBar.onProgressChange(newProgress);
            }
        });
        //1.首先在WebView初始化时添加如下代码
        mWebView.setWebViewCallback(this);
        if (Build.VERSION.SDK_INT >= 19)
        {
            /**对系统API在19以上的版本作了兼容。
             因为4.4以上系统在onPageFinished时再恢复图片加载时,
             如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。
             */
            settings.setLoadsImagesAutomatically(true);
        } else
        {
            settings.setLoadsImagesAutomatically(false);
        }
        //native层的方法必须遵循某种规范，不然就非常不安全了。
        // 在native中，我们需要一个JSBridge统一管理这些暴露给js的类和方法，
        // 并且能实时添加，这时候就需要这么一个方法
        //JSBridge.register方法第二个参数必须是IBridge接口的实现类
        JSBridge.register("bk_bridge", BridgeImpl.class);
    }

    /**
     * 无参调用网页
     *
     * @param url 网页地址
     */
    public void CallUrl(String url)
    {
        CallUrl(url, null);
    }

    /**
     * URL传参
     *
     * @param url   网页地址
     * @param param 入参value
     */
    public void CallUrl(String url, Map<String, String> param)
    {
        if (CheckUtil.getInstance().isEmpty(url))
        {
            showProgress(false);
            //showDialog(getString(R.string.data_exception));
            return;
        }
        Map<String, String> requestParam = getHeadParam(param);
        if (!CheckUtil.getInstance().isEmpty(getUrlParamsByMap(requestParam)))
        {
            if (url.contains("?"))
            {
                url = url + "&" + getUrlParamsByMap(requestParam);
            } else
            {
                url = url + "?" + getUrlParamsByMap(requestParam);
            }
        }
        Log.e(getClass().getSimpleName(), "webUrl = " + url);
        mWebView.loadUrl(url);
    }

    /**
     * 获取头部入参
     *
     * @param param 入参map
     * @return 加密后的头部入参map
     */
    public Map<String, String> getHeadParam(Map<String, String> param)
    {
        Map<String, String> requestParam = new HashMap<>();
        try
        {
            appVersion = BaseApplication.CONTEXT.getPackageManager().getPackageInfo(BaseApplication.CONTEXT.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Exception e)
        {
            appVersion = "";
        }
//        String token = SpHelper.getInstance().readMsgFromSp(SpKey.LOGIN, SpKey.LOGIN_ACCESSTOKEN);
//        putParam(requestParam, "access_token", token);
//        putParam(requestParam, "channel", AppApplication.CHANNEL);
//        putParam(requestParam, "channel_no", AppApplication.channel_no);
//        putParam(requestParam, "APPVersion", "AND-P-" + appVersion);
//        putParam(requestParam, "DeviceModel", "AND-P-" + Build.MODEL);
//        putParam(requestParam, "DeviceResolution", "AND-P-" + UiUtil.getDeviceWidth() + "," + UiUtil.getDeviceHeight());
//        putParam(requestParam, "SysVersion", "AND-P-" + Build.VERSION.RELEASE);

        if (null != param && 0 != param.size())
        {
            String userId;
            if (!CheckUtil.getInstance().isEmpty(param.get("userId")))
            {
                userId = param.get("userId");
                putParam(requestParam, "userId", userId);
            }
            String custNo;
            if (!CheckUtil.getInstance().isEmpty(param.get("custNo")))
            {
                custNo = param.get("custNo");
                putParam(requestParam, "custNo", custNo);
            }
            String custNm;
            if (!CheckUtil.getInstance().isEmpty(param.get("custNm")))
            {
                custNm = param.get("custNm");
                putParam(requestParam, "custNm", custNm);
            }
            String cooperNm;
            if (!CheckUtil.getInstance().isEmpty(param.get("cooperNm")))
            {
                cooperNm = param.get("cooperNm");
                putParam(requestParam, "cooperNm", cooperNm);
            }
        }
        return requestParam;
    }

    /**
     * Map加密拼参
     *
     * @param map
     * @param key
     * @param value
     * @return
     */
    public Map<String, String> putParam(Map<String, String> map, String key, String value)
    {
        try
        {
//            if(!CheckUtil.isEmpty(value)){
//                map.put(URLEncoder.encode(EncryptUtil.simpleEncryptForJS(key), "UTF-8").replace("-", "%2B"),
//                        URLEncoder.encode(EncryptUtil.simpleEncryptForJS(value), "UTF-8").replace("-", "%2B"));
//            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), "H5转码失败:" + e);
        }
        return map;
    }


    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */
    public String getUrlParamsByMap(Map<String, String> map)
    {
        if (map == null)
        {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&"))
        {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    /**
     * java 调用js 中的方法
     */
    public void javaToJs(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId","java调用js方法");
            new CallJS(mWebView).getUserInfo("getUserId", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), "getUserId" + e);
        }
    }

    @Override
    public void refreshView()
    {

    }

    @Override
    public void NextActivity(JSONObject param)
    {

    }

    @Override
    public void deelSynchMethod(JSONObject param, JSCallBack callback)
    {
    }

    @Override
    public void deelAsynchMethod(JSONObject param, JSCallBack callback)
    {
    }

    @Override
    public void commonMethod(JSONObject param, JSCallBack callback)
    {
    }
}
