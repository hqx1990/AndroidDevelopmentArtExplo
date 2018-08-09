package bk.com.jsbridge;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by sunqiujing on 2017/7/14.
 * 主动调用js
 */

public class CallJS
{


    //Looper.getMainLooper()就表示放到主线程中去处理
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    //    private static final String CALL_JS_FORMAT = "javascript:JSBridge.callJS('%s', %s);";
    private static final String CALL_JS_FORMAT = "javascript:bridge.callJs('%s', %s);";

    private WeakReference<WebView> mWebViewRef;

    public CallJS(WebView view)
    {
        mWebViewRef = new WeakReference<>(view);

    }


    public void apply(final String flag, final JSONObject jsonObject)
    {
        final String execJs = String.format(CALL_JS_FORMAT, flag, String.valueOf(jsonObject));
        if (mWebViewRef != null && mWebViewRef.get() != null)
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mWebViewRef.get().loadUrl(execJs);
                }
            });
        }

    }

    /**
     * 照片base64字符串传入到js
     *
     * @param photoBase64 用户信息
     */
    public void getPhotoBase64(String flag, final JSONObject photoBase64)
    {
        final String execJs = String.format(CALL_JS_FORMAT, flag, String.valueOf(photoBase64));
        if (mWebViewRef != null && mWebViewRef.get() != null)
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mWebViewRef.get().loadUrl(execJs);
                }
            });
        }

    }

    /**
     * 用户信息传入到js
     *
     * @param userInfo 用户信息
     */
    public void getUserInfo(String flag, final JSONObject userInfo)
    {
        final String execJs = String.format(CALL_JS_FORMAT, flag, String.valueOf(userInfo));
        if (mWebViewRef != null && mWebViewRef.get() != null)
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mWebViewRef.get().loadUrl(execJs);
                }
            });
        }

    }

    /**
     * 实名信息传入到js
     *
     * @param userCustInfo 实名信息
     */
    public void getCustNo(String flag, JSONObject userCustInfo)
    {
        final String execJs = String.format(CALL_JS_FORMAT, flag, String.valueOf(userCustInfo));
        if (mWebViewRef != null && mWebViewRef.get() != null)
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mWebViewRef.get().loadUrl(execJs);
                }
            });
        }
    }

}
