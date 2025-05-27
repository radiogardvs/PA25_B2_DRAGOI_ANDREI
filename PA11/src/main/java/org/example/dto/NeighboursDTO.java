package org.example.dto;



public class NeighboursDTO {
    private Long id;
    private Long countryId;
    private Long neighbourId;


    public NeighboursDTO() {
    }

    public NeighboursDTO(Long id, Long countryId, Long neighbourId) {
        this.id = id;
        this.countryId = countryId;
        this.neighbourId = neighbourId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getCountryId() {
        return countryId;

    }

    public Long getNeighbourId() {
        return neighbourId;

    }

}
