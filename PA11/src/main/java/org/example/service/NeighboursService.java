package org.example.service;

import org.example.dao.Countries;
import org.example.dao.Neighbours;
import org.example.dto.NeighboursDTO;
import org.example.repository.NeighboursRepository;
import org.example.repository.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.mapper.NeighboursMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class NeighboursService {
    private static final Logger logger = Logger.getLogger(NeighboursService.class.getName());
    private final NeighboursRepository neighboursRepository;
    private final CountriesRepository countriesRepository;


    @Autowired
    public NeighboursService(NeighboursRepository neighboursRepository,CountriesRepository countriesRepository ) {

        this.neighboursRepository=neighboursRepository;
        this.countriesRepository=countriesRepository;

    }

    public List<NeighboursDTO> findAll() {
        return neighboursRepository.findAll()
                .stream()
                .map(NeighboursMapper::toDTO)
                .collect(Collectors.toList());
    }

    public NeighboursDTO findById(Long id) {
        Neighbours entity = neighboursRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "CitY with id " + id + " not found";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        return NeighboursMapper.toDTO(entity);
    }

    @Transactional
    public NeighboursDTO create(NeighboursDTO dto) {
        Neighbours entity = NeighboursMapper.toEntity(dto);

        Countries country =countriesRepository.findById(dto.getCountryId()).orElseThrow();
        Countries neighbour =countriesRepository.findById(dto.getNeighbourId()).orElseThrow();

        entity.setCountry(country);
        entity.setNeighbour(neighbour);
        return NeighboursMapper.toDTO(neighboursRepository.save(entity));
    }

    @Transactional
    public NeighboursDTO update(Long id, NeighboursDTO dto) {
        Neighbours existing = neighboursRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Neighbour with id " + id + " not found for update";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });

        Countries country =countriesRepository.findById(dto.getCountryId()).orElseThrow();
        Countries neighbour =countriesRepository.findById(dto.getNeighbourId()).orElseThrow();

        existing.setCountry(country);
        existing.setNeighbour(neighbour);
        return NeighboursMapper.toDTO(neighboursRepository.save(existing));
    }
    @Transactional
    public NeighboursDTO updatePartial(Long id, Map<String, Object> updates) {
        Neighbours existing = neighboursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Neighbours with id " + id + " not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "countryId" ->
                        {
                            Countries country =countriesRepository.findById(Long.parseLong(value.toString())).orElseThrow();

                            existing.setCountry(country);


                        }
                case "neighbourId"->{
                    Countries neighbour =countriesRepository.findById(Long.parseLong(value.toString())).orElseThrow();

                    existing.setNeighbour(neighbour);
                }

            }
        });

        return NeighboursMapper.toDTO(neighboursRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        Neighbours existing = neighboursRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Neighbour with id " + id + " not found for deletion";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        neighboursRepository.delete(existing);
    }
}
