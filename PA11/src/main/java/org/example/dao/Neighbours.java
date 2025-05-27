package org.example.dao;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "neighbours")
public class Neighbours implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Countries country;

    @ManyToOne
    private Countries neighbour;


    public Neighbours() {
       this.country = new  Countries();
       this.neighbour = new  Countries();

    }

    public Neighbours(Countries country, Countries neighbour) {
        this();
        this.country = country;
        this.neighbour = neighbour;
    }


public Long getId()
{
        return id;
}

public void setId(Long id) {
        this.id = id;
}
    public Long getCountry() {
        return country.getId();
    }
    public void setCountry(Countries name) {
        this.country= name;
    }
    public Long getNeighbour() {
        return neighbour.getId();
    }
    public void setNeighbour(Countries name) {
        this.neighbour= name;
    }

}