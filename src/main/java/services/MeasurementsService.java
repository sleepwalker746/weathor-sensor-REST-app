package services;

import entities.Measurements;
import entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.MeasurementsRepository;

import java.util.List;


@Service
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;

        this.sensorsService = sensorsService;
    }

    public void saveMeasurements(Measurements measurement) {

        String sensorName = measurement.getSensor().getName();

        Sensors sensor = sensorsService.findByName(sensorName);
        measurement.setSensor(sensor);
        measurementsRepository.save(measurement);
    }

    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }
}
