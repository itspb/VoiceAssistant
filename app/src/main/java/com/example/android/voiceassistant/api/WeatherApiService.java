package com.example.android.voiceassistant.api;

import com.example.android.voiceassistant.pojo.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("v1/current.json?key=4c8c9d28374a46c4a46154320190706")
    Observable<Weather.ApiResponse> getCurrentWeather(@Query("q") String city, @Query("lang") String lang);
}
