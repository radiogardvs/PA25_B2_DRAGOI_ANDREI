package org.example.mapper;

import org.example.dao.Neighbours;
import org.example.dto.NeighboursDTO;

public class NeighboursMapper {

    public static NeighboursDTO toDTO(Neighbours entity) {
        return new NeighboursDTO(
                entity.getId(),
                entity.getCountry(),
                entity.getNeighbour()
        );
    }

    public static Neighbours toEntity(NeighboursDTO dto) {
        Neighbours entity = new Neighbours();

        return entity;
    }
}
