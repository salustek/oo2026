package ee.skev.mekanik.controller;

import ee.skev.mekanik.entity.Car;
import ee.skev.mekanik.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("cars")
    public List<Car> getCars(){
        return carRepository.findAll();
    }

    @PostMapping("cars")
    public List<Car> addCar(@RequestBody Car car){
        if (car.getId()!=null) {
            throw new RuntimeException("Can not add with ID");
        }

        if (car.getBrand() == null || car.getBrand().isBlank()) {
            throw new RuntimeException("Car make is required");
        }

        if (car.getModel() == null || car.getModel().isBlank()) {
            throw new RuntimeException("Car model is required");
        }

        if (car.getYear() < 1886) {
            throw new RuntimeException("Year must be 1886 or newer"); // esimene auto
        }

        if (car.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }
        carRepository.save(car);
        return carRepository.findAll();
    }
}