package ua.august.rest_weather_sensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import ua.august.rest_weather_sensor.entities.Measurements;
import ua.august.rest_weather_sensor.entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.august.rest_weather_sensor.repositories.MeasurementsRepository;



@Service
public class MeasurementsService {
    private final ModelMapper modelMapper;
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(ModelMapper modelMapper, MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.modelMapper = modelMapper;
        this.measurementsRepository = measurementsRepository;

        this.sensorsService = sensorsService;
    }

    public void saveMeasurements(MeasurementsDTO measurementsDTO) {

        Measurements measurements = modelMapper.map(measurementsDTO, Measurements.class);
        String sensorName = measurementsDTO.getSensorsName();
        Sensors sensor = sensorsService.findByName(sensorName);
        measurements.setSensor(sensor);
        measurementsRepository.save(measurements);
    }

    public Page<Measurements> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return measurementsRepository.findAll(pageable);
    }

    public Long findRainingMeasurements() {
       return measurementsRepository.findAll().stream()
                .filter(Measurements::isRaining)
                .count();
    }
}
