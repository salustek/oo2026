package ee.skev.decathlon.controller;

import ee.skev.decathlon.dto.TulemusDto;
import ee.skev.decathlon.entity.Spordiala;
import ee.skev.decathlon.entity.Sportlane;
import ee.skev.decathlon.entity.Tulemus;
import ee.skev.decathlon.repository.SpordialaRepository;
import ee.skev.decathlon.repository.SportlaneRepository;
import ee.skev.decathlon.repository.TulemusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TulemusController {

    @Autowired
    private TulemusRepository tulemusRepository;

    @Autowired
    private SpordialaRepository spordialaRepository;

    @Autowired
    private SportlaneRepository sportlaneRepository;


    @GetMapping("tulemused")
    public Page<Tulemus> getTulemused(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long spordialaId,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equals("asc")
                ? Sort.by("punktid").ascending()
                : Sort.by("punktid").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (spordialaId != null) {
            return tulemusRepository.findBySpordialaId(spordialaId, pageable);
        }
        return tulemusRepository.findAll(pageable);
    }

    @PostMapping("add-tulemus")
    public Tulemus addTulemus(@RequestBody Tulemus tulemus){
        return tulemusRepository.save(tulemus);
    }

    @PostMapping("tulemused")
    public Tulemus addTulemus(@RequestBody TulemusDto dto){
        if (dto.getSportlaneId() == null || dto.getSpordialaId() == null) {
            throw new RuntimeException("Missing references");
        }

        Sportlane sportlane = sportlaneRepository.findById(dto.getSportlaneId())
                .orElseThrow();

        Spordiala spordiala = spordialaRepository.findById(dto.getSpordialaId())
                .orElseThrow();

        Tulemus tulemus = new Tulemus();
        tulemus.setPunktid(dto.getPunktid());
        tulemus.setSportlane(sportlane);
        tulemus.setSpordiala(spordiala);

        tulemusRepository.save(tulemus);

        return tulemus;
    }

    @DeleteMapping("tulemused/{id}")
    public List<Tulemus> deleteTulemus(@PathVariable Long id) {
        tulemusRepository.deleteById(id); // kustutan
        return tulemusRepository.findAll(); // uuenenud seis
    }
}