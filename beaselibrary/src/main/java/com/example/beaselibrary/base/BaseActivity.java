package com.example.beaselibrary.base;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beaselibrary.interfaces.DialogListener;
import com.example.beaselibrary.network.okHttp.NetResultCallBack;
import com.example.beaselibrary.util.ShowDialog;
import com.example.beaselibrary.util.taskbar.FitStateUI;
import com.example.beaselibrary.util.taskbar.OSUtils;


/**
 * Created by Administrator on 2018/3/29.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,NetResultCallBack {

    private LockScreen lockScreen;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BaseApplication.getInstance().currentActivity=this;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        BaseApplication.getInstance().currentActivity=this;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        BaseApplication.getInstance().currentActivity=this;
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BaseApplication.getInstance().currentActivity=this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BaseApplication.getInstance().currentActivity=this;
    }
    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.getInstance().currentActivity=this;
        if(null != lockScreen){
            lockScreen.registerListener(new LockScreen.HDTouchEvtTimeoutListener() {
                @Override
                public void touchTimeoutReached(Object tag, long timeoutInMs) {
                    //屏幕已锁定
                    showDialog("屏幕已锁定，请解锁");
                }
            });
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.FLYME)
            FitStateUI.setMeizuStatusBarDarkIcon(this, true);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.EMUI)
            FitStateUI.setImmersionStateMode(this);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.MIUI)
            FitStateUI.setStatusBarFontIconDark(true, this);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.OTHER)
            FitStateUI.setImmersionStateMode(this);
        FitStateUI.initStatusBar(this);

        BaseApplication.getActManger().pushActivity(this);//将activity加入统一管理类
        BaseApplication.getInstance().currentActivity = this;
        lockScreen = new LockScreen();
    }

    @Override
    protected void onDestroy() {
        BaseApplication.getActManger().popActivity(this);//将activity移出栈
        BaseApplication.getInstance().currentActivity = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    private static Toast mToast;
    public void showToast(String str){
        if (mToast == null)
        {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 用于销毁presenter
     */
    protected abstract void destroyPresenter();

    /**
     * 只有一个按钮的提示框
     * @param str
     */
    public void showDialog(String str){
        ShowDialog.getInstance().showDialog(this, str, true, new DialogListener() {
            @Override
            public void dialogCallBack(final AlertDialog dialog, TextView left, TextView right) {
                left.setVisibility(View.GONE);
                right.setText("知道了");
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 含有两个按钮的提示框
     * @param str
     * @param listener
     */
    public void showDialog(String str , DialogListener listener){
        ShowDialog.getInstance().showDialog(this,str,true,listener);
    }

    /**
     * 两个按钮的提示
     * @param str
     * @param isTitle  title 是否展示 。f 不含有title , t 含有title
     * @param listener
     */
    public void showDialog(String str , boolean isTitle , DialogListener listener){
        ShowDialog.getInstance().showDialog(this,str,isTitle,listener);
    }
    /**
     * 菊花
     */
    public void showProgress(boolean flag){
        ShowDialog.getInstance().showProgress(flag,"",this);
    }

    /**
     * 含有提示语的菊花
     * @param flag
     * @param str
     */
    public void showProgress(boolean flag,String str){
        ShowDialog.getInstance().showProgress(flag,str,this);
    }

    public void toActivity(Class<?> cls,Bundle bundle,int requestCode){
        Intent intent = new Intent(this,cls);
        if(null != bundle) {
            intent.putExtras(bundle);
        }
        if(-1 != requestCode){
            startActivityForResult(intent,requestCode);
        }else{
            startActivity(intent);
        }

    }
    public void toActivity(Class<?> cls){
        toActivity(cls,null,-1);
    }
    public void toActivity(Class<?> cls,Bundle bundle){
        toActivity(cls,bundle,-1);
    }

    @Override
    public void onSuccess(Object response, String flag) {
    }

    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {
        showProgress(false);
        showDialog(response.toString());
    }

    /*****************************************锁屏机制*****************************************************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //每次操作都重新计时
                onUserAction();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 重新计时
     */
    private void onUserAction(){
        if(null != lockScreen){
            lockScreen.onUserAction();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(null != lockScreen){
            lockScreen.stopChecking();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != lockScreen){
            lockScreen.onUserAction();
        }
    }
}
