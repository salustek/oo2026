package ee.skev.filmid.repository;

import ee.skev.filmid.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long>{
}