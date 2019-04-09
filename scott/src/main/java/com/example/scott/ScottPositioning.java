package com.example.scott;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.beaselibrary.util.Logger;
import com.example.scott.bean.SclttBean;
import com.example.scott.exception.ScottException;
import com.example.scott.myInterface.LocationListener;

/**
 * 高德定位封装
 * //                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
 * //                        aMapLocation.getLatitude();//获取纬度
 * //                        aMapLocation.getLongitude();//获取经度
 * //                        aMapLocation.getAccuracy();//获取精度信息
 * //                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
 * //                        aMapLocation.getCountry();//国家信息
 * //                        aMapLocation.getProvince();//省信息
 * //                        aMapLocation.getCity();//城市信息
 * //                        aMapLocation.getDistrict();//城区信息
 * //                        aMapLocation.getStreet();//街道信息
 * //                        aMapLocation.getStreetNum();//街道门牌号信息
 * //                        aMapLocation.getCityCode();//城市编码
 * //                        aMapLocation.getAdCode();//地区编码
 * //                        aMapLocation.getAoiName();//获取当前定位点的AOI信息
 * //                        aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
 * //                        aMapLocation.getFloor();//获取当前室内定位的楼层
 * //                        aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
 * //                        //获取定位时间
 * //                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 * //                        Date date = new Date(aMapLocation.getTime());
 * //                        df.format(date);
 *
 *
 */
public class ScottPositioning {

    public static ScottPositioning getIntent(){
        return new ScottPositioning();
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    public AMapLocationClientOption mLocationOption = null;

    private int locationCounts=1;//定位次数
    /**
     * 获取定位信息
     */
    public void getData(Context context , final LocationListener locationListener){
        Context mContext ;
        if(null != context){
            mContext = context.getApplicationContext();
        }else{
            new ScottException("context不能为空");
            return;
        }

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //监听定位结果
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        locationCounts = 1;

                        SclttBean sclttBean = new SclttBean();
                        sclttBean.setLatitude(String.valueOf(aMapLocation.getLatitude()));
                        sclttBean.setLongitude(String.valueOf(aMapLocation.getLongitude()));
                        sclttBean.setCountry(aMapLocation.getCountry());
                        sclttBean.setProvince(aMapLocation.getProvince());
                        sclttBean.setCity(aMapLocation.getCity());
                        sclttBean.setCityCode(aMapLocation.getCityCode());
                        sclttBean.setDistrict(aMapLocation.getDistrict());
                        sclttBean.setAdCode(aMapLocation.getAdCode());
                        sclttBean.setStreet(aMapLocation.getStreet());
                        Logger.e("-----","定位成功："+sclttBean.getCountry()+sclttBean.getProvince()+sclttBean.getCity()+sclttBean.getDistrict()+
                                "；经纬度："+sclttBean.getLongitude()+","+sclttBean.getLatitude());
                        locationListener.onLocationChanged(sclttBean);

                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        locationCounts ++;
                        if(locationCounts <= 3){
                            mLocationClient.startLocation();
                            return;
                        }else{
                            locationListener.toLocateFailure(String.valueOf(aMapLocation.getErrorCode()),aMapLocation.getErrorInfo());
                        }
                    }
                }else{
                    locationCounts ++;
                    if(locationCounts <= 3){
                        mLocationClient.startLocation();
                        return;
                    }else{
                        locationListener.toLocateFailure("","获取定位信息失败");
                    }
                }
                mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(mContext);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();

        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        //设置定位模式，SDK默认高精度定位模式
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        // 会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);

        /*
        仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，
        需要在室外环境下才可以成功定位。注意，自 v2.9.0 版本之后，仅设备定位模式下支持返回地址描述信息。
         */
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);



        //设置单次定位
        //获取一次定位结果,该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);



        /*
        自定义连续定位
SDK默认采用连续定位模式，时间间隔2000ms。如果您需要自定义调用间隔：
设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
         */
//        mLocationOption.setInterval(1000);



        //设置是否返回地址信息（默认返回地址信息）,,,,,,设置定位同时是否需要返回地址描述
        mLocationOption.setNeedAddress(true);




        //设置定位请求超时时间，默认为30秒。
        mLocationOption.setHttpTimeOut(20000);


        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);


        //启动定位,给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }
}
