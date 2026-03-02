package ee.skev.rentalstore.controller;

import ee.skev.rentalstore.dto.FilmRentalDto;
import ee.skev.rentalstore.entity.Film;
import ee.skev.rentalstore.entity.Rental;
import ee.skev.rentalstore.repository.FilmRepository;
import ee.skev.rentalstore.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final RentalRepository rentalRepository;
    private final FilmRepository filmRepository;
    private double premiumPrice = 4;
    private double basicPrice = 3;

    @GetMapping("rentals")
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    // DTO ---> mis film (ID), mitmeks päevaks võtan
    @PostMapping("start-rental")
    public Rental startRental(@RequestBody List<FilmRentalDto> filmRentalDtos) {
        Rental rental = new Rental(); // {id: null, initialFee: null, lateFee: null}
        Rental dbRental = rentalRepository.save(rental); // {id: 2, initialFee: null, lateFee: null}

        double sum = 0;
        // mis tüüp       muutuja          mida läbi käin
        for (FilmRentalDto filmRentalDto : filmRentalDtos) {
            Film dbFilm = filmRepository.findById(filmRentalDto.filmId()).orElseThrow();
            dbFilm.setRental(dbRental);
            dbFilm.setDays(filmRentalDto.days());
            switch (dbFilm.getType()) {
                case NEW -> sum += premiumPrice * filmRentalDto.days();
                case REGULAR -> {
                    if (filmRentalDto.days() <= 3) {
                        sum += basicPrice;
                    } else {
                        sum += basicPrice + basicPrice * (filmRentalDto.days() - 3);
                    }
                }
                case OLD -> {
                    if (filmRentalDto.days() <= 5) {
                        sum += basicPrice;
                    } else {
                        sum += basicPrice + basicPrice * (filmRentalDto.days() - 5);
                    }
                }
            }
            filmRepository.save(dbFilm);
        }

        dbRental.setInitialFee(sum); // {id: 2, initialFee: 123.0, lateFee: null}
        return rentalRepository.save(dbRental);
    }

    // DTO ---> mis film (ID), mitu päeva tegelikult rendis oli
    @PostMapping("end-rental")
    public double endRental(@RequestBody List<FilmRentalDto> filmRentalDtos) {

        double sum = 0;
        for (FilmRentalDto filmRentalDto : filmRentalDtos) {
            Film dbFilm = filmRepository.findById(filmRentalDto.filmId()).orElseThrow();
            Rental rental = dbFilm.getRental();
            rental.setLateFee(rental.getLateFee() + FILMI_SUMMA);
            rentalRepository.save(rental);

            dbFilm.setRental(null);
            dbFilm.setDays(0);
            filmRepository.save(dbFilm);
        }
        return sum;
    }
}