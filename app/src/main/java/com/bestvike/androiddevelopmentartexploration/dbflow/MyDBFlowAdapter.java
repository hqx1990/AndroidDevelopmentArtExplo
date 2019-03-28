package com.bestvike.androiddevelopmentartexploration.dbflow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseRecyclerAdapter;

import java.util.List;

public class MyDBFlowAdapter extends BaseRecyclerAdapter<NoteBean> {
    public MyDBFlowAdapter(Context context, List<NoteBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, View view, int viewType) {
        return new MyHolder(view);
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(datas.get(position).getContent());
    }

    private static final class MyHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
