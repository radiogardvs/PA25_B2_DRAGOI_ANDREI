package org.lab6;

public class Main {

    public static void main(String[] args) {
        ContinentDAO continentDAO = new ContinentDAO();
        CountryDAO countryDAO = new CountryDAO();

        continentDAO.addContinent("Europe");

        Continent europe = continentDAO.getContinentByName("Europe");
        if (europe != null) {
            System.out.println("Continentul cu id-ul " + europe.getId() + " a fost gasit!");
            countryDAO.addCountry("Germany", "DEU", europe.getId());
        } else {
            System.out.println("Nu am reusit sa obtinem continentul 'Europe' din baza de date.");
        }

        Country germany = countryDAO.getCountryById(1);
        if (germany != null) {
            System.out.println("Tara adaugata: " + germany.getName() + " cu codul " + germany.getCode());
        } else {
            System.out.println("Nu am reusit sa obtinem tara cu ID-ul 1.");
        }
    }
}
