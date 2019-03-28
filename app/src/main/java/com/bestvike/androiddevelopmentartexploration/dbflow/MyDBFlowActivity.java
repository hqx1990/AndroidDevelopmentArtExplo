package com.bestvike.androiddevelopmentartexploration.dbflow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.util.Logger;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * DBFlow 简单使用
 *
 */
public class MyDBFlowActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    private RecyclerView recyclerView;
    private MyDBFlowAdapter adapter;
    private List<NoteBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dbflow);
        getData();
        findView();

    }

    private void getData(){
        //插入数据
        for (int i = 0;i<10;i++){
            NoteBean noteTable = new NoteBean();
            inseartData(noteTable,i);
        }
        list = queryData();
        Logger.e("---","数据库："+list.size());
    }

    private void findView(){
        findViewById(R.id.btAdd).setOnClickListener(this);//增
        findViewById(R.id.btDelete).setOnClickListener(this);//删
        findViewById(R.id.btModifyThe).setOnClickListener(this);//改
        findViewById(R.id.btTheQuery).setOnClickListener(this);//查

        adapter = new MyDBFlowAdapter(this,list,R.layout.item_dbflow);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//分割线
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btAdd:
                //增

                break;
            case R.id.btDelete:
                //删

                break;
            case R.id.btModifyThe:
                //改

                break;
            case R.id.btTheQuery:
                //查

                break;
        }
    }

    /**
     * 插入数据
     * @param model
     */
    public void inseartData(NoteBean model,int i){
        //1.model,insert()
        model.setTitle("title");
        model.setDate("2018-04-17");
        model.setContent("content："+ i);
        model.save();
        model.insert();
    }

    /**
     * 删除数据
     * @param model
     */
    public void deleteData(NoteBean model){
        //1.model.delete()
        model.delete();
    }

    /**
     * 更新数据
     * @param model
     */
    public void updateData(NoteBean model) {
        //1.model.update()
        model.update();
    }

    /**
     * 查询数据
     */
    public List<NoteBean> queryData(){
        //根据条件查询
        List<NoteBean> users=new Select().from(NoteBean.class).queryList();
        return users;
    }
}
