package jdev.kovalev.weather_api_starter.service;

public interface WeatherService {
    String getWeather(String city);
    void deleteWeatherInfo(String city);
    String getWeatherWithAnotherApiKey(String city, String apiKey);
}
