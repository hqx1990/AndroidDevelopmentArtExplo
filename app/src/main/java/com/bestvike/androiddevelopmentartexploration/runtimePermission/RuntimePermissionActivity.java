package com.bestvike.androiddevelopmentartexploration.runtimePermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                //权限检查，回调是权限申请结果
                AndPermission.with(this).
                        runtime().
                        permission(Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA).//要获取的权限组
                        onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                //有权限
                                showToast("有权限");
                                Log.e("---","有权限");
                            }
                        }).
                        onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                //没有权限
                                showToast("没有权限");
                                Log.e("---","没有权限");
                            }
                        }).
                        start();
                break;

        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        // 只需要调用这一句，剩下的AndPermission自动完成。
//        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }
}
