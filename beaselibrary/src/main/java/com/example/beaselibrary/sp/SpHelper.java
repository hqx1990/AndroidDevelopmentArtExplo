package com.example.beaselibrary.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.beaselibrary.base.BaseApplication;
import com.example.beaselibrary.util.CheckUtil;
import com.example.beaselibrary.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjie on 2017/6/5.
 * SP存值帮助类，统一管理sp存值
 * 这个类中只有存取String数据的一些公共操作方法，需要存取其他类型的可以通过方法获取实例，然后进行存取
 * 存取时为保证存值与取值key一致，相对应的key值存在接口文件 SpKey中
 * 需要新增值类型存进sp中时，key值也要写到 SpKey中，方便管理与查阅
 */

public class SpHelper {
    /**
     * 获取sp实例方法，需要传入sp的key值，不再作为公共方法
     */
    private SharedPreferences getSpInstance(String spKey){
        SharedPreferences sharedPreferences = null;
        try{
            sharedPreferences = BaseApplication.getInstance().getSharedPreferences(spKey, Context.MODE_PRIVATE);
        }catch (Exception e){
            Logger.e("-----",""+e.toString());
        }
        return sharedPreferences;
    }

    /**
     * 获取editor实例的方法，需要传入sp的key值，不再作为公共方法
     */
    private SharedPreferences.Editor getSpEditorInstance(String spKey){
        return getSpInstance(spKey).edit();
    }

    /**
     * 批量保存Sp的公共方法
     * @param map
     * @param spKey
     * 该方法仅支持批量保存String类型的数据
     */
    public void saveMsgToSp(Map<String,String> map , String spKey){
        if(!map.isEmpty()){
            SharedPreferences.Editor editor = getSpEditorInstance(spKey);
            for(Map.Entry<String,String> entry : map.entrySet()){
                String value = entry.getValue();
                editor.putString(entry.getKey(),value);
            }
            editor.commit();
        }
    }

    /**
     * 保存sp信息的公共方法
     * spKey：sp的key值
     * valueKey：键值对的key值
     */
    public void saveMsgToSp(String spKey , String valueKey , Object value){
        SharedPreferences.Editor editor = getSpEditorInstance(spKey);
        if(null != value){
            if(value instanceof String){
                //String类型数据，加密
                String str = value.toString();
                editor.putString(valueKey,str).commit();
            } else if(value instanceof Integer){
                //int类型
                editor.putInt(valueKey, (Integer) value).commit();
            } else if(value instanceof Boolean){
                //布尔类型
                editor.putBoolean(valueKey, (Boolean) value).commit();
            } else if(value instanceof Float){
                //double类型
                editor.putFloat(valueKey, (Float) value).commit();
            } else if(value instanceof Long){
                //Long类型
                editor.putLong(valueKey, (Long) value).commit();
            } else {
                //默认String类型数据，加密
                String str = value.toString();
                editor.putString(valueKey,str).commit();
            }
        } else {
            editor.putString(valueKey,"").commit();
        }
    }

    /**
     * 读取sp信息的公共方法
     * spKey：sp的key值
     * valueKey：键值对的key值
     * 该方法只能读取String类型的数据
     */
    public String readMsgFromSp(String spKey , String valueKey){
        String value = getSpInstance(spKey).getString(valueKey,"");
        return CheckUtil.getInstance().deletePointZero(value);
    }

    /**
     * 读取sp信息的公共方法
     * spKey：sp的key值
     * valueKey：键值对的key值
     * 该方法可以读取多种类型的数据，返回值为Object类型
     *
     * 该方法取值可能会造成空指针异常 不建议使用
     */
    public Object readAllTypFromSp(String spKey , String valueKey , Object defaultObject){
        if(defaultObject instanceof String){
            return getSpInstance(spKey).getString(valueKey, (String) defaultObject);
        } else if(defaultObject instanceof Boolean){
            return getSpInstance(spKey).getBoolean(valueKey, (Boolean) defaultObject);
        } else if(defaultObject instanceof Integer){
            return getSpInstance(spKey).getInt(valueKey, (Integer) defaultObject);
        } else if(defaultObject instanceof Long){
            return getSpInstance(spKey).getLong(valueKey, (Long) defaultObject);
        } else if(defaultObject instanceof Float){
            return getSpInstance(spKey).getFloat(valueKey, (Float) defaultObject);
        }
        return null;
    }

    /**
     * 读取sp信息的公共方法
     * @param spKey  sp的key值
     * @param valueKey  键值对的key值
     * @return
     */
    public boolean readBooleanFromSp(String spKey , String valueKey){
        boolean value = getSpInstance(spKey).getBoolean(valueKey,false);

        return value;
    }

    /**
     * 删除某个sp文件下的所有值的公共方法
     * spKey：sp的key值
     */
    public void deleteAllMsgFromSp(String spKey){
        SharedPreferences.Editor editor = getSpEditorInstance(spKey);
        editor.clear().commit();
    }

    /**
     * 删除sp中某一条信息的公共方法
     * spKey：sp的key值
     * valueKey：键值对的key值
     */
    public void deleteMsgFromSp(String spKey , String valueKey){
        SharedPreferences.Editor editor = getSpEditorInstance(spKey);
        editor.remove(valueKey).commit();
    }

    /**
     * 需要删除多个sp中的数据时使用
     * 将初始化sp的key值封装成list传进来即可
     * @param list
     */
    public void deleteAllMsgFromSpList(List<String> list){
        for(String str : list){
            deleteAllMsgFromSp(str);
        }
    }

    /**
     * 清空所有sp信息
     * 用户输入的登录名不清空
     * 调用情况：
     * 1、点击 退出登录 时调用，清空缓存
     * 2、登录页面点击 登录 时调用，防止用户切换账号后取到老数据
     * 其他情况要谨慎调用，需要的话要一起磋商，防止产生丢值问题
     */
    public void clearSp(){
        List<String> list = new ArrayList<>();
        list.add(SpKey.LOGIN);
        list.add(SpKey.USER);
        list.add(SpKey.STATE);
        list.add(SpKey.FLAG);
        list.add(SpKey.CONFIGURE);
        list.add(SpKey.OTHER);
        deleteAllMsgFromSpList(list);
    }

    //单例
    private static final class SingleTonHolder{
        private static final SpHelper instance = new SpHelper();
    }
    public static SpHelper getInstance(){
        return SingleTonHolder.instance;
    }
    private SpHelper(){}
}
