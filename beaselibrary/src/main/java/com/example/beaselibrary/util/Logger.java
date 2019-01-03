package com.example.beaselibrary.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Logger
{
    public static boolean isShowLog =  true;
    public static void e(String tag, String msg)
    {
        if (!CheckUtil.getInstance().isEmpty(tag) && !CheckUtil.getInstance().isEmpty(msg))
        {
            if(isShowLog){
                Log.e(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg)
    {
        if(isShowLog){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg)
    {
        if(isShowLog){
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg)
    {
        if(isShowLog){
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg)
    {
        if(isShowLog){
            Log.w(tag, msg);
        }
    }
}
