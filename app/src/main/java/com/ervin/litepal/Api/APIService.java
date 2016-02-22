package com.ervin.litepal.Api;

import com.ervin.litepal.Model.GitModel;
import com.ervin.litepal.Model.LoginData;
import com.ervin.litepal.Request.RequestBody;
import com.ervin.litepal.Request.User;

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

/**
 * Created by Ervin on 2015/11/12.
 */
public interface APIService {

    @FormUrlEncoded
    @POST("/appUsersLogin")
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
    Call<LoginData> createUser(String type,@Body RequestBody user);
}
