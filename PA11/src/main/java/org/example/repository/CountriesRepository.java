package org.example.repository;

import org.example.dao.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountriesRepository extends JpaRepository<Countries,Long> {
}
