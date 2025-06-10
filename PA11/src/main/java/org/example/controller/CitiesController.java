package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.service.CitiesService;
import org.example.dto.CitiesDTO;

import java.util.List;
import java.util.Map;

@Tag(name = "cities", description = "Operatii cu orase")
@RestController
@RequestMapping("/cities")
@Tag(name = "AccessRequests", description = "Operations related to access requests")

public class CitiesController {

    private final CitiesService CitiesService;

    public CitiesController(CitiesService CitiesService) {
        this.CitiesService = CitiesService;
    }



    @Operation(
            summary = "Returneaza toate orasele",
            description = "Returneaza detalii despre toate orasele.",
            tags = {"cities", "get"}
    )
    @GetMapping("/all")
    public ResponseEntity<List<CitiesDTO>> getAllCitiess() {

        List<CitiesDTO> Cities = CitiesService.findAll();

        return ResponseEntity.ok(Cities);

    }

    @Operation(
            summary = "Gaseste oras dupa ID",
            description = "Returneaza detalii despre un oras folosind ID-ul acestuia.",
            tags = {"cities", "get"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<CitiesDTO> getCitiesById(@PathVariable Long id) {

        CitiesDTO CitiesDTO = CitiesService.findById(id);

        return ResponseEntity.ok(CitiesDTO);
    }


    @Operation(
            summary = "Creeaza o inregistrare de tip oras",
            description = "Returneaza detalii despre un oras creat",
            tags = {"cities", "post"}
    )
    @PostMapping
    public ResponseEntity<CitiesDTO> createCities(@RequestBody CitiesDTO dto) {

        CitiesDTO createdRequest = CitiesService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }


    @Operation(
            summary = "Modifica un oras dupa ID",
            description = "Returneaza detalii despre un oras modificat->update integral",
            tags = {"cities", "put"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<CitiesDTO> updateCities(
            @PathVariable Long id,
            @RequestBody CitiesDTO dto) {
        CitiesDTO updatedRequest = CitiesService.update(id, dto);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(
            summary = "Modifica un oras dupa ID",
            description = "Returneaza detalii despre un oras modificat->update partial",
            tags = {"cities", "patch"}
    )
    @PatchMapping("/{id}")
    public ResponseEntity<CitiesDTO> updatePartialCities(

            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        CitiesDTO updatedRequest = CitiesService.updatePartial(id, updates);
        return ResponseEntity.ok(updatedRequest);
    }


    @Operation(
            summary = "Sterge un oras dupa ID",
            description = "Sterge un oras dupa un ID",
            tags = {"cities", "delete"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCities(
            @PathVariable Long id) {
        CitiesService.delete(id);
        return ResponseEntity.noContent().build();
    }


}