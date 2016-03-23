package com.ervin.litepal.api;

import com.ervin.litepal.model.GitModel;
import com.ervin.litepal.model.LoginData;
import com.ervin.litepal.model.User;
import com.ervin.litepal.model.movie.MovieEntity;
import com.ervin.litepal.request.RequestBody;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Ervin on 2015/11/12.
 */
public interface APIService {

    @FormUrlEncoded
    @POST("appUsersLogin")
    Call<LoginData> getLoginData(@Field("type") String type,@Field("data") String data); //2.0的写法
    //void getLoginData(@Field("phoneNumber") String phoneNumber,@Field("password") String password,@Field("mode") String mode, Callback<LoginData> callback);

    @GET("/users/{user}")
    Call<GitModel> getFeed(@Path("user") String user, Callback<GitModel> response);

    @GET("/users/{user}")
    Call<GitModel> getGitModel(@Path("user") String user);

    @GET("/users")
    Call<List<GitModel>> getGitModelList();

    @GET("/appUsersLogin?type=appUsersLogin&data={data}")
    Call<LoginData>  getLoginData(@Query("data") String data);

    @POST("/user/new")
    Call<GitModel> updateUser(@Body User user);

    @POST("/appUsersLogin")
    Call<LoginData> createUser(@Field("type") String type, @Field("data") RequestBody user);

    //只使用Retrofit请求网络，我们使用豆瓣电影的Top250做测试连接，目标地址为:https://api.douban.com/v2/movie/top250?start=0&count=10
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    //使用Rxandroid+Retrofit进行网络请求
    @GET("top250")
    Observable<MovieEntity> getRxTopMovie(@Query("start") int start, @Query("count") int count);
}
