package com.bestvike.androiddevelopmentartexploration.paging.asyncListUtil;

import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MyViewCallback extends AsyncListUtil.ViewCallback  {
    private String TAG = "调试";
    private final int NULL = -1;
    private RecyclerView mRecyclerView;

    protected MyViewCallback(RecyclerView mRecyclerView){
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public void getItemRangeInto(int[] outRange) {
        getOutRange(outRange);

        /**
         * 如果当前的RecyclerView为空，主动为用户加载数据.
         * 假设预先加载若干条数据
         *
         */
        if (outRange[0] == NULL && outRange[1] == NULL) {
            Log.d(TAG, "当前RecyclerView为空！");
            outRange[0] = 0;
            outRange[1] = 9;
        }

        Log.d(TAG, "getItemRangeInto,当前可见position: " + outRange[0] + " ~ " + outRange[1]);

    }

    @Override
    public void onDataRefresh() {
        int[] outRange = new int[2];
        getOutRange(outRange);
        mRecyclerView.getAdapter().notifyItemRangeChanged(outRange[0], outRange[1] - outRange[0] + 1);

        Log.d(TAG, "onDataRefresh:"+outRange[0]+","+outRange[1]);
    }

    @Override
    public void onItemLoaded(int position) {
        mRecyclerView.getAdapter().notifyItemChanged(position);
        Log.d(TAG, "onItemLoaded:" + position);
    }
    private void    getOutRange(int[] outRange){
        outRange[0] = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outRange[1] = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
    }
}
