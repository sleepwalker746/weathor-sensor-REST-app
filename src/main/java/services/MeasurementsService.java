package services;

import entities.Measurements;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.MeasurementsRepository;
import util.MeasurementNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }

    public Measurements findByMeasurementId(int id) {
        Optional<Measurements> foundMeasurements = measurementsRepository.findById(id);
        return foundMeasurements.orElseThrow(() -> new MeasurementNotFoundException("Measurement with id: " + id + " not found"));
    }

    @Transactional
    public void saveMeasurements(Measurements measurements) {
        measurementsRepository.save(measurements);
    }

}
