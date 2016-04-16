package com.ervin.litepal.request;

import android.util.Log;

import com.ervin.litepal.api.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ervin on 2015/11/12.
 */
public class RestClient {
    private static APIService apiService;
    private static final int DEFAULT_TIMEOUT = 5;
    static OkHttpClient.Builder builder;
    static OkHttpClient client;


    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    public RestClient(String url){
        checkHttps(url);
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        apiService = retrofit.create(APIService.class);
    }

    public static APIService RestRxClient(String url,boolean fromatjson){
        Retrofit retrofit;
        checkHttps(url);

        if(fromatjson) {
            retrofit = new Retrofit.Builder().client(client).baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }else{
            retrofit = new Retrofit.Builder().client(client).baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }
        apiService = retrofit.create(APIService.class);
        return apiService;
    }

    public APIService getApiService(){
        return apiService;
    }

   /* public static OkHttpClient.Builder setTimeOut(){
        //手动创建一个OkHttpClient并设置超时时间(注意Ok3之后改动很大)
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);

        return builder;
    }*/

    public static void checkHttps(String url){

        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);

        if (url.startsWith("https")) {
            try {
                /*SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustManagers, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory);*/

                /**
                 * Okhttp3.0中https的请求模式配置
                 * https://github.com/square/okhttp/wiki/HTTPS
                 */

                ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .cipherSuites(
                                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                        .build();
                builder.connectionSpecs(Collections.singletonList(spec));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("RestClient","request is a https");
        }
        client = builder.build();

    }

    //增加一个自制签名证书 覆盖google默认的证书检查机制(Ok3中已经集成了自定义的签名证书CustomTrust,现无效)
    static TrustManager[] trustManagers = new TrustManager[]{new TrustEveryOneManager()};
    @Deprecated
    static class TrustEveryOneManager implements X509TrustManager{
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}
