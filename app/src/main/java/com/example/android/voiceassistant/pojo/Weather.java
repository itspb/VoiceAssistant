package com.example.android.voiceassistant.pojo;

import com.google.gson.annotations.SerializedName;

public class Weather {

    public class ApiResponse {
        @SerializedName("current")
        private Forecast forecast;

        public Forecast getForecast() {
            return forecast;
        }
    }

    public class Forecast {
        @SerializedName("temp_c")
        private Double temperature;

        @SerializedName("feelslike_c")
        private Double feelsLikeTemperature;

        @SerializedName("wind_dir")
        private String windDirection;

        @SerializedName("wind_kph")
        private Double windSpeedKph;

        @SerializedName("condition")
        private Condition condition;

        public Double getTemperature() {
            return temperature;
        }

        public Double getFeelsLikeTemperature() {
            return feelsLikeTemperature;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public Double getWindSpeedKph() {
            return windSpeedKph;
        }

        public Condition getCondition() {
            return condition;
        }
    }

    public class Condition {
        @SerializedName("text")
        private String weatherDescription;

        public String getWeatherDescription() {
            return weatherDescription;
        }
    }
}