package org.lab6;

public class Country {

    private int id;
    private String name;
    private String code;
    private int continentId;

    public Country(int id, String name, String code, int continentId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.continentId = continentId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getContinentId() {
        return continentId;
    }
}
