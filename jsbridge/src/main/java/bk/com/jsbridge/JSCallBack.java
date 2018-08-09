package bk.com.jsbridge;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by sunqiujing on 2017/7/10.
 * java执行完相关业务，回调
 */

public class JSCallBack {


    //Looper.getMainLooper()就表示放到主线程中去处理
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static final String CALLBACK_JS_FORMAT = "javascript:bridge.callAppResult('%s', %s);";


    private String mPort;
    private WeakReference<BKWebView> mWebViewRef;

       public JSCallBack(BKWebView view, String port) {
        mWebViewRef = new WeakReference<>(view);
        mPort = port;
    }


    public void apply(JSONObject jsonObject) {
        final String execJs = String.format(CALLBACK_JS_FORMAT, mPort, String.valueOf(jsonObject));
        if (mWebViewRef != null && mWebViewRef.get() != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mWebViewRef.get().loadUrl(execJs);
                }
            });

        }

    }

}
