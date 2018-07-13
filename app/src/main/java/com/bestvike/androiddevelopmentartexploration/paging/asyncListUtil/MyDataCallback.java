package com.bestvike.androiddevelopmentartexploration.paging.asyncListUtil;

import android.os.SystemClock;
import android.support.v7.util.AsyncListUtil;
import android.util.Log;

public class MyDataCallback extends AsyncListUtil.DataCallback<String>  {
    private String TAG = "调试";
    @Override
    public int refreshData() {
        //更新数据的元素个数。
        //假设预先设定更新若干条。
        int count = Integer.MAX_VALUE;
        Log.d(TAG, "refreshData:" + count);
        return count;
    }

    /**
     * 在这里完成数据加载的耗时任务。
     *
     * @param data
     * @param startPosition
     * @param itemCount
     */
    @Override
    public void fillData(String[] data, int startPosition, int itemCount) {
        Log.d(TAG, "fillData:" + startPosition + "," + itemCount);
        for (int i = 0; i < itemCount; i++) {
            data[i] = String.valueOf(System.currentTimeMillis());

            //模拟耗时任务，故意休眠一定时延。
            SystemClock.sleep(100);
        }
    }
}
