package ee.skev.veebipood.repository;

import ee.skev.veebipood.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
