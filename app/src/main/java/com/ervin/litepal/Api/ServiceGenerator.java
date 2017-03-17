package com.ervin.litepal.api;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ervin on 2016/4/15.
 */
//大神推荐的封装方法
public class ServiceGenerator {
    public static final String API_BASE_URL = "https://your.api-base.url";
    private static final int DEFAULT_TIMEOUT = 5;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <T> T createService(Class<T> serviceClass){
        setHttpClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static void setHttpClient(){
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
    }
}
