package org.example.repository;

import org.example.dao.Continents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentsRepository extends JpaRepository<Continents,Long> {
}
