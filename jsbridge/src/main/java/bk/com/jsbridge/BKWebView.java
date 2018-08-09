package bk.com.jsbridge;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import org.json.JSONObject;

/**
 * Created by sunqiujing on 2017/7/18.
 * 实现可以用activity通信的webview
 */

public class BKWebView extends WebView
{
    public IWebViewCallback webViewCallback;

    private int mMaxHeight = -1;

    public BKWebView(Context context)
    {
        super(context);
    }

    public BKWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BKWebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void setMaxHeight(int height)
    {
        mMaxHeight = height;
    }

    public void setWebViewCallback(IWebViewCallback callback)
    {
        webViewCallback = callback;
    }

    /**
     * webview 内js调用java 跳转界面
     *
     * @param param 参数
     */
    public void popToNext(JSONObject param)
    {
        webViewCallback.NextActivity(param);
    }

    /**
     * webview 内js同步调用java方法，
     *
     * @param param    传参数
     * @param callback 给js回执结果，可以没有回调为null
     */
    public void synchMethod(JSONObject param, final JSCallBack callback)
    {
        webViewCallback.deelSynchMethod(param, callback);
    }

    /**
     * webview 内js异步调用java方法，
     *
     * @param param    传参数
     * @param callback 回调，可以没有回调为null
     */
    public void asynchMethod(JSONObject param, final JSCallBack callback)
    {
        webViewCallback.deelAsynchMethod(param, callback);
    }

    /**
     * webview 内js调用java 通用方法
     *
     * @param param 参数
     */
    public void commonMethod(JSONObject param, final JSCallBack callback)
    {
        webViewCallback.commonMethod(param, callback);
    }
}
