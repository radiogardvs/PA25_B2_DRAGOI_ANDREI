package org.example.dto;


import org.example.dao.Cities;

import java.util.ArrayList;
import java.util.List;

public class CountriesDTO {
    private Long id;
    private String name;
    private String code;
    private Long continentId;
    private List<String> cities = new ArrayList<>();
    private Long colorId;

    public CountriesDTO() {
        this.cities = new ArrayList<>();
    }


    public CountriesDTO(Long id, String name, String code, Long continentId, List<String> cities, Long color_id) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.continentId = continentId;
        this.cities = (cities != null) ? new ArrayList<>(cities) : new ArrayList<>();
        this.colorId = color_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getColorId() {
        return colorId;
    }
    public void setColorId(Long colorId) {
        this.colorId = colorId;
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
    public Long getContinentId() {
        return continentId;
    }
    public void setContinentId(Long continentId) {
        this.continentId = continentId;
    }
    public List<String> getCities() {
        return cities;

    }
    public void setCities(List<String> cities) {
        this.cities = cities;
    }


}
