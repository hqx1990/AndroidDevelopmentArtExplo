package com.bestvike.androiddevelopmentartexploration.textViewOnClick;

import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by hackzhang on 2018/5/21 13:44.
 * PROJECT_NAME: HFAndroid   PACKAGE_NAME: com.haiercash.utils
 * Describe: Textview多个协议点击事件封装
 * Warning:
 */
public class MyClickableSpan extends ClickableSpan {
    private View.OnClickListener listener;
    private int color;

    public MyClickableSpan(@ColorInt int color, View.OnClickListener listener) {
        this.listener = listener;
        this.color = color;
    }

    @Override
    public void onClick(View widget) {
        listener.onClick(widget);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(color);
    }
}
