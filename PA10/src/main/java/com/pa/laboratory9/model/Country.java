package com.pa.laboratory9.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Country")
@NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name LIKE :name")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String continent;

    private Long population;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities;

    // Constructori
    public Country() {}

    public Country(String name, String code, String continent, Long population) {
        this.name = name;
        this.code = code;
        this.continent = continent;
        this.population = population;
    }

    // Getteri și setteri
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getContinent() { return continent; }

    public void setContinent(String continent) { this.continent = continent; }

    public Long getPopulation() { return population; }

    public void setPopulation(Long population) { this.population = population; }

    public List<City> getCities() { return cities; }

    public void setCities(List<City> cities) { this.cities = cities; }
}
