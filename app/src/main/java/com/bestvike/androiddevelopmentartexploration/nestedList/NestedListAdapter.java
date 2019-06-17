package com.bestvike.androiddevelopmentartexploration.nestedList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseRecyclerAdapter;

import java.util.List;

public class NestedListAdapter extends BaseRecyclerAdapter<NestedListBean>{

    private NestedCheckbox nestedCheckbox;
    public void setNestedCheckbox(NestedCheckbox nestedCheckbox){
        this.nestedCheckbox = nestedCheckbox;
    }

    public NestedListAdapter(Context context, List<NestedListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, View view, int viewType) {
        return  new MyViewHolder(view);
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tvName.setText(datas.get(position).getName());

        NestedItemAdapter adapter = new NestedItemAdapter(reference.get(),
                datas.get(position).getNestedItemBeans(),
                R.layout.nested_item);
        myViewHolder.itemRecyclerView.setLayoutManager(new LinearLayoutManager(reference.get()));
        adapter.setNestedCheckbox(nestedCheckbox,position);
        myViewHolder.itemRecyclerView.setAdapter(adapter);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public RecyclerView itemRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            itemRecyclerView = itemView.findViewById(R.id.itemRecyclerView);
        }
    }
}
