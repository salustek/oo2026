package ee.skev.decathlon.controller;

import ee.skev.decathlon.entity.Sportlane;
import ee.skev.decathlon.entity.Tulemus;
import ee.skev.decathlon.repository.SportlaneRepository;
import ee.skev.decathlon.repository.TulemusRepository;
import ee.skev.decathlon.service.SportlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SportlaneController {

    @Autowired
    private SportlaneRepository sportlaneRepository;

    @Autowired
    private SportlaneService sportlaneService;

    @Autowired
    private TulemusRepository tulemusRepository;

    @GetMapping("athletes")
    public List<Sportlane> getSportlased() { return sportlaneRepository.findAll(); }

    @GetMapping("athletes/{id}")
    public Sportlane getSportlane(@PathVariable Long id) { return sportlaneRepository.findById(id).orElseThrow(); }

    @GetMapping("athletes/{id}/total")
    public int getTotalPoints(@PathVariable Long id){

        Sportlane sportlane = sportlaneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        return sportlane.getTulemused()
                .stream()
                .mapToInt(Tulemus::getPunktid)
                .sum();
    }

    @PostMapping("athletes")
    public Sportlane signup(@RequestBody Sportlane sportlane){
        //sportlaneService.validate(sportlane);
        return sportlaneRepository.save(sportlane);
    }

    @PostMapping("athletes/{id}/tulemused")
    public Sportlane addResult(
            @PathVariable Long id,
            @RequestBody Tulemus result){

        Sportlane sportlane = sportlaneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        if (result.getPunktid() <= 0) {
            throw new RuntimeException("Points must be more than 0");
        }

        result.setSportlane(sportlane);
        tulemusRepository.save(result);

        return sportlaneRepository.findById(id).get();
    }



}