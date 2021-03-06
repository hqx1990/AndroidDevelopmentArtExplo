package com.bestvike.androiddevelopmentartexploration.scott;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.scott.myInterface.LocationListener;
import com.example.scott.bean.SclttBean;
import com.example.scott.ScottPositioning;

/**
 * 高德定位
 * hqx
 */
public class ScottActivity extends BaseActivity implements LocationListener{
    @Override
    protected void destroyPresenter() {

    }

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scott_activity);
        textView = findViewById(R.id.textView);

        ScottPositioning.getIntent().getData(this,this);
    }

    /**
     * 定位成功后返回
     * @param sclttBean  定位信息
     */
    @Override
    public void onLocationChanged(SclttBean sclttBean) {
        if(null == sclttBean){
            return;
        }
        textView.setText("定位成功："+sclttBean.getCountry()+sclttBean.getProvince()+sclttBean.getCity()+sclttBean.getDistrict()+
                "\n经纬度"+sclttBean.getLongitude()+","+sclttBean.getLatitude()
        );
    }

    /**
     * 定位失败
     * @param errorCode  失败错误码
     * @param errorInfo  失败信息
     */
    @Override
    public void toLocateFailure(String errorCode, String errorInfo) {

    }
}
