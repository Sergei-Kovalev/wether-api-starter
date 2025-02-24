package jdev.kovalev.weather_api_starter.mapper.impl;

import jdev.kovalev.weather_api_starter.entity.Weather;
import jdev.kovalev.weather_api_starter.mapper.WeatherMapper;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.WeatherFromApi;

public class WeatherMapperImpl implements WeatherMapper {

    @Override
    public Weather map(WeatherFromApi weatherFromApi) {
        if ( weatherFromApi == null ) {
            return null;
        }

        Weather.WeatherBuilder weather = Weather.builder();

        weather.main( weatherFromApi.getMain() );
        weather.description( weatherFromApi.getDescription() );

        return weather.build();
    }
}
