package jdev.kovalev.weather_api_starter.exception;

/**
 * Класс исключения, выбрасывается в случае неверного наименования города, введённого пользователем
 * @author Sergey Kovalev
 */
public class CityNameNotValidException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "weather-api-starter: Наименование города некорректно. Проверьте правильность ввода";

    public CityNameNotValidException() {
        super(DEFAULT_MESSAGE);
    }
}
