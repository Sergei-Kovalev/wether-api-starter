package jdev.kovalev.weather_api_starter.util.impl;

import jdev.kovalev.weather_api_starter.exception.ApiKeyNotValidException;
import jdev.kovalev.weather_api_starter.exception.CityNameNotValidException;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import jdev.kovalev.weather_api_starter.util.entity.geocoding_api.ResponseFromGeocodingApi;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class OpenWeatherReceiverImpl implements OpenWeatherReceiver {

    public static final String URL_FOR_OPEN_WEATHER_MAP_API = "https://api.openweathermap.org/data/2.5/weather?lat=%.2f&lon=%.2f&appid=%s";
    public static final String URL_FOR_GEOCODE_API = "http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s";

    @Override
    public ResponseFromWeatherApi getCurrentWeather(String city, String appid) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseFromGeocodingApi geocode;
        ResponseFromGeocodingApi[] geocodes = getGeocode(city, appid);
        if (geocodes != null && geocodes.length > 0) {
            geocode = geocodes[0];
        } else {
            throw new CityNameNotValidException();
        }
        String url = String.format(URL_FOR_OPEN_WEATHER_MAP_API,
                                   geocode.getLat(), geocode.getLon(), appid);

        return restTemplate.getForObject(url, ResponseFromWeatherApi.class);
    }

    private ResponseFromGeocodingApi[] getGeocode(String city, String appid) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format(URL_FOR_GEOCODE_API, city, appid);

        ResponseFromGeocodingApi[] geocodes;
        try {
            geocodes = restTemplate.getForObject(url, ResponseFromGeocodingApi[].class);
        } catch (HttpStatusCodeException e) {
            throw new ApiKeyNotValidException();
        }
        return geocodes;
    }
}
