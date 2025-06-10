package org.example.algorithms;

/**
 * Interfata generica pentru orice algoritm de decompozitie a grafurilor.
 * Permite definirea unei structuri unitare indiferent de ce tip de rezultat intoarce algoritmul.
 *
 * @param <T> Tipul rezultatului returnat (ex: lista de arbori, liste de noduri, seturi etc.)
 */
public interface GraphDecomposition<T> {

    /**
     * Executa algoritmul de decompozitie pe graful dat (graful este transmis de obicei prin constructor).
     */
    void run();

    /**
     * Returneaza o descriere simpla a algoritmului.
     * @return Numele sau descrierea tipului de decompozitie.
     */
    String getDescription();

    /**
     * Returneaza rezultatul obtinut in urma executiei algoritmului.
     * Tipul exact depinde de implementare (generic).
     * @return Structura de date rezultata.
     */
    T getResult();
}
