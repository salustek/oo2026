package ee.skev.decathlon.repository;

import ee.skev.decathlon.entity.Sportlane;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SportlaneRepository extends JpaRepository<Sportlane, Long> {
    boolean existsByName(String name);

    Page<Sportlane> findByCountryId(Long countryId, Pageable pageable);

    @Query(value = "SELECT s FROM Sportlane s LEFT JOIN s.tulemused t " +
                   "WHERE (:riikId IS NULL OR s.country.id = :riikId) " +
                   "GROUP BY s ORDER BY COALESCE(SUM(t.punktid), 0) DESC",
           countQuery = "SELECT COUNT(DISTINCT s) FROM Sportlane s " +
                        "WHERE (:riikId IS NULL OR s.country.id = :riikId)")
    Page<Sportlane> findAllSortedByResult(@Param("riikId") Long riikId, Pageable pageable);
}