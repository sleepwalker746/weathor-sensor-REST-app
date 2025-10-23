package ua.august.rest_weather_sensor.repositories;

import ua.august.rest_weather_sensor.entities.Sensors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensors, Integer> {
    Optional<Sensors> findByName(String name);
}
