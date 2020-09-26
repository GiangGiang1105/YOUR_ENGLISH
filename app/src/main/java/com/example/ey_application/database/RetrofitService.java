package com.example.ey_application.database;


import com.example.ey_application.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitService {
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://eyapplycation.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build();
    public static <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
    private static Retrofit retrofitEnglish = new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v1/entries/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build();
    public static <S> S createServiceEnglish(Class<S> serviceClass){
        return retrofitEnglish.create(serviceClass);
    }
}
