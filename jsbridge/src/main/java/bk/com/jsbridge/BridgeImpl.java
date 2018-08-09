package bk.com.jsbridge;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunqiujing on 2017/7/04.
 * 暴漏出的方法类，所有的交互方法都放在这里
 * 类中的方法的规定
 * 1、为了调用方便，我们规定类中的方法必须是static的，这样直接根据类而不必新建对象进行调用了（还要是public的），voke 的时候传null
 * 2、该方法不具有返回值，因为返回值我们在回调中返回，方法的执行结果需要通过callback传递回去，既然有回调，参数列表就肯定有一个callback，
 * 3、js传来的方法调用所需的参数，是一个json对象，在java层中我们定义成JSONObject对象；
 * 4、java执行js方法需要一个WebView对象，
 * 满足规范的方法原型就出来了
 */

public class BridgeImpl implements IBridge {

    //js调用的方法
    public void synchMethod(BKWebView webView, JSONObject param, final JSCallBack callback) {

        webView.commonMethod(param, callback);

       String code = param.optString("code");
        //约定业务处理的code
        if ("0000".equals(code)) {
            webView.popToNext(param);
        } else {
//            String message = param.optString("msg");
//            Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();

            webView.synchMethod(param, callback);


//            if (null != callback) {
//                try {
//                    JSONObject object = new JSONObject();
//                    object.put("key", "value");
//                    object.put("key1", "value1");
//                    object.put("key3", "这个是通过回调回传的数据");
//                    callback.apply(getJSONObject(0001, "成功", object));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }

    }

    //js调用的方法
    public void asynchMethod(BKWebView webView, JSONObject param, final JSCallBack callback) {
//        String code = param.optString("code");
        String code = param.optString("code");
        webView.asynchMethod(param, callback);
    }

    private JSONObject getJSONObject(int code, String msg, JSONObject result) {
        JSONObject object = new JSONObject();
        try {
            object.put("code", code);
            object.put("msg", msg);
            object.putOpt("result", result);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
