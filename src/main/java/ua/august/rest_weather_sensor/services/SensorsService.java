package ua.august.rest_weather_sensor.services;

import ua.august.rest_weather_sensor.entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.august.rest_weather_sensor.exceptions.SensorNotFoundException;
import ua.august.rest_weather_sensor.repositories.SensorsRepository;
import ua.august.rest_weather_sensor.exceptions.SensorExistsException;

@Service
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService (SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Sensors register(Sensors sensor) {
        sensorsRepository.findByName(sensor.getName()).ifPresent(s -> {
            throw new SensorExistsException("Sensor with name " +  sensor.getName() + " already exists!");
        });
        return sensorsRepository.save(sensor);
    }

    public Sensors findByName(String name) {
        return sensorsRepository.findByName(name)
                .orElseThrow(() -> new SensorNotFoundException("Sensor with name " + name + " not found!"));
    }

}
