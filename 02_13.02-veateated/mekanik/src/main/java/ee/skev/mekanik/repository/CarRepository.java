package ee.skev.mekanik.repository;

import ee.skev.mekanik.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}