package com.bestvike.androiddevelopmentartexploration.liveData;

import android.arch.lifecycle.Observer;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;

/**
 * myxgactivity
 */
public class LiveDataActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myxgactivity);
        LocationLiveData.get(this).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                Log.e("liveData","测试"+location.toString());
            }
        });
    }
}
