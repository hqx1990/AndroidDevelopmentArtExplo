package com.bestvike.androiddevelopmentartexploration.liveData;

import android.arch.lifecycle.Observer;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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
        LocationLiveData.get().observe(this,new MyObserver());
    }

    private class MyObserver implements  Observer<String>{
        @Override
        public void onChanged(@Nullable String s) {
            Log.e("LiveData","测试："+s);
            Toast.makeText(getApplicationContext(),String.valueOf(s),Toast.LENGTH_SHORT).show();
        }
    }
}
