package bk.com.jsbridge;

import org.json.JSONObject;

/**
 * Created by sunqiujing on 2017/7/18.
 * webview 与activity通信接口
 */

public interface IWebViewCallback {

    //加载完h5界面，如果需要调用js
    public void  refreshView();

    /**
     * webview 内js调用java 跳转界面
     * @param param 参数
     */

    public void  NextActivity(JSONObject param);

    /**
     * webview 内js同步调用java方法，
     * @param param 传参数
     * @param callback 给js回执结果，可以没有回调为null
     */
   public void  deelSynchMethod(JSONObject param, final JSCallBack callback);
    /**
     * webview 内js异步调用java方法，
     * @param param 传参数
     * @param callback 回调，可以没有回调为null
     */
    public void  deelAsynchMethod(JSONObject param, final JSCallBack callback);

    /**
     * webview 内js调用java通用方法，
     * @param param 传参数
     * @param callback 回调，可以没有回调为null
     */
    public void  commonMethod(JSONObject param, final JSCallBack callback);
}
