package jdev.kovalev.weather_api_starter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemInformation {
    private long sunrise;
    private long sunset;
}
