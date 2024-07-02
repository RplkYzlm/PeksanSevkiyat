package com.replik.peksansevkiyat.Interface;

import com.replik.peksansevkiyat.Transection.GlobalVariable;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIPdfClient {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.apiPdfUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
