package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import lombok.Data;

@Data
public class CoordFromApi {
    private double lon;
    private double lat;
}