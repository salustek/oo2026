package ee.skev.cipher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Setter;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String word;

    public Word() {}

    public Word(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

}