package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reprezinta o muchie intre doua noduri ale grafului.
 * Muchia este ponderata si poate fi folosita atat in graf orientat, cat si neorientat.
 * Clasa este compatibila cu libraria Jackson pentru serializare si deserializare.
 */
public class Edge {
    private Node from;
    private Node to;
    private int weight;

    /**
     * Constructor complet folosit de Jackson la deserializare JSON.
     * @param from Nodul sursa
     * @param to Nodul destinatie
     * @param weight Greutatea muchiei
     */
    @JsonCreator
    public Edge(@JsonProperty("from") Node from,
                @JsonProperty("to") Node to,
                @JsonProperty("weight") int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * Constructor gol (necesar pentru Jackson in modul clasic cu setteri).
     */
    public Edge() {}

    public Node getFrom() { return from; }
    public void setFrom(Node from) { this.from = from; }

    public Node getTo() { return to; }
    public void setTo(Node to) { this.to = to; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    /**
     * Compara doua muchii indiferent de directie.
     * @param o Obiectul comparat
     * @return true daca muchiile au aceleasi capete (in orice ordine)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge e = (Edge) o;
        return (from.equals(e.from) && to.equals(e.to)) ||
                (from.equals(e.to) && to.equals(e.from));
    }

    /**
     * Cod hash simetric (pentru graf neorientat).
     * @return valoarea hash
     */
    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }

    /**
     * Reprezentare textuala a muchiei.
     */
    @Override
    public String toString() {
        return "(Edge(" + from.getId() + "-" + to.getId() + "))";
    }
}
