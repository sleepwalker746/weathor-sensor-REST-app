package ua.august.rest_weather_sensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestWeatherSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestWeatherSensorApplication.class, args);
	}

}

/*
1. Отношения в БД между Показателям и Сенсороом должны быть Many To One; Уточнять какие показатели пришли с какого сенсора.
2. Должно быть 4 адреса: Все измерения(GET), Получение дней с дождём(GET), Регистрация Сенсора (POST), Добавление показателей от сенсора (POST)
3.




 */

