package org.example.service;

import org.example.dao.Countries;
import org.example.dao.Cities;
import org.example.dto.CitiesDTO;
import org.example.repository.CitiesRepository;
import org.example.repository.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.mapper.CitiesMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CitiesService {
    private static final Logger logger = Logger.getLogger(CitiesService.class.getName());
    private final CitiesRepository citiesRepository;
    private final CountriesRepository countriesRepository;


    @Autowired
    public CitiesService(CitiesRepository citiesRepository, CountriesRepository countriesRepository) {
        this.countriesRepository=countriesRepository;
        this.citiesRepository=citiesRepository;
        
    }

    public List<CitiesDTO> findAll() {
        return citiesRepository.findAll()
                .stream()
                .map(CitiesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CitiesDTO findById(Long id) {
        Cities entity = citiesRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "CitY with id " + id + " not found";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        return CitiesMapper.toDTO(entity);
    }

    @Transactional
    public CitiesDTO create(CitiesDTO dto) {
        Cities entity = CitiesMapper.toEntity(dto);

        Countries country =countriesRepository.findById(dto.getCountryId()).orElseThrow();

        entity.setName(dto.getName());
        entity.setPopulation(dto.getPopulation());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setCountry(country);
        return CitiesMapper.toDTO(citiesRepository.save(entity));
    }

    @Transactional
    public CitiesDTO update(Long id, CitiesDTO dto) {
        Cities existing = citiesRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "CitY with id " + id + " not found for update";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        Countries country =countriesRepository.findById(dto.getCountryId()).orElseThrow();
        existing.setName(dto.getName());
        existing.setPopulation(dto.getPopulation());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setCountry(country);
        return CitiesMapper.toDTO(citiesRepository.save(existing));
    }
    @Transactional
    public CitiesDTO updatePartial(Long id, Map<String, Object> updates) {
        Cities existing = citiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cities with id " + id + " not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> existing.setName(value.toString());
                case "capital"->existing.setCapital(Boolean.valueOf(value.toString()));
                case "population"->existing.setPopulation(Integer.valueOf(value.toString()));
                case "latitude"->existing.setLatitude(Double.valueOf(value.toString()));
                case "longitude"->existing.setLongitude(Double.valueOf(value.toString()));
                case "countryId"->{
                    Countries country =countriesRepository.findById((Long)value).orElseThrow();
                    existing.setCountry(country);
                }

            }
        });

        return CitiesMapper.toDTO(citiesRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        Cities existing = citiesRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "CitY with id " + id + " not found for deletion";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        citiesRepository.delete(existing);
    }
}
