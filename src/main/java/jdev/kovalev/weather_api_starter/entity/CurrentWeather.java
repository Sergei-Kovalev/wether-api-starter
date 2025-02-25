package jdev.kovalev.weather_api_starter.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Базовый класс объекта для хранения/ответа пользователю
 * @author Sergey Kovalev
 */
@Data
@Builder
public class CurrentWeather {
    private Weather weather;
    private Temperature temperature;
    private int visibility;
    private Wind wind;
    private long datetime;
    private SystemInformation sys;
    private int timezone;
    private String name;
}
