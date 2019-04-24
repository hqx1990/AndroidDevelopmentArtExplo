package com.example.beaselibrary.sp;


import com.example.beaselibrary.base.BaseApplication;

/**
 * 项目名称：sharepreferance
 * 项目作者：hqx
 * 创建日期：2019/4/24
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public class SpUtil {
    //保存登录返回的信息
    private static IPreference iBankCardPreference;
    public static IPreference getBankCardInstance(){
        if (null == iBankCardPreference) {
            iBankCardPreference = IPreference.prefHolder.getPreference(BaseApplication.getInstance(), SpKey.LOGIN);
        }
        return iBankCardPreference;
    }


}