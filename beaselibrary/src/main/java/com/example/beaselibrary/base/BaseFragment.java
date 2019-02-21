package com.example.beaselibrary.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.example.beaselibrary.R;
import com.example.beaselibrary.interfaces.DialogListener;
import com.example.beaselibrary.network.okHttp.NetResultCallBack;
import com.example.beaselibrary.util.taskbar.FitStateUI;
import com.example.beaselibrary.util.taskbar.OSUtils;


/**
 * 项目名称：修改baseFragment
 * 项目作者：hqx
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * activity可以被所有继承者直接访问到
 * ----------------------------------------------------------------------------------------------------
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener, NetResultCallBack {

    private FragmentActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            activity = (BaseActivity) context;
        }else{
            activity =  getActivity();
        }
    }

    @Override
    public void onAttach(Activity activitya) {
        super.onAttach(activitya);
        if(null==activity){
            if(activitya instanceof FragmentActivity){
                activity = (FragmentActivity) activitya;
            }else{
                activity =  getActivity();
            }
        }
    }

    public FragmentActivity getAct(){
        if(null==activity){
            FragmentActivity localActivity=getActivity();
            if(null==localActivity){
                activity = BaseApplication.getInstance().currentActivity;
            }else{
                activity = localActivity;
            }
        }
        return activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity=null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 适配小米和魅族沉浸式状态栏
         */
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.FLYME)
            FitStateUI.setMeizuStatusBarDarkIcon(activity, true);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.EMUI)
            FitStateUI.setImmersionStateMode(activity);
        FitStateUI.initStatusBar(activity);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.MIUI)
            FitStateUI.setStatusBarFontIconDark(true, getActivity());
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.OTHER)
            FitStateUI.setImmersionStateMode(activity);
        FitStateUI.initStatusBar(activity);
    }

    public void showDialog(String msg) {
        if(isBaseAct()){
            ((BaseActivity)activity).showDialog(msg);
        }
    }
    private boolean isBaseAct(){
        activity=getAct();
        return null != activity && activity instanceof BaseActivity;
    }

    public void showDialog(String msg, DialogListener listener) {
        if(isBaseAct()){
            ((BaseActivity)activity).showDialog(msg, listener);
        }
    }



    public void showProgress(boolean flag) {
        if(isBaseAct()){
            ((BaseActivity)activity).showProgress(flag);
        }
    }

    public void showProgress(boolean flag, String msg) {
        if(isBaseAct()){
            ((BaseActivity)activity).showProgress(flag, msg);
        }
    }


    @Override
    public void onDestroy() {
        destroyPresenter();
        super.onDestroy();
        this.activity = null;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onSuccess(Object response, String flag) {
        if(isBaseAct()){
            ((BaseActivity)activity).onSuccess( response,flag);
        }
    }

    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {
        if(isBaseAct()){
            ((BaseActivity)activity).onErr(retFlag, response,retBody,flag);
        }
    }

    /**
     * 用于销毁Presenter
     */
    protected abstract void destroyPresenter();

    /**
     * fragment name
     */
    public String getFragmentName() {
        return this.getClass().getSimpleName();
    }
}
