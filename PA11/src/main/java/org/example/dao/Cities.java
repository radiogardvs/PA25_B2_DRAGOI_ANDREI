package org.example.dao;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "cities")
public class Cities implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Countries country;


    private String name;

    private Boolean capital;

    private Double latitude;

    private Double longitude;

    private Integer population;


    public Cities() {
        this.name = "NoCity";
        this.country = new Countries();
        this.capital = false;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.population = 0;

    }

    public Cities(Countries country, String name, Boolean capital, Double latitude, Double longitude, Integer population) {
        this();
        this.name = name;
        this.country = country;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;


    }
    public Countries getCountry() {
        return country;
    }
    public void setCountry(Countries country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getCapital() {
        return capital;
    }
    public void setCapital(Boolean capital) {
        this.capital = capital;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getPopulation() {
        return population;
    }
    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}