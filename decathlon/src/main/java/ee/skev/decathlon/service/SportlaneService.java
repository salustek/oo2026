package ee.skev.decathlon.service;



import ee.skev.decathlon.entity.Sportlane;
import ee.skev.decathlon.repository.SportlaneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor



public class SportlaneService {

    private SportlaneRepository sportlaneRepository;

    public void validate(Sportlane sportlane){
        if (sportlane.getId() != null) {
            throw new RuntimeException("Error with ID");
        }
        if (sportlaneRepository.existsByName(sportlane.getName())) {
            throw new RuntimeException("Already exists");
        }
        if (sportlane.getName() == null || sportlane.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }
    }
}