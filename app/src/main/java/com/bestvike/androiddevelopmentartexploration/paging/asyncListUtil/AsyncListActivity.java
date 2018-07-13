package com.bestvike.androiddevelopmentartexploration.paging.asyncListUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;


/**
 * 基于Android官方AsyncListUtil优化改进RecyclerView分页加载机制
 */
public class AsyncListActivity extends BaseActivity {

    private String TAG = "调试";
    private RecyclerView mRecyclerView;
    private AsyncListUtil mAsyncListUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynclist_activity);
        findView();
    }

    private void findView(){
        mRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyDataCallback mDataCallback = new MyDataCallback();
        MyViewCallback mViewCallback = new MyViewCallback(mRecyclerView);
        mAsyncListUtil = new AsyncListUtil(String.class, 20, mDataCallback, mViewCallback);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Log.d(TAG, "onRangeChanged");
                mAsyncListUtil.onRangeChanged();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "refresh");
                mAsyncListUtil.refresh();
            }
        });

        RecyclerView.Adapter mAdapter = new MyAdapter(mAsyncListUtil);
        mRecyclerView.setAdapter(mAdapter);
    }
}
