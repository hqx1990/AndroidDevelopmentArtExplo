package com.bestvike.androiddevelopmentartexploration.theKeyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.util.Logger;
import com.example.beaselibrary.util.SoftKeyBoardListenerUtil;
import com.example.beaselibrary.util.UiUtil;

/**
 * 软键盘弹出消失输入监听
 * hqx
 */
public class TheKeyboardActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_keyboard);
        editText = findViewById(R.id.editText);

        UiUtil.getTextWatcherZero(editText);
        SoftKeyBoardListenerUtil.setListener(this, new SoftKeyBoardListenerUtil.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //弹出键盘
                Logger.e("---","弹出键盘");
            }

            @Override
            public void keyBoardHide(int height) {
                //键盘关闭，进行还款试算
                Logger.e("---","关闭键盘");
            }
        });
    }
}
