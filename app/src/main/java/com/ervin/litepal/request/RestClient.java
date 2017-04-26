package com.ervin.litepal.request;

import android.os.Build;
import android.util.Log;

import com.ervin.litepal.api.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.*;
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


    public static void checkHttps(String url){

        builder = new OkHttpClient.Builder();
        builder.addInterceptor(new LoggingInterceptor());//
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);

        if (url.startsWith("https")) {
            try {
                //Okhttp默认是可以访问https的，不过支持的网站都是经过CA机构颁发的证书，如果要访问自制证书的https网站则会报错
                /*javax.net.ssl.SSLHandshakeException:
                java.security.cert.CertPathValidatorException:
                Trust anchor for certification path not found.*/

                //信任所有
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustManagers, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory);
                builder.hostnameVerifier((hostname, session) -> true);

                /**
                 * Okhttp3.0中https的请求模式配置
                 * https://github.com/square/okhttp/wiki/HTTPS
                 */
                //仅支持android5.0
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                            .tlsVersions(TlsVersion.TLS_1_2)
                            .cipherSuites(
                                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                            .build();
                    builder.connectionSpecs(Collections.singletonList(spec));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ervin",e.getMessage());
            }
            Log.i("RestClient","request is a https");
        }
        client = builder.build();

    }

    //增加一个信任访问所有自制签名的https网址
    static TrustManager[] trustManagers = new TrustManager[]{new TrustEveryOneManager()};
    static class TrustEveryOneManager implements X509TrustManager{
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};//StackOverflow
        }
    }

}
