package jdev.kovalev.weather_api_starter.mapper.impl;

import jdev.kovalev.weather_api_starter.entity.Temperature;
import jdev.kovalev.weather_api_starter.mapper.TemperatureMapper;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.MainFromApi;

public class TemperatureMapperImpl implements TemperatureMapper {

    @Override
    public Temperature map(MainFromApi mainFromApi) {
        if ( mainFromApi == null ) {
            return null;
        }

        Temperature.TemperatureBuilder temperature = Temperature.builder();

        temperature.temp( mainFromApi.getTemp() );
        temperature.feels_like( mainFromApi.getFeels_like() );

        return temperature.build();
    }
}
