package com.dashubio.request;

import com.dashubio.model.HttpResult;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import rx.Observable;

/**
 * Created by victor on 04/01/16.
 */
public interface CacheProviders {


    Observable<HttpResult> toLogin(Observable<HttpResult> loginResp, DynamicKey username, EvictDynamicKey evictUsername);


}
