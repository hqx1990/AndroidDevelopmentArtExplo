package com.bestvike.androiddevelopmentartexploration.liveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;
import android.util.Log;

public class LocationLiveData extends LiveData<String> {

    private static LocationLiveData sInstance;

    private final String TAG = "LiveData";
    private int cont = 0;
    private boolean RUN = true;

    private LongTimeWork mThread = new LongTimeWork();

    @MainThread
    public static LocationLiveData get() {
        if (sInstance == null) {
            sInstance = new LocationLiveData();
        }
        return sInstance;
    }


    private LocationLiveData() {
        mThread.start();
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.e(TAG,"测试：回到前台");
        RUN = true;
        mThread.interrupt();
    }

    @Override
    protected void onInactive() {
        Log.e(TAG,"测试：去后台");
        RUN = false;
    }

    private class LongTimeWork extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    if(!RUN){
                        Thread.sleep(Long.MAX_VALUE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cont++;
                postValue(String.valueOf(cont));

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
