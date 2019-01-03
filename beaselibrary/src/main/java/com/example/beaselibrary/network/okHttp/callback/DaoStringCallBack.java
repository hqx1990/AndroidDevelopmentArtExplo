package com.example.beaselibrary.network.okHttp.callback;


import com.alibaba.fastjson.JSON;
import com.example.beaselibrary.R;
import com.example.beaselibrary.base.BaseApplication;
import com.example.beaselibrary.network.bean.ResultBean;
import com.example.beaselibrary.network.okHttp.NetResultCallBack;
import com.example.beaselibrary.network.okHttp.utils.JsonUtils;
import com.example.beaselibrary.network.token.RequestParamsForToken;
import com.example.beaselibrary.network.token.TokenHelper;
import com.example.beaselibrary.util.CheckUtil;
import com.example.beaselibrary.util.Logger;

import okhttp3.Call;


/**
 * 项目名称：HFAndroid
 * 项目作者：hqx
 * 创建日期：2017/6/3 14:51.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public class DaoStringCallBack extends StringCallback {
    public static final String SUCCESS = "00000";
    public static final String RETMSG = "retMsg";
    public static final String BODY = "json";
    public NetResultCallBack callBack;

    public DaoStringCallBack(NetResultCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onError(Call call, Exception e, String flag, int responseCode) {
        RequestParamsForToken rpft = TokenHelper.getInstance().requestMap.remove(flag);//token bean
        Logger.e("网络错误", e.toString());
        try {
            if (e.toString().contains("Canceled") || e.toString().contains("Socket closed")) {
                if (rpft != null)
                    rpft.release();//清除token之前数据
                return;
            }
            else if (TokenHelper.getInstance().isTokenOutOfDate
                    (e.getMessage()) && !flag.startsWith(TokenHelper.TOKEN_FLAG)) {
                TokenHelper.getInstance().requestMap.put(flag, rpft);
                TokenHelper.getInstance().getToken(flag, null);
                Logger.e("重新请求token", "<----------------------------Token重新请求------------------------------>");
                return;
            }
            if (null!=callBack){
//                try {
//                    if(NetWorkUtils.isNetWorkAvailable(BaseApplication.getInstance())){
//                        CrashReport.postCatchedException(new Exception("noConnect无网络链接标识："+flag+"状态码："+responseCode+"错误日志："+e.toString()));//上报日志，追踪原因
//                    }
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
                callBack.onErr(BaseApplication.getInstance().getResources().getString(R.string.noConnectionCode),
                        BaseApplication.getInstance().getResources().getString(R.string.noConnection).concat(getShowCode(responseCode)),"", flag);
            }
        } catch (Exception ex) {
            e.printStackTrace();
        }
    }

    /**
     * 获取拼接显示的code。仅在responseCode取值【300,599】区间，且非生产环境配置时，返回拼接code
     *
     * @param responseCode 传入的resonse code
     * @return 返回“（404）”格式，或者返回空
     */
    private String getShowCode(int responseCode) {
        String code = "";
        if (responseCode >= 300 && responseCode <= 599) {
            code = String.format("(%s)", responseCode);
        }
        return code;
    }

    @Override
    public void onResponse(String response, String flag, int responseCode) {
        Logger.e(flag, "服务器返回：" + response);
        RequestParamsForToken rpft = TokenHelper.getInstance().requestMap.remove(flag);
        if (rpft != null)
            rpft.release();
        if (response == null) {
            if (callBack != null)
                callBack.onErr(SUCCESS,
                        BaseApplication.getInstance().getResources()
                                .getString(R.string.noResponse)
                                .concat(getShowCode(responseCode)),"", flag);
            return;
        }
        ResultBean bean = null;
        String responseFront;
        if (15 < response.length()) {
            responseFront = response.substring(0, 15);
            if (responseFront.contains("\"response\"")) {
                response = response.substring(12, response.length() - 1);
            }
        }
        try {
            bean = JsonUtils.json2Class(response, ResultBean.class);
        } catch (Exception e) {
            if (callBack != null)
                callBack.onErr(e.getMessage(),
                        BaseApplication.getInstance().getResources()
                                .getString(R.string.data_exception)
                                .concat(getShowCode(responseCode)),"", flag);
            return;
        }

        if (bean == null) {
            if (callBack != null)
                callBack.onErr(SUCCESS,
                        BaseApplication.getInstance().getResources()
                                .getString(R.string.noResponse)
                                .concat(getShowCode(responseCode)),"", flag);
            return;
        }

        if (bean.head == null) {
            if (flag != null && flag.startsWith(TokenHelper.TOKEN_FLAG)) {
                //token重新请求
                if (callBack != null) {
                    callBack.onSuccess(response, flag);
                }
            } else {
                if (callBack != null)
                    callBack.onErr(SUCCESS,
                            BaseApplication.getInstance().getResources()
                                    .getString(R.string.data_exception)
                                    .concat(getShowCode(responseCode)),"", flag);
            }
        } else {
            if (SUCCESS.equals(bean.head.retFlag)) {
                Object obj = bean.body;
                if (obj == null) {
                    if (response.contains("body")) {
                        if (callBack != null)
                            callBack.onErr(SUCCESS,
                                    BaseApplication.getInstance().getResources()
                                            .getString(R.string.data_exception)
                                            .concat(getShowCode(responseCode)),"", flag);

                    } else {
                        if (callBack != null)
                            try {
                                callBack.onSuccess(response, flag);
                            } catch (Exception e) {
                                Logger.e(flag, e.getMessage());
                                callBack.onErr(e.getMessage(),
                                        BaseApplication.getInstance().getResources()
                                                .getString(R.string.data_exception)
                                                .concat(getShowCode(responseCode)),"", flag);
                            }
                    }
                } else {
                    String json;
                    try {
                        json = JSON.toJSON(bean.body).toString();
                    } catch (Exception e) {
                        if (callBack != null)
                            callBack.onErr(e.getMessage(),
                                    BaseApplication.getInstance().getResources()
                                            .getString(R.string.data_exception).concat(getShowCode(responseCode)),"", flag);
                        return;
                    }
                    if (!CheckUtil.getInstance().isEmpty(json)) {
                        if (callBack != null) {
                            try {
                                callBack.onSuccess(json, flag);
                            } catch (Exception e) {
                                Logger.e(flag, e.getMessage());
                                callBack.onErr(e.getMessage(),
                                        BaseApplication.getInstance().getResources()
                                                .getString(R.string.data_exception)
                                                .concat(getShowCode(responseCode)),"", flag);
                            }
                        }
                    } else {
                        if (callBack != null)
                            callBack.onErr(SUCCESS,
                                    BaseApplication.getInstance().getResources()
                                            .getString(R.string.data_exception).concat(getShowCode(responseCode)),"", flag);
                    }
                }
            } else {
                if (callBack != null) {
                    bean.head.retMsg = bean.head.retMsg == null ? "" : bean.head.retMsg;
                    if (bean.body != null) {
                        String json = JSON.toJSON(bean.body).toString();
                        //增加过滤空body情况
                        if (!CheckUtil.getInstance().isEmpty(json) && !"\"\"".equals(json)) {
                            //目前仅有存在返回体的情况下retBody返回
                            callBack.onErr(bean.head.retFlag, bean.head.retMsg, json, flag);
                        } else {
                            callBack.onErr(bean.head.retFlag, bean.head.retMsg,"", flag);
                        }
                    } else {
                        callBack.onErr(bean.head.retFlag, bean.head.retMsg,"", flag);
                    }
                }
            }
        }
    }
}
