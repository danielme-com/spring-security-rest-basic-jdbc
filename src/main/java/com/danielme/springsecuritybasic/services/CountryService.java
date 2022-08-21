package com.danielme.springsecuritybasic.services;

import com.danielme.springsecuritybasic.model.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final List<Country> countries;

    public CountryService() {
        countries = new ArrayList<>();
        countries.add(new Country(1L, "Spain", 49067981));
        countries.add(new Country(2L, "Mexico", 130497248));
    }

    public Optional<Country> findById(Long id) {
        return countries.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        findById(id).ifPresent(countries::remove);
    }

}