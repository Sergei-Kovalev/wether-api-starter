package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import lombok.Data;

@Data
public class WindFromApi {
    private double speed;
    private int deg;
    private double gust;
}
