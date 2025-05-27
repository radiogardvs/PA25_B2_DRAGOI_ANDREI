package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.service.NeighboursService;
import org.example.dto.NeighboursDTO;

import java.util.List;
import java.util.Map;

@Tag(name = "neighbours", description = "Tuple de tipul tara-vecin")
@RestController
@RequestMapping("/neighbours")

public class NeighboursController {

    private final NeighboursService NeighboursService;

    public NeighboursController(NeighboursService NeighboursService) {
        this.NeighboursService = NeighboursService;
    }

    @Operation(
            summary = "Returneaza toate vecinatatile",
            description = "Returneaza detalii despre toate vecinatatile.",
            tags = {"neighbours", "get"}
    )
    @GetMapping("/all")
    public ResponseEntity<List<NeighboursDTO>> getAllNeighbourss() {

        List<NeighboursDTO> Neighbours = NeighboursService.findAll();

        return ResponseEntity.ok(Neighbours);

    }

    @Operation(
            summary = "Gaseste o vecinatate dupa ID",
            description = "Returneaza detalii despre o vecinatate folosind ID-ul acestuia.",
            tags = {"neighbours", "get"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<NeighboursDTO> getNeighboursById(@PathVariable Long id) {

        NeighboursDTO neighboursDTO = NeighboursService.findById(id);

        return ResponseEntity.ok(neighboursDTO);
    }


    @Operation(
            summary = "Creeaza o inregistrare de tip vecinatate",
            description = "Returneaza detalii despre o vecinatate creata",
            tags = {"neighbours", "post"}
    )
    @PostMapping
    public ResponseEntity<NeighboursDTO> createNeighbours(@Valid @RequestBody NeighboursDTO dto) {

        NeighboursDTO createdRequest = NeighboursService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @Operation(
            summary = "Modifica o vecinatate dupa ID",
            description = "Returneaza detalii despre o vecinatate modificat->update integral",
            tags = {"neighbours", "put"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<NeighboursDTO> updateNeighbours(
            @PathVariable Long id,
            @Valid @RequestBody NeighboursDTO dto) {
        NeighboursDTO updatedRequest = NeighboursService.update(id, dto);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(
            summary = "Modifica o vecinatate dupa ID",
            description = "Returneaza detalii despre o vecinatate modificat->update partial",
            tags = {"neighbours", "patch"}
    )
    @PatchMapping("/{id}")
    public ResponseEntity<NeighboursDTO> updatePartialNeighbours(

            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        NeighboursDTO updatedRequest = NeighboursService.updatePartial(id, updates);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(
            summary = "Sterge o vecinatate dupa ID",
            description = "Sterge o vecinatate dupa un ID",
            tags = {"cities", "delete"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNeighbours(
            @PathVariable Long id) {
        NeighboursService.delete(id);
        return ResponseEntity.noContent().build();
    }


}