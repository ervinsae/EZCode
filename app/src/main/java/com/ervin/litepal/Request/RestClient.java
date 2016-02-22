package com.ervin.litepal.Request;

import com.ervin.litepal.Api.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Ervin on 2015/11/12.
 */
public class RestClient {
    private APIService apiService;
    public RestClient(String url){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).build();

        apiService = retrofit.create(APIService.class);
    }

    public APIService getApiService(){
        return apiService;
    }

}
