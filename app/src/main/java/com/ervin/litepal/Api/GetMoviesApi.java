package com.ervin.litepal.api;

import com.ervin.litepal.model.movie.MovieEntity;
import com.ervin.litepal.request.RequestConstants;
import com.ervin.litepal.request.RestClient;

import retrofit.Callback;

/**
 * Created by Ervin on 2016/3/22.
 */
public class GetMoviesApi {

    public static void request(int start, int count, Callback<MovieEntity> callback){

        RestClient restClient = new RestClient(RequestConstants.DOUBANAPI_URL);
        restClient.getApiService().getTopMovie(start,count).enqueue(callback);
    }
}
