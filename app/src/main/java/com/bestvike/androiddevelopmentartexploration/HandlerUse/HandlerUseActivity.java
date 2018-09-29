package com.bestvike.androiddevelopmentartexploration.HandlerUse;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class HandlerUseActivity extends BaseActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private int num1  = 60;
    private boolean isBoolean = true;

    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_use_activity);
        findView();
    }

    private void findView(){
        findViewById(R.id.button1).setOnClickListener(this);
        textView1 = findViewById(R.id.textView1);
        findViewById(R.id.button2).setOnClickListener(this);
        textView2 = findViewById(R.id.textView2);

        findViewById(R.id.button3).setOnClickListener(this);
        textView3 = findViewById(R.id.textView3);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                Message message = new Message();
                message.what =1;
                num1 -- ;
                message.arg1 = num1;
                handler.sendMessage(message);
                break;

            case R.id.button2:
                num1 = 60;
                //点击 1秒后调用
                handler.obtainMessage();
                handler.sendEmptyMessage(2);
                break;

            case R.id.button3:
                num1 = 60;
                myHandler.post(runnable);
                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    textView1.setText("方法1："+String.valueOf(msg.arg1));
                    break;

                case 2:
                    if (msg.what == 2){
                        num1 --;
                        if(num1 >= 0){
                            handler.sendEmptyMessageDelayed(2,1000);
                            textView2.setText("方法2："+num1+"s");
                        }
                    }
                    break;

            }
            super.handleMessage(msg);
        }
    };

    private Handler myHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            num1 -- ;
            if(num1 >= 0){
                myHandler.postDelayed(runnable,1000);
                textView3.setText("方法3："+num1);
            }
        }
    };
}
