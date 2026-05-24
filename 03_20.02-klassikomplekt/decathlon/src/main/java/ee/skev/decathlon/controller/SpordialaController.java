package ee.skev.decathlon.controller;

import ee.skev.decathlon.entity.Spordiala;
import ee.skev.decathlon.repository.RiikRepository;
import ee.skev.decathlon.repository.SpordialaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class SpordialaController {

    @Autowired
    private SpordialaRepository spordialaRepository;

    @Autowired
    private RiikRepository riikRepository;

    @GetMapping("spordialad")
    public List<Spordiala> getSpordialad() { return spordialaRepository.findAll(); }

    @PostMapping("add-spordiala")
    public Spordiala addSpordiala(@RequestBody Spordiala spordiala){
        return spordialaRepository.save(spordiala);
    }

}