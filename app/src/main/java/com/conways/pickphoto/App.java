package com.conways.pickphoto;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by Conways on 2018/4/16.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    public void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.threadPoolSize(16);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.diskCache(new UnlimitedDiskCache(new File("")));//自定义缓存图片的地址
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }
}
