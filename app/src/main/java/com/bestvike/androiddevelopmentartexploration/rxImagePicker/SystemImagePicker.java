package com.bestvike.androiddevelopmentartexploration.rxImagePicker;

import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;

import io.reactivex.Observable;

public interface SystemImagePicker {
    @Gallery//打开相册
    Observable<Result> openGallery();

    @Camera//打开相机
    Observable<Result> openCamera();
}
