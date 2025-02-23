package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import lombok.Data;

@Data
public class MainFromApi {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;
    private int sea_level;
    private int grnd_level;
}