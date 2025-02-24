package jdev.kovalev.weather_api_starter.config;

import jdev.kovalev.weather_api_starter.cache.Cache;
import jdev.kovalev.weather_api_starter.cache.impl.LRUCache;
import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.mapper.TemperatureMapper;
import jdev.kovalev.weather_api_starter.mapper.WeatherMapper;
import jdev.kovalev.weather_api_starter.mapper.impl.CurrentWeatherMapperImpl;
import jdev.kovalev.weather_api_starter.mapper.impl.TemperatureMapperImpl;
import jdev.kovalev.weather_api_starter.mapper.impl.WeatherMapperImpl;
import jdev.kovalev.weather_api_starter.service.WeatherService;
import jdev.kovalev.weather_api_starter.service.impl.WeatherServiceImpl;
import jdev.kovalev.weather_api_starter.sheduler.CurrentWeatherScheduler;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.impl.OpenWeatherReceiverImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WeatherApiStarterProperties.class)
public class WeatherApiStarterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OpenWeatherReceiver openWeatherReceiver() {
        return new OpenWeatherReceiverImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public WeatherMapper weatherMapper() {
        return new WeatherMapperImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public TemperatureMapper temperatureMapper() {
        return new TemperatureMapperImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public CurrentWeatherMapper currentWeatherMapper(WeatherMapper weatherMapper, TemperatureMapper temperatureMapper) {
        return new CurrentWeatherMapperImpl(weatherMapper, temperatureMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public Cache<String, CurrentWeather> cache() {
        return new LRUCache<>();
    }

    @Bean
    @ConditionalOnMissingBean
    public WeatherService weatherService(
            WeatherApiStarterProperties properties,
            OpenWeatherReceiver openWeatherReceiver,
            CurrentWeatherMapper currentWeatherMapper,
            Cache<String, CurrentWeather> cache) {
        return new WeatherServiceImpl(properties.getApiKey(), openWeatherReceiver, currentWeatherMapper, cache);
    }

    @Bean
    @ConditionalOnProperty(name = "weather-api-starter.polling-mode", havingValue = "true")
    public CurrentWeatherScheduler currenWeatherScheduler(
            WeatherApiStarterProperties properties,
            OpenWeatherReceiver openWeatherReceiver,
            CurrentWeatherMapper currentWeatherMapper,
            Cache<String, CurrentWeather> cache) {
        return new CurrentWeatherScheduler(properties.getApiKey(),
                                           openWeatherReceiver,
                                           currentWeatherMapper,
                                           cache);
    }
}
