package com.danielme.springsecuritybasic.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.danielme.springsecuritybasic.model.Country;

@Service
public class CountryService {

    private static List<Country> countries;

    public CountryService() {
        countries = new LinkedList<>();
        countries.add(new Country(1L, "Spain", 49067981));
        countries.add(new Country(2L, "Mexico", 130497248));
    }

    public Optional<Country> findById(Long id) {
        return countries.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        findById(id).ifPresent(c -> countries.remove(c));
    }

}