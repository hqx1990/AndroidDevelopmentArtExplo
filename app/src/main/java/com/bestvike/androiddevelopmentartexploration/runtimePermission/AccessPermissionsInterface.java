package com.bestvike.androiddevelopmentartexploration.runtimePermission;

import java.util.List;

public interface AccessPermissionsInterface {
    /**
     * 判断是否有权限
     * @param isPermissions
     * @param data
     */
    void authorityToJudge (boolean isPermissions , List<String> data);
}
