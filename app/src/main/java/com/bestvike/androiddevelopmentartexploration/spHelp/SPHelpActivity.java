package com.bestvike.androiddevelopmentartexploration.spHelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.sp.SpKey;
import com.example.beaselibrary.sp.SpUtil;
import com.example.beaselibrary.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class SPHelpActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_help);
        findViewById(R.id.buSaveSingle).setOnClickListener(this);//保存单条数据
        findViewById(R.id.buToViewSingle).setOnClickListener(this);//查看保存结果
        findViewById(R.id.buDeleteSingle).setOnClickListener(this);//删除保存的单个数据
        findViewById(R.id.buSaveMultiple).setOnClickListener(this);//保存多条个数据
        findViewById(R.id.buToViewMultiple).setOnClickListener(this);//查看保存结果
        findViewById(R.id.buDeleteMultiple).setOnClickListener(this);//删除保存的多条数据
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buSaveSingle:
                //保存单条数据
                SpUtil.getBankCardInstance().put(SpKey.LOGIN_CLIENTSERCERT,123);
                break;
            case R.id.buToViewSingle:
                //查看保存结果
                String str = SpUtil.getBankCardInstance().getInteger(SpKey.LOGIN_CLIENTSERCERT)+"";
                showDialog(str);
                Logger.e("","获取sp："+str);
                break;
            case R.id.buDeleteSingle:
                //删除保存的单个数据
                SpUtil.getBankCardInstance().remove(SpKey.LOGIN_CLIENTSERCERT);
                break;
            case R.id.buSaveMultiple:
                //保存多条个数据
                Map<String , String> map = new HashMap<>();
                map.put(SpKey.LOGIN_THE_TEST_DATA1,"a1");
                map.put(SpKey.LOGIN_THE_TEST_DATA2,"a2");
                map.put(SpKey.LOGIN_THE_TEST_DATA3,"a3");
                SpUtil.getBankCardInstance().putAll(map);
                break;
            case R.id.buToViewMultiple:
                //查看保存结果
                Logger.e("","获取sp："+SpUtil.getBankCardInstance().getString(SpKey.LOGIN_THE_TEST_DATA1));
                Logger.e("","获取sp："+SpUtil.getBankCardInstance().getString(SpKey.LOGIN_THE_TEST_DATA2));
                Logger.e("","获取sp："+SpUtil.getBankCardInstance().getString(SpKey.LOGIN_THE_TEST_DATA3));
                showDialog(
                        SpUtil.getBankCardInstance().getString(SpKey.LOGIN_THE_TEST_DATA1)  +
                                SpUtil.getBankCardInstance().getString(SpKey.LOGIN_THE_TEST_DATA2)+
                        SpUtil.getBankCardInstance().getString(SpKey.LOGIN_THE_TEST_DATA3)
                );
                break;
            case R.id.buDeleteMultiple:
                //删除保存的多条数据
                SpUtil.getBankCardInstance().clear();
                break;

        }
    }
}
