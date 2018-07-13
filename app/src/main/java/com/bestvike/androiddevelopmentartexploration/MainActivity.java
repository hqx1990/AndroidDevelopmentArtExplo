package com.bestvike.androiddevelopmentartexploration;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.IPC.SecondActivity;
import com.bestvike.androiddevelopmentartexploration.IPC.ThirdActivity;
import com.bestvike.androiddevelopmentartexploration.IPC.UserManager;
import com.bestvike.androiddevelopmentartexploration.homePpage.HomePageAdapter;
import com.bestvike.androiddevelopmentartexploration.listLeftDelete.leftDelete.LeftDeletaActivity;
import com.bestvike.androiddevelopmentartexploration.paging.pagingLibrary.PagingLibraryActivity;
import com.bestvike.androiddevelopmentartexploration.paging.asyncListUtil.AsyncListActivity;
import com.bestvike.androiddevelopmentartexploration.rxImagePicker.RxImagePickerActivity;
import com.bestvike.androiddevelopmentartexploration.xg.MyXGActivity;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.base.BaseRecyclerAdapter;
import com.example.beaselibrary.interfaces.DialogListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private HomePageAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datas();
        initView();
        UserManager.sUserId = 2;
        Log.e("0-----","userId:"+UserManager.sUserId);
    }

    private void datas(){
        list = new ArrayList<String>();
        list.add("弹框1");
        list.add("弹框2");
        list.add("弹框3");
        list.add("弹框4");
        list.add("弹框5");
        list.add("多线程1  :remote");
        list.add("多线程2  com.ryg.chapter_2.remote");
        list.add("RecyclerView仿QQ左滑出删除功能");
        list.add("信鸽推送");
        list.add("图片库RxImagePicker应用");
        list.add("PagingLibrary分页加载框架的使用");
        list.add("基于Android官方AsyncListUtil优化改进RecyclerView分页加载机制");
        for(int i = 0;i<30;i++){
            list.add(String.valueOf(i));
        }
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//分割线
        adapter = new HomePageAdapter(this,list,R.layout.homepageadapter);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String str  = list.get(position);
                showToast(str);
                switch (position){
                    case 0:
                        showDialog(str);
                        break;
                    case 1:
                        showDialog(str, new DialogListener() {
                            @Override
                            public void dialogCallBack(final AlertDialog dialog, TextView left, TextView right) {
                                left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                                right.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                    case 2:
                        showDialog(str, false, new DialogListener() {
                            @Override
                            public void dialogCallBack(final AlertDialog dialog, TextView left, TextView right) {
                                left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                                right.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                    case 3:
                        showProgress(true,"请稍后。。。");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(false);
                            }
                        },2000);
                        break;
                    case 4:
                        showProgress(true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(false);
                            }
                        },2000);
                        break;
                    case 5:
                        //多线程1  :remote
                        startActivity(new Intent(MainActivity.this,SecondActivity.class));
                        break;

                    case 6:
                        //多线程2  com.ryg.chapter_2.remote
                        startActivity(new Intent(MainActivity.this,ThirdActivity.class));
                        break;

                    case 7:
                        //RecyclerView仿QQ左滑出删除功能
                        toActivity(LeftDeletaActivity.class);
                        break;

                    case 8:
                        //信鸽推送
                        toActivity(MyXGActivity.class);
                        break;
                    case 9:
                        //图片库RxImagePicker应用
                        toActivity(RxImagePickerActivity.class);
                        break;
                    case 10:
                        //PagingLibrary分页加载框架的使用
                        toActivity(PagingLibraryActivity.class);
                        break;
                    case 11:
                        //基于Android官方AsyncListUtil优化改进RecyclerView分页加载机制
                        toActivity(AsyncListActivity.class);
                        break;
                }
            }
        });

    }
}
