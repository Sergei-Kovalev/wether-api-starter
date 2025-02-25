package jdev.kovalev.weather_api_starter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.kovalev.weather_api_starter.cache.Cache;
import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.service.WeatherService;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;

/**
 * Класс с реализацией методов интерфейса
 * @see WeatherService
 * @author Sergey Kovalev
 */
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

    /**
     * Метод получающий текущую погоду используя API либо кэш
     * @param city - параметр с названием города по которому происходит поиск
     * @return JSON с необходимой информацией
     */
    @Override
    public String getWeather(String city) {
        return getResponse(city, appid);
    }

    /**
     * Метод удаляющий объект с данными о погоде из кэша
     * @param city - параметр с названием города, объект с данными о погоде который необходимо удалить
     */
    @Override
    public void deleteWeatherInfo(String city) {
        cache.remove(city);
    }

    /**
     * Метод получающий текущую погоду используюя API либо кэш
     * @param city - параметр с названием города по которому происходит поиск
     * @param appid - ключ, отличный от того, который установлен в стартере, как основной.
     * @return JSON с необходимой информацией
     */
    @Override
    public String getWeatherWithAnotherApiKey(String city, String appid) {
        return getResponse(city, appid);
    }

    /**
     * Основной метод, который используется другими методами, обращающимися к API либо к кэшу
     * Здесь реализована логика выбора: Обращается стартер к кэшу или к API
     * @param city - параметр с названием города по которому происходит поиск
     * @param appid - ключ, отличный от того, который установлен в стартере, как основной.
     * @return JSON с необходимой информацией
     */
    public String getResponse(String city, String appid) {
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

    /**
     * Метод определяющий является ли данный объект актуальным либо нет.
     * @param currentWeather - объект, который проверяется на актуальность
     * @return булевое значение
     */
    private boolean isActual(CurrentWeather currentWeather) {
        long datetime = currentWeather.getDatetime();
        long datetimeNow = System.currentTimeMillis();
        long diffMin = Math.abs(datetime - datetimeNow) / (1000 * 60);
        return diffMin < 10;
    }
}
