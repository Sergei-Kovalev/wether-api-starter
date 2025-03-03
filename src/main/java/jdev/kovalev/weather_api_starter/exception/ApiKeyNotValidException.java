package jdev.kovalev.weather_api_starter.exception;

/**
 * Класс исключения, выбрасывается в случае неверного API ключа, введённого пользователем
 * @author Sergey Kovalev
 */
public class ApiKeyNotValidException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "weather-api-starter: Не валидный API key. Проверьте правильность ввода";

    public ApiKeyNotValidException() {
        super(DEFAULT_MESSAGE);
    }
}
