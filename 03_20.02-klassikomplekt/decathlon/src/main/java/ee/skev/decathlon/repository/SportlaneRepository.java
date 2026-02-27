package ee.skev.decathlon.repository;

import ee.skev.decathlon.entity.Sportlane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportlaneRepository extends JpaRepository<Sportlane,Long> {
    boolean existsByName(String name);
}