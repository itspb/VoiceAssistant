package com.example.android.voiceassistant;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;


import com.example.android.voiceassistant.api.QuoteApiFactory;
import com.example.android.voiceassistant.api.QuoteApiService;
import com.example.android.voiceassistant.api.WeatherApiFactory;
import com.example.android.voiceassistant.api.WeatherApiService;
import com.example.android.voiceassistant.pojo.Quote;
import com.example.android.voiceassistant.pojo.Weather;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AIViewModel extends AndroidViewModel {

    private MutableLiveData<String> result;
    private CompositeDisposable compositeDisposable;

    public AIViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        result = new MutableLiveData<>();
    }

    public MutableLiveData<String> getResult() {
        return result;
    }

    public void getAnswer (String userQuestion) {
        boolean isApiRequired = false;

        LinkedHashMap<String, String> mapOfQuestionsAndAnswers = new LinkedHashMap<String, String>() {{
            put("привет", "ку");
            put("как дела", "дела потихоньку");
            put("чем занимаешься", "отвечаю тебе");
            put("откуда ты", "я из северной столицы");
            put("есть ли жизнь на марсе", "жизни на Марсе нет, но возможно была когда-то");
            put("кто президент россии", "президент России киборг, конечно же");
            put("какого цвета небо", "цвета у неба нет, но большинство людей видит его синим, глупцы");
            put("какой сегодня день", Utils.getCurrentDay());
            put("сколько сейчас времени", Utils.getCurrentTime());
        }};
        userQuestion = userQuestion.toLowerCase();
        final ArrayList<String> answers = new ArrayList<>();
        for (String questionFromMap : mapOfQuestionsAndAnswers.keySet()) {
            if (userQuestion.contains(questionFromMap)) {
                answers.add(mapOfQuestionsAndAnswers.get(questionFromMap));
            }
        }

        Pattern cityPattern = Pattern.compile("какая погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(userQuestion);
        if (matcher.find()) {
            isApiRequired = true;
            String cityName = matcher.group(1);
            getWeather(cityName);
        }

        if (userQuestion.contains("расскажи афоризм")) {
            isApiRequired = true;
            getQuote();
        }

        if (!answers.isEmpty()) {
            // Список ответов не пуст, выводим его, ответы от API выведутся отдельно, как только будут получены
            result.setValue(Utils.listToString(answers, ", "));
        } else if (!isApiRequired) {
            // Список пуст и запросов к API не было, т.е. ни один вопрос не опознан
            result.setValue("На это мне нечего ответить, я еще только учусь");
        }

    }

    public void getWeather (String city) {
        WeatherApiFactory weatherApiFactory = WeatherApiFactory.getInstance();
        WeatherApiService weatherApiService = weatherApiFactory.getApiService();
        Disposable disposable = weatherApiService.getCurrentWeather(city, "ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather.ApiResponse>() {
                    @Override
                    public void accept(Weather.ApiResponse apiResponse) throws Exception {
                        Weather.Forecast forecast = apiResponse.getForecast();
                        String answer = "Там сейчас " + forecast.getCondition().getWeatherDescription().toLowerCase()
                                + ", около " + forecast.getTemperature().intValue() + " градусов, ощущается как "
                                + forecast.getFeelsLikeTemperature().intValue() + ", ветер "
                                + Utils.getWindDirection(forecast.getWindDirection()) + ", " +
                                Utils.getWindSpeedMs(forecast.getWindSpeedKph()) + " м/секунду";
                        result.setValue(answer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.setValue("О таком городе мне неизвестно :(");
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void getQuote() {
        QuoteApiFactory quoteApiFactory = QuoteApiFactory.getInstance();
        QuoteApiService quoteApiService = quoteApiFactory.getApiService();
        Disposable disposable = quoteApiService.getQuote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Quote>() {
                    @Override
                    public void accept(Quote apiResponse) throws Exception {
                        String quote = apiResponse.getQuoteText() + apiResponse.getQuoteAuthor();
                        result.setValue(quote);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}