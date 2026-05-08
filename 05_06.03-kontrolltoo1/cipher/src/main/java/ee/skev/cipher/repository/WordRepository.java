package ee.skev.cipher.repository;


import ee.skev.cipher.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}