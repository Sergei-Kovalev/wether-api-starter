package jdev.kovalev.weather_api_starter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс определяющий параметры, которые могут конфигурироваться в стартере
 * @author Sergey Kovalev
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "weather-api-starter")
public class WeatherApiStarterProperties {
    private String apiKey;
    private boolean pollingMode;
}
