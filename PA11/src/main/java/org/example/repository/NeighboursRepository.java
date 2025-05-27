package org.example.repository;

import org.example.dao.Countries;
import org.example.dao.Neighbours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NeighboursRepository extends JpaRepository<Neighbours,Long> {
        List<Neighbours> findByCountry(Countries countryId);
    List<Neighbours> findByNeighbour(Countries neighbourId);
}

