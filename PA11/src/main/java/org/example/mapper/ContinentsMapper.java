package org.example.mapper;

import org.example.dao.Continents;
import org.example.dao.Countries;
import org.example.dto.ContinentsDTO;
import org.example.dto.CountriesDTO;

import java.util.ArrayList;
import java.util.List;

public class ContinentsMapper {

    public static ContinentsDTO toDTO(Continents entity) {
        List<String> countryNames = new ArrayList<>();
        if (entity.getCountries() != null) {
            for (var country : entity.getCountries()) {
                countryNames.add(country.getName());
            }
        }
        return new ContinentsDTO(
                entity.getId(),
                entity.getName(),
                countryNames
        );
    }



    public static Continents toEntity(ContinentsDTO dto) {
        Continents entity = new Continents();
        entity.setName(dto.getName());
        return entity;
    }
}
