package com.example.beaselibrary.network.token;


import android.Manifest;

import com.example.beaselibrary.base.BaseUrl;
import com.example.beaselibrary.network.okHttp.HttpDAO;
import com.example.beaselibrary.network.okHttp.utils.JsonUtils;
import com.example.beaselibrary.network.okHttp.NetResultCallBack;
import com.example.beaselibrary.network.okHttp.callback.DaoStringCallBack;
import com.example.beaselibrary.permissions.AccessPermissions;
import com.example.beaselibrary.permissions.AccessPermissionsInterface;
import com.example.beaselibrary.sp.SpHelper;
import com.example.beaselibrary.sp.SpKey;
import com.example.beaselibrary.util.CheckUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：ysApprove
 * 项目作者：hqx
 * 创建日期：2017/5/8 9:47.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：token失效后重新获取token的帮助类
 * ----------------------------------------------------------------------------------------------------
 */
public class TokenHelper implements NetResultCallBack {

    public static final String TOKEN_FLAG = "request_token_flag#";
    private String flag = "";
    public final Map<String, RequestParamsForToken> requestMap = new HashMap<>();

    /**
     * 获取token
     *
     * 若refresh为空，则是请求token;否则刷新token
     *
     * 若callBack不为null，则请求token成功后不需要继续请求，而是回调给客户端
     */
    public void getToken(String flag, NetResultCallBack callBack){
        this.flag = TOKEN_FLAG + flag;

        // 请求token
        final HttpDAO dao;
        if(callBack == null){
            dao = new HttpDAO(this);
        }else{
            dao = new HttpDAO(callBack);
        }

        AccessPermissions.getInstance().isPermissions(new AccessPermissionsInterface() {
            @Override
            public void authorityToJudge(boolean isPermissions, List<String> data) {
                String flag = TokenHelper.this.flag;
                if(isPermissions){
                    //有权限，继续请求
                    String deviceId = "0000000000";
                    if(CheckUtil.getInstance().isEmpty(deviceId)){
                        deviceId = "0000000000";
                    }
                    Map<String, String> map = new HashMap<>();
                    map.put("client_secret", "入参2");
                    map.put("grant_type", "client_credentials");
                    map.put("client_id", "入参3");
                    dao.get(BaseUrl.baseUrl, map, flag,false);

                }else{
                    //没有权限，直接回调onerr提示
                    flag = flag.substring(flag.indexOf("#") + 1);
                    RequestParamsForToken requestParams = requestMap.remove(flag);
                    try{
                        if(!(requestParams.callBack.callBack instanceof TokenHelper)){
                            requestParams.callBack.callBack.onErr("token_deviceId err", "设备号获取失败，请打开读取手机权限并重试",
                                    "", flag);
                        }
                        requestParams.release();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        },Manifest.permission.READ_PHONE_STATE);
    }

    public boolean isTokenOutOfDate(String response){
        return !CheckUtil.getInstance().isEmpty(response) && response.contains("invalid_token");
    }

    @Override
    public void onSuccess(Object response, String flag) {
        Token bean = JsonUtils.json2Class((String) response, Token.class);
        SpHelper.getInstance().saveMsgToSp(SpKey.LOGIN,SpKey.LOGIN_ACCESSTOKEN,bean.access_token);
        flag = flag.substring(flag.indexOf("#") + 1);
        RequestParamsForToken requestParams = requestMap.get(flag);
        if(requestParams == null) return;
        HttpDAO dao = new HttpDAO(requestParams.callBack.callBack);
        if("POST".equals(requestParams.method)){
            dao.post(requestParams.url, requestParams.params, flag);
        }else if("GET".equals(requestParams.method)){
            dao.get(requestParams.url, (HashMap)requestParams.params, flag);
        }else if("PUT".equals(requestParams.method)){
            dao.put(requestParams.url, requestParams.params, flag);
        }
        requestParams.release();
    }

    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {
        flag = flag.substring(flag.indexOf("#") + 1);
        RequestParamsForToken requestParams = requestMap.remove(flag);
        try{
            if(!(requestParams.callBack.callBack instanceof TokenHelper)){
                requestParams.callBack.callBack.onErr(retFlag, response, retBody, flag);
            }
            requestParams.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private TokenHelper(){}
    private static final class SingleTonHelper{
        private static final TokenHelper instance = new TokenHelper();
    }
    public static TokenHelper getInstance(){
        return SingleTonHelper.instance;
    }

    /** 保存请求参数。用于重新发起请求 */
    public void saveRequestParams(String url, Object params, String flag, String method, DaoStringCallBack callBack){
        if(!flag.startsWith(TokenHelper.TOKEN_FLAG)){
            RequestParamsForToken rpft = new RequestParamsForToken();
            rpft.url = url;
            rpft.params = params;
            rpft.flag = flag;
            rpft.method = method;
            rpft.callBack = callBack;
            TokenHelper.getInstance().requestMap.put(flag, rpft);
        }
    }
}
