package com.msf.bakingtime.network;

import com.msf.bakingtime.BuildConfig;

import retrofit2.Retrofit;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .build();
        }
        return retrofit;
    }

}