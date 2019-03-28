package com.bestvike.androiddevelopmentartexploration.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 创建表
 * 在已经创建好数据库的前提下就可以创建表了，
 * 表的模型类一般需要继承 BaseModel，
 * 并为模型类中的每个字段添加 @Column 注解，
 * 该注解将映射模型类的字段到对应表中的列，定义一张表具体如下：
 *
 *
 * 注意：在一张表中至少必须定义一个字段作为主键（primary key），如果模型类中某个字段是私有的，
 * 一定要定义相应的 getter、setter 方法，否则会在创建表的环节失败，表的命名要使用驼峰命名法，否则可能会出现如此下问题：
 */
@Table(database = MyDatabase.class)
public class NoteBean extends BaseModel {

    @Column
    @PrimaryKey
    private int id;
    @Column
    private String title;
    @Column
    private String date;
    @Column
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
