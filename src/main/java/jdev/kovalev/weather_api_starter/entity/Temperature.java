package jdev.kovalev.weather_api_starter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Temperature {
    private double temp;
    private double feels_like;
}
