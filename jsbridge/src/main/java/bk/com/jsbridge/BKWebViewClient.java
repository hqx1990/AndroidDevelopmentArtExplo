package bk.com.jsbridge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * Created by sunqiujing on 2017/7/14.
 * 主要帮助WebView处理各种通知、请求事件的
 */

public class BKWebViewClient extends WebViewClient
{
    //实现对网页中超链接的拦截(比如如果是极客导航的主页，则直接拦截转到百度主页)：
    //当点击页面中的链接后，会在WebView加载URL前回调shouldOverrideUrlLoading(WebView view, String url)方法，
    // 一般点击一个链接此方法调用一次。
    // return true，则在打开新的url时WebView就不会再加载这个url了，所有处理都需要在WebView中操作，包含加载；
    // return false，则系统就认为上层没有做处理，接下来还是会继续加载这个url的；默认return false

    private WeakReference<Activity> weakReference_activity;

    public BKWebViewClient(Activity activity)
    {
        weakReference_activity = new WeakReference<Activity>(activity);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view,
                                            String url)
    {
        String scheme = Uri.parse(url).getScheme();
        //还需要判断host
        if (TextUtils.equals("bestvike", scheme))
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (weakReference_activity != null)
            {
                Activity activity = weakReference_activity.get();

                activity.startActivity(intent);
            }
            return true;
        }
        return false;
    }

    // 加载网页时替换某个资源（比如在加载一个网页时，需要加载一个logo图片，
    // 而我们想要替换这个logo图片，用我们assets目录下的一张图片替代）
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request)
    {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error)
    {
        handler.proceed();  // 接受信任所有网站的证书
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon)
    {
    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        //加载玩页面后再加载图片
        if (!view.getSettings().getLoadsImagesAutomatically())
        {
            view.getSettings().setLoadsImagesAutomatically(true);
        }

        if (weakReference_activity != null)
        {
            ((IWebViewCallback) weakReference_activity.get()).refreshView();
        }
    }
}
