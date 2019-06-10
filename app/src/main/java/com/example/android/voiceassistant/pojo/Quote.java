package com.example.android.voiceassistant.pojo;

import android.support.v4.util.Consumer;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Quote {
    @SerializedName("quoteText")
    private String quoteText;
    @SerializedName("quoteAuthor")
    private String quoteAuthor;

    public String getQuoteText() {
        return quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

}
