package com.bestvike.androiddevelopmentartexploration.paging.asyncListUtil;

import android.os.SystemClock;
import android.support.v7.util.AsyncListUtil;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDataCallback extends AsyncListUtil.DataCallback<User>  {
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
    public void fillData(User[] data, int startPosition, int itemCount) {
        Log.d(TAG, "fillData:" + startPosition + "," + itemCount);
        List<User> list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            User user = new User();
            user.setName("名字："+i);
            user.setPhone("电话："+133956677+i);
            list.add(user);
        }


        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);

            //模拟耗时任务，故意休眠一定时延。
            SystemClock.sleep(100);
        }
    }
}
