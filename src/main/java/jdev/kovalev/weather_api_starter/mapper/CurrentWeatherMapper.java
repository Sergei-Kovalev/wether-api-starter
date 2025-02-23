package jdev.kovalev.weather_api_starter.mapper;

import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.exception.WeatherFromApiNotFoundException;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.WeatherFromApi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;

@Mapper(componentModel = "spring", uses = {WeatherMapper.class, TemperatureMapper.class})
public interface CurrentWeatherMapper {
    @Mapping(target = "weather", source = "weather", qualifiedByName = "firstWeather")
    @Mapping(target = "datetime", source = "dt")
    @Mapping(target = "temperature", source = "main")
    CurrentWeather fromApiToCurrentWeather(ResponseFromWeatherApi responseFromWeatherApi);

    @Named("firstWeather")
    default WeatherFromApi firstWeather(ArrayList<WeatherFromApi> weather) {
        return weather.stream()
                .findFirst()
                .orElseThrow(WeatherFromApiNotFoundException::new);
    }
}
