package jdev.kovalev.weather_api_starter.util.entity.current_weather_api;

import lombok.Data;

/**
 * Класс объекта необходимый для создания базового класса
 * @author Sergey Kovalev
 */
@Data
public class SysFromApi {
    private int type;
    private int id;
    private String country;
    private int sunrise;
    private int sunset;
}
