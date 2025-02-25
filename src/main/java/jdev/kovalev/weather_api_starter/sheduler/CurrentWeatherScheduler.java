package jdev.kovalev.weather_api_starter.sheduler;

import jdev.kovalev.weather_api_starter.cache.Cache;
import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Класс шедулера, выполняющего запросы с заданной периодичностью и обновляющий данные в кэш
 * @author Sergey Kovalev
 */
public class CurrentWeatherScheduler {
    private final String appid;

    private final OpenWeatherReceiver openWeatherReceiver;
    private final CurrentWeatherMapper currentWeatherMapper;
    private final Cache<String, CurrentWeather> cache;

    public CurrentWeatherScheduler(String appid,
                                   OpenWeatherReceiver openWeatherReceiver,
                                   CurrentWeatherMapper currentWeatherMapper,
                                   Cache<String, CurrentWeather> cache) {
        this.appid = appid;
        this.openWeatherReceiver = openWeatherReceiver;
        this.currentWeatherMapper = currentWeatherMapper;
        this.cache = cache;
    }

    /**
     * Основной метод который выполняется с периодичностью каждые 10 минут.
     * Сохраняет полученные данные в кэш
     */
    @Scheduled(fixedDelay = 600000)
    public void updateCurrentWeather() {
        cache.getAllKeys().forEach(city -> {
            ResponseFromWeatherApi responseFromWeatherApi = openWeatherReceiver.getCurrentWeather(city, appid);
            CurrentWeather currentWeather = currentWeatherMapper.fromApiToCurrentWeather(responseFromWeatherApi);
            cache.set(city, currentWeather);
        });
    }
}
