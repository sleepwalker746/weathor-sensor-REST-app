package ua.august.rest_weather_sensor.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import org.springframework.http.ResponseEntity;
import ua.august.rest_weather_sensor.services.RestTemplateService;

import java.util.List;

@Component
public class DataClientRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataClientRunner.class);
    private final RestTemplateService restTemplateService;

    public DataClientRunner(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public void registerSensor(String sensorName) {
        logger.info("Registering sensor {}", sensorName);
        restTemplateService.registerSensor(sensorName)
                .ifPresentOrElse(
                        s -> logger.info("Sensor {} registered successfully", sensorName),
                        () -> logger.warn("Sensor {} already registered", sensorName)
                );
    }

    public void sendMeasurements(String sensorName) {
        logger.info("Sending measurements for sensor {}", sensorName);
        restTemplateService.sendRandomMeasurements(sensorName);
    }

    public void getMeasurements(String sensorName) {
        ResponseEntity<List<MeasurementsDTO>> response = restTemplateService.getAllMeasurements();
        List<MeasurementsDTO> measurementsDTOs = response.getBody();
        if (measurementsDTOs != null) {
            logger.info("Number of measurements is: {}", measurementsDTOs.size());
        } else {
            logger.error("Error: Empty list of measurements has been received. Error code: {}", response.getStatusCode());
        }
    }

    public void execute(String sensorName) {
        logger.info("<< Run MARK-1 client >>");
        registerSensor(sensorName);
        sendMeasurements(sensorName);
        getMeasurements(sensorName);
        logger.info("<< MARK-1 client completed >>");
    }

}
