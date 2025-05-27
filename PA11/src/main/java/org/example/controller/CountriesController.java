package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.service.CountriesService;
import org.example.dto.CountriesDTO;

import java.util.List;
import java.util.Map;

@Tag(name = "countries", description = "Operatii cu tari")
@RestController
@RequestMapping("/countries")


public class CountriesController {

    private final CountriesService CountriesService;

    public CountriesController(CountriesService CountriesService) {
        this.CountriesService = CountriesService;
    }

    @Operation(
            summary = "Returneaza toate tarile",
            description = "Returneaza detalii despre toate tarile.",
            tags = {"countries", "get"}
    )
    @GetMapping("/all")
    public ResponseEntity<List<CountriesDTO>> getAllCountriess() {
        List<CountriesDTO> Countries = CountriesService.findAll();
        return ResponseEntity.ok(Countries);
    }

    @Operation(
            summary = "Gaseste o tara dupa ID",
            description = "Returneaza detalii despre o tara folosind ID-ul acestuia.",
            tags = {"countries", "get"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<CountriesDTO> getCountriesById(

            @PathVariable Long id) {
        CountriesDTO CountriesDTO = CountriesService.findById(id);
        return ResponseEntity.ok(CountriesDTO);
    }

    @Operation(
            summary = "Creeaza o inregistrare de tip tara",
            description = "Returneaza detalii despre o tara creata",
            tags = {"countries", "post"}
    )
    @PostMapping
    public ResponseEntity<CountriesDTO> createCountries(

            @Valid @RequestBody CountriesDTO dto) {
        CountriesDTO createdRequest = CountriesService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @Operation(
            summary = "Modifica o tara dupa ID",
            description = "Returneaza detalii despre o tara modificat->update integral",
            tags = {"countries", "put"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<CountriesDTO> updateCountries(
            @PathVariable Long id,
            @Valid @RequestBody CountriesDTO dto) {
        CountriesDTO updatedRequest = CountriesService.update(id, dto);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(
            summary = "Modifica o tara dupa ID",
            description = "Returneaza detalii despre o tara modificat->update partial",
            tags = {"countries", "patch"}
    )
    @PatchMapping("/{id}")
    public ResponseEntity<CountriesDTO> updatePartialCountries(

            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        CountriesDTO updatedRequest = CountriesService.updatePartial(id, updates);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(
            summary = "Sterge o tara dupa ID",
            description = "Sterge o tara dupa un ID",
            tags = {"countries", "delete"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountries(
            @PathVariable Long id) {
        CountriesService.delete(id);
        return ResponseEntity.noContent().build();
    }


}