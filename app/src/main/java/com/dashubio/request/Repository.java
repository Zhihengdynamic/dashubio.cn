package com.dashubio.request;


import com.dashubio.app.AppApplication;
import com.dashubio.utils.Utils;

import java.io.File;

import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;


public class Repository {
    public static final int PER_PAGE = 25;//分页时每页大小

    private static Repository mInstance;

    /* 单例 */
    public static Repository getInstance() {
        if (mInstance == null) {
            synchronized (Repository.class) {
                if (mInstance == null) {
                    mInstance = new Repository(Utils.getExternalCacheDir(AppApplication.getContext()));
                }
            }
        }
        return mInstance;
    }

    private final CacheProviders mCacheProviders;
    private final ApiServiceUtils mApiServiceUtils;
    private final ApiService mApiService;

    private Repository(File cacheDir) {
        mCacheProviders = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(CacheProviders.class);

        mApiServiceUtils = ApiServiceUtils.getInstance();
        mApiService = mApiServiceUtils.getApiService();
    }

//    public Observable<BaseResp> toLogin(String username, String password, final boolean update) {
//        return mCacheProviders.toLogin(mApiService.login(username, password), new DynamicKey(username), new EvictDynamicKey(true));
//    }


}
