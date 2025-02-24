package jdev.kovalev.weather_api_starter;

import com.fasterxml.jackson.core.JsonProcessingException;
import jdev.kovalev.weather_api_starter.service.WeatherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WeatherApiStarterApplication {

	public static void main(String[] args) throws JsonProcessingException {
		ConfigurableApplicationContext context = SpringApplication.run(WeatherApiStarterApplication.class, args);

		WeatherService service = context.getBean(WeatherService.class);
		String town = service.getWeather("Minsk");
		System.out.println(town);
	}
}