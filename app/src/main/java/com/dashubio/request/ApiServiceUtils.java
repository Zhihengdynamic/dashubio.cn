package com.dashubio.request;

import com.dashubio.BuildConfig;
import com.dashubio.utils.log.Logger;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Android Studio
 * User: Xinbin Zhang
 * Date: 2016-08-06
 * Time: 09:35
 */
public class ApiServiceUtils {

    private static ApiServiceUtils mInstance;
    private ApiService apiService;

    //base URL
//    public static final String BASE_URL = "http://dashubio.returnlive.com/Mobile/";
    public static final String BASE_URL = "http://dashubio.cn/Mobile/";

    private final String LOGGER_FILTER = "status";//HTTP日志过滤器


    private ApiServiceUtils() {
        /* JSON 解析 */
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient())
                .build();

        apiService = retrofit.create(ApiService.class);

    }

    /* 单例 */
    public static ApiServiceUtils getInstance() {
        if (mInstance == null) {
            synchronized (ApiServiceUtils.class) {
                if (mInstance == null) {
                    mInstance = new ApiServiceUtils();
                }
            }
        }
        return mInstance;
    }

    public ApiService getApiService() {
        return apiService;
    }

    private OkHttpClient okHttpClient() {
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//Debug
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);//Release
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        if (BuildConfig.DEBUG) {
            //Debug模式时添加 Stetho network helpers
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }


    // 可以通过实现 Logger 接口更改日志保存位置
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
           // if (!StringUtils.isEmpty(message) && message.contains(LOGGER_FILTER)) {
                Logger.i(message);
           // }
        }
    });


}
