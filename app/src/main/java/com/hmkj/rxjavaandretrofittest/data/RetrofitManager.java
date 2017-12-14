package com.hmkj.rxjavaandretrofittest.data;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/11/15.
 */

public class RetrofitManager {

    private static final String TAG = "RetrofitManager";
    private static RetrofitManager mManager;

    private static Retrofit mRetrofit;

    private static final String URL = "http://api.51clj.com";

    private RetrofitManager()
    {
        // need PRIVATE;
    }

    public static RetrofitManager getInstance()
    {
        if (mManager != null)
        {
            return mManager;
        }
        synchronized (TAG)
        {
            if (mManager != null)
            {
                return mManager;
            }
            mManager = new RetrofitManager();
        }
        return mManager;
    }


    public Retrofit getRetrofitClient()
    {
        if (mRetrofit == null)
        {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10,TimeUnit.SECONDS);
            builder.writeTimeout(10,TimeUnit.SECONDS);

            OkHttpClient client = builder.build();

            Retrofit.Builder retroBuilder = new Retrofit.Builder();
            retroBuilder.baseUrl(URL);
            retroBuilder.addConverterFactory(GsonConverterFactory.create());
            retroBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retroBuilder.client(client);

            mRetrofit = retroBuilder.build();
        }
        return mRetrofit;
    }

}
