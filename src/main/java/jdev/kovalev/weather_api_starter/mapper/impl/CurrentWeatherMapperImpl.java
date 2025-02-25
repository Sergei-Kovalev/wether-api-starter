package jdev.kovalev.weather_api_starter.mapper.impl;

import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.entity.SystemInformation;
import jdev.kovalev.weather_api_starter.entity.Wind;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.mapper.TemperatureMapper;
import jdev.kovalev.weather_api_starter.mapper.WeatherMapper;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.SysFromApi;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.WindFromApi;


public class CurrentWeatherMapperImpl implements CurrentWeatherMapper {

    private final WeatherMapper weatherMapper;
    private final TemperatureMapper temperatureMapper;

    public CurrentWeatherMapperImpl(WeatherMapper weatherMapper, TemperatureMapper temperatureMapper) {
        this.weatherMapper = weatherMapper;
        this.temperatureMapper = temperatureMapper;
    }

    @Override
    public CurrentWeather fromApiToCurrentWeather(ResponseFromWeatherApi responseFromWeatherApi) {
        if ( responseFromWeatherApi == null ) {
            return null;
        }

        CurrentWeather.CurrentWeatherBuilder currentWeather = CurrentWeather.builder();

        currentWeather.weather( weatherMapper.map( firstWeather( responseFromWeatherApi.getWeather() ) ) );
        currentWeather.datetime( responseFromWeatherApi.getDt() );
        currentWeather.temperature( temperatureMapper.map( responseFromWeatherApi.getMain() ) );
        currentWeather.visibility( responseFromWeatherApi.getVisibility() );
        currentWeather.wind( windFromApiToWind( responseFromWeatherApi.getWind() ) );
        currentWeather.sys( sysFromApiToSystemInformation( responseFromWeatherApi.getSys() ) );
        currentWeather.timezone( responseFromWeatherApi.getTimezone() );
        currentWeather.name( responseFromWeatherApi.getName() );

        return currentWeather.build();
    }

    protected Wind windFromApiToWind(WindFromApi windFromApi) {
        if ( windFromApi == null ) {
            return null;
        }

        Wind.WindBuilder wind = Wind.builder();

        wind.speed( windFromApi.getSpeed() );

        return wind.build();
    }

    protected SystemInformation sysFromApiToSystemInformation(SysFromApi sysFromApi) {
        if ( sysFromApi == null ) {
            return null;
        }

        SystemInformation.SystemInformationBuilder systemInformation = SystemInformation.builder();

        systemInformation.sunrise( sysFromApi.getSunrise() );
        systemInformation.sunset( sysFromApi.getSunset() );

        return systemInformation.build();
    }
}
