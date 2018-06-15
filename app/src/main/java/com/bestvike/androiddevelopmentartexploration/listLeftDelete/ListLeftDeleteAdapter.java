package com.bestvike.androiddevelopmentartexploration.listLeftDelete;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseRecyclerAdapter;

import java.util.List;

public class ListLeftDeleteAdapter extends BaseRecyclerAdapter<String>{


    public ListLeftDeleteAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, View view, int viewType) {
        return new MyHolder(view);
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, final int position) {

        MyHolder myHolder = (MyHolder) holder;
        setText(myHolder.textView,datas.get(position));

        myHolder.rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete.delete(position);
            }
        });

    }

    private OnClickDelete onClickDelete;
    public void setOnDelete(OnClickDelete onClickDelete){
        this.onClickDelete = onClickDelete;
    }


    private class MyHolder extends RecyclerView.ViewHolder{
        public TextView textView,rightView;
        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            rightView = itemView.findViewById(R.id.rightView);
        }
    }
}
