package ee.skev.cipher.controller;


import ee.skev.cipher.entity.Word;
import ee.skev.cipher.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordController {

    @Autowired
    WordRepository wordRepository;

    @PostMapping("/addWord")
    public String addWord(@RequestBody Word word) {

        if(word.getWord() == null || word.getWord().isEmpty()){
            return "Sõna ei tohi olla tühi";
        }

        if(word.getWord().length() < 2){
            return "Sõna peab olema vähemalt 2 tähemärki pikk";
        }

        wordRepository.save(word);

        return "Sõna lisatud";
    }

    @GetMapping("/words")
    public List<Word> getWords(){
        return wordRepository.findAll();
    }

    @PostMapping("/shiftWords")
    public List<Word> shiftWords(@RequestParam int shift) {

        List<Word> words = wordRepository.findAll();

        for (Word w : words) {
            w.setWord(shiftString(w.getWord(), shift));
        }

        wordRepository.saveAll(words);
        return words;
    }

    @PostMapping("/shiftByLength")
    public List<Word> shiftByLength() {

        List<Word> words = wordRepository.findAll();

        for (Word w : words) {
            int shift = w.getWord().length();
            w.setWord(shiftString(w.getWord(), shift));
        }
        wordRepository.saveAll(words);
        return words;
    }


    private String shiftString(String input, int shift) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char shifted = (char) (c + shift);
                result.append(shifted);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}