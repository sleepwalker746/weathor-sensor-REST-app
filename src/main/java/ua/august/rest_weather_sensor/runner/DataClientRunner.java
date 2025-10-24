package ua.august.rest_weather_sensor.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import ua.august.rest_weather_sensor.dto.SensorsRegistrationDTO;
import org.springframework.http.ResponseEntity;
import ua.august.rest_weather_sensor.services.RestTemplateService;

import java.util.List;

@Component
public class DataClientRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataClientRunner.class);
    private final RestTemplateService restTemplateService;
    private static final String SENSOR_NAME = "TestSensor-Client";

    public DataClientRunner(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public void execute() throws Exception {

        logger.info("<< Run test of MARK-1 client >>");

        logger.info("1. Registration of a new sensor");

        try {
            SensorsRegistrationDTO registeredSensor = restTemplateService.registerSensor(SENSOR_NAME);
            logger.info("Sensor was successfully registered {} ", registeredSensor);
        } catch (HttpServerErrorException e) {
            logger.debug("Sensor with name {} is already registered. Continue to send data...", SENSOR_NAME);
        }


        logger.info("2. Sending 1000 random measurements");

        restTemplateService.sendRandomMeasurements(SENSOR_NAME);

        logger.info("3. Getting every measurements");

        ResponseEntity<List<MeasurementsDTO>> response = restTemplateService.getAllMeasurements();
        List<MeasurementsDTO> measurementsDTOs = response.getBody();
        if (measurementsDTOs != null) {
            logger.info("Number of measurements is: {}", measurementsDTOs.size());
        } else {
            logger.error("Error: Empty list of measurements has been received. Error code: {}", response.getStatusCode());
        }

        logger.info("<< MARK-1 client has completed the task >>");

    }
}
