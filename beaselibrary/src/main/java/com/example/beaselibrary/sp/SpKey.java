package com.example.beaselibrary.sp;

/**
 * Created by zhangjie on 2017/6/5.
 * 定义一个接口，用来存放存取SP时的key值
 * 外部使用时，直接 SpKey. 即可
 * ex：SpKey.LOGIN
 */

public interface SpKey {
    /**
     * 登录相关信息
     */
    String LOGIN = "LOGIN";
    String LOGIN_CLIENTSERCERT = "clientSecret";//请求token用
    String LOGIN_ACCESSTOKEN = "accessToken";//获取到的token值

    /**
     * 新手引导相关
     */
    String NEW_GUID = "news_guid";
    String LOGIN_IF_ONCE = "login_if_once1";

    /**
     * 实名相关信息，启动页会清除该sp文件
     */
    String USER = "USER";

    /**
     * 信息状态标识（是否登录，实名，有无支付密码等）等信息，启动页会清除该sp文件
     */
    String STATE = "STATE";

    /**
     * 流程标识相关信息，流程结束后一定clean该文件下的所有标识，启动页也会清除该sp文件，MainActivity的onStart中也会清除
     * 包括流程中可能需要保存的信息，比如流水号等
     */
    String FLAG = "FLAG";


    /**
     * 系统配置项（默认参数），启动页会清除该sp文件
     */
    String CONFIGURE = "CONFIGURE";
    /**
     * 其他未分类信息
     */
    String OTHER = "OTHER";

    /**
     * 是否是第一次登陆
     */
    String ISFIRST = "isfirst";//首次
    String ISFIRST_VERSION = "version";//根据版本号判断需不需要展示引导页
    String ISFIRST_ED_HINT = "edHint";//是否隐藏首页额度展示（Y：隐藏）
    String ISFIRST_FUND_HINT = "fundHint";//是否隐藏公积金/社保账户余额（Y：隐藏）
}
