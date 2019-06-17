package com.bestvike.androiddevelopmentartexploration.nestedList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseRecyclerAdapter;
import com.example.beaselibrary.util.CheckUtil;

import java.util.List;

public class NestedItemAdapter extends BaseRecyclerAdapter<NestedItemBean> {

    private NestedCheckbox nestedCheckbox;
    private int outsidePosition;
    public void setNestedCheckbox(NestedCheckbox nestedCheckbox,int outsidePosition){
        this.nestedCheckbox = nestedCheckbox;
        this.outsidePosition = outsidePosition;
    }

    public NestedItemAdapter(Context context, List<NestedItemBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, View view, int viewType) {
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        final NestedItemBean nestedItemBean = datas.get(position);
        myViewHolder.tvItemName.setText(nestedItemBean.getItemName());
        String strCheckBox = nestedItemBean.getStrCheckBox();
        if(CheckUtil.getInstance().isEmpty(strCheckBox)){
            myViewHolder.cbDetermine.setChecked(false);
            myViewHolder.cbCancel.setChecked(false);
        }else{
            if(TextUtils.equals("Y",strCheckBox)){
                //确定
                myViewHolder.cbDetermine.setChecked(true);
                myViewHolder.cbCancel.setChecked(false);
            }else{
                myViewHolder.cbDetermine.setChecked(false);
                myViewHolder.cbCancel.setChecked(true);
            }
        }

        myViewHolder.cbDetermine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //选中确定
                    myViewHolder.cbCancel.setChecked(false);
                    myViewHolder.cbDetermine.setChecked(true);
                    nestedCheckbox.setCheckBox(outsidePosition,position,"Y");
                }
            }
        });

        myViewHolder.cbCancel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //选中确定
                    myViewHolder.cbCancel.setChecked(true);
                    myViewHolder.cbDetermine.setChecked(false);
                    nestedCheckbox.setCheckBox(outsidePosition,position,"N");
                }
            }
        });
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tvItemName;
        public CheckBox cbDetermine;//确定
        public CheckBox cbCancel;//取消

        public MyViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            cbDetermine = itemView.findViewById(R.id.cbDetermine);
            cbCancel = itemView.findViewById(R.id.cbCancel);
        }
    }
}
