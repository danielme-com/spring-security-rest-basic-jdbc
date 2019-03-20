package com.danielme.springsecuritybasic.model;

public class Country {

    private Long id;

    private String name;

    private Integer population;

    public Country() {
        super();
    }

    public Country(Long id, String name, Integer population) {
        super();
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPopulation() {
        return population;
    }

}
