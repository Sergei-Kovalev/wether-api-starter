package jdev.kovalev.weather_api_starter.mapper;

import jdev.kovalev.weather_api_starter.entity.Temperature;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.MainFromApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemperatureMapper {
    Temperature map(MainFromApi mainFromApi);
}
