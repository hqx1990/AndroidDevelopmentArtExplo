package com.bestvike.androiddevelopmentartexploration.rxImagePicker;

import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;

import io.reactivex.Observable;

/**
 * 使用微信主题
 */
public interface WechatImagePicker {
    String KEY_WECHAT_PICKER_ACTIVITY = "key_wechat_picker";

    @Gallery(viewKey = KEY_WECHAT_PICKER_ACTIVITY)
    Observable<Result> openGallery();

    @Camera
    Observable<Result> openCamera();
}
