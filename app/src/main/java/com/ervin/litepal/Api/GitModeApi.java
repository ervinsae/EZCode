package com.ervin.litepal.Api;

import com.ervin.litepal.Model.GitModel;
import com.ervin.litepal.Request.RequestConstants;
import com.ervin.litepal.Request.RestClient;
import com.ervin.litepal.Request.User;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;

/**
 * Created by Ervin on 2015/11/20.
 */
public class GitModeApi {
    public static void request(String user, Callback<GitModel> callback){
        RestClient restClient = new RestClient(RequestConstants.GITMODEL_BASE);

        Call<GitModel> call = restClient.getApiService().getGitModel(user);
        call.enqueue(callback);
    }

    public static void request(Callback<List<GitModel>> callback){
        RestClient restClient = new RestClient(RequestConstants.GITMODEL_BASE);
        Call<List<GitModel>> call = restClient.getApiService().getGitModelList();
        call.enqueue(callback);
    }

    public static void postRequest(User user,Callback<GitModel> callback){
        RestClient restClient = new RestClient(RequestConstants.GITMODEL_BASE);
        Call<GitModel> call = restClient.getApiService().updateUser(user);
        call.enqueue(callback);
    }
}
