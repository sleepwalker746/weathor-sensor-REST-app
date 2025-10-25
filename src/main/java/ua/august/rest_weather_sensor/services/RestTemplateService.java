package ua.august.rest_weather_sensor.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
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
import java.util.Optional;
import java.util.Random;

@Service
public class RestTemplateService {

    private final Logger logger = LoggerFactory.getLogger(RestTemplateService.class);
    private final RestTemplate restTemplate;

    static final double MIN_AIR_TEMPERATURE = -100.0;
    static final double MAX_AIR_TEMPERATURE = 100.0;
    final int MEASUREMENT_COUNT = 1000;

    @Value("${api.url.measurements}")
    private String measurementsUrl;

    @Value("${api.url.sensors.registration}")
    private String sensorsRegistrationUrl;

    @Value("${api.url.measurements.creation}")
    private String creationMeasurementsUrl;

    @Autowired
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<SensorsRegistrationDTO> registerSensor(String sensorsName) {
        try {
            SensorsRegistrationDTO sensorsRegistrationDTO = new SensorsRegistrationDTO(sensorsName);
            logger.info("Registering new sensor with name: {}", sensorsRegistrationDTO.getName());
             restTemplate.postForObject(
                    sensorsRegistrationUrl,
                    sensorsRegistrationDTO,
                    SensorsRegistrationDTO.class
            );
            return Optional.of(sensorsRegistrationDTO);

        } catch (HttpClientErrorException.Conflict e) {

            logger.warn("Sensor with name {} already exists", sensorsName);
            return Optional.empty();

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            logger.error("Error occurred while registering sensor: {} ", e.getStatusCode());
            logger.error("Details: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Unable to register sensor: " + e.getMessage());

        } catch (ResourceAccessException e) {

            logger.error("Network error: unable to reach server {}", e.getMessage());
            throw new RuntimeException("Connection error while registering sensor: " + e.getMessage());

        }
    }

    public void sendRandomMeasurements(String sensorsName) {

        Random random = new Random();

        for (int i = 0; i < MEASUREMENT_COUNT; i++) {

            double randomValue = random.nextDouble() * (MAX_AIR_TEMPERATURE - MIN_AIR_TEMPERATURE) + MIN_AIR_TEMPERATURE;
            boolean randomRaining = random.nextBoolean();

            MeasurementsDTO measurementsDTO = new MeasurementsDTO(
                    randomValue,
                    randomRaining,
                    sensorsName
            );
            try {
                this.sendMeasurements(measurementsDTO);
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                logger.error("HTTP error while sending measurements: {}", e.getMessage());
                logger.error("Response body: {}", e.getResponseBodyAsString());
            } catch (ResourceAccessException e) {
                logger.error("Connection error: unable to reach server {}", e.getMessage());
            }
        }
    }

    public void sendMeasurements(MeasurementsDTO measurementsDTO) {
        try {
            restTemplate.postForObject(creationMeasurementsUrl, measurementsDTO, Void.class);
        } catch (HttpClientErrorException e) {
            logger.error("Error sending the measurements, details: {}", e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<List<MeasurementsDTO>> getAllMeasurements() {
        ParameterizedTypeReference<List<MeasurementsDTO>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(measurementsUrl, HttpMethod.GET, null, responseType);
    }
}
