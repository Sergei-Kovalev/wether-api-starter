package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RainFromApi {
    @JsonProperty("1h")
    private double _1h;
}
