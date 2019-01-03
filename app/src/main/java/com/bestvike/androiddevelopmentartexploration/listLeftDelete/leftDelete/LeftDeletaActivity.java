package com.bestvike.androiddevelopmentartexploration.listLeftDelete.leftDelete;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bestvike.androiddevelopmentartexploration.R;
import com.bestvike.androiddevelopmentartexploration.listLeftDelete.ListLeftDeleteAdapter;
import com.bestvike.androiddevelopmentartexploration.listLeftDelete.OnClickDelete;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.base.BaseRecyclerAdapter;
import com.example.beaselibrary.util.ShowDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeftDeletaActivity extends BaseActivity {

    private SwipeMenuRecyclerView recyclerView;
    private ListLeftDeleteAdapter adapter;

    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_deleta_activity);

        getData();
        findView();
    }

    @Override
    protected void destroyPresenter() {

    }

    private void getData(){
        list = new ArrayList<String>();

        for (int i = 0;i<30;i++){
            list.add(String.valueOf(i));
        }

    }

    private void findView(){
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ListLeftDeleteAdapter(this,list,R.layout.left_delete_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//分割线
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ShowDialog.getInstance().toast("点击了："+position);
            }
        });
        adapter.setOnDelete(new OnClickDelete(){

            @Override
            public void delete(int position) {
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
