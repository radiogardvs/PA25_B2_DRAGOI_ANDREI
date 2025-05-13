package com.pa.laboratory9.model;

import jakarta.persistence.*;
import java.util.List;

@Table(name="Country")
@Entity
@NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name LIKE :name")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private int population;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities;

    public Country() {}

    public Country(String name, String code, int population, Continent continent) {
        this.name = name;
        this.code = code;
        this.population = population;
        this.continent = continent;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public int getPopulation() { return population; }

    public void setPopulation(int population) { this.population = population; }

    public Continent getContinent() { return continent; }

    public void setContinent(Continent continent) { this.continent = continent; }

    public List<City> getCities() { return cities; }

    public void setCities(List<City> cities) { this.cities = cities; }
}
