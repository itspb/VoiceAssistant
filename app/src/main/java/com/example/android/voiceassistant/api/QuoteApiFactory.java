package com.example.android.voiceassistant.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteApiFactory {
    private static QuoteApiFactory quoteApiFactory;
    private static Retrofit retrofit;

    private QuoteApiFactory() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.forismatic.com/")
                .build();
    };

    public static QuoteApiFactory getInstance() {
        if (quoteApiFactory == null) {
            quoteApiFactory = new QuoteApiFactory();
        }
        return quoteApiFactory;
    }

    public QuoteApiService getApiService() {
        return retrofit.create(QuoteApiService.class);
    }
}
