package jdev.kovalev.weather_api_starter.exception;

public class WeatherFromApiNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "API не вернул данных о погоде";

    public WeatherFromApiNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
