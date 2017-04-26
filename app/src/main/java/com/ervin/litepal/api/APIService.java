package com.ervin.litepal.api;

import com.ervin.litepal.model.GitModel;
import com.ervin.litepal.model.LoginData;
import com.ervin.litepal.model.User;
import com.ervin.litepal.model.meizhi.MeizhiEntity;
import com.ervin.litepal.model.meizhi.VideoEntity;
import com.ervin.litepal.model.movie.MovieEntity;
import com.ervin.litepal.request.RequestBody;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Ervin on 2015/11/12.
 */
public interface APIService {

    @FormUrlEncoded
    @POST("appUsersLogin")
    Call<LoginData> getLoginData(@Field("type") String type, @Field("data") String data); //2.0的写法
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

    //Gank.io API 进行请求
    @GET("data/福利/" + 10 + "/{page}")
    Observable<MeizhiEntity> getMeizhiData( @Path("page") int page);   //https://gank.io/api/data/福利/10/1

   /* @GET("/day/{year}/{month}/{day}") Observable<GankData> getGankData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);//https://gank.io/api/day/2016/02/15*/

    @GET("data/休息视频/" + 10 + "/{page}")
    Observable<VideoEntity> getVedioData(@Path("page") int page);

    //上传文件
    @Multipart
    @POST("/upload")
    Call<Void> upLoad(@Part("type") String type, @Part MultipartBody.Part file);
}
