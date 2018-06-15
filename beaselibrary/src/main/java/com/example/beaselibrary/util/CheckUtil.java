package com.example.beaselibrary.util;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.List;

public class CheckUtil {
    private static final class SingleTonHolder{
        private static final CheckUtil instance = new CheckUtil();
    }

    public static CheckUtil getInstance(){
        return SingleTonHolder.instance;
    }

    private CheckUtil(){}

    /* 检查是否为空 */
    public boolean isEmpty(String value)
    {
        return value == null || value.trim().length() == 0 || "".equals(value.trim()) || "null".equals(value.trim());
    }

    public boolean isEmpty(List list)
    {
        return list == null || list.size() <= 0;
    }

    /**
     * 获取屏幕的宽度
     */
    public int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        return dm.widthPixels;
    }

}
