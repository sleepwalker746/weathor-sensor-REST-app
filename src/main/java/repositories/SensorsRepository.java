package repositories;

import entities.Sensors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensors, Integer> {
    Optional<Sensors> findByName(String name);
}
