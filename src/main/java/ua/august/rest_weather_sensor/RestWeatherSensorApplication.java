package ua.august.rest_weather_sensor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.client.HttpClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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


/*
1. Отношения в БД между Показателям и Сенсороом должны быть Many To One; Уточнять какие показатели пришли с какого сенсора.
2. Должно быть 4 адреса: Все измерения(GET), Получение дней с дождём(GET), Регистрация Сенсора (POST), Добавление показателей от сенсора (POST)
3.




 */

