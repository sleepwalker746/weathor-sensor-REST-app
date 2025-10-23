package ua.august.rest_weather_sensor.repositories;

import ua.august.rest_weather_sensor.entities.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
}
