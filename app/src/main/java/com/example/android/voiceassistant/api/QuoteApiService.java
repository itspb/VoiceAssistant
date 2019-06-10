package com.example.android.voiceassistant.api;

import com.example.android.voiceassistant.pojo.Quote;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface QuoteApiService {
    @GET("api/1.0/?method=getQuote&format=json&lang=ru")
    Observable<Quote> getQuote();
}
