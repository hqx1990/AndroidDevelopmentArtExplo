package com.bestvike.androiddevelopmentartexploration;

import android.Manifest;
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

import com.bestvike.androiddevelopmentartexploration.HandlerUse.HandlerUseActivity;
import com.bestvike.androiddevelopmentartexploration.IPC.SecondActivity;
import com.bestvike.androiddevelopmentartexploration.IPC.ThirdActivity;
import com.bestvike.androiddevelopmentartexploration.IPC.UserManager;
import com.bestvike.androiddevelopmentartexploration.homePpage.HomePageAdapter;
import com.bestvike.androiddevelopmentartexploration.jsWebView.JsWebViewActivity;
import com.bestvike.androiddevelopmentartexploration.listLeftDelete.leftDelete.LeftDeletaActivity;
import com.bestvike.androiddevelopmentartexploration.listLeftDelete.leftDelete2.LeftDeletaActivity2;
import com.bestvike.androiddevelopmentartexploration.liveData.LiveDataActivity;
import com.bestvike.androiddevelopmentartexploration.paging.pagingLibrary.PagingLibraryActivity;
import com.bestvike.androiddevelopmentartexploration.paging.asyncListUtil.AsyncListActivity;
import com.bestvike.androiddevelopmentartexploration.runtimePermission.RuntimePermissionActivity;
import com.bestvike.androiddevelopmentartexploration.scott.ScottActivity;
import com.bestvike.androiddevelopmentartexploration.spHelp.SPHelpActivity;
import com.bestvike.androiddevelopmentartexploration.textViewOnClick.AgreementActivity;
import com.bestvike.androiddevelopmentartexploration.useMVPFramework.UseMVPFrameworkActivity;
import com.bestvike.androiddevelopmentartexploration.viewPagerFragment.ViewPagerActivity;
import com.bestvike.androiddevelopmentartexploration.xg.MyXGActivity;
import com.bestvike.androiddevelopmentartexploration.zxing.activity.SweepTheVardActivity;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.base.BaseRecyclerAdapter;
import com.example.beaselibrary.interfaces.DialogListener;
import com.example.beaselibrary.permissions.AccessPermissions;
import com.example.beaselibrary.permissions.AccessPermissionsInterface;
import com.zy.logcat.LogCatControl;

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

    @Override
    protected void destroyPresenter() {

    }

    private void datas(){
        list = new ArrayList<>();
        list.add("弹框1");
        list.add("弹框2");
        list.add("弹框3");
        list.add("弹框4");
        list.add("弹框5");
        list.add("多线程1  :remote");
        list.add("多线程2  com.ryg.chapter_2.remote");
        list.add("RecyclerView仿QQ左滑出删除功能");
        list.add("信鸽推送");
        list.add("发送验证码");
        list.add("PagingLibrary分页加载框架的使用");
        list.add("基于Android官方AsyncListUtil优化改进RecyclerView分页加载机制");
        list.add("LiveData感知生命周期，数据同步回复等");
        list.add("LogcatView 一款可以在手机中打开logcat控制台");
        list.add("RecyclerView仿QQ左滑出删除功能2");
        list.add("jsWebView交互");
        list.add("扫描二维码，识别相册中的二维码");
        list.add("权限判断类库：RuntimePermission");
        list.add("Handler使用");
        list.add("打包自动处理环境");
        list.add("TextView点击");
        list.add("ViewPager的简单实用");
        list.add("高德");
        list.add("sp使用");
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
//                showToast(str);
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
                        //发送验证码
                        toActivity(UseMVPFrameworkActivity.class);
                        break;
                    case 10:
                        //PagingLibrary分页加载框架的使用
                        toActivity(PagingLibraryActivity.class);
                        break;
                    case 11:
                        //基于Android官方AsyncListUtil优化改进RecyclerView分页加载机制
                        toActivity(AsyncListActivity.class);
                        break;
                    case 12:
                        //LiveData感知生命周期，数据同步回复等
                        toActivity(LiveDataActivity.class);
                        break;
                    case 13:
                        //LogcatView 一款可以在手机中打开logcat控制台
//                        toActivity(null);
                        //显示dialog
                        LogCatControl.getBuilder(MainActivity.this)
                                .setTitle("自定义标题")
                                .setSearchContent("自定义搜索内容")
                                .setSearchTag("自定义Tag")
                                .setShowGrade(0) //设置显示级别:0 所有，1 系统，2 警告,3 错误
                                .show();

                        //清除dialog
//        LogCatControl.getBuilder(this).clear();
                        break;

                        case 14:
                            //RecyclerView仿QQ左滑出删除功能
                            toActivity(LeftDeletaActivity2.class);
                            break;

                    case 15:
                        //jsWebView交互
                        toActivity(JsWebViewActivity.class);
                        break;
                    case 16:
                        //扫描二维码，识别相册中的二维码
                        AccessPermissions.getInstance().isPermissions(new AccessPermissionsInterface() {
                            @Override
                            public void authorityToJudge(boolean isPermissions, List<String> data) {
                                if(isPermissions){
                                    //有权限
                                    Log.e("---","获取到相机权限");
                                    toActivity(SweepTheVardActivity.class);
                                }else{
                                    //没有权限
                                    showToast("请打开相机权限");
                                    Log.e("---","没有获取到相机权限");
                                }
                            }
                        },Manifest.permission.CAMERA);
                        break;

                    case 17:
                        //权限判断类库：RuntimePermission
                        toActivity(RuntimePermissionActivity.class);
                        break;

                    case 18:
                        //Handler使用
                        toActivity(HandlerUseActivity.class);
                        break;

                    case 19:
                        //打包自动处理环境
                        showToast(BuildConfig.TheEnvironment);
                        break;
                    case 20:
                        //TextView点击
                        toActivity(AgreementActivity.class);
                        break;
                    case 21:
                        //ViewPager的简单实用
                        toActivity(ViewPagerActivity.class);
                        break;
                    case 22:
                        //高德
                        //获取定位权限
                        AccessPermissions.getInstance().isPermissions(
                                new AccessPermissionsInterface() {
                                    @Override
                                    public void authorityToJudge(boolean isPermissions, List<String> data) {
                                        if(isPermissions){
                                            toActivity(ScottActivity.class);
                                        }else{
                                            showToast("没有权限");
                                        }
                                    }
                                }, Manifest.permission.ACCESS_COARSE_LOCATION);
                        break;
                    case 23:
                        //sp使用
                        toActivity(SPHelpActivity.class);
                        break;
                }
            }
        });

    }
}
