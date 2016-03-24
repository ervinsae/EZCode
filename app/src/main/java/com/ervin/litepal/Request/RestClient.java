package com.ervin.litepal.request;

import com.ervin.litepal.api.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Ervin on 2015/11/12.
 */
public class RestClient {
    private static APIService apiService;
    private static final int DEFAULT_TIMEOUT = 5;
    static OkHttpClient client;

    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    public RestClient(String url){

        setTimeOut();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        apiService = retrofit.create(APIService.class);
    }

    public static APIService RestRxClient(String url,boolean fromatjson){
        Retrofit retrofit;
        setTimeOut();
        if(fromatjson) {
            retrofit = new Retrofit.Builder().client(client).baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }else{
            retrofit = new Retrofit.Builder().client(client).baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }
        apiService = retrofit.create(APIService.class);
        return apiService;
    }

    public APIService getApiService(){
        return apiService;
    }

    public static void setTimeOut(){
        //手动创建一个OkHttpClient并设置超时时间
        client = new OkHttpClient();
        client.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
    }

}
