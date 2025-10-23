package ua.august.rest_weather_sensor.services;

import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import ua.august.rest_weather_sensor.dto.SensorsRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class RestTemplateService {

    private final RestTemplate restTemplate;

    final double MIN_AIR_TEMPERATURE = -100.0;
    final double MAX_AIR_TEMPERATURE = 100.0;
    final int MEASUREMENT_COUNT = 1000;

    @Value("${api.url.base}")
    private String baseUrl;

    @Value("${api.url.sensors.registration}")
    private String sensorsRegistrationUrl;

    @Value("${api.url.measurements.creation}")
    private String creationMeasurementsUrl;

    @Autowired
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SensorsRegistrationDTO registerSensor(String sensorsName) {
        try {
            SensorsRegistrationDTO sensorsRegistrationDTO = new SensorsRegistrationDTO(sensorsName);
            return restTemplate.postForObject(
                    sensorsRegistrationUrl,
                    sensorsRegistrationDTO,
                    SensorsRegistrationDTO.class
            );
        } catch (HttpClientErrorException e) {
            System.err.println("Произошла ошибка при регистрации сенсора. Статус ошибки: " + e.getStatusCode());
            System.err.println("Подобробнее: " + e.getResponseBodyAsString());
            throw new RuntimeException("Не удалось зарегистрировать сенсор: " + e.getMessage());
        }
    }

    public void sendRandomMeasurements(String sensorsName) {

        Random random = new Random();

        for (double i = 0; i < MEASUREMENT_COUNT; i++) {

            double randomValue = random.nextDouble() * (MAX_AIR_TEMPERATURE - MIN_AIR_TEMPERATURE) + MIN_AIR_TEMPERATURE;
            boolean randomRaining = random.nextBoolean();

            MeasurementsDTO measurementsDTO = new MeasurementsDTO(
                    randomValue,
                    randomRaining,
                    sensorsName
            );
            try {
                this.sendMeasurements(measurementsDTO);
            } catch (HttpClientErrorException e) {
                System.err.println("Неизвестная ошибка: " + e.getMessage());
                System.err.println("Ошибка при отправке показателей: " + e.getResponseBodyAsString());
            }
        }
    }

    public void sendMeasurements(MeasurementsDTO measurementsDTO) {

        try {
            restTemplate.postForObject(creationMeasurementsUrl, measurementsDTO, Void.class);
        } catch (HttpClientErrorException e) {
            System.out.println("Ошибка при отправке измерений!" + e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<List<MeasurementsDTO>> getAllMeasurements() {
        ParameterizedTypeReference<List<MeasurementsDTO>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(baseUrl, HttpMethod.GET, null, responseType);
    }
}
