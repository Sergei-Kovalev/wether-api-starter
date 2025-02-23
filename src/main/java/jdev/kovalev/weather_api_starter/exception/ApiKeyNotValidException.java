package jdev.kovalev.weather_api_starter.exception;

public class ApiKeyNotValidException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Не валидный API key. Проверьте правильность ввода";

    public ApiKeyNotValidException() {
        super(DEFAULT_MESSAGE);
    }
}
