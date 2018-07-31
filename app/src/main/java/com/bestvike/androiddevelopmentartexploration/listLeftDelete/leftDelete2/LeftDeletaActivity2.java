package com.bestvike.androiddevelopmentartexploration.listLeftDelete.leftDelete2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class LeftDeletaActivity2 extends BaseActivity implements LeftDeletaAdapter2.IonSlidingViewClickListener{

    private List<String> list;
    private RecyclerView recyclerView;
    private LeftDeletaAdapter2 adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leftdeletaactivity2);

        getData();
        findView();
    }

    private void getData(){
        list = new ArrayList<String>();

        for (int i = 0;i<30;i++){
            list.add(String.valueOf(i));
        }

    }

    private void findView(){
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new LeftDeletaAdapter2(this,list,R.layout.leftdeleteitem2);
        adapter.setIonSlidingViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//分割线
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//设置控制item增删的动画

    }

    /**
     * 点击item正文
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        ShowDialog.getInstance().toast("点击列表："+list.get(position));
    }

    /**
     * 点击“删除”
     * @param view
     * @param position
     */
    @Override
    public void onDeleteBtnCilck(View view, int position) {
        list.remove(position);
        adapter.notifyDataSetChanged();
        ShowDialog.getInstance().toast("点击删除："+list.get(position));
    }

    /**
     * 点击“设置”
     * @param view
     * @param position
     */
    @Override
    public void onSetBtnCilck(View view, int position) {
        ShowDialog.getInstance().toast("点击设置："+list.get(position));
    }
}
