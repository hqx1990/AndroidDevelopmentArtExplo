package com.example.beaselibrary.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beaselibrary.R;
import com.example.beaselibrary.base.BaseApplication;
import com.example.beaselibrary.interfaces.DialogListener;
import com.example.beaselibrary.view.LoadingProgress;


/**
 * Created by use on 2018/3/14.
 * 共用弹框提示，菊花
 */

public class ShowDialog {
    public static ShowDialog getInstance(){
        return SingleTonHolder.instance;
    }
    private static class SingleTonHolder{
        private static ShowDialog instance = new ShowDialog();
    }

    private AlertDialog adSelect;
    private View adSelectView;
    private TextView tvContainer;
    private TextView tvConfirm;
    private TextView tvCancel;
    /**
     * 展示有按个按钮的dialog
     * @param str  展示内容
     * @param listener
     * @param isTitle  是否隐藏title
     */
    public void showDialog(Activity activity, String str , boolean isTitle, DialogListener listener){
        try
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            adSelect = builder.create();
            adSelectView = View.inflate(activity, R.layout.dialog_warn, null);
            TextView title = adSelectView.findViewById(R.id.title_);
            if(!isTitle && null != title){
                title.setVisibility(View.GONE);
            }else{
                title.setVisibility(View.VISIBLE);
            }
            tvContainer = (TextView) adSelectView.findViewById(R.id.tv_container);
            tvConfirm = (TextView) adSelectView.findViewById(R.id.tv_confirm);
            tvCancel = (TextView) adSelectView.findViewById(R.id.tv_cancel);
            View view = adSelectView.findViewById(R.id.view);
            tvContainer.setText(str);
            listener.dialogCallBack(adSelect, tvCancel, tvConfirm);
            if(tvCancel.getVisibility() == View.GONE){
                view.setVisibility(View.GONE);
            }
            adSelect.show();

            Window window = adSelect.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            Display display = activity.getWindowManager().getDefaultDisplay();
            params.width = (int) (display.getWidth() * 0.7);
            window.setAttributes(params);
            window.setBackgroundDrawable(new BitmapDrawable());
            adSelect.setContentView(adSelectView);
            adSelect.setCanceledOnTouchOutside(false);
        } catch (Exception e)
        {
            if (!activity.isFinishing() && adSelect != null && adSelect.isShowing())
            {
                adSelect.dismiss();
            }
            e.printStackTrace();
        }
    }


    public LoadingProgress progress;
    /**
     * 展示网络请求等待的菊花
     */
    public void showProgress(boolean flag , String str ,final Activity activity){
        try
        {
            if (flag)
            {
                progress = LoadingProgress.show(activity, str, true, new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        dialog.dismiss();
                        activity.onBackPressed();
                    }
                });
                progress.setMsg(str);
                if (!progress.isShowing())
                    progress.show();
            } else
            {
                if (progress != null && progress.isShowing())
                {
                    progress.dismiss();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Toast mToast;
    public void toast(String msg){
        if (mToast == null)
        {
            mToast = Toast.makeText(BaseApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
