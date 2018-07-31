package com.bestvike.androiddevelopmentartexploration.listLeftDelete.leftDelete2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseRecyclerAdapter;

import java.util.List;

public class LeftDeletaAdapter2 extends BaseRecyclerAdapter<String> implements LeftSlideView.IonSlidingButtonListener{

    private int BanSliding = 100886;//不可滑动

    private LeftSlideView mMenu = null;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private IonSlidingViewClickListener mISetBtnClickListener;


    public LeftDeletaAdapter2(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, View view, int viewType) {
        if(BanSliding == viewType){
            View v = LayoutInflater.from(reference.get()).inflate(R.layout.leftdeleteitem3,parent,false);
            return new ViewHolderBanSliding(v);
        }else{
            return new MyViewHolder(view);
        }
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ViewHolderBanSliding){
            final ViewHolderBanSliding viewHolder = (ViewHolderBanSliding) holder;
            viewHolder.textView.setText("不可滑动"+datas.get(position));
            //设置内容布局的宽为屏幕宽度
            viewHolder.layout_content.getLayoutParams().width = Utils.getScreenWidth(reference.get());
        }else{
            final MyViewHolder viewHolder = (MyViewHolder) holder;

            viewHolder.textView.setText("可以滑动"+datas.get(position));
            //设置内容布局的宽为屏幕宽度
            viewHolder.layout_content.getLayoutParams().width = Utils.getScreenWidth(reference.get());

            //item正文点击事件
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //判断是否有删除菜单打开
                    if (menuIsOpen()) {
                        closeMenu();//关闭菜单
                    } else {
                        int n = viewHolder.getLayoutPosition();
                        mIDeleteBtnClickListener.onItemClick(v, n);
                    }

                }
            });

            //左滑设置点击事件
            viewHolder.btn_Set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeMenu();//关闭菜单
                    int n = viewHolder.getLayoutPosition();
                    mISetBtnClickListener.onSetBtnCilck(view, n);
                }
            });

            //左滑删除点击事件
            viewHolder.btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = viewHolder.getLayoutPosition();
                    mIDeleteBtnClickListener.onDeleteBtnCilck(view, n);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < 3){
            return BanSliding;
        }
        return super.getItemViewType(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView btn_Set;
        public TextView btn_Delete;
        public TextView textView;
        public ViewGroup layout_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Set =  itemView.findViewById(R.id.tv_set);
            btn_Delete =  itemView.findViewById(R.id.tv_delete);
            textView =  itemView.findViewById(R.id.text);
            layout_content =  itemView.findViewById(R.id.layout_content);

            ((LeftSlideView) itemView).setSlidingButtonListener(LeftDeletaAdapter2.this);
        }
    }

    private class ViewHolderBanSliding extends RecyclerView.ViewHolder{

        public TextView textView;
        public ViewGroup layout_content;

        public ViewHolderBanSliding(View itemView) {
            super(itemView);
            textView =  itemView.findViewById(R.id.text);
            layout_content =  itemView.findViewById(R.id.layout_content);
        }
    }




    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }

    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }
    }
    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断菜单是否打开
     *
     * @return
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    public void setIonSlidingViewClickListener(IonSlidingViewClickListener ionSlidingViewClickListener){
        this.mIDeleteBtnClickListener = ionSlidingViewClickListener;
        this.mISetBtnClickListener = ionSlidingViewClickListener;
    }

    /**
     * 注册接口的方法：点击事件。在Mactivity.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);//点击item正文

        void onDeleteBtnCilck(View view, int position);//点击“删除”

        void onSetBtnCilck(View view, int position);//点击“设置”
    }


}
