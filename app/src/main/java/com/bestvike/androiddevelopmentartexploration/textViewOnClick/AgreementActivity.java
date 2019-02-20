package com.bestvike.androiddevelopmentartexploration.textViewOnClick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个textView 点击不同的地方调用不同的方法
 */
public class AgreementActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        textView = findViewById(R.id.textView);
        List<String> list = new ArrayList<>();
        list.add("用户注册协议");
        list.add("个人信息使用授权");
        list.add("消费信贷服务协议");
        list.add("用户隐私政策");
        setContract("我同意用户注册协议，个人信息使用授权，消费信贷服务协议和用户隐私政策", textView, 0xff32beff, list);
    }

    private void setContract(String contractName, TextView textView , int color, final List<String> list){
        SpannableString sp = new SpannableString(contractName);

        for(int i = 0;i<list.size();i++){
            final int position = i;
            setClickableSpan(sp, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.e("协议点击",list.get(position));
                    showToast(list.get(position));
                }
            }, contractName, list.get(i),color);
        }

        textView.setText(sp);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /*设置协议展示和点击事件*/
    private SpannableString setClickableSpan(
            SpannableString sp, View.OnClickListener clickListener, String str, String span,int color) {
        sp.setSpan(new MyClickableSpan(color, clickListener),
                str.indexOf(span), str.indexOf(span) + span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
