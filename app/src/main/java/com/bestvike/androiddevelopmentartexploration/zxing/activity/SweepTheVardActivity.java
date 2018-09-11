package com.bestvike.androiddevelopmentartexploration.zxing.activity;

import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.beaselibrary.interfaces.DialogListener;
import com.example.beaselibrary.util.CheckUtil;

public class SweepTheVardActivity extends ZXingActivity {

    @Override
    protected void successful(String str) {
        //扫描成功后或者识别相册中二维码调用
        if(CheckUtil.getInstance().isEmpty(str)){
            showToast("二维码识别失败");
        }else{
            showDialog("二维码失败成功：" + str, new DialogListener() {
                @Override
                public void dialogCallBack(final AlertDialog dialog, TextView left, TextView right) {
                    left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            restartPreview();
                        }
                    });
                    right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            restartPreview();
                        }
                    });
                }
            });
        }
    }
}
