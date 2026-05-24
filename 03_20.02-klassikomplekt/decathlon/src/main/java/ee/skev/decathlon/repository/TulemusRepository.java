package ee.skev.decathlon.repository;

import ee.skev.decathlon.entity.Tulemus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TulemusRepository extends JpaRepository<Tulemus, Long> {
    Page<Tulemus> findBySpordialaId(Long spordialaId, Pageable pageable);
}