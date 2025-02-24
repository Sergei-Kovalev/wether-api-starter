package jdev.kovalev.weather_api_starter.service.impl;

import jdev.kovalev.weather_api_starter.cache.Cache;
import jdev.kovalev.weather_api_starter.entity.CurrentWeather;
import jdev.kovalev.weather_api_starter.entity.SystemInformation;
import jdev.kovalev.weather_api_starter.entity.Temperature;
import jdev.kovalev.weather_api_starter.entity.Weather;
import jdev.kovalev.weather_api_starter.entity.Wind;
import jdev.kovalev.weather_api_starter.mapper.CurrentWeatherMapper;
import jdev.kovalev.weather_api_starter.util.OpenWeatherReceiver;
import jdev.kovalev.weather_api_starter.util.entity.current_weather_api.ResponseFromWeatherApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {
    @Mock
    OpenWeatherReceiver openWeatherReceiver;
    @Mock
    CurrentWeatherMapper currentWeatherMapper;
    @Mock
    Cache<String, CurrentWeather> cache;

    @InjectMocks
    WeatherServiceImpl weatherService;

    private String city;
    private String appid;
    private long timestamp;
    private CurrentWeather currentWeather;
    private ResponseFromWeatherApi responseFromWeatherApi;
    private String expected;

    @BeforeEach
    void setUp() {
        city = "Minsk";
        appid = "sdaf564";

        Weather weather = Weather.builder()
                .main("Clouds")
                .description("scattered clouds")
                .build();
        Temperature temperature = Temperature.builder()
                .temp(269.6)
                .feels_like(267.57)
                .build();
        Wind wind = Wind.builder()
                .speed(1.38)
                .build();
        SystemInformation sys = SystemInformation.builder()
                .sunrise(1675751262)
                .sunset(1675787560)
                .build();
        timestamp = System.currentTimeMillis();
        currentWeather = CurrentWeather.builder()
                .weather(weather)
                .temperature(temperature)
                .visibility(10000)
                .wind(wind)
                .datetime(timestamp)
                .sys(sys)
                .timezone(3600)
                .name("Zocca")
                .build();
        responseFromWeatherApi = ResponseFromWeatherApi.builder()
                .build();
        expected = """
                {
                  "weather" : {
                    "main" : "Clouds",
                    "description" : "scattered clouds"
                  },
                  "temperature" : {
                    "temp" : 269.6,
                    "feels_like" : 267.57
                  },
                  "visibility" : 10000,
                  "wind" : {
                    "speed" : 1.38
                  },
                  "datetime" : %d,
                  "sys" : {
                    "sunrise" : 1675751262,
                    "sunset" : 1675787560
                  },
                  "timezone" : 3600,
                  "name" : "Zocca"
                }""".formatted(timestamp);
    }

    @Test
    void deleteWeatherInfo() {
        weatherService.deleteWeatherInfo(city);
        Mockito.verify(cache, times(1)).remove(any());
    }

    @Test
    void getResponse_whenNotPresentedInCache() {
        when(cache.get(city))
                .thenReturn(null);
        when(openWeatherReceiver.getCurrentWeather(city, appid))
                .thenReturn(responseFromWeatherApi);
        when(currentWeatherMapper.fromApiToCurrentWeather(responseFromWeatherApi))
                .thenReturn(currentWeather);
        doNothing().when(cache).set(city, currentWeather);

        String actual = weatherService.getResponse(city, appid);

        assertThat(actual)
                .isEqualToIgnoringNewLines(expected);
    }

    @Test
    void getResponse_whenNotActual() {
        when(cache.get(city))
                .thenReturn(CurrentWeather.builder().datetime(timestamp - 10000000).build());
        when(openWeatherReceiver.getCurrentWeather(city, appid))
                .thenReturn(responseFromWeatherApi);
        when(currentWeatherMapper.fromApiToCurrentWeather(responseFromWeatherApi))
                .thenReturn(currentWeather);
        doNothing().when(cache).set(city, currentWeather);

        String actual = weatherService.getResponse(city, appid);

        assertThat(actual)
                .isEqualToIgnoringNewLines(expected);
    }

    @Test
    void getResponse_whenPresentedInCache() {
        when(cache.get(city))
                .thenReturn(currentWeather);

        String actual = weatherService.getResponse(city, appid);

        assertThat(actual)
                .isEqualToIgnoringNewLines(expected);
    }
}