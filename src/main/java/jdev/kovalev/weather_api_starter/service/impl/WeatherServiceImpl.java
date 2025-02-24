package jdev.kovalev.weather_api_starter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.kovalev.weather_api_starter.cache.Cache;
import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.service.WeatherService;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final OpenWeatherReceiver openWeatherReceiver;
    private final CurrentWeatherMapper currentWeatherMapper;
    private final Cache<String, CurrentWeather> cache;

    private final String appid = "bd5e378503939ddaee76f12ad7a97608";

    @Override
    public String getWeather(String city) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CurrentWeather currentWeather;
        currentWeather = cache.get(city);
        if (currentWeather == null || !isActual(currentWeather)) {
            ResponseFromWeatherApi responseFromWeatherApi = openWeatherReceiver.getCurrentWeather(city, appid);
            currentWeather = currentWeatherMapper.fromApiToCurrentWeather(responseFromWeatherApi);
            cache.set(city, currentWeather);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentWeather);
    }

    private boolean isActual(CurrentWeather currentWeather) {
        long datetime = currentWeather.getDatetime();
        long datetimeNow = System.currentTimeMillis();
        long diffMin = Math.abs(datetime - datetimeNow) / (1000 * 60);
        return diffMin < 10;
    }
}
