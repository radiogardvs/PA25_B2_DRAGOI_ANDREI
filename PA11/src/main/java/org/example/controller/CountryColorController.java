package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.dto.CountriesDTO;
import org.example.service.CountriesService;
import org.example.service.CountryColoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "colors", description = "Colorarea tarilor prin id-uri de culoare distincte")
@RestController
@RequestMapping("/api/colors")
public class CountryColorController {

    private final CountryColoringService service;
    private final CountriesService CountriesService;



    public CountryColorController(CountryColoringService service, CountriesService CountriesService) {
        this.service = service;
        this.CountriesService = CountriesService;
    }

    @Operation(
            summary = "Colorare tari",
            description = "Imi coloreaza tarile dupa id-uri distincte",
            tags = {"/api/colors", "post"}
    )
    @PostMapping("/assign")
    public ResponseEntity<List<CountriesDTO>> assignColorsToAllCountries() {
        service.assignColorsToCountries();
        List<CountriesDTO> Countries = CountriesService.findAll();
        return ResponseEntity.ok(Countries);
    }
}
