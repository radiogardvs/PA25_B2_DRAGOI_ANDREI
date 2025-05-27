package org.example.service;

import org.example.dao.Continents;
import org.example.dao.Countries;
import org.example.dto.CountriesDTO;
import org.example.repository.ContinentsRepository;
import org.example.repository.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.mapper.CountriesMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CountriesService {
    private static final Logger logger = Logger.getLogger(CountriesService.class.getName());
    private final CountriesRepository countriesRepository;
    private final ContinentsRepository continentsRepository;

    @Autowired
    public CountriesService(CountriesRepository countriesRepository, ContinentsRepository continentsRepository) {
        this.countriesRepository = countriesRepository;
        this.continentsRepository = continentsRepository;
    }

    public List<CountriesDTO> findAll() {
        return countriesRepository.findAll()
                .stream()
                .map(CountriesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CountriesDTO findById(Long id) {
        Countries entity = countriesRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Country with id " + id + " not found";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        return CountriesMapper.toDTO(entity);
    }
    @Transactional
    public CountriesDTO create(CountriesDTO dto) {
        Countries entity = CountriesMapper.toEntity(dto);
        Continents continent=continentsRepository.findById(dto.getContinentId())
                .orElseThrow() ;
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setContinent(continent);
        entity.setCities(new ArrayList<>());
        Countries saved = countriesRepository.save(entity);


        return CountriesMapper.toDTO(saved);
    }
    @Transactional
    public CountriesDTO update(Long id, CountriesDTO dto) {
        Countries existing = countriesRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Country with id " + id + " not found for update";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        Continents continent =continentsRepository.findById(dto.getContinentId()).orElseThrow();
        existing.setName(dto.getName());
        existing.setCode(dto.getCode());
        existing.setContinent(continent);
        return CountriesMapper.toDTO(countriesRepository.save(existing));
    }
    @Transactional
    public CountriesDTO updatePartial(Long id, Map<String, Object> updates) {
        Countries existing = countriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Countries with id " + id + " not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> existing.setName(value.toString());
                case "code" -> existing.setCode(value.toString());
                case "continent" ->
                        {
                            Continents continent =continentsRepository.findById((Long) value).orElseThrow();

                            existing.setContinent(continent);
                        }
            }
        });

        return CountriesMapper.toDTO(countriesRepository.save(existing));
    }
    @Transactional
    public void delete(Long id) {
        Countries existing = countriesRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Country with id " + id + " not found for deletion";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        countriesRepository.delete(existing);
    }
}
