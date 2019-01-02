package com.bestvike.androiddevelopmentartexploration.runtimePermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;

import java.util.List;


/**
 * 权限判断类库：RuntimePermission
 *
 *
 */
public class RuntimePermissionActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtimepermission_activity);

        findView();
    }
    private void findView(){
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.btTheAddressBook).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                //获取通讯录权限和相机权限
                permissionsSet(
                        Manifest.permission.READ_CONTACTS,//通讯录权限
                        Manifest.permission.CAMERA//相机权限
                );
                break;

            case  R.id.btTheAddressBook:
                //获取通讯录权限
                permissionsSet(
                        Manifest.permission.READ_CONTACTS//通讯录权限
                );
                break;

        }
    }

    /**
     * 获取权限
     */
    private void permissionsSet(String... permissions){
        AccessPermissions.getInstance().isPermissions(
            new AccessPermissionsInterface() {
                @Override
                public void authorityToJudge(boolean isPermissions, List<String> data) {
                    if(isPermissions){
                        showToast("有权限");
                    }else{
                        showToast("没有权限");
                    }
                }
            }, permissions);
    }
}
