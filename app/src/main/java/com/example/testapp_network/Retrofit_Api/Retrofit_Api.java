package com.example.testapp_network.Retrofit_Api;

import com.example.testapp_network.Api_interface.Api_interface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Api {

    public static Api_interface RETROFIT_API_INSTANCE() {
        Api_interface api = null;
        Gson gson = new GsonBuilder().setLenient().serializeNulls().create();
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        //   OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api_interface.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        return api = retrofit.create(Api_interface.class);

    }

    public static Retrofit retrofit = null;

    public static Retrofit getRetrofit (String url){

        if(retrofit==null){

            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
