package ee.skev.decathlon.controller;

import ee.skev.decathlon.dto.KohtunikDto;
import ee.skev.decathlon.dto.ValjakDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ApiController {

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("kohtunikud")
    public List <KohtunikDto> getKohtunikud() {
        String url = "https://69fdaa6430ad0a6fd1c13a82.mockapi.io/decathlon/kohtunik";
        KohtunikDto[] response = restTemplate.exchange(
                url, HttpMethod.GET, null, KohtunikDto[].class
        ).getBody();
        return Arrays.asList(response);
    }

    @GetMapping("valjakud")
    public List <ValjakDto> getValjak() {
        String url = "https://69fdaa6430ad0a6fd1c13a82.mockapi.io/decathlon/valjak";
        ValjakDto[] response = restTemplate.exchange(
                url, HttpMethod.GET, null, ValjakDto[].class
        ).getBody();
        return Arrays.asList(response);
    }
}