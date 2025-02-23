package jdev.kovalev.weather_api_starter.util.impl;

import jdev.kovalev.weather_api_starter.exception.ApiKeyNotValidException;
import jdev.kovalev.weather_api_starter.exception.CityNameNotValidException;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import jdev.kovalev.weather_api_starter.util.entity.geocoding_api.ResponseFromGeocodingApi;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherReceiverImpl implements OpenWeatherReceiver {
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
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%.2f&lon=%.2f&appid=%s",
                                   geocode.getLat(), geocode.getLon(), appid);

        return restTemplate.getForObject(url, ResponseFromWeatherApi.class);
    }

    private ResponseFromGeocodingApi[] getGeocode(String city, String appid) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", city, appid);

        ResponseFromGeocodingApi[] geocodes;
        try {
            geocodes = restTemplate.getForObject(url, ResponseFromGeocodingApi[].class);
        } catch (HttpStatusCodeException e) {
            throw new ApiKeyNotValidException();
        }
        return geocodes;
    }
}
