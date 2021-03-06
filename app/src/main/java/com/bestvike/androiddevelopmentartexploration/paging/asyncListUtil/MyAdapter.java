package com.bestvike.androiddevelopmentartexploration.paging.asyncListUtil;

import android.graphics.Color;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.AppApplication;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {


    private AsyncListUtil mAsyncListUtil;

    protected MyAdapter(AsyncListUtil mAsyncListUtil){
        this.mAsyncListUtil = mAsyncListUtil;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(AppApplication.getInstance()).inflate(android.R.layout.simple_list_item_2, null);
        MyAdapter.ViewHolder holder = new MyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {
        viewHolder.text1.setText(String.valueOf(i));

        User user = (User) mAsyncListUtil.getItem(i);

        if(null != user){
            String name = user.getName();
            String phone = user.getPhone();
            viewHolder.text1.setText(name);
            viewHolder.text2.setText(phone);
        }else {
            viewHolder.text1.setText("");
            viewHolder.text2.setText("加载中...");
        }
    }

    @Override
    public int getItemCount() {
        return mAsyncListUtil.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;

        public ViewHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(android.R.id.text1);
            text1.setTextColor(Color.RED);

            text2 = itemView.findViewById(android.R.id.text2);
            text2.setTextColor(Color.BLUE);
        }
    }
}
