package jdev.kovalev.weather_api_starter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {
    private String main;
    private String description;
}
