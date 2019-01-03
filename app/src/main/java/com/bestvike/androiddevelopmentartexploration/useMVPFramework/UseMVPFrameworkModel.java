package com.bestvike.androiddevelopmentartexploration.useMVPFramework;

import com.example.beaselibrary.base.BaseModel;
import com.example.beaselibrary.base.BaseUrl;
import com.example.beaselibrary.network.okHttp.HttpDAO;
import com.example.beaselibrary.network.okHttp.NetResultCallBack;
import com.example.beaselibrary.network.okHttp.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class UseMVPFrameworkModel extends BaseModel {
    public UseMVPFrameworkModel(NetResultCallBack callBack) {
        super(callBack);
    }

    private HttpDAO httpDAO;
    public void get(){
        if(null == httpDAO){
            httpDAO = new HttpDAO(callBack());
        }
        HashMap<String,String> map = new HashMap<>();

        httpDAO.get(BaseUrl.URL_GETDICT,map,BaseUrl.URL_GETDICT);
    }

    @Override
    public Object parseResponse(String response) {
        GetDictBeanRtn getDictBeanRtn = JsonUtils.json2Class(response,GetDictBeanRtn.class);
        return getDictBeanRtn;
    }
}
