package com.pa.laboratory9.model;

import jakarta.persistence.*;
import java.util.List;

@Table(name = "Continent")
@Entity
@NamedQuery(name = "Continent.findByName", query = "SELECT c FROM Continent c WHERE c.name LIKE :name")
public class Continent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Country> countries;

    public Continent() {}

    public Continent(String name) {
        this.name = name;
    }

    // Getteri și setteri
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Country> getCountries() { return countries; }

    public void setCountries(List<Country> countries) { this.countries = countries; }
}
