package com.example.beaselibrary.network.token;

/**
 * 项目名称：HFAndroid
 * 项目作者：hqx
 * 创建日期：2017/6/5 9:33.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */

public class Token {

    public String access_token;
    public String refresh_token;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
