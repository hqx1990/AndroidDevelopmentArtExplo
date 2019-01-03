package com.example.beaselibrary.permissions;



import com.example.beaselibrary.base.BaseApplication;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class AccessPermissions {
    /**
     * 单例模式
     */
    private static final class SingleTonHolder{
        private static final AccessPermissions accessPermissions = new AccessPermissions();
    }
    public static AccessPermissions getInstance(){
        return SingleTonHolder.accessPermissions;
    }

    /**
     * 是否有权限
     * @param permissions  要申请的权限
     * @param accessPermissionsInterface  接口回调，是否已获取到权限
     * @return
     */
    public boolean isPermissions(final AccessPermissionsInterface accessPermissionsInterface, String... permissions){
        AndPermission.with(BaseApplication.getInstance()).
                runtime().
                permission(permissions).//要获取的权限组
                onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        //有权限
                        accessPermissionsInterface.authorityToJudge(true,data);
                    }
                }).
                onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        //没有权限
                        accessPermissionsInterface.authorityToJudge(false,data);
                    }
                }).
                start();
        return true;
    }

}
