package org.example.repository;

import org.example.dao.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CitiesRepository extends JpaRepository<Cities,Long> {
}

