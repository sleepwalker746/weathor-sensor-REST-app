package services;

import entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.SensorsRepository;

@Service
public class SensorsService {

    private SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService (SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Sensors registerSensors(Sensors sensors) {
        return sensorsRepository.save(sensors);
    }

    public Sensors findByName(String name) {
        return sensorsRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Сенсор не найден!"));
    }



}
