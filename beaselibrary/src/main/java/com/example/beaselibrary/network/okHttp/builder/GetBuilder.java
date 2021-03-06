package com.example.beaselibrary.network.okHttp.builder;


import com.example.beaselibrary.network.okHttp.request.GetRequest;
import com.example.beaselibrary.network.okHttp.request.RequestCall;
import com.example.beaselibrary.util.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public class GetBuilder extends OkHttpRequestBuilder
{
    @Override
    public RequestCall build()
    {
        if (params != null)
        {
            url = appendParams(url, params);
        }
        Logger.e("请求报文体", url+"");
        return new GetRequest(url, tag, params, headers).build();
    }
    public RequestCall build(boolean isRetry)
    {
        if (params != null)
        {
            url = appendParams(url, params);
        }
        Logger.e("请求报文体", url+"");
        if(!isRetry) {
            return new GetRequest(url, tag, params, headers).build(isRetry);
        }
        return new GetRequest(url, tag, params, headers).build();
    }

    private String appendParams(String url, Map<String, String> params){
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty())
        {
            for (String key : params.keySet())
            {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public GetBuilder url(String url)
    {
        this.url = url;
        return this;
    }

    @Override
    public GetBuilder tag(Object tag)
    {
        this.tag = tag ;
        return this;
    }

    @Override
    public GetBuilder params(Map<String, String> params)
    {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val)
    {
        if (this.params == null)
        {
            params = new LinkedHashMap<String, String>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public GetBuilder headers(Map<String, String> headers)
    {
        this.headers = headers;
        return this;
    }

    @Override
    public GetBuilder addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<String, String>();
        }
        headers.put(key, val);
        return this;
    }
}
