package com.bestvike.androiddevelopmentartexploration.rxImagePicker;

import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;

import io.reactivex.Observable;

/**
 * 使用知乎主题
 */
public interface ZhihuImagePicker {
    String KEY_ZHIHU_PICKER_NORMAL = "key_zhihu_picker_theme_normal";
    String KEY_ZHIHU_PICKER_DRACULA = "key_zhihu_picker_theme_dracula";

    @Gallery(viewKey = KEY_ZHIHU_PICKER_NORMAL)		//日间主题
    Observable<Result> openGalleryAsNormal();

    @Gallery(viewKey = KEY_ZHIHU_PICKER_DRACULA)	//夜间主题
    Observable<Result> openGalleryAsDracula();

    @Camera    //打开相机
    Observable<Result> openCamera();
}
