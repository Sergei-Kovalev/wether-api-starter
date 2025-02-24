package jdev.kovalev.weather_api_starter.util.sheduler;

import jdev.kovalev.weather_api_starter.cache.Cache;
import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrenWeatherScheduler {
    @Value("${application.polling-mode}")
    private boolean isPollingMode;
    @Value("${application.api-key}")
    private String appid;

    private final OpenWeatherReceiver openWeatherReceiver;
    private final CurrentWeatherMapper currentWeatherMapper;
    private final Cache<String, CurrentWeather> cache;

    @Scheduled(cron = "${application.update-delay}")
    public void updateCurrentWeather() {
        if (isPollingMode) {
            cache.getAllKeys().forEach(city -> {
                ResponseFromWeatherApi responseFromWeatherApi = openWeatherReceiver.getCurrentWeather(city, appid);
                CurrentWeather currentWeather = currentWeatherMapper.fromApiToCurrentWeather(responseFromWeatherApi);
                cache.set(city, currentWeather);
            });
        }
    }
}
