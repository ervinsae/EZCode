package com.ervin.litepal.request;

import com.ervin.litepal.api.APIService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Ervin on 2015/11/12.
 */
public class RestClient {
    private APIService apiService;
    public RestClient(String url){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        apiService = retrofit.create(APIService.class);
    }

    public APIService getApiService(){
        return apiService;
    }

}
