package org.example.model;

import java.util.Objects;

/**
 * Reprezintă un nod (vârf) într-un graf.
 * Nodul este identificat printr-un ID unic de tip int și poate avea o etichetă (label).
 *
 * Clasa este compatibilă cu biblioteci de serializare precum Jackson sau Gson.
 */
public class Node {
    private int id;         // ID-ul nodului
    private String label;   // Eticheta (numele) nodului

    /**
     * Constructor fără parametri (necesar pentru Jackson).
     */
    public Node() {}

    /**
     * Constructor care inițializează un nod cu un ID specific.
     * @param id ID-ul nodului
     */
    public Node(int id) {
        this.id = id;
        this.label = "Node_" + id; // Etichetă implicită
    }

    /**
     * Constructor care inițializează un nod cu ID și etichetă.
     * @param id ID-ul nodului
     * @param label Eticheta nodului
     */
    public Node(int id, String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * Returnează ID-ul nodului.
     * @return ID-ul
     */
    public int getId() {
        return id;
    }

    /**
     * Setează ID-ul nodului (folosit de Jackson la deserializare).
     * @param id ID-ul nou
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returnează eticheta (label) asociată nodului.
     * @return Eticheta nodului
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setează eticheta (label) nodului.
     * @param label Eticheta nouă
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Verifică egalitatea între două noduri pe baza ID-ului.
     * @param o Obiectul comparat
     * @return true dacă ID-urile sunt egale
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    /**
     * Returnează codul hash bazat pe ID.
     * @return hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returnează o reprezentare textuală a nodului.
     * @return "Node(ID)"
     */
    @Override
    public String toString() {
        return "Node(" + id + ")";
    }
}
