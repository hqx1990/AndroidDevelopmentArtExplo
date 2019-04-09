package com.example.beaselibrary.base;


import android.os.Handler;
import java.util.HashSet;

/**
 * 设置锁屏机制
 * hqx
 */
public class LockScreen {

    private long lastUpdatedTime;//最近一次操作时间
    private long timeoutInMs = 1000 * 60 * 5;//限制时间差，5分钟锁屏
    private boolean checkingStopped = false;//停止检查标识
    private Handler handler = new Handler();

    /**
     * 检查是否超时
     */
    private void check(){
        //时间差值
        long actualTimeout = System.currentTimeMillis() - lastUpdatedTime;
        //差值大于时间限制时向外回调
        if(actualTimeout >= timeoutInMs){
            stopChecking();
            notifyAllListeners();
        }
    }

    /**
     * 计时
     */
    private Runnable checkTask = new Runnable() {
        @Override
        public void run() {
            //停止检查标识
            if(checkingStopped){
                return;
            }
            //检查是否超时
            check();
            //每隔一秒检查一次
            handler.postDelayed(this, 1000);
        }
    };


    /*******************************************向外回调************************************************/

    private HashSet<HDTouchEvtTimeoutListener> listeners = null;//维护的本地队列

    public interface HDTouchEvtTimeoutListener{
        void touchTimeoutReached(Object tag, long timeoutInMs);
    }

    private void notifyAllListeners(){
        //防止有listener处理回调的时候执行unregister动作造成迭代器失效
        HashSet<HDTouchEvtTimeoutListener> cloneListeners = (HashSet<HDTouchEvtTimeoutListener>) listeners.clone();

        for(HDTouchEvtTimeoutListener listener : cloneListeners){
            listener.touchTimeoutReached("lockScreen", timeoutInMs);
        }
    }

    /**********************************提供注册方法*****************************************************/
    public synchronized void registerListener(HDTouchEvtTimeoutListener listener){
        if(null == listeners){
            listeners = new HashSet<>();
        }
        if(listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
        startChecking();
        onUserAction();
        check(); //保证时效性
    }


    /**
     * 开始计时
     */
    private void startChecking(){
        checkingStopped = false;
        handler.postDelayed(checkTask, 1000);
    }

    /**
     * 停止计时
     */
    public void stopChecking(){
        checkingStopped = true;
        handler.removeCallbacks(checkTask);
    }

    /**
     * 更新最近操作时间
     */
    public synchronized void onUserAction(){
        lastUpdatedTime = System.currentTimeMillis();
    }
}
