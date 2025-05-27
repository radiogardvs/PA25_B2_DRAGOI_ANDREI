package com.pa.laboratory9.model;

import jakarta.persistence.*;

@Entity
@Table(name = "City")
@NamedQuery(name = "City.findByName", query = "SELECT c FROM City c WHERE c.name LIKE :name")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int population;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // Constructori
    public City() {}

    public City(String name, int population, Country country) {
        this.name = name;
        this.population = population;
        this.country = country;
    }

    // Getteri și setteri
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getPopulation() { return population; }

    public void setPopulation(int population) { this.population = population; }

    public Country getCountry() { return country; }

    public void setCountry(Country country) { this.country = country; }
}
