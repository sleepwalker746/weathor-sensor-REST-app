package ua.august.rest_weather_sensor.services;

import ua.august.rest_weather_sensor.entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.august.rest_weather_sensor.repositories.SensorsRepository;

@Service
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService (SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Sensors register(Sensors sensor) {
        sensorsRepository.findByName(sensor.getName()).ifPresent(s -> {
            throw new RuntimeException("Сенсор с таким именем уже существует!");
        });
        return sensorsRepository.save(sensor);
    }

    public Sensors findByName(String name) {
        return sensorsRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Сенсор не найден!"));
    }

}
