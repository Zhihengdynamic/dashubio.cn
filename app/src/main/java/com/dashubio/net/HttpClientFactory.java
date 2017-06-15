package com.dashubio.net;

import java.util.WeakHashMap;

import com.dashubio.utils.Utils;


/**
 * http client工厂�??.<br>
 * 
 * You can fetch http client by using getHttpClient method easily. 
 */
public class HttpClientFactory {

    private static HttpClientFactory mInstance;
    
    private static String DASHU_CLIENT = "dashu";

    
    private WeakHashMap<String, AndroidHttpClient> mHttpClientMap;
    
    private HttpClientFactory() {
        synchronized (this) {
            mHttpClientMap = new WeakHashMap<String, AndroidHttpClient>(1);
        }
    }
    
    public static HttpClientFactory get() {
        if (mInstance == null) {
            mInstance = new HttpClientFactory();
        }
        return mInstance;
    }
    
    /**
     * Get the http client for MARKET module
     * @param userAgent customize user agent
     * @return android http client contains some default settings for android device
     */
    public AndroidHttpClient getHttpClient() {
        
        AndroidHttpClient client = mHttpClientMap.get(DASHU_CLIENT);
        if(client != null) {
            return client;
        }
        
        client = AndroidHttpClient.newInstance("");

        mHttpClientMap.put(DASHU_CLIENT, client);
        return client;
    }

    /**
     * 更新 G-Header
     * @param gHeader
     */
    public void updateMarketHeader(String gHeader) {
        AndroidHttpClient client = mHttpClientMap.get(DASHU_CLIENT);
        if(client != null) {
            client.getParams().setParameter("G-Header", gHeader);
            Utils.D("update client " + client.toString() + " g-header " + gHeader);   
        }
    }

    /**
     * 当应用�??出时 必须关闭�??有的http clients
     */
    public synchronized void close() {
        AndroidHttpClient client;
        if (mHttpClientMap.containsKey(DASHU_CLIENT)) {
            client = mHttpClientMap.get(DASHU_CLIENT);
            if (client != null) {
                client.close();
                client = null;
            }
        }
        mHttpClientMap.clear();
        mInstance = null;
    }
}