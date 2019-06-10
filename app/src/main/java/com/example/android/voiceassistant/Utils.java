package com.example.android.voiceassistant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Utils {
    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        String currentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        return "сегодня " + currentDay;
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        return "сейчас " + currentHour + ":" + currentMinute;
    }

    public static String getWindDirection(String key) {
        HashMap<String, String> map = new HashMap<String, String>() {{
            put("N", "северный");
            put("E", "восточный");
            put("S", "южный");
            put("W", "западный");
            put("NE", "северовосточный");
            put("SE", "юговосточный");
            put("SW", "югозападный");
            put("NW", "северозападный");
            put("NNE", "северный-северовосточный");
            put("ENE", "восточный-северовосточный");
            put("ESE", "восточный-юговосточный");
            put("SSE", "южный-юговосточный");
            put("SSW", "южный-югозападный");
            put("WSW", "западный-югозападный");
            put("WNW", "западный-северозападный");
            put("NNW", "северный-северозападный");
        }};
        return map.get(key);
    }

    public static int getWindSpeedMs(Double speedKph) {
        Double speedMs = speedKph / 3.6;
        return speedMs.intValue();
    }

    public static String listToString(ArrayList<String> list, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                stringBuilder.append(list.get(i));
                break;
            }
            stringBuilder.append(list.get(i)).append(delimiter);
        }
        return stringBuilder.toString();
    }
}
