package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import lombok.Data;

@Data
public class WeatherFromApi {
    private int id;
    private String main;
    private String description;
    private String icon;
}
