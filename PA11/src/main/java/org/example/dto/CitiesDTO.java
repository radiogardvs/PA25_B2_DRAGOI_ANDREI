package org.example.dto;

public class CitiesDTO {
    private Long id;
    private Long countryId;
    private String name;
    private Boolean capital;
    private Double latitude;
    private Double longitude;
    private Integer population;


    public CitiesDTO() {
    }

    public CitiesDTO(Long id, Long countryId, String name, Boolean capital, Double latitude, Double longitude, Integer population) {
this.id = id;
this.countryId = countryId;
this.name = name;
this.capital = capital;

this.latitude = latitude;
this.longitude = longitude;
this.population = population;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Long getCountryId() {
        return countryId;

    }
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }



}
