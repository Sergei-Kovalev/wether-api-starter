package jdev.kovalev.weather_api_starter.util;

import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;

public interface OpenWeatherReceiver {
    ResponseFromWeatherApi getCurrentWeather(String city, String appid);
}
