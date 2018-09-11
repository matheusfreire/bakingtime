package com.msf.bakingtime.network;

import com.msf.bakingtime.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit mRetrofit;

    public static Retrofit getInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

}