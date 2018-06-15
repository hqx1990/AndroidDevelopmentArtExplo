package com.example.beaselibrary.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beaselibrary.interfaces.DialogListener;
import com.example.beaselibrary.util.ShowDialog;

/**
 * Created by Administrator on 2018/3/29.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
