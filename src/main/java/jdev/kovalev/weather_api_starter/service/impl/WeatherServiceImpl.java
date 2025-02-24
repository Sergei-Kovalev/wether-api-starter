package jdev.kovalev.weather_api_starter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.kovalev.weather_api_starter.cache.Cache;
import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.service.WeatherService;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;

public class WeatherServiceImpl implements WeatherService {
    public static final String STARTER_NAME = "weather-api-starter: ";
    private final String appid;

    private final OpenWeatherReceiver openWeatherReceiver;
    private final CurrentWeatherMapper currentWeatherMapper;
    private final Cache<String, CurrentWeather> cache;

    public WeatherServiceImpl(String appid,
                              OpenWeatherReceiver openWeatherReceiver,
                              CurrentWeatherMapper currentWeatherMapper,
                              Cache<String, CurrentWeather> cache) {
        this.appid = appid;
        this.openWeatherReceiver = openWeatherReceiver;
        this.currentWeatherMapper = currentWeatherMapper;
        this.cache = cache;
    }

    @Override
    public String getWeather(String city) {
        ObjectMapper objectMapper = new ObjectMapper();
        CurrentWeather currentWeather;
        currentWeather = cache.get(city);
        if (currentWeather == null || !isActual(currentWeather)) {
            ResponseFromWeatherApi responseFromWeatherApi = openWeatherReceiver.getCurrentWeather(city, appid);
            currentWeather = currentWeatherMapper.fromApiToCurrentWeather(responseFromWeatherApi);
            cache.set(city, currentWeather);
        }
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentWeather);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(STARTER_NAME + e.getMessage());
        }
    }

    private boolean isActual(CurrentWeather currentWeather) {
        long datetime = currentWeather.getDatetime();
        long datetimeNow = System.currentTimeMillis();
        long diffMin = Math.abs(datetime - datetimeNow) / (1000 * 60);
        return diffMin < 10;
    }
}
