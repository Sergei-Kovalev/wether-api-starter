package jdev.kovalev.weather_api_starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface WeatherService {
    String getWeather(String city) throws JsonProcessingException;
}
