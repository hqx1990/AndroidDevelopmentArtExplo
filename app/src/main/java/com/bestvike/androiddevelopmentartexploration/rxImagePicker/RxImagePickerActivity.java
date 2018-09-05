package com.bestvike.androiddevelopmentartexploration.rxImagePicker;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.bestvike.androiddevelopmentartexploration.zxing.decoding.RGBLuminanceSource;
import com.bumptech.glide.Glide;
import com.example.beaselibrary.base.BaseActivity;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder;
import com.qingmei2.rximagepicker_extension_wechat.ui.WechatImagePickerActivity;
import com.qingmei2.rximagepicker_extension_zhihu.ZhihuConfigurationBuilder;
import com.qingmei2.rximagepicker_extension_zhihu.ui.ZhihuImagePickerActivity;

import java.util.Hashtable;

import io.reactivex.functions.Consumer;

/**
 * 图片库RxImagePicker应用
 */
public class RxImagePickerActivity extends BaseActivity {

    private ImageView imageView ;
    private SystemImagePicker imagePicker;
    private WechatImagePicker wechatImagePicker;//微信主题
    private ZhihuImagePicker zhihuImagePicker;//知乎主题

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_imagepicker_activity);
        findView();
        getData();
    }

    private void findView(){
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        imageView = findViewById(R.id.imageView);

    }

    private void getData(){
        // 基础组件
        imagePicker = new RxImagePicker.Builder()
                .with(this)
                .build()
                .create(SystemImagePicker.class);

        // 微信图片选择器
        wechatImagePicker =  new RxImagePicker.Builder()
                .with(this)
                .addCustomGallery(
                        WechatImagePicker.KEY_WECHAT_PICKER_ACTIVITY,
                        WechatImagePickerActivity.class,
                        new WechatConfigrationBuilder(MimeType.ofImage(), false)
                                .maxSelectable(1)	//最大可选图片数
                                .spanCount(4)		//每行展示四张图片
                                .countable(false)	//关闭计数模式
                                .theme(R.style.Wechat)	//微信主题
                                .build()
                )
                .build()
                .create(WechatImagePicker.class);

        //知乎主题
        zhihuImagePicker = new RxImagePicker.Builder()
                .with(this)
                .addCustomGallery(		//注入日间模式主题的UI
                        ZhihuImagePicker.KEY_ZHIHU_PICKER_NORMAL,
                        ZhihuImagePickerActivity.class,
                        new ZhihuConfigurationBuilder(MimeType.ofImage(), false)
                                .maxSelectable(9)	//最大可选择数量
//                                .countable(true)	//可计数
                                .spanCount(3)		//每行图片数量
                                .theme(R.style.Zhihu_Normal)	//日间主题
                                .build()
                )
                .addCustomGallery(		//注入夜间模式主题的UI
                        ZhihuImagePicker.KEY_ZHIHU_PICKER_DRACULA,
                        ZhihuImagePickerActivity.class,
                        new ZhihuConfigurationBuilder(MimeType.ofImage(), false)
                                .spanCount(3)	  //每行图片数量
                                .maxSelectable(1) //最大可选择数量
                                .theme(R.style.Zhihu_Dracula)	//夜间主题
                                .build()
                )
                .build()
                .create(ZhihuImagePicker.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                // 基础组件
                imagePicker.openGallery()               //打开系统相册选取图片
                        .subscribe(new Consumer<Result>() {
                            @Override
                            public void accept(Result result) throws Exception {
                                // 做您想做的，比如将选取的图片展示在ImageView中
                                Glide.with(RxImagePickerActivity.this)
                                        .load(result.getUri())
                                        .into(imageView);

                                identifyAlbumQrCode(result.getUri());
                            }
                        });
                break;
            case R.id.button2:
                // 自定义UI层基础组件

                break;
            case R.id.button3:
                // 知乎图片选择器

                //打开日间主题相册选取图片，或者调用imagePicker.openGalleryAsDracula()打开夜间主题
                zhihuImagePicker.openGalleryAsNormal()
                        .subscribe(new Consumer<Result>() {
                            @Override
                            public void accept(Result result) throws Exception {
                                // 做您想做的，比如将选取的图片展示在ImageView中
                                Glide.with(RxImagePickerActivity.this)
                                        .load(result.getUri())
                                        .into(imageView);
                                identifyAlbumQrCode(result.getUri());
                            }
                        });
                break;
            case R.id.button4:
                // 微信图片选择器
                wechatImagePicker.openGallery()               //打开微信相册选取图片
                        .subscribe(new Consumer<Result>() {
                            @Override
                            public void accept(Result result) throws Exception {
                                // 做您想做的，比如将选取的图片展示在ImageView中
                                Glide.with(RxImagePickerActivity.this)
                                        .load(result.getUri())
                                        .into(imageView);
                                identifyAlbumQrCode(result.getUri());
                            }
                        });

                break;

        }
    }

    private void identifyAlbumQrCode(final Uri uriPath){

        final String strPath = getRealPathFromUri(uriPath);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                com.google.zxing.Result result = scanningImage(strPath);
                if(null != result){
                    showToast("识别成功："+result.getText());
                }else{
                    showToast("识别失败,图片中没有二维码");
                }
            }
        });

    }

    public String getRealPathFromUri(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = uri.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;

    }



    /**
     * 扫描二维码图片的方法
     * @param path
     * @return
     */
    public com.google.zxing.Result scanningImage(String path) {
        if(TextUtils.isEmpty(path)){
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
