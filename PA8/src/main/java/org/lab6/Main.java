package org.lab6;


import jakarta.persistence.EntityManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = EntityManagerSingleton.getEntityManagerFactory().createEntityManager();
        try {
            CountryRepository countryRepository = new CountryRepository();
            ContinentRepository continentRepository = new ContinentRepository();

            Continent continent = new Continent();
            continent.setName("Europe");
            continent.setCode("EU");
            em.getTransaction().begin();
            em.persist(continent);
            em.getTransaction().commit();

            Country country = new Country();
            country.setName("Romania");
            country.setCode("RO");
            em.getTransaction().begin();
            em.persist(country);
            em.getTransaction().commit();

            List<Country> countries = countryRepository.findByName("Romania");
            for (Country c : countries) {
                System.out.println("Country: " + c.getName() + ", Code: " + c.getCode());
            }

            List<Continent> continents = continentRepository.findByName("Europe");
            for (Continent c : continents) {
                System.out.println("Continent: " + c.getName() + ", Code: " + c.getCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            EntityManagerSingleton.close();
        }
    }
}
