package ua.august.rest_weather_sensor.services;

import org.modelmapper.ModelMapper;
import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import ua.august.rest_weather_sensor.entities.Measurements;
import ua.august.rest_weather_sensor.entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.august.rest_weather_sensor.repositories.MeasurementsRepository;

import java.util.List;


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

    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }

    public Long findRainingMeasurements() {
       return measurementsRepository.findAll().stream()
                .filter(Measurements::isRaining)
                .count();
    }
}
