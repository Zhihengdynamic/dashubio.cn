/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dashubio.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.dashubio.BuildConfig;
import com.dashubio.utils.Utils;
import com.dashubio.utils.log.Logger;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;


import java.io.File;


/**
 * Android Main Application
 */
public class AppApplication extends MultiDexApplication {

    private final static String TAG = "DaShuBio";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        this.initializeLogger();
        Stetho.initializeWithDefaults(this);
        //初始化图片加载的UIL
        initImageLoader(getApplicationContext());
    }


    private void initializeLogger() {
//        Settings loggerSettings = Logger.init(TAG);
//        if (!BuildConfig.DEBUG) {
//           // loggerSettings.logLevel(LogLevel.NONE); // default LogLevel.FULL
//        }
        if (BuildConfig.DEBUG) {
            Logger.setOutputLevel(Logger.DEBUG);
        } else {
            Logger.setOutputLevel(Logger.WARNING);
        }
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        File defaultCacheDir = StorageUtils.getCacheDirectory(context);
        File cacheDir = new File(defaultCacheDir, Utils.IMAGE_CACHE_DIR);
        Logger.i("------initImageLoader()------>" + cacheDir.getAbsolutePath());
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCache(new UnlimitedDiskCache(cacheDir));
//        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static Context getContext() {
        return context;
    }

}
