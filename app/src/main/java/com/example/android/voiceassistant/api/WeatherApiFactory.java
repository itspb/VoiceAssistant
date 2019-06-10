package com.example.android.voiceassistant.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiFactory {
    private static WeatherApiFactory weatherApiFactory;
    private static Retrofit retrofit;

    private WeatherApiFactory() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://api.apixu.com/")
                .build();
    };

    public static WeatherApiFactory getInstance() {
        if (weatherApiFactory == null) {
            weatherApiFactory = new WeatherApiFactory();
        }
        return weatherApiFactory;
    }

    public WeatherApiService getApiService() {
        return retrofit.create(WeatherApiService.class);
    }
}
