package org.example.mapper;

import org.example.dao.Cities;
import org.example.dto.CitiesDTO;

public class CitiesMapper {

    public static CitiesDTO toDTO(Cities entity) {
        return new CitiesDTO(
                entity.getId(),
                entity.getCountry().getId(),
                entity.getName(),
                entity.getCapital(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getPopulation()

        );
    }

    public static Cities toEntity(CitiesDTO dto) {
        Cities entity = new Cities();
        entity.setName(dto.getName());
        entity.setCapital(dto.getCapital());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setPopulation(dto.getPopulation());
        return entity;
    }
}
