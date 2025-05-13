package com.pa.laboratory9.solver;


import com.pa.laboratory9.model.City;
import com.pa.laboratory9.repository.jdbc.CityJdbcRepository;
import com.pa.laboratory9.repository.jdbc.CountryJdbcRepository;

import java.util.*;
import java.util.stream.Collectors;

public class CitySolver {

    private final CityJdbcRepository cityRepository;
    private final CountryJdbcRepository countryRepository;

    public CitySolver(CityJdbcRepository cityRepository, CountryJdbcRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    public List<City> solve(char startingLetter, int minPopulation, int maxPopulation) {
        List<City> cities = cityRepository.findByName(String.valueOf(startingLetter));

        List<City> filteredCities = cities.stream()
                .filter(city -> city.getPopulation() >= minPopulation && city.getPopulation() <= maxPopulation)
                .collect(Collectors.toList());

        // Group cities by the first letter of their names
        Map<Character, List<City>> citiesByLetter = filteredCities.stream()
                .collect(Collectors.groupingBy(city -> city.getName().charAt(0)));

        List<City> validCities = new ArrayList<>();
        Set<String> countries = new HashSet<>();

        for (List<City> group : citiesByLetter.values()) {
            for (City city : group) {
                if (!countries.contains(city.getCountry().getName())) {
                    validCities.add(city);
                    countries.add(city.getCountry().getName());
                }
            }
        }

        return validCities;
    }
}
