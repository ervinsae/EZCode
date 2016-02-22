package com.ervin.litepal.Ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ervin.litepal.Api.GitModeApi;
import com.ervin.litepal.Api.LoginApi;
import com.ervin.litepal.Model.GitModel;
import com.ervin.litepal.Model.LoginData;
import com.ervin.litepal.R;
import com.ervin.litepal.Request.RequestBody;
import com.ervin.litepal.Request.RequestConstants;
import com.ervin.litepal.Request.RestClient;
import com.ervin.litepal.Request.User;
import com.ervin.litepal.Utils.Md5;
import com.ervin.litepal.table.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Ervin on 2015/11/19.
 */
public class RetrofitTest extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.ref_get)
    TextView tv_ref_get;
    @Bind(R.id.ref_get2)
    TextView tv_ref_get2;
    @Bind(R.id.ref_login)
    TextView tv_ref_login;
    @Bind(R.id.ref_post)
    TextView tv_ref_post;
    @Bind(R.id.test_json)
    TextView test_json;
    @Bind(R.id.test_notification)
    TextView test_notity;


    private NotificationReceiver nReceiver;
    RestClient retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_testing);
        ButterKnife.bind(this);
        tv_ref_get.setOnClickListener(this);
        tv_ref_get2.setOnClickListener(this);
        tv_ref_login.setOnClickListener(this);
        tv_ref_post.setOnClickListener(this);
        test_json.setOnClickListener(this);
        test_notity.setOnClickListener(this);

        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(nReceiver,filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ref_get:
                Log.d("retrofit","clicked");
                GitModeApi.request(new Callback<List<GitModel>>() {
                    @Override
                    public void onResponse(Response<List<GitModel>> response, Retrofit retrofit) {
                        if(response != null){
                            List<GitModel> dataList = response.body();
                            for(GitModel gitModel : dataList){
                                Model model = new Model();
                                model.setName(gitModel.getLogin());
                                model.setCompany(gitModel.getOrganizationsUrl());
                                model.setEmail(gitModel.getType());
                                model.setLocation(gitModel.getAvatarUrl());
                                model.setFollowers(gitModel.getId());
                                model.save();
                            }
                            Log.d("retrofit","save all model");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                break;
            case R.id.ref_get2:
                Log.d("retrofit2", "clicked");
                GitModeApi.request("basil2style", new Callback<GitModel>() {
                    @Override
                    public void onResponse(Response<GitModel> response, Retrofit retrofit) {
                        if (response != null) {
                            GitModel gitModel = response.body();
                            Log.d("retrofit", gitModel.getName());
                            Model model = new Model();
                            model.setName(gitModel.getName());
                            model.setCompany(gitModel.getCompany());
                            model.setEmail(gitModel.getEmail());
                            model.setLocation(gitModel.getLocation());
                            model.setFollowers(gitModel.getFollowers());
                            model.save();

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                break;

            case R.id.ref_login:
                Log.d("retrofit3", "clicked");
                Md5 md5 = new Md5();
                String psw = md5.getMD5Str("123456");
                RequestBody body = new RequestBody();
                body.phoneNumber = "15820789114";
                body.password = psw;
                body.mode = "0";
                LoginApi.request(RequestConstants.LOGIN_URL, body, new Callback<LoginData>() {
                    @Override
                    public void onResponse(Response<LoginData> response, Retrofit retrofit) {
                        if (response != null) {
                            Log.d("retrofit", response.body().phoneNumber);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                break;
            case R.id.ref_post:
                Log.d("retrofit4", "clicked");
                User user = new User(2, "defunkt");
                GitModeApi.postRequest(user, new Callback<GitModel>() {
                    @Override
                    public void onResponse(Response<GitModel> response, Retrofit retrofit) {
                        if (response != null) {
                            Log.d("retrofit", response.body().getName());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                break;
            case R.id.test_json:
                Log.d("test_json", "clicked");
                String str = "{\"equipmentMac\":\"D0:31:01:B5:A7:E4\",\"equipmentId\":\"355456060583874\",\"equipmentType\":\"0\",\"phoneNumber\":\"15820789114\"}" ;
                try {
                    JSONObject json = new JSONObject(str);
                    Toast.makeText(this,json.getString("equipmentId"),Toast.LENGTH_LONG);
                    Log.d("ervin",json.optString("equipmentId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test_json", "fail");
                }
                break;

            case R.id.test_notification:
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                startActivity(intent);
                break;
        }
    }

    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("notification_event") + "n" ;
            Log.d("ervin",temp);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }
}
