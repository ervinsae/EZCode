package com.ervin.litepal.ui;

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

import com.ervin.litepal.R;
import com.ervin.litepal.Utils.Md5;
import com.ervin.litepal.api.GetMoviesApi;
import com.ervin.litepal.api.GitModeApi;
import com.ervin.litepal.api.LoginApi;
import com.ervin.litepal.model.GitModel;
import com.ervin.litepal.model.LoginData;
import com.ervin.litepal.model.movie.MovieEntity;
import com.ervin.litepal.model.movie.Subjects;
import com.ervin.litepal.request.RequestBody;
import com.ervin.litepal.request.RequestConstants;
import com.ervin.litepal.request.RestClient;
import com.ervin.litepal.request.User;
import com.ervin.litepal.table.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;

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
    @Bind(R.id.test_publishsubject)
    TextView test_publishSuject;
    @Bind(R.id.test_douban_api)
    TextView test_douban;


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
        test_publishSuject.setOnClickListener(this);
        test_douban.setOnClickListener(this);

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

            case R.id.ref_login: //失败
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
            case R.id.ref_post: //失败
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
            case R.id.test_publishsubject:
                //publishSubjectExample();
                RxJavaExample();
                break;
            case R.id.test_douban_api://获取豆瓣top200电影数据
                GetMoviesApi.request(0, 10, new Callback<MovieEntity>() {
                    @Override
                    public void onResponse(Response<MovieEntity> response, Retrofit retrofit) {
                        if(response != null) {
                            Log.i("ervin","返回码："+response.code());
                            if(response.body()!=null) {
                                MovieEntity movieEntity = response.body();
                                for (Subjects subjects : movieEntity.getSubjects()) {
                                    Log.i("ervin", subjects.getTitle());
                                }
                            }else{
                                Log.i("ervin",response.errorBody().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("ervin",t.getMessage());
                    }
                });
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

    //Subject
    public void publishSubjectExample(){
        PublishSubject<String> stringPublishSubject = PublishSubject.create();
        stringPublishSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RetrofitTest.this,"Observable completed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RetrofitTest.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RetrofitTest.this,s,Toast.LENGTH_LONG).show();
            }
        });
        stringPublishSubject.onNext("Hello Rxandroid World");
    }

    /**
     * RxJava 有四个基本概念：Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。
     * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
     *
     * 当观察到Observable发生变化时，Observer是需要去执行的（所以有一些回调函数之类的）,
     */
    public void RxJavaExample(){
        String[] words = {"Hello", "Rxandroid", "World"};
        Observable.from(words).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RetrofitTest.this,s,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
