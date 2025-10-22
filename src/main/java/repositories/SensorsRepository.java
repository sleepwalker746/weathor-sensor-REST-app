package repositories;

import entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
}
