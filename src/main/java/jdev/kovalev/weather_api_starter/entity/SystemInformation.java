package jdev.kovalev.weather_api_starter.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Класс объекта необходимый для создания базового класса
 * @author Sergey Kovalev
 */
@Data
@Builder
public class SystemInformation {
    private long sunrise;
    private long sunset;
}
