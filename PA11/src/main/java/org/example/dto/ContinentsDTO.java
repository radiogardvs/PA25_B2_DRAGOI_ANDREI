package org.example.dto;

import java.util.ArrayList;
import java.util.List;

public class ContinentsDTO {
    private Long id;
    private String name;
    private List<String> countries = new ArrayList<>();

    public ContinentsDTO() {
    }

    public ContinentsDTO(Long id, String name, List<String> countryNames) {
        this.id = id;
        this.name = name;
        if (countryNames != null) {
            this.countries.addAll(countryNames);
        }
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
    public List<String> getCountries() {
        return countries;
    }
    public void SetCountries(List<String>countries) {
        this.countries = countries;
    }

}
