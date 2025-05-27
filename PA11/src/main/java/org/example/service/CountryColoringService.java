package org.example.service;

import org.example.dao.Countries;
import org.example.dao.Neighbours;
import org.example.repository.CountriesRepository;
import org.example.repository.NeighboursRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryColoringService {

    private final CountriesRepository countriesRepository;
    private final NeighboursRepository neighbourRepository;

    public CountryColoringService(CountriesRepository countriesRepository, NeighboursRepository neighbourRepository) {
        this.countriesRepository = countriesRepository;
        this.neighbourRepository = neighbourRepository;
    }

    public void assignColorsToCountries() {
        List<Countries> allCountries = countriesRepository.findAll();

        for (Countries country : allCountries) {

            country.setColor(-1L);
        }

        countriesRepository.saveAll(allCountries);


        Map<Long, Long> countryColorMap = new HashMap<>();

        for (Countries country : allCountries) {
            Long countryId = country.getId();

            List<Neighbours> neighbours = neighbourRepository.findByCountry(country);
            List<Neighbours> reverseNeighbourss = neighbourRepository.findByNeighbour(country);

            Set<Long> neighborIds = new HashSet<>();
            neighbours.forEach(n -> neighborIds.add(n.getNeighbour()));
            reverseNeighbourss.forEach(n -> neighborIds.add(n.getCountry()));

            Set<Long> usedColors = neighborIds.stream()
                    .map(countryColorMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());


            if (usedColors.isEmpty()) {
                usedColors = neighborIds.stream()
                        .map(countriesRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(Countries::getColor)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
            }


            long color = 1;
            while (usedColors.contains(color)) {
                color++;
            }

            countryColorMap.put(countryId, color);
        }

        for (Countries country : allCountries) {
            Long color = countryColorMap.get(country.getId());
            country.setColor(color);
        }

        countriesRepository.saveAll(allCountries);
    }

}

