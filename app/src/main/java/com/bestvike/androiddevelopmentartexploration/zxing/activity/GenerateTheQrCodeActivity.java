package com.bestvike.androiddevelopmentartexploration.zxing.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.util.CheckUtil;
import com.example.beaselibrary.util.Logger;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 生成二维码
 * hqx
 */
public class GenerateTheQrCodeActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_the_code_activity);
        findView();
    }

    private void findView(){
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        findViewById(R.id.button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String url = editText.getText().toString().trim();
        try {
            Bitmap bitmap = createQRCode(url);
            imageView.setImageBitmap(bitmap);

        }catch (Exception e){
            Logger.e("Exception",e.toString());
        }
    }
    public static Bitmap createQRCode(String url) throws Exception {

        if(CheckUtil.getInstance().isEmpty(url)){
            return null;
        }
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
