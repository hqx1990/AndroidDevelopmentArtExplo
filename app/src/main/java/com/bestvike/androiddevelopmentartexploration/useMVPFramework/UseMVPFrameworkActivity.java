package com.bestvike.androiddevelopmentartexploration.useMVPFramework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.base.BaseUrl;

/**
 * mvp 框架的使用案例
 */
public class UseMVPFrameworkActivity extends BaseActivity implements UseMVPFrameworkView {

    private UseMVPFrameworkPersenter verificationCodePersenter;

    @Override
    protected void destroyPresenter() {
        if(null != verificationCodePersenter){
            verificationCodePersenter.onDettached();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtimepermission_activity);

        findView();
    }
    private void findView(){
        Button button = findViewById(R.id.button);
        button.setText("发送验证码");
        button.setOnClickListener(this);
        findViewById(R.id.btTheAddressBook).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
              //发送验证码
                showProgress(true);
                if(null == verificationCodePersenter){
                    verificationCodePersenter = new UseMVPFrameworkPersenter(this);
                }
                verificationCodePersenter.get();
                break;

            case  R.id.btTheAddressBook:
                //获取通讯录权限
                break;

        }
    }

    @Override
    public void verificationCodeView(GetDictBeanRtn getDictBeanRtn) {
        showDialog("接口调用成功"+getDictBeanRtn.COM_KIND.size()+","+BaseUrl.baseUrl);
    }
}
