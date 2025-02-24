package jdev.kovalev.weather_api_starter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "weather-api-starter")
public class WeatherApiStarterProperties {
    private String apiKey;
    private boolean pollingMode;
}
