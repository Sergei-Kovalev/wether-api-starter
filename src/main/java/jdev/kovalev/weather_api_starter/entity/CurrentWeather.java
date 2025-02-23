package jdev.kovalev.weather_api_starter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentWeather {
    private Weather weather;
    private Temperature temperature;
    private int visibility;
    private Wind wind;
    private long datetime;
    private SystemInformation sys;
    private int timezone;
    private String name;
}
