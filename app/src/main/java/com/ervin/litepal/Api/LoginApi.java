package com.ervin.litepal.Api;

import android.util.Log;

import com.ervin.litepal.Model.LoginData;
import com.ervin.litepal.Request.RequestBody;
import com.ervin.litepal.Request.RequestConstants;
import com.ervin.litepal.Request.RestClient;

import org.json.JSONObject;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;

/**
 * Created by Ervin on 2015/11/12.
 */
public class LoginApi {
    public static void request(Map<String,Object> params,Callback<LoginData> callback){
        RestClient restClient = new RestClient(RequestConstants.BASE_URL);
        JSONObject json =  new JSONObject(params);

        String jsonParams = json.toString();
        Log.d("ervin", "jsonParams:" + jsonParams);

        restClient.getApiService().getLoginData(RequestConstants.LOGIN_URL,jsonParams).enqueue(callback);
    }

    public static void request(Callback<LoginData> callback){
        RestClient restClient = new RestClient(RequestConstants.BASE_URL);

        Call<LoginData> call = restClient.getApiService().getLoginData("'phoneNumber':'15820789114','password':'E10ADC3949BA59ABBE56E057F20F883E','mode':'0'");
        call.enqueue(callback);
    }

    public static void request(String type,RequestBody body,Callback<LoginData> callback){
        RestClient restClient = new RestClient(RequestConstants.BASE_URL);

        restClient.getApiService().createUser(type,body).enqueue(callback);
    }
}
