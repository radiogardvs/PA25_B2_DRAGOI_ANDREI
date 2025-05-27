package org.example.mapper;

import org.example.dao.Cities;
import org.example.dao.Countries;
import org.example.dto.CountriesDTO;

import java.util.ArrayList;
import java.util.List;

public class CountriesMapper {

    public static CountriesDTO toDTO(Countries entity) {
        List<String> cityNames = new ArrayList<>();
        if (entity.getCities() != null) {
            for (Cities city : entity.getCities()) {
                cityNames.add(city.getName());
            }
        }
        return new CountriesDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getContinent().getId(),
                cityNames,
                entity.getColor()
        );
    }
    public static Countries toEntity(CountriesDTO dto) {
        Countries entity = new Countries();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }
}
