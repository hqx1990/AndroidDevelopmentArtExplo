package com.bestvike.androiddevelopmentartexploration.xg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.lang.ref.WeakReference;

public class MyXGActivity extends BaseActivity {

    private TextView textView;
    private Message m;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myxgactivity);

        findView();
        getData();



    }

    @Override
    protected void destroyPresenter() {

    }

    private void getData(){
        //清除通知栏消息
        //XGPushManager.cancelAllNotifaction();

        //代码内动态注册access ID
        //XGPushConfig.setAccessId(this,2100250470);

        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(this, true);
        XGPushConfig.getToken(this);

        Handler handler = new HandlerExtension(this);
        m = handler.obtainMessage();

        registerPush();
        //设置账号
        //注意在3.2.2 版本信鸽对账号绑定和解绑接口进行了升级具体详情请参考API文档。
        XGPushManager.bindAccount(getApplicationContext(), "18562552931");

        XGPushManager.setTag(this,"18562552931");//设置标签

    }

    private void findView(){
        textView = findViewById(R.id.textView);
    }

    private static class HandlerExtension extends Handler {
        WeakReference<MyXGActivity> mActivity;

        HandlerExtension(MyXGActivity activity) {
            mActivity = new WeakReference<MyXGActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyXGActivity theActivity = mActivity.get();
            if (theActivity == null) {
                theActivity = new MyXGActivity();
            }
            if (msg != null) {
                Log.d("TPush", msg.obj.toString());
                TextView textView = (TextView) theActivity
                        .findViewById(R.id.textView);
                textView.setText(XGPushConfig.getToken(theActivity));
            }
            // XGPushManager.registerCustomNotification(theActivity,
            // "BACKSTREET", "BOYS", System.currentTimeMillis() + 5000, 0);
        }
    }

    /**
     * 注册信鸽
     */
    private void registerPush(){
          /*
        注册信鸽服务的接口
        如果仅仅需要发推送消息调用这段代码即可
        */
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {

                        Log.e("TPush", "注册成功，设备token为：" + data);

                        m.obj = "+++ register push sucess. token:" + data;
                        m.sendToTarget();
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {

                        Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);

                        m.obj = "+++ register push fail. token:" + data
                                + ", errCode:" + errCode + ",msg:" + msg;
                        m.sendToTarget();
                    }
                });
    }

}
