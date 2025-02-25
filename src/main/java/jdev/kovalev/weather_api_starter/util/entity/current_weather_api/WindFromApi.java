package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import lombok.Data;

/**
 * Класс объекта необходимый для создания базового класса
 * @author Sergey Kovalev
 */
@Data
public class WindFromApi {
    private double speed;
    private int deg;
    private double gust;
}
