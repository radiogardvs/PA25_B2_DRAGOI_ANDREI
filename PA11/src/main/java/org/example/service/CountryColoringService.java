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
        // 1. Obținem toate țările din baza de date
        List<Countries> allCountries = countriesRepository.findAll();

        // 2. Resetăm culoarea fiecărei țări la -1 (necolorat)
        for (Countries country : allCountries) {
            country.setColor(-1L);
        }

        // Salvăm modificările intermediare
        countriesRepository.saveAll(allCountries);

        // 3. Inițializăm o mapare (țară_id -> culoare atribuită)
        Map<Long, Long> countryColorMap = new HashMap<>();

        // 4. Iterăm prin fiecare țară pentru a-i atribui o culoare
        for (Countries country : allCountries) {
            Long countryId = country.getId();

            // 4.1 Obținem vecinii direcți ai țării (ambele sensuri)
            List<Neighbours> neighbours = neighbourRepository.findByCountry(country); // țara -> vecin
            List<Neighbours> reverseNeighbours = neighbourRepository.findByNeighbour(country); // vecin -> țara

            // 4.2 Colectăm toate ID-urile țărilor vecine
            Set<Long> neighborIds = new HashSet<>();
            neighbours.forEach(n -> neighborIds.add(n.getNeighbour()));
            reverseNeighbours.forEach(n -> neighborIds.add(n.getCountry()));

            // 4.3 Verificăm ce culori sunt deja folosite de vecini
            Set<Long> usedColors = neighborIds.stream()
                    .map(countryColorMap::get) // luăm culoarea vecinului din maparea curentă
                    .filter(Objects::nonNull)  // ignorăm valorile nule
                    .collect(Collectors.toSet());

            // 4.4 Dacă nu am găsit culorile vecinilor în mapă (ex: la început), căutăm în baza de date
            if (usedColors.isEmpty()) {
                usedColors = neighborIds.stream()
                        .map(countriesRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(Countries::getColor)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
            }

            // 4.5 Căutăm cea mai mică culoare care nu e folosită de vecini
            long color = 1;
            while (usedColors.contains(color)) {
                color++;
            }

            // 4.6 Salvăm culoarea aleasă în mapare
            countryColorMap.put(countryId, color);
        }

        // 5. Atribuim culorile calculate fiecărei țări în obiectul lor
        for (Countries country : allCountries) {
            Long color = countryColorMap.get(country.getId());
            country.setColor(color);
        }

        // 6. Salvăm toate țările cu culorile noi în baza de date
        countriesRepository.saveAll(allCountries);
    }
}
