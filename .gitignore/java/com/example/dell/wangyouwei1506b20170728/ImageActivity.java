package com.example.dell.wangyouwei1506b20170728;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dell.wangzhenyu1506b20170728.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * ImageLoader 加载图片，并将图片存入sdcard
 */

public class ImageActivity extends com.youth.banner.loader.ImageLoader{
    ImageLoader imageLoader;
    DisplayImageOptions options;
    public ImageActivity(Context context) {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(context);
        //将configuration配置到imageloader中
        imageLoader= ImageLoader.getInstance();
        imageLoader.init(configuration);
        options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .build();
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
