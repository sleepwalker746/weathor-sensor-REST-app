package services;

import entities.Measurements;
import entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.MeasurementsRepository;
import repositories.SensorsRepository;
import util.MeasurementNotCreatedException;


import java.util.List;


@Service
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository,
                               SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public void saveMeasurements(Measurements measurement) {

        String sensorName = measurement.getSensor().getName();

        Sensors sensor = sensorsRepository.findByName(sensorName)
                .orElseThrow(() -> new MeasurementNotCreatedException(
                        "Сенсор с именем: '" + sensorName + "' не найден!"));

        measurement.setSensor(sensor);
        measurementsRepository.save(measurement);
    }

    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }
}
