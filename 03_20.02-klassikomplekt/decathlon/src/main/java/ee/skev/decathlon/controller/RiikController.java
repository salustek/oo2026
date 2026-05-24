package ee.skev.decathlon.controller;

import ee.skev.decathlon.entity.Riik;
import ee.skev.decathlon.repository.RiikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RiikController {

    @Autowired
    private RiikRepository riikRepository;

    @GetMapping("countries")
    public List<Riik> getCountries() { return riikRepository.findAll(); }

    @PostMapping("add-country")
    public Riik addCountry(@RequestBody Riik riik){
        return riikRepository.save(riik);
    }

}