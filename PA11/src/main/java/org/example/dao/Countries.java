package org.example.dao;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
public class Countries implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "continent_id")
    private Continents continent;


    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cities> cities = new ArrayList<>();

    private Long color;
    public Countries() {
        this.name = "NoCountry";
        this.code = "NoCode";
        this.continent = new Continents();
        this.color = 0L;
    }

    public Countries(String name, String code,Continents continent, Long color) {
        this();
        this.name = name;
        this.code = code;
        this.continent = continent;
        this.color = color;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Continents getContinent() {
        return continent;
    }
    public void setContinent(Continents continent) {
        this.continent=continent;
    }

    public List<Cities> getCities() {
        return cities;
    }
    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }



    public Long getColor() {
        return color;
    }
    public void setColor(Long color) {
        this.color = color;
    }
    

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}