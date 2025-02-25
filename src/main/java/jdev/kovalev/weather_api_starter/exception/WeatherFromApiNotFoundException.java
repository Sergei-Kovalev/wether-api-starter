package jdev.kovalev.weather_api_starter.exception;

/**
 * Класс исключения, выбрасывается в случае неверных данных, возвращенный сервером.
 * @author Sergey Kovalev
 */
public class WeatherFromApiNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "weather-api-starter: API не вернул данных о погоде";

    public WeatherFromApiNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
