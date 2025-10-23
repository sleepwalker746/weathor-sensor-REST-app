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

    @Value("${api.url.base}")
    private String baseUrl;

    @Value("${api.url.sensors.registration}")
    private String registrationUrl;

    @Autowired
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SensorsRegistrationDTO registerSensor(String sensorsName) {

        SensorsRegistrationDTO sensorsRegistrationDTO = new SensorsRegistrationDTO(sensorsName);

        System.out.println("Регистрация сенсора с именем: " + sensorsRegistrationDTO.getName());

        try {
            return restTemplate.postForObject(
                    registrationUrl,
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

        double min = -100.0;
        double max = 100.0;

        for (double i = 0; i < 1000; i++) {

            double randomValue = random.nextDouble() * (max - min) + min;
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

        String postUrl = baseUrl + "/add";

        try {
            restTemplate.postForObject(postUrl, measurementsDTO, Void.class);
        } catch (HttpClientErrorException e) {
            System.out.println("Ошибка при отправке измерений!" + e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<List<MeasurementsDTO>> getAllMeasurements() {
        ParameterizedTypeReference<List<MeasurementsDTO>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(baseUrl, HttpMethod.GET, null, responseType);
    }
}
