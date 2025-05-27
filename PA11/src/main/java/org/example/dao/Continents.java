package org.example.dao;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "continents")

public class Continents implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Countries> countries = new ArrayList<>();


    public Continents(String name) {
        this.name = name;
    }

    public Continents() {
        this.name = "NoContinent";
    }


    public String getName() {
        return name;
    }
    public void  setName(String name) {
        this.name = name;
    }
    public List<Countries> getCountries() {
        return countries;
    }
    public void  setCountries(List<Countries> countries) {
        this.countries = countries;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}