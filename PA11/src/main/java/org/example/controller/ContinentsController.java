package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.service.ContinentsService;
import org.example.dto.ContinentsDTO;

import java.util.List;
import java.util.Map;

@Tag(name = "continents", description = "Operatii cu continente")
@RestController
@RequestMapping("/continents")


public class ContinentsController {

    private final ContinentsService ContinentsService;

    public ContinentsController(ContinentsService ContinentsService) {
        this.ContinentsService = ContinentsService;
    }

    @Operation(
            summary = "Returneaza toate continentele",
            description = "Returneaza detalii despre toate continentele.",
            tags = {"continents", "get"}
    )
    @GetMapping("/all")
    public ResponseEntity<List<ContinentsDTO>> getAllContinentss() {
        List<ContinentsDTO> Continents = ContinentsService.findAll();
        return ResponseEntity.ok(Continents);
    }

    @Operation(
            summary = "Gaseste un continent dupa ID",
            description = "Returneaza detalii despre un continent folosind ID-ul acestuia.",
            tags = {"continents", "get"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<ContinentsDTO> getContinentsById(@PathVariable Long id) {
        ContinentsDTO ContinentsDTO = ContinentsService.findById(id);
        return ResponseEntity.ok(ContinentsDTO);
    }

    @Operation(
            summary = "Creeaza o inregistrare de tip continent",
            description = "Returneaza detalii despre un continent creat",
            tags = {"continents", "post"}
    )
    @PostMapping
    public ResponseEntity<ContinentsDTO> createContinents(@Valid @RequestBody ContinentsDTO dto) {
        ContinentsDTO createdRequest = ContinentsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @Operation(
            summary = "Modifica un continent dupa ID",
            description = "Returneaza detalii despre un continent modificat->update integral",
            tags = {"continents", "put"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<ContinentsDTO> updateContinents(@PathVariable Long id, @Valid @RequestBody ContinentsDTO dto) {
        ContinentsDTO updatedRequest = ContinentsService.update(id, dto);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(
            summary = "Modifica un continent dupa ID",
            description = "Returneaza detalii despre un continent modificat->update partial",
            tags = {"continents", "patch"}
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ContinentsDTO> updatePartialContinents(

            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        ContinentsDTO updatedRequest = ContinentsService.updatePartial(id, updates);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(
            summary = "Sterge un continent dupa ID",
            description = "Sterge un continent dupa un ID",
            tags = {"continents", "delete"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContinents(
            @PathVariable Long id) {
        ContinentsService.delete(id);
        return ResponseEntity.noContent().build();
    }


}