package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class ResponseFromWeatherApi {
    private CoordFromApi coord;
    private ArrayList<WeatherFromApi> weather;
    private String base;
    private MainFromApi main;
    private int visibility;
    private WindFromApi wind;
    private RainFromApi rain;
    private CloudsFromApi clouds;
    private long dt;
    private SysFromApi sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;
}
