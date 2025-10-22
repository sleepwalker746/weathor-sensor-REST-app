package services;

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



}
