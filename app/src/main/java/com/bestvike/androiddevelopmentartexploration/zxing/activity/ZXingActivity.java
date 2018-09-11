package com.bestvike.androiddevelopmentartexploration.zxing.activity;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import com.bestvike.androiddevelopmentartexploration.R;
import com.bestvike.androiddevelopmentartexploration.rxImagePicker.SystemImagePicker;
import com.bestvike.androiddevelopmentartexploration.zxing.camera.CameraManager;
import com.bestvike.androiddevelopmentartexploration.zxing.decoding.CaptureActivityHandler;
import com.bestvike.androiddevelopmentartexploration.zxing.decoding.InactivityTimer;
import com.bestvike.androiddevelopmentartexploration.zxing.decoding.RGBLuminanceSource;
import com.bestvike.androiddevelopmentartexploration.zxing.view.ViewfinderView;
import com.example.beaselibrary.base.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import io.reactivex.functions.Consumer;

/**
 * zxing   扫描二维码，识别相册中的二维码
 */
public class ZXingActivity extends BaseActivity  implements SurfaceHolder.Callback {


    private SystemImagePicker imagePicker;//开启相册，基础控件

    private ViewfinderView viewfinderView;//扫描控件
    private ImageButton btnFlash;//开启闪光灯
    private boolean isFlashOn = false;//判断是否开启了闪光灯
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private boolean playBeep;
    private boolean vibrate;
    private CaptureActivityHandler handler;
    private MediaPlayer mediaPlayer;
    private static final float BEEP_VOLUME = 0.10f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxing_activity);
        CameraManager.init(getApplication());
        findview();
        getData();

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    private void findview(){
        findViewById(R.id.btn_album).setOnClickListener(this);

        viewfinderView = findViewById(R.id.viewfinder_content);
        btnFlash =  findViewById(R.id.btn_flash);
        btnFlash.setOnClickListener(this);

    }

    private void getData(){
        // 基础组件配置
        imagePicker = new RxImagePicker.Builder()
                .with(this)
                .build()
                .create(SystemImagePicker.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_album:
                //点击相册，打开系统相册选取图片
                imagePicker.openGallery()
                        .subscribe(new Consumer<Result>() {
                            @Override
                            public void accept(Result result) throws Exception {
                                // 做您想做的，比如将选取的图片展示在ImageView中
//                                Glide.with(ZXingActivity.this)
//                                        .load(result.getUri())
//                                        .into(imageView);
                                identifyAlbumQrCode(result.getUri());
                            }
                        });
                break;

            case R.id.btn_flash:
                //开启、关闭闪光灯
                try {
                    boolean isSuccess = CameraManager.get().setFlashLight(!isFlashOn);
                    if(!isSuccess){
                        showToast( "暂时无法开启闪光灯");
                        return;
                    }
                    if (isFlashOn) {
                        // 关闭闪光灯
                        btnFlash.setImageResource(R.drawable.flash_off);
                        isFlashOn = false;
                    } else {
                        // 开启闪光灯
                        btnFlash.setImageResource(R.drawable.flash_on);
                        isFlashOn = true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    //--------------------------------------以下为zxing处理相册中的二维码----------------------------------------------------------

    private void identifyAlbumQrCode(final Uri uriPath){

        final String strPath = getRealPathFromUri(uriPath);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                com.google.zxing.Result result = scanningImage(strPath);
                if(null != result){
//                    showToast("识别成功："+result.getText());
                    successful(result.getText());
                }else{
                    showToast("识别失败,图片中没有二维码");
                }
            }
        });

    }

    public String getRealPathFromUri(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri,
                new String[]{MediaStore.Images.ImageColumns.DATA},
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



    //--------------------------------------以下为zxing扫描方法----------------------------------------------------------


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView =  findViewById(R.id.scanner_view);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(com.google.zxing.Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();//扫描的内容

        successful(resultString);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 连续扫描
     */
    protected void restartPreview(){
        if (handler != null)
        {//实现连续扫描
            Message mes = new Message();
            mes.what = R.id.restart_preview;
            handler.handleMessage(mes);
        }
    }

    //扫描成功后或者识别相册二维码成功后调用
    protected void successful(String str){}

}
