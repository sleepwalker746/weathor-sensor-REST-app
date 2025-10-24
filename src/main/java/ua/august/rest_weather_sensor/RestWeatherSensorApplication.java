package ua.august.rest_weather_sensor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.client.HttpClientAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
		exclude = {HttpClientAutoConfiguration.class}
)
public class RestWeatherSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestWeatherSensorApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

