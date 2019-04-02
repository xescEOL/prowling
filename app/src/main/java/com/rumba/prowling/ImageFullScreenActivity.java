package com.rumba.prowling;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class ImageFullScreenActivity extends Activity {

    ImageView imageView;

    boolean isImageFitCenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagefullscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = (ImageView) findViewById(R.id.imgShow);
        String imgIntent = getIntent().getStringExtra("img");
        Glide.with(getApplicationContext())
                .load(imgIntent)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitCenter) {
                    isImageFitCenter=false;
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }else{
                    isImageFitCenter=true;
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                }
            }
        });

    }
}