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

/**
 * Объект автоконфигурации, создающий все необходимые для стартера бины
 * @author Sergey Kovalev
 */
@Configuration
@EnableConfigurationProperties(WeatherApiStarterProperties.class)
public class WeatherApiStarterAutoConfiguration {

    /**
     * Метод для создания бина
     * @see OpenWeatherReceiver
     * @return OpenWeatherReceiverImpl
     */
    @Bean
    @ConditionalOnMissingBean
    public OpenWeatherReceiver openWeatherReceiver() {
        return new OpenWeatherReceiverImpl();
    }

    /**
     * Метод для создания бина
     * @see WeatherMapper
     * @return WeatherMapperImpl
     */
    @Bean
    @ConditionalOnMissingBean
    public WeatherMapper weatherMapper() {
        return new WeatherMapperImpl();
    }

    /**
     * Метод для создания бина
     * @see TemperatureMapper
     * @return TemperatureMapperImpl
     */
    @Bean
    @ConditionalOnMissingBean
    public TemperatureMapper temperatureMapper() {
        return new TemperatureMapperImpl();
    }

    /**
     * Метод для создания бина
     * @see CurrentWeatherMapper
     * @return CurrentWeatherMapperImpl
     */
    @Bean
    @ConditionalOnMissingBean
    public CurrentWeatherMapper currentWeatherMapper(WeatherMapper weatherMapper, TemperatureMapper temperatureMapper) {
        return new CurrentWeatherMapperImpl(weatherMapper, temperatureMapper);
    }

    /**
     * Метод для создания бина кэша
     * @see Cache
     * @return LRUCache
     */
    @Bean
    @ConditionalOnMissingBean
    public Cache<String, CurrentWeather> cache() {
        return new LRUCache<>();
    }

    /**
     * Метод для создания бина кэша
     * @see WeatherService
     * @return WeatherServiceImpl
     */
    @Bean
    @ConditionalOnMissingBean
    public WeatherService weatherService(
            WeatherApiStarterProperties properties,
            OpenWeatherReceiver openWeatherReceiver,
            CurrentWeatherMapper currentWeatherMapper,
            Cache<String, CurrentWeather> cache) {
        return new WeatherServiceImpl(properties.getApiKey(), openWeatherReceiver, currentWeatherMapper, cache);
    }

    /**
     * Метод для создания бина кэша
     * создаётся только если в properties файле значение polling-mode в значении true
     * @see CurrentWeatherScheduler
     * @return CurrentWeatherScheduler
     */
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
