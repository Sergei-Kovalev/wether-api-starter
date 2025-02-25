package jdev.kovalev.weather_api_starter.mapper;

import jdev.kovalev.weather_api_starter.entity.Weather;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.WeatherFromApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    Weather map(WeatherFromApi weatherFromApi);
}
