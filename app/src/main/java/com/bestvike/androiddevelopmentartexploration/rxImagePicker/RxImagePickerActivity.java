package com.bestvike.androiddevelopmentartexploration.rxImagePicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;

/**
 * 图片库RxImagePicker应用
 */
public class RxImagePickerActivity extends BaseActivity {

    private ImageView imageView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_imagepicker_activity);
        findView();
    }

    private void findView(){
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        imageView = findViewById(R.id.imageView);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                // 基础组件

                break;
            case R.id.button2:
                // 自定义UI层基础组件

                break;
            case R.id.button3:
                // 知乎图片选择器

                break;
            case R.id.button4:
                // 微信图片选择器

                break;

        }
    }
}
