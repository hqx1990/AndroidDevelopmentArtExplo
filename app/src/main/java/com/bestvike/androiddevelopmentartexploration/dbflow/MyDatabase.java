package com.bestvike.androiddevelopmentartexploration.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 创建数据库
 * 使用 @Database 注解来定义自己的数据库，该类应该要定义数据库的名称和数据库的版本
 *
 * 注意：如果以后要修改任意表的结构，为避免与旧版本数据库冲突一定要修改版本号，且保证版本号只升不降。
 */
@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    //数据库名称
    public static final String NAME = "MyDatabase";
    //数据库版本号
    public static final int VERSION = 1;
}
