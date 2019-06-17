package com.bestvike.androiddevelopmentartexploration.nestedList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 两层列表嵌套 数据丢失测试
 */
public class NestedListActivity extends BaseActivity implements NestedCheckbox{

    private RecyclerView recyclerView;
    private NestedListAdapter adapter;
    private List<NestedListBean> list ;

    @Override
    protected void destroyPresenter() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_nested_list);
        getData();
        initView();

    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//分割线
        adapter = new NestedListAdapter(this,list,R.layout.nested_list_item);
        adapter.setNestedCheckbox(this);
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            NestedListBean nestedListBean = new NestedListBean();
            nestedListBean.setName("测试数据："+i);
            List <NestedItemBean>itemList = new ArrayList();
            for (int j = 0;j < 3;j++){
                NestedItemBean nestedItemBean = new NestedItemBean();
                nestedItemBean.setItemName("测试"+j);
                itemList.add(nestedItemBean);
            }
            nestedListBean.setNestedItemBeans(itemList);
            list.add(nestedListBean);
        }
    }

    /**
     * 选中确定或者取消监听
     * @param outsidePosition 外层数据条数
     * @param position 内层数据条数
     * @param isDetermine
     */
    @Override
    public void setCheckBox(int outsidePosition,int position ,String isDetermine) {
        list.get(outsidePosition).getNestedItemBeans().get(position).setStrCheckBox(isDetermine);
        adapter.notifyDataSetChanged();
    }
}
