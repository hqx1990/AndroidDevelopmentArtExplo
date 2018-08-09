package bk.com.jsbridge;

import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by sunqiujing on 2017/7/10.
 * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度
 * h5层调native层,设置WebView 的setWebChromeClient方法，设置WebChromeClient对象
 *  而这个对象中有三个方法，分别是onJsAlert,onJsConfirm,onJsPrompt，当js调用window对象的对应的方法，
 * 即window.alert，window.confirm，window.prompt，WebChromeClient对象中的三个方法对应的就会被触发
 * sbridge://className:port/methodName?jsonObj
 * jsbridge://className:callbackAddress/methodName?jsonObj
 */

public class BKWebChromeClient extends WebChromeClient {
    /**
     * nav与js的交互基本就三种方式
     * 一是直接用@jsInterface这个注解，
     * 二是在webviewclient的shouldloaduri这个方法中通过对uri的判断进行交互，
     * 三是使用webchormeClient中的onjsprompt这个方法，通过接受在js端通过prompt这个方法传入数据,我们通过这个方法接收所的消息
     * 为替代addJavascriptInterface方法，可以利用prompt方法传参以完成java与js的交互。对应java中的onJsPrompt方法的声明如下：
     * JS能把信息（文本）传递到Java，而Java也能把信息（文本）传递到JS中。
     *
     *
     * @param view
     * @param url
     * @param message      标识
     * @param defaultValue 参数
     * @param result       回调
     * @return
     */


    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//        result.confirm("flag034");
        result.confirm(JSBridge.callNativeJava((BKWebView) view, message));

        return true;
    }
}
