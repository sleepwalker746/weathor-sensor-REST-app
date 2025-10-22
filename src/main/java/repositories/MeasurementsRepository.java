package repositories;

import entities.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
}
