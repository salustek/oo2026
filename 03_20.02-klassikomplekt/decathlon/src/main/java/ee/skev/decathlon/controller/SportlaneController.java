package ee.skev.decathlon.controller;

import ee.skev.decathlon.entity.Sportlane;
import ee.skev.decathlon.entity.Tulemus;
import ee.skev.decathlon.repository.SportlaneRepository;
import ee.skev.decathlon.repository.TulemusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class SportlaneController {

    @Autowired
    private SportlaneRepository sportlaneRepository;

    @Autowired
    private TulemusRepository tulemusRepository;

    @GetMapping("athletes")
    public Page<Sportlane> getSportlased(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long riikId,
            @RequestParam(defaultValue = "false") boolean sortByResult) {

        Pageable pageable = PageRequest.of(page, size);

        if (sortByResult) {
            return sportlaneRepository.findAllSortedByResult(riikId, pageable);
        }
        if (riikId != null) {
            return sportlaneRepository.findByCountryId(riikId, pageable);
        }
        return sportlaneRepository.findAll(pageable);
    }

    @GetMapping("athletes/{id}")
    public Sportlane getSportlane(@PathVariable Long id) { return sportlaneRepository.findById(id).orElseThrow(); }

    @DeleteMapping("athletes/{id}")
    public List<Sportlane> deleteSportlane(@PathVariable Long id) {
        sportlaneRepository.deleteById(id);
        return sportlaneRepository.findAll();
    }

    @PostMapping("athletes")
    public Sportlane signup(@RequestBody Sportlane sportlane){
        return sportlaneRepository.save(sportlane);
    }

    @PostMapping("athletes/{id}/tulemused")
    public Sportlane addResult(
            @PathVariable Long id,
            @RequestBody Tulemus result){

        Sportlane sportlane = sportlaneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        if (result.getPunktid() <= 0) {
            throw new RuntimeException("Points must be higher than 0");
        }

        result.setSportlane(sportlane);
        tulemusRepository.save(result);

        return sportlaneRepository.findById(id).get();
    }



}