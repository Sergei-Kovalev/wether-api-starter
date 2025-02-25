package jdev.kovalev.weather_api_starter.util.entity.geocoding_api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Базовый класс объекта получаемого от API <a href="https://openweathermap.org/api/geocoding-api">...</a>
 * @author Sergey Kovalev
 */
@Data
public class ResponseFromGeocodingApi {
    private String name;
    @JsonIgnore
    private String local_names;
    private double lat;
    private double lon;
    private String country;
}
