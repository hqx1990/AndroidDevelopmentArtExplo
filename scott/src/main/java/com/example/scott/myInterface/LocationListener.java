package com.example.scott.myInterface;


import com.example.scott.bean.SclttBean;

public interface LocationListener {
    void onLocationChanged(SclttBean sclttBean);//定位成功
    void toLocateFailure(String errorCode , String errorInfo);//定位失败
}
